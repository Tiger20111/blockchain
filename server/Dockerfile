FROM openjdk:8
RUN mkdir server
COPY target/server-0.1.0.jar /server/server.jar
WORKDIR /server
RUN chmod u+x server.jar
CMD ["java", "-jar", "-Dspring.config.location = application.properties", "server.jar"]