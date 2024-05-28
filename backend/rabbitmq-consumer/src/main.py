from azure.storage.blob import BlobServiceClient
from azure.core.exceptions import ResourceNotFoundError
import time
from settings import Settings
import pika
import logging
from azurebatchload import Utils
import os

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')


class FileDeletionConsumer:
    def __init__(self):
        self.settings = Settings()
        self.blob_service_client = (BlobServiceClient
                                    .from_connection_string(self.settings.azure['storage_connection_string']))
        self.container_name = self.settings.azure['container_name']
        self.rabbitmq = {
            'host': self.settings.rabbitmq['host'],
            'username': self.settings.rabbitmq['username'],
            'password': self.settings.rabbitmq['password'],
            'queue': self.settings.rabbitmq['queue'],
            'exchange': self.settings.rabbitmq['exchange']
        }
        self.channel = self.setup_rabbitmq()

    def setup_rabbitmq(self):
        credentials = pika.PlainCredentials(self.rabbitmq['username'], self.rabbitmq['password'])
        parameters = pika.ConnectionParameters(self.rabbitmq['host'], credentials=credentials)
        connection_attempts = 5
        time.sleep(15)
        for i in range(connection_attempts):
            try:
                logging.info(f"Connecting to RabbitMQ Attempt: {i + 1}")
                connection = pika.BlockingConnection(parameters)
                channel = connection.channel()
                channel.queue_declare(queue=self.rabbitmq['queue'], durable=True)
                return channel
            except pika.exceptions.AMQPConnectionError as e:
                logging.error(f"Error connecting to RabbitMQ: {e}")
                time.sleep(5)
        raise Exception(f"Failed to connect to RabbitMQ after {connection_attempts} attempts")

    def callback(self, ch, method, properties, body):
        try:
            # Assuming the body contains the message in the format: type,mediaId,userId
            type_name, media_id = body.decode('utf-8').split(',')
            logging.info(f"Received type: {type_name}, media_id: {media_id}")

            # Get container client
            container_client = self.blob_service_client.get_container_client(self.container_name)
            prefix = f"{type_name}/{media_id}"

            # List blobs with the specified prefix
            blob_list = container_client.list_blobs(name_starts_with=prefix)

            found_blob = None
            for blob in blob_list:
                root, ext = os.path.splitext(blob.name)
                if root == prefix:
                    logging.info(f"Found blob with extension: {ext}")
                    found_blob = blob
                    break

            if found_blob:
                blob_client = container_client.get_blob_client(found_blob.name)
                try:
                    # Perform the delete operation
                    blob_client.delete_blob(delete_snapshots='only')
                    logging.info(f"Deleted file: {found_blob.name}")
                    return
                except ResourceNotFoundError:
                    logging.warning(f"File not found: {found_blob.name}")
            else:
                logging.warning(f"No file found for media_id: {media_id}")

        except ValueError as e:
            logging.error(f"Error decoding message: {e}")
        except Exception as e:
            logging.error(f"Unexpected error: {e}")

    def delete_blob(self, file_name):
        try:
            container_client = self.blob_service_client.get_container_client(self.container_name)
            blob_client = container_client.get_blob_client(file_name)
            blob_client.delete_blob(file_name)
            logging.info(f"Deleted file: {file_name}")
        except Exception as e:
            logging.error(f"Error deleting file: {e}")

    def consume_messages(self):
        self.channel.basic_consume(queue=self.rabbitmq['queue'], on_message_callback=self.callback, auto_ack=True)
        logging.info("### Waiting for messages. ###")
        self.channel.start_consuming()


if __name__ == "__main__":
    # Create consumer
    consumer = FileDeletionConsumer()
    consumer.consume_messages()
