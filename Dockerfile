FROM openjdk:8-jdk-alpine
ARG JAR_FILE=letsgongbu-0.0.1-SNAPSHOT.jar
COPY ./build/libs/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]