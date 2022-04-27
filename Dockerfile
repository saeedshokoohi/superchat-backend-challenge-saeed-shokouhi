FROM maven:3.6.3 AS mvn_superchat_challenge
LABEL MAINTAINER="saeed.shokuhi@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn clean -DskipTests=true install

# For Java 11,
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=superchat_challenge.jar

WORKDIR /opt/app

# Copy the mvn_superchat_challenge.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=mvn_superchat_challenge /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 8282
ENTRYPOINT ["java","-jar","superchat_challenge.jar"]