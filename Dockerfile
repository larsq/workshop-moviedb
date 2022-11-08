FROM eclipse-temurin:17.0.5_8-jdk
ARG VERSION
ARG JAR_FILE=build/libs/*-${VERSION}.jar
WORKDIR apps
COPY ${JAR_FILE} ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]