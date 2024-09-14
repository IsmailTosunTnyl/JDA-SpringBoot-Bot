# Use the official Maven image to build the application
FROM maven:3.8.8 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and other Maven configuration files to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Create a directory for external libraries and download the necessary JAR
RUN mkdir -p /opt/app/libs && \
    curl -L -o /opt/app/libs/youtube-common-1.7.2.jar https://github.com/lavalink-devs/youtube-source/releases/download/1.7.2/youtube-common-1.7.2.jar

# Install the external library into the local Maven repository
RUN mvn install:install-file -Dfile=/opt/app/libs/youtube-common-1.7.2.jar -DgroupId=dev.lavalink -DartifactId=youtube-common -Dversion=1.7.2 -Dpackaging=jar

# Build the application and copy dependencies, skipping tests
RUN mvn clean install -DskipTests && mvn dependency:copy-dependencies -DskipTests

# Use a smaller JDK image to run the application
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/discordbotultimate-0.0.1-SNAPSHOT.jar .

# Copy the dependencies from the build stage
COPY --from=build /app/target/dependency/* ./lib/

# Expose the application port
EXPOSE 8081

# Command to run the application
CMD ["java", "-jar", "discordbotultimate-0.0.1-SNAPSHOT.jar"]
