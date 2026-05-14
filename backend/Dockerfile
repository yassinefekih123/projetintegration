FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copy only what we need to build
COPY backend/pom.xml backend/
COPY backend/src backend/src

RUN mvn -f backend/pom.xml -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/backend/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
