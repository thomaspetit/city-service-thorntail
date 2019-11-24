# Thorntail Sample

This code example demonstrates the key features and strenghts of Thorntail. Got some improvements in mind?
Create a pull request ;)

## Work in progress :muscle:
* Log an issue on OpenAPI Generator to fix response return type: https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator/src/main/resources/JavaJaxRS/spec/apiInterface.mustache ⟶ Create a pull request
* Add FlyWay
* SpanID not returned in case an invalid request (incorrect url) is returned

## Features :sparkles:
* Contract first OpenAPI generation (framework: OpenAPI Maven Codegen)
* Architecture testing to validate package boundaries (framework: ArchUnit)
* JPA repository interface (framework: DeltaSpike)
* Mapping between layer boundaries (framework: MapStruct)
* Configuration Injection
* Rest service consumption
* SpanID used for apiError handling
+
WARNING: Adapter layer is currently marked as `@RequestScope`

## Getting started :running:
To start the application locally you can either choose different methods.

Properties should be configured using the project-defaults.yml file to ensure we can cleanly override them.
To ensure property loading order (empty|default) we need to define properties under 'thorntail.*'.
Check https://access.redhat.com/documentation/en-us/red_hat_openshift_application_runtimes/1/html/thorntail_runtime_guide/using-thorntail-maven-plugin_wf-swarm[this post] for more information.

### Intellij
1. 'Add new configuration > Application'
2. Select `org.wildfly.swarm.runner.Runner` class
3. If required set 'Environment Variables' for example `thorntail.properties.zipcode-service.url=http://localhost:8080`

The runner class will automatically download maven dependencies using a jboss and apache repository. This doesn't work when behind a proxy. Add a thorntail runner property file to the project if you want to run it behind a proxy.

### Using the 'uberjar'
Simply run this command from the project root folder:
```
java -Xmx256M -Dthorntail.properties.zipcode-service.url=http://localhost:8080 -jar target/cityservice-thorntail.jar
```

### Maven
You can also easily run it using this maven command:
```
   mvn thorntail:run -Dthorntail.properties.zipcode-service.url=http://localhost:8080
```

## Testing :eyes:
### Endpoints

* A rest service is exposed that you can test (HTTP GET) which should return a list of cities for the country that you specified.

  ⟶ http://localhost:8080/countries/BE/cities

* Metrics are also provided (Prometheus). Navigate to following URL to consume this information

  ⟶ http://localhost:8080/metrics

* Application health status is available on a dedicated endpoint. This can be extended with custom applicative health checks.

  ⟶ http://localhost:8080/health

* REST contract defined by service can be consulted on dedicated endpoint. Useful for service catalog in the future but can be viewed in editor.swagger.io for now.

  ⟶ http://localhost:8080/openapi

=== Exception handling

We've embedded apiError handling in this service so that the consumer does not get any stacktraces

* In case the user did not specify the correct input value we throw a validation error:

  ⟶ http://localhost:8080/countries/BEL/cities

* If an apiError occurs, like a null pointer apiError, we catch it and throw an internal server error to the consumer.

  ⟶ http://localhost:8080/countries/NULL/cities

## Deployment :rocket:
Make sure the artifact is generated first ('mvn install'). For this dockerfile the uberjar is used

1. Build the image:
   `docker build -t city-service .`
2. Run the image:
   `docker run -it -m 256m -p 8080:8080 -e ZIPCODE_SERVICE_URL="http://localhost:8080" city-service`

NOTE: As you probably already see by now parameters are not passed dynamically (yet) but we rather use environment variables

## Monitoring :scream:

### Docker
Jaeger can be started via Docker as follows:

```
docker run \
    --rm \
    -p5775:5775/udp \
    -p6831:6831/udp \
    -p6832:6832/udp \
    -p5778:5778 \
    -p16686:16686 \
    -p14268:14268 \
    --name=jaeger \
    jaegertracing/all-in-one:latest
```
### Windows
Or, if you do not have docker available on your machine you can also start it using the provided binary.
Download it on: https://www.jaegertracing.io/download/#binaries

Execute jaeger in cmd:
```
PATH_HERE_TO_BINARY_HERE/jaeger-all-in-one.exe
```

### Run

Jaeger will be available at http://localhost:16686

Whenever an operation is called you'll see in the logs that span id is communicated with Jaeger:

```
2019-09-08 21:46:17,909 INFO  [io.jaegertracing.internal.reporters.LoggingReporter] (default task-1) Span reported: e4b3509a94e1ffc6:e4b3509a94e1ffc6:0:1 - GET:CountriesApi.findCitiesByCountryCode
```

You can search in jaeger under the service name 'template-service'. Each time a service call is done on one of the endpoints you should see a trace in Jaeger.