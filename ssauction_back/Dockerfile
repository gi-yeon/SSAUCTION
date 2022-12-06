FROM openjdk:11-jdk

LABEL maintainer="giyeon3145@gmail.com"

EXPOSE 8080

ARG JAR_FILE=build/libs/*.war

COPY ${JAR_FILE} app.war

ENTRYPOINT ["java","-jar","/app.war"]

