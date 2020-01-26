# challenge-api-image
A challenge to build an API with springboot and mongodb


#Development environment (infra)
-Linux: Xubuntu 18.04
-OpenJDK: 8
-Eclipse: Version: 2019-12 (4.14.0)
-Docker: 19.03.5
-docker-compose: version 1.17.1
-MongoDB: 4.2.2 (Under docker)
-openjdk: 8-alpine (Under docker)




# Run dev
$ ./mvnw spring-boot:run

# Run tests
$ ./mvnw test




# Runing with Docker


# 1 - Build Springboot
$ ./mvnw package -DskipTests


# 2 - Docker run
$ sudo docker-compose up -d


# 3 - Docker containers production
Mongo: http://localhost:27017
Mongo Express: http://localhost:8081/
Challenge API: https://localhost (port 80 redirect to 443)
Swagger: https://localhost/swagger-ui.html
Api-docs: https://localhost/v2/api-docs


# 4 - Docker stop
$ sudo docker-compose down


# 5 - Docker remove challenge package to another new deploy
$ sudo docker image rm challengeapiimage_springboot




