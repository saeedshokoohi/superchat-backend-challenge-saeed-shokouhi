##  Superchat backend challenge - Basic CRM

This project is simulating a basic CRM app having current features :


##  Features
- Adding contact.
- List of current contacts.
- Sending message to contact.
- Receiving message from the external resources by providing a webhook.
- Handling placeholders in sending message content.
- List of messages per contact as a conversation.

## Assumptions
- Authentication and authorization are not included for simplicity.
- Sending messages to external platforms is not implemented, but just relevant interfaces are specified, and mock implementations have been done.
- I assume that this is a single-user application.
- Contact from external platforms must have "platform" and "clientId" to be identified and sent a message to them.
- Placeholders which is currently supported have the following formats : ${contactname} ${bitcoinprice}
## Frameworks and design decisions

### Back-end side

- **Springboot** (java) as framework
- **Spring Data/Hibernate** for persistence layer
- **PostgresSql** as database
- **Apache Kafka** as message broker
- **Swagger** for api documentation
- **Liquibase** for handling database change logs


### Test
- **JUnit 5**
- **Mockito**


### Packaging & build tools

- **Maven**
- **Docker & Docker-compose**

### Source control
- **GIT**

## System Design

The fallowing diagram shows a top level system design and the way every part of system are connecting.


![System Design](https://github.com/saeedshokoohi/superchat-backend-challenge-saeed-shokouhi/blob/c2c42dc4a239ed4cd7ff00724c05c3854d992b8b/docs/system-design.PNG)

# How to run
This project is fully dockerized and on any OS where Docker is installed, the following commands can be used to run this project:

`docker-compose up --build`



**Api docs would be available after project startup :**

- swagger docs : [http://localhost:8282/swagger-ui/](http://localhost:8282/swagger-ui/ "http://localhost:3000/swagger-ui/")


