#FROM maven:3.8.1-openjdk-16 as maven
#COPY ./pom.xml ./pom.xml
#RUN mvn dependency:go-offline -B
#COPY ./src ./src
#RUN mvn package -DskipTests

FROM openjdk:16-jdk-alpine
VOLUME /boilerplate
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]