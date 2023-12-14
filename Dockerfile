FROM eclipse-temurin:17-alpine

EXPOSE 8080

COPY target/PaymentService-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]