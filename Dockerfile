FROM maven:3.8.8
# Set the working directory
WORKDIR /opt/app

# Copy the project files into the working directory
COPY . .

# Download the external JAR dependency
RUN mkdir -p /opt/app/libs && \
    curl -L -o /opt/app/libs/youtube-common-1.7.2.jar https://github.com/lavalink-devs/youtube-source/releases/download/1.7.2/youtube-common-1.7.2.jar

# Install the external JAR dependency into the local Maven repository
RUN mvn install:install-file -Dfile=/opt/app/libs/youtube-common-1.7.2.jar -DgroupId=dev.lavalink -DartifactId=youtube-common -Dversion=1.7.2 -Dpackaging=jar

# Expose the port your application listens on
EXPOSE 8081

# Define the entry point to run the application using mvn exec:java
ENTRYPOINT ["mvn", "exec:java", "-Dexec.mainClass=net.ismailtosun.discordbotultimate.DiscordbotultimateApplication"]