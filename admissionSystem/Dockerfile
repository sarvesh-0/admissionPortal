# Use a Maven image to build the JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .  # Copy everything into /app
RUN mvn clean package -DskipTests

# Use a smaller JDK image to run the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
