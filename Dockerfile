FROM openjdk:17
EXPOSE 8084
ADD target/passive-microservice-0.0.1-SNAPSHOT.jar passive-microservice.jar
ENTRYPOINT ["java","-jar","/passive-microservice.jar"]