package io.example.service.city.api.mapper;

import io.example.service.common.rest.exceptions.mapper.DelegateApiErrorMapper;
import io.example.service.common.rest.exceptions.model.ApiError;
import io.example.service.common.rest.exceptions.model.ApiException;
import io.example.service.city.api.model.Error;
import io.example.service.city.api.model.ErrorList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public abstract class ApiErrorMapper implements DelegateApiErrorMapper {
    @Mapping(source = "apiErrors", target = "errors")
    public abstract ErrorList map(ApiException exceptions);

    @Mapping(source = "type", target = "errorType")
    @Mapping(source = "description", target = "errorDescription")
    @Mapping(source = "spanId", target = "errorReference")
    abstract Error map(ApiError apiError);
}