package io.example.service.city.repository.mapper;


import io.example.service.city.repository.model.CityEntity;
import io.example.service.city.service.model.CityModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface CityEntityMapper {
    @Mapping(source = "cityIdn", target = "idn")
    CityModel mapCityModel(CityEntity cityEntity);
}