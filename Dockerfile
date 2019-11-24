FROM openjdk:jre-alpine

ENV JAVA_OPTS="-Xmx256M"
ENV ZIPCODE_SERVICE_URL=""
ENV JAEGER_HOST=""

ADD target/*-thorntail.jar /opt/service.jar

EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar /opt/service.jar \
    -Dthorntail.properties.zipcode-service.url=$ZIPCODE_SERVICE_URL \
    -Dthorntail.jaeger.agent-host=$JAEGER_HOST