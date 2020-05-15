#
# Build stage
#
FROM maven:3.6.3-jdk-11-slim AS build
COPY pet-clinic-data/src /app/pet-clinic-data/src
COPY pet-clinic-web/src /app/pet-clinic-web/src
COPY pet-clinic-data/pom.xml /app/pet-clinic-data
COPY pet-clinic-web/pom.xml /app/pet-clinic-web
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /app/pet-clinic-data/target/pet-clinic-data-0.0.3-SNAPSHOT.jar /usr/local/lib/pet-clinic-data.jar
COPY --from=build /app/pet-clinic-web/target/pet-clinic-web-0.0.3-SNAPSHOT.jar /usr/local/lib/pet-clinic-web.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=mysql", "/usr/local/lib/pet-clinic-web.jar"]