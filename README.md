# challenge-api-image
A challenge to build an API with springboot and mongodb


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
Swagger: https://localhost/swagger-ui.html
Challenge API: https://localhost (port 80 redirect to 443)


# 4 - Docker stop
$ sudo docker-compose down


# 5 - Docker remove challenge package to another new deploy
$ sudo docker image rm challengeapiimage_springboot




