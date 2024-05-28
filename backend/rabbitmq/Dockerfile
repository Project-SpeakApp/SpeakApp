# Use an Alpine Linux base image
FROM alpine:3.19 AS downloader

# Install curl
RUN apk add --no-cache curl

# Download the RabbitMQ plugin
RUN curl -LJO https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v3.13.0/rabbitmq_delayed_message_exchange-3.13.0.ez

# Stage 2: Build the RabbitMQ image
FROM rabbitmq:3.13-management-alpine

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the downloaded plugin from the downloader stage
COPY --from=downloader /rabbitmq_delayed_message_exchange-3.13.0.ez /plugins/

# Enable the plugin and start RabbitMQ
CMD rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange && rabbitmq-server
