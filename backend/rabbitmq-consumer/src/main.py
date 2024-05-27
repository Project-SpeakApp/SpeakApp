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

    def callback(self, ch, method, properties, body):
        container_client = self.blob_service_client.get_container_client(self.container_name)
        blob_client = container_client.get_blob_client(body)
        try:
            # blob_client.delete_blob(body)
            print(f"Deleted file: {body}")
        except ResourceNotFoundError:
            print(f"File {body} not found")

    def consume_messages(self):
        connection_parameters = pika.ConnectionParameters(host=self.rabbitmq['host'])
        connection = pika.BlockingConnection(connection_parameters)
        channel = connection.channel()

        # Create queue
        channel.queue_declare(queue=self.rabbitmq['queue'])
        channel.basic_consume(queue=self.rabbitmq['queue'], on_message_callback=self.callback, auto_ack=True)
        print("### Waiting for messages. ###")
        channel.start_consuming()


if __name__ == "__main__":
    # Create consumer
    consumer = FileDeletionConsumer()
    consumer.consume_messages()

