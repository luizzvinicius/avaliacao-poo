FROM maven:3.9-amazoncorretto-21-alpine AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine-3.21

ENV db_url: "jdbc:postgresql://postgres:5432/avaliacao-poo"
ENV user="postgres"
ENV password="postgres123"

COPY --from=build app/target/avaliacao-poo-1.0-SNAPSHOT.jar app.jar
COPY ./src/tables.sql /src/tables.sql

EXPOSE 5432

CMD ["java", "-jar", "app.jar"]