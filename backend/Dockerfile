FROM eclipse-temurin:17-jre
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java","-jar","/app/app.jar"]