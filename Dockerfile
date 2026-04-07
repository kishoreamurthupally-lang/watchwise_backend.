# Use Java 17 (required for your project)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/watchwise-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Railway / Render will override)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
