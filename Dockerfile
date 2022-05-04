#Build Stage
FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/src/pom.xml clean package

#Run Stage
FROM openjdk:17-oracle
COPY --from=build /home/app/target/drone-0.0.1.jar /usr/local/lib/drone.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/usr/local/lib/drone.jar"]