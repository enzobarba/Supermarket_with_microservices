FROM openjdk:21
WORKDIR /app
COPY target/productService-0.0.1-SNAPSHOT.jar /app
EXPOSE 9092
CMD ["java", "-jar", "productService-0.0.1-SNAPSHOT.jar" ]