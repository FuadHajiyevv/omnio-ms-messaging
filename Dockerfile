FROM eclipse-temurin:17.0.8_7-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM eclipse-temurin:17.0.8_7-jre-alpine
ENV JAR_FILE=msmessaging-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
EXPOSE 8080
COPY --from=build /app/build/libs/$JAR_FILE /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
