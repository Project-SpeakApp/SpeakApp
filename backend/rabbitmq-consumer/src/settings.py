import configparser


class Settings:
    def __init__(self, config_file='resources/application.conf'):
        self.config = configparser.ConfigParser()
        self.config.read(config_file)
        self.rabbitmq = {
            'host': self.config.get('rabbitmq', 'host'),
            'username': self.config.get('rabbitmq', 'username'),
            'password': self.config.get('rabbitmq', 'password'),
            'queue': self.config.get('rabbitmq', 'queue_name'),
            'exchange': self.config.get('rabbitmq', 'delayed_exchange')
        }
        self.azure = {
            'container_name': self.config.get('azure', 'storage_container_name'),
            'storage_connection_string': self.config.get('azure', 'storage_connection_string')
        }