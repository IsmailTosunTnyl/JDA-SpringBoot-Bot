FROM maven:3.8.8 AS builder

# Set your preferred working directory
WORKDIR /opt/app

# Copy everything from your current local directory into the working directory of the image
COPY . .

# Download the external JAR dependency
RUN mkdir -p /opt/app/libs && \
    curl -L -o /opt/app/libs/youtube-common-1.7.2.jar https://github.com/lavalink-devs/youtube-source/releases/download/1.7.2/youtube-common-1.7.2.jar

# Compile, test and package
RUN mvn clean package -Dmaven.test.skip=true

###

# Second stage: final image containing only JAR files

# The base image for the final result can be as small as Alpine with a JRE
FROM openjdk:17-jdk-alpine

# Once again, the current directory as seen by your image
WORKDIR /opt/app

# Get artifacts from the previous stage and copy them to the new image.
COPY --from=builder /opt/app/target/discordbotultimate-0.0.1-SNAPSHOT.jar ./

# Copy the external JAR dependency to the final image
COPY --from=builder /opt/app/libs/youtube-common-1.7.2.jar /opt/app/libs/youtube-common-1.7.2.jar

# Expose whichever port you use in the Spring application
EXPOSE 8081

# Define the application to run when the Docker container is created.
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/opt/app/discordbotultimate-0.0.1-SNAPSHOT.jar"]
