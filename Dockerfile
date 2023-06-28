FROM gradle:7.4.1-jdk17 as builder

ARG ELASTICSEARCH_MIGRATOR_VERSION=1.0.0
WORKDIR /build
COPY build/libs/elasticsearch-migrator-${ELASTICSEARCH_MIGRATOR_VERSION}.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-focal
WORKDIR /app
ARG APP_USER=1000
ARG APP_GROUP=1000
COPY --from=builder --chown=${APP_USER}:${APP_GROUP} build/dependencies/ ./
COPY --from=builder --chown=${APP_USER}:${APP_GROUP} build/snapshot-dependencies/ ./
COPY --from=builder --chown=${APP_USER}:${APP_GROUP} build/spring-boot-loader/ ./
COPY --from=builder --chown=${APP_USER}:${APP_GROUP} build/application/ ./
COPY --chown=${APP_USER}:${APP_GROUP} src/docker/entrypoint.sh entrypoint.sh
USER ${APP_USER}:${APP_GROUP}
ENV PORT=8080
ENTRYPOINT ["./entrypoint.sh"]