FROM maven:3.8.7-eclipse-temurin-18 AS MAVEN_BUILD
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline

COPY src /build/src/
RUN mvn package -DskipTests

FROM eclipse-temurin:17.0.8-jdk-slim
ENV TZ=America/Sao_Paulo

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar

RUN useradd -ms /bin/bash appuser
USER appuser

EXPOSE 8081
HEALTHCHECK --interval=30s --timeout=10s --start-period=15s --retries=3 \
  CMD curl --fail http://localhost:8081/actuator/health || exit 1

ENTRYPOINT ["java", "--add-opens", "java.base/java.io=ALL-UNNAMED", "-jar", "/app/app.jar"]