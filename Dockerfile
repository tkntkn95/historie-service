FROM openjdk:11
EXPOSE 8080
ADD target/historie-service.jar historie-service.jar
ENTRYPOINT ["java","-jar","historie-service.jar"]