FROM gradle:7.4.1-jdk17 as builder

ARG ELASTICSEARCH_MIGRATOR_VERSION=1.0.0
WORKDIR /build
COPY build/libs/elasticsearch-migrator-${ELASTICSEARCH_MIGRATOR_VERSION}.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=builder build/dependencies/ ./
COPY --from=builder build/snapshot-dependencies/ ./
COPY --from=builder build/spring-boot-loader/ ./
COPY --from=builder build/application/ ./
COPY src/docker/entrypoint.sh entrypoint.sh
ENV PORT=8080
ENTRYPOINT ["./entrypoint.sh"]