# Multi-stage Dockerfile for backend (Spring Boot)
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY backend/pom.xml ./
COPY backend/src ./src
RUN mvn -B -DskipTests package spring-boot:repackage

FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
