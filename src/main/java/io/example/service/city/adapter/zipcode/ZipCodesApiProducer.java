package io.example.service.city.adapter.zipcode;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import zipcode.api.ZipCodesApi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.net.URI;

public class ZipCodesApiProducer {
    @Inject
    @ConfigProperty(name = "thorntail.properties.zipcode-service.url")
    String serviceUrl;

    @Inject
    private RestClientBuilder restClientBuilder;

    @Produces
    @ApplicationScoped
    public ZipCodesApi createApi() {
        return restClientBuilder.baseUri(URI.create(serviceUrl)).build(ZipCodesApi.class);
    }
}