from azure.storage.blob import BlobServiceClient
from azure.core.exceptions import ResourceNotFoundError
import time
from settings import Settings
import pika


class FileDeletionConsumer:
    def __init__(self):
        self.settings = Settings()
        self.blob_service_client = (BlobServiceClient
                                    .from_connection_string(self.settings.azure['storage_connection_string']))
        self.container_name = self.settings.azure['storage_container_name']
        self.rabbitmq = {
            'host': self.settings.rabbitmq['host'],
            'user': self.settings.rabbitmq['username'],
            'password': self.settings.rabbitmq['password'],
            'queue': self.settings.rabbitmq['queue_name'],
            'exchange': self.settings.rabbitmq['delayed_exchange']
        }
        self.channel = self.setup_rabbitmq()

    def setup_rabbitmq(self):
        credentials = pika.PlainCredentials(self.rabbitmq['username'], self.rabbitmq['password'])
        parameters = pika.ConnectionParameters(self.rabbitmq['host'], credentials=credentials)
        connection = pika.BlockingConnection(parameters)
        channel = connection.channel()
        channel.queue_declare(queue=self.rabbitmq['queue'])
        return channel

    def callback(self, ch, method, properties, body):
        print("Received message")
        time.sleep(180)
        body = body.decode('utf-8')
        container_client = self.blob_service_client.get_container_client(self.container_name)
        blob_client = container_client.get_blob_client(body)
        try:
            # blob_client.delete_blob(body)
            print(f"Deleted file: {body}")
        except ResourceNotFoundError:
            print(f"File {body} not found")

    def delete_blob(self, file_name):
        try:
            container_client = self.blob_service_client.get_container_client(self.container_name)
            blob_client = container_client.get_blob_client(file_name)
            # blob_client.delete_blob(file_name)
            print(f"Deleted file: {file_name}")

        except Exception as e:
            print(f"Error deleting file: {e}")

    def consume_messages(self):
        self.channel.basic_consume(queue=self.rabbitmq['queue'], on_message_callback=self.callback, auto_ack=True)
        print("### Waiting for messages. ###")
        self.channel.start_consuming()


if __name__ == "__main__":
    # Create consumer
    consumer = FileDeletionConsumer()
    consumer.consume_messages()

