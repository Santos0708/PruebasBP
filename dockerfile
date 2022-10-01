FROM openjdk:18-jdk-alpine3.14
COPY ./target/TestPB-1.0.0.jar /tmp/TestPB-1.0.0.jar
WORKDIR /tmp
CMD ["java" , "-jar","TestPB-1.0.0.jar"]