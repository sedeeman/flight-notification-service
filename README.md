# Flight Notification Service

### Flight Notification Service is a microservice that provides real-time flight status updates to subscribers via email. It uses RabbitMQ for message queuing and Spring Mail to send emails to subscribers.

### Getting Started
To run the Flight Notification Service, you need to have RabbitMQ and Java installed on your system. Follow the steps below to get started:

#### Clone the repository:
`git clone https://github.com/sedeeman/flight-notification-service.git`
`cd flight-notification-service`

#### Build the project:
`./mvnw clean package`

#### Run the application:
`java -jar target/flight-notification-service.jar`

### Configuration
Before running the application, make sure to configure the RabbitMQ and email settings in the application.properties file. If you don't need to use your own settings, you can rely on the default configurations provided in the application properties.

#### RabbitMQ Configuration
spring.rabbitmq.host=your_rabbitmq_host
spring.rabbitmq.port=5672
spring.rabbitmq.username=your_rabbitmq_username
spring.rabbitmq.password=your_rabbitmq_password

RabbitMQ Local Env  [http://localhost:15672/#/queues/%2F/email-queue](http://localhost:15672/#/queues/%2F/email-queue)

#### Email Configuration
spring.mail.host=your_smtp_host
spring.mail.port=587
spring.mail.username=your_email_username
spring.mail.password=your_email_password

####  API Endpoints
The Flight Notification Service exposes the following API endpoints:

#### Subscribe to Flight Updates

##### POST http://localhost:3001/api/flights/subscribe
Subscribe to flight updates by providing the sample object as shown below. You can customize which status users need to subscribe to, such as arrival, departure, delay, etc.

Request body example:

`{
"email": "selan.dimantha@gmail.com",
"flightNumber": "FL123",
"subscribeToDelay": true,
"subscribeToArrival": true,
"subscribeToDeparture": false
}`


##### POST http://localhost:3001/api/flights/flight-status
Update flight status and automatically send email notifications for subscribers based on their preference.

Request body example:

`{
"flightNumber":"FL123",
"status":"arrival"
}`


#### Messaging with RabbitMQ
The Flight Notification Service uses RabbitMQ for message queuing to handle flight status updates. When a flight status changes, a message is sent to the RabbitMQ exchange, and subscribers receive email notifications based on their subscription preferences.


#### Testing
To run the unit tests for the Flight Notification Service, use the following command:

`./mvnw test`


