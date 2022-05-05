#Build Stage
FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /app
COPY src /home/app/src
COPY pom.xml /app
RUN mvn clean package


#Run Stage
FROM openjdk:17-oracle
COPY target/drone-0.0.1.jar /usr/local/lib/drone.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar", "/usr/local/lib/drone.jar"]

