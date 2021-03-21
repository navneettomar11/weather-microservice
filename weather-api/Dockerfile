FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} weather-api-app.jar
ENTRYPOINT ["java","-jar","/weather-api-app.jar"]