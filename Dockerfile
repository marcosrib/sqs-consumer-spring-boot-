FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./


COPY src ./src

EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]


