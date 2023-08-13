# Flight Notification Service

### Flight Notification Service is a microservice that provides real-time flight status updates to subscribers via email. It uses RabbitMQ for message queuing and Spring Mail to send emails to subscribers.

### High Level Design

![image](https://github.com/sedeeman/flight-notification-service/assets/119731054/c500c971-a4f7-4d96-b121-b26db1ec4b54)


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

##### POST http://localhost:3001/api/flight-notification-service/subscribe
Subscribe to flight updates by providing the sample object as shown below. You can customize which status users need to subscribe to, such as arrival, departure, delay, etc.

Request body example:

`{
"email": "selan.dimantha@gmail.com",
"flightNumber": "FL123",
"subscribeToDelay": true,
"subscribeToArrival": true,
"subscribeToDeparture": false
}`

#### Messaging with RabbitMQ
The Flight Notification Service employs RabbitMQ to consume messages generated by the flight service. It listens to a message queue and dispatches emails using an email server. When a flight status is changed by admin users, a message is sent to the RabbitMQ exchange. The Flight Notification Service then listens for this message from the queue and sends email notifications to Subscribers based on their subscription preferences.

### MySQL Database
The Flight Service Microservice uses an MySQL Database You can modify the database connection properties in the application.properties file if you want to switch to a different database.


### Swagger2 Documentation
The API endpoints are documented using Swagger2. To access the documentation, launch the application and navigate to the following URL in your web browser:

[http://localhost:3001/api/swagger-ui/index.html#/](http://localhost:3001/api/swagger-ui/index.html#/)


#### Testing
To run the unit tests for the Flight Notification Service, use the following command:

`./mvnw test`


