# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/fileSharingManagement-0.0.1-SNAPSHOT.jar .

# Run the application with the correct JAR name
ENTRYPOINT ["java", "-jar", "fileSharingManagement-0.0.1-SNAPSHOT.jar"]
