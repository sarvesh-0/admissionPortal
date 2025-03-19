# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the target directory
COPY target/*.jar app.jar

# Expose the application port (Change it if your application runs on a different port)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]