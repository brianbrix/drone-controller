#Build Stage
FROM maven:3.8.3-openjdk-17 AS builder
#WORKDIR /app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


#Run Stage
FROM openjdk:17-oracle
WORKDIR /app
RUN #cd /home/app/
RUN ls
COPY --from=builder /home/app/target/drone-0.0.1.jar /app/drone.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar", "/app/drone.jar"]

