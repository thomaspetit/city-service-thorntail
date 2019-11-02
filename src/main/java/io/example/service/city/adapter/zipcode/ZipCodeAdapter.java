package io.example.service.city.adapter.zipcode;

import io.example.service.common.logging.Logged;
import io.example.service.city.adapter.zipcode.mapper.ZipCodeMapper;
import io.example.service.city.service.model.CityModel;
import io.opentracing.contrib.concurrent.TracedExecutorService;
import org.slf4j.Logger;
import zipcode.api.ZipCodesApi;
import zipcode.api.model.City;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@Logged
public class ZipCodeAdapter {

    @Inject
    private TracedExecutorService tracedExecutorService;

    @Inject
    private transient Logger log;

    @Inject
    private ZipCodesApi zipCodesApi;

    @Inject
    private ZipCodeMapper zipCodeMapper;

    public CompletableFuture<CityModel> getZipCodeInfo(String zipCode, String countryCode) {
        log.info("START searching for cities");
        return CompletableFuture.supplyAsync(() -> {
            Response response = zipCodesApi.getZipCodeInfo(countryCode, zipCode);
            if (Response.Status.OK.getStatusCode() != response.getStatus() ) {
                return null;
            }
            final CityModel cityModel = zipCodeMapper.map(response.readEntity(City.class));
            log.info("END searching for cities");
            return cityModel;
        }, tracedExecutorService);
    }
}
