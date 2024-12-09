# Use a Maven base image to build the project
FROM maven:3.8.6-openjdk-11 as build

# Set working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Run the application in a lightweight JRE image
FROM openjdk:11-jre-slim

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/LibrarySystem-0.0.1-SNAPSHOT.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar", "com.library.Library"]
