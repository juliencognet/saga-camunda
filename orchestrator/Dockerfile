FROM openjdk:8
MAINTAINER julien.cognet@cgi.com
COPY target/orchestrator*.jar /tmp/orchestrator.jar
CMD ["java", "-jar", "/tmp/orchestrator.jar"]
EXPOSE 8090