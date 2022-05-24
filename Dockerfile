FROM gradle:7.4.1-jdk11 as builder

WORKDIR /build
COPY build/libs/elasticsearch-migrator*-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=builder build/dependencies/ ./
COPY --from=builder build/snapshot-dependencies/ ./
COPY --from=builder build/spring-boot-loader/ ./
COPY --from=builder build/application/ ./
COPY src/docker/entrypoint.sh entrypoint.sh

RUN ls -al /app/config
ENV PORT=8080
ENTRYPOINT ["./entrypoint.sh"]