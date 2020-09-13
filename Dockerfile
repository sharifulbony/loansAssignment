FROM openjdk:12-alpine
COPY build/libs/*.jar /app.jar
CMD ["java","-jar","/app.jar"]
