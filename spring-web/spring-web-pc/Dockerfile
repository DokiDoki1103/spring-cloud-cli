FROM java:openjdk-8u111-jre-alpine

WORKDIR /app

COPY ./target/spring-web-pc-0.0.1-SNAPSHOT.jar /app/pc.jar

EXPOSE 8080

CMD ["java", "-jar", "pc.jar"]
