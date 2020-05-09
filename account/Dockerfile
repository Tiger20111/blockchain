FROM openjdk:8
RUN mkdir client
COPY target/account-1.0-SNAPSHOT.jar /client/client.jar
WORKDIR /client
RUN chmod u+x client.jar
CMD ["java", "-jar", "-Dspring.config.location = application.properties", "client.jar"]