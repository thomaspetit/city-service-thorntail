package io.example.service.city.api;

import io.example.service.common.rest.exceptions.model.ApiError;
import io.example.service.common.rest.exceptions.model.BadRequestException;
import io.example.service.common.rest.exceptions.model.ForbiddenException;
import io.example.service.common.tracing.TracedResponse;
import io.example.service.city.api.mapper.CityMapper;
import io.example.service.city.api.model.City;
import io.example.service.city.api.model.CityList;
import io.example.service.city.service.CityService;

import org.slf4j.Logger;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.core.Response;

@TracedResponse
@ApplicationScoped
public class CountriesController implements CountriesApi {

    @Inject
    private transient Logger log;

    @Inject
    private CityMapper cityMapper;

    @Inject
    private CityService cityService;

    @Override
    public Response findCitiesByCountryCode(
            @Pattern(regexp = "[A-Z]{2}") String countryCode,
            @Pattern(regexp = "^[a-f0-9]{16}|[a-f0-9]{32}$") String xB3TraceId,
            @Pattern(regexp = "^[a-f0-9]{16}$") String xB3SpanId,
            @Min(0) Integer offset,
            @Min(1) @Max(50) Integer limit
    ) {
        List<City> cities;
        switch (countryCode) {
            case "NP": throw new NullPointerException();
            case "BE": cities = cityMapper.map(cityService.getCitiesForCountryParallel(countryCode)); break;
            case "FR": cities = cityMapper.map(cityService.getCitiesForCountrySequential(countryCode)); break;
            case "EX":
                try {
                    cities = cityMapper.map(cityService.throwServiceException());
                } catch (IllegalAccessException e) {
                    throw new ForbiddenException(ApiError.Type.TECHNICAL, e.getMessage());
                }
                break;
            default:
                throw new BadRequestException(ApiError.Type.BUSINESS, "Use Country ISO2 code", "countryCode", countryCode);
        }

        CityList cityList = new CityList();
        cityList.setItems(cities);
        cityList.setCount(cities.size());
        cityList.setTotalCount(cities.size());

        log.info("Retrieved {} cities", cities.size());

        return Response.ok(cityList).build();
    }
}
