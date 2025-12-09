FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 7575
ENTRYPOINT ["java", "-jar", "app.jar"]