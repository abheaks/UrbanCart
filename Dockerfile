FROM openjdk:17

# Copy the jar file to the image
COPY build/libs/UrbanCart-0.0.1-SNAPSHOT.jar app.jar

# Use the correct command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
