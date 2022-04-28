##  Superchat backend challenge - Basic CRM

This project is simulating a basic CRM app having current features :


##  Features
- Adding contact
- List of current contacts
- Sending message to contact
- Receiving message from the external resources by providing a webhook
- List of messages per contact as a conversation


## Frameworks and design decisions

### Back-end side

- **Springboot** (java) as framework
- **Spring Data/Hibernate** for persistence layer
- **PostgresSql** as database
- **Apache Kafka** as message broker
- **Swagger** for api documentation
- **Liquibase** for handling database change logs


### Test
- **Juint 5**
- **Mockito**


### Packaging & build

- **Maven**
- **Npm**
- **Docker & Docker-compose**

### Source control
- **GIT**



# How to run
This project is fully dockerized and on any OS where Docker is installed, the following commands can be used to run this project:

`docker-compose up --build`



**The default urls are :**

- swagger docs : [http://localhost:8282/swagger-ui/](http://localhost:8282/swagger-ui/ "http://localhost:3000/swagger-ui/")


