# challenge-api-image
A challenge to build an API with springboot and mongodb



# Run tests
$ ./mvnw test




# Runing with Docker


# 1 - Build Springboot
$ ./mvnw -Pprod package -DskipTests


# 2 - Docker run
$ sudo docker-compose up -d


# 3 - Docker containers production
Mongo: http://localhost:27017
Mongo Express: http://localhost:8081/
Swagger: http://localhost/swagger-ui.html
Challenge API: http://localhost (port 80)


# 4 - Docker stop
$ sudo docker-compose down


# 5 - Docker remove challenge package to another new deploy
$ sudo docker image rm challengeapiimage_springboot




