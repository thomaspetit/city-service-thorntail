package io.example.service.city.adapter.zipcode.mapper;

import io.example.service.city.service.model.CityModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import zipcode.api.model.City;
import zipcode.api.model.Place;

@Mapper(componentModel = "cdi")
public interface ZipCodeMapper {
    @Mapping(target = "zipCode", source = "postCode")
    @Mapping(target = "countryCode", source = "countryAbbreviation")
    @Mapping(target = "name", source = "places", qualifiedByName = "placeName")
    CityModel map(City city);

    @Named("placeName")
    default String mapPlaceName(List<Place> places) {
        return places != null && !places.isEmpty() ? places.get(0).getPlaceName() : null;
    }
}