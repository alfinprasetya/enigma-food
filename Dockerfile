FROM openjdk:17-jdk-alpine
ADD target/food-0.0.1-SNAPSHOT.jar food.jar
CMD ["java", "-jar", "/food.jar"]