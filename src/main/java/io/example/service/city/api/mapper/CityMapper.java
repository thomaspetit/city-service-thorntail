package io.example.service.city.api.mapper;


import io.example.service.city.api.model.City;
import io.example.service.city.service.model.CityModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "cdi")
public interface CityMapper {
   @Mapping(source = "name", target="cityName")
   City map(CityModel city);
   List<City> map(Stream<CityModel> cityStream);
}