FROM fabric8/java-alpine-openjdk8-jre

ENV JAVA_OPTIONS=""
ENV AB_ENABLED=jmx_exporter
ENV ZIPCODE_SERVICE_URL=""
ENV JAEGER_AGENT_HOST=""

ADD target/*-thorntail.jar /deployments/app.jar

EXPOSE 8080

# run with user 1001 and be prepared for be running in OpenShift too
RUN adduser -G root --no-create-home --disabled-password 1001 \
  && chown -R 1001 /deployments \
  && chmod -R "g+rwX" /deployments \
  && chown -R 1001:root /deployments
USER 1001

ENTRYPOINT exec java $JAVA_OPTIONS \
    -Djava.net.preferIPv4Stack=true \
    -Djava.net.preferIPv4Addresses=true \
    -jar /deployments/app.jar \
    -Dthorntail.properties.zipcode-service.url=$ZIPCODE_SERVICE_URL \
    -Dthorntail.jaeger.agent-host=$JAEGER_AGENT_HOST