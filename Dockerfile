FROM openjdk:8-alpine

MAINTAINER Luciano Lima

RUN apk update && apk add bash

RUN mkdir -p /opt/app

ENV PROJECT_HOME /opt/app

COPY target/challenge.jar $PROJECT_HOME/challenge.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"./challenge.jar"]