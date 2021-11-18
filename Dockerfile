FROM openjdk:11
EXPOSE 8081
ADD target/kasse-service.jar kasse-service.jar
ENTRYPOINT ["java","-jar","kasse-service.jar"]