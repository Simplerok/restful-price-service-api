FROM openjdk:17-jdk-alpine
COPY target/*.jar price-serv.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","price-serv.jar"]