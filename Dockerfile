FROM eclipse-temurin:17
WORKDIR /app
COPY target/UserService-0.0.1-SNAPSHOT.jar /app/UserService.jar
ENTRYPOINT ["java","-jar","/app/UserService.jar"]
EXPOSE 8080
