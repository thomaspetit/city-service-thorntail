FROM openjdk:11-jdk

ENV JAVA_OPTIONS="-Djava.net.preferIPv4Stack=true"
ENV AB_ENABLED=jmx_exporter
ENV ZIPCODE_SERVICE_URL=""
ENV JAEGER_AGENT_HOST=""

ADD target/*-thorntail.jar /app.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTIONS \
    -jar /app.jar \
    -Dthorntail.properties.zipcode-service.url=$ZIPCODE_SERVICE_URL \
    -Dthorntail.jaeger.agent-host=$JAEGER_AGENT_HOST