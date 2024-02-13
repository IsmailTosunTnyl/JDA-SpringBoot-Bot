# First stage: build fat JAR

# Select base image.
# (The "AS builder" gives a name to the stage that we will need later)
# (I think it's better to use a slim image with Maven already installed instead
#  than ./mvnw. Otherwise you could need to give execution rights to your file
#  with instructions like "RUN chmod +x mvnw".)
FROM maven:latest AS builder

# Set your preferred working directory
# (This tells the image what the "current" directory is for the rest of the build)
WORKDIR /opt/app

# Copy everything from you current local directory into the working directory of the image
COPY . .

# Compile, test and package
# (-e gives more information in case of errors)
# (I prefer to also run unit tests at this point. This may not be possible if your tests
#  depend on other technologies that you don't whish to install at this point.)
#RUN mvn -e clean verify
RUN mvn clean verify -Dmaven.test.skip=true

###

# Second stage: final image containing only WAR files

# The base image for the final result can be as small as Alpine with a JRE
FROM openjdk:latest

# Once again, the current directory as seen by your image
WORKDIR /opt/app

# Get artifacts from the previous stage and copy them to the new image.
# (If you are confident the only JAR in "target/" is your package, you could NOT
#  use the full name of the JAR and instead something like "*.jar", to avoid updating
#  the Dockerfile when the version of your project changes.)
COPY --from=builder /opt/app/target/*.jar ./

# Expose whichever port you use in the Spring application
EXPOSE 8081

# Define the application to run when the Docker container is created.
# Either ENTRYPOINT or CMD.
# (Optionally, you could define a file "entrypoint.sh" that can have a more complex
#  startup logic.)
# (Setting "java.security.egd" when running Spring applications is good for security
#  reasons.)
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /opt/app/*.jar