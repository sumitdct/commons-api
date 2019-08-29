# The Docker file
FROM openjdk:8-jdk-alpine
MAINTAINER SUMIT CHOUKSEY "sumitchouksey2315@gmail.com"
ADD /target/commons-api.jar commons-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/commons-api.jar"]
