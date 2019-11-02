package io.example.service.common.rest.exceptions.mapper;

import io.example.service.common.rest.exceptions.model.ApiException;

public interface DelegateApiErrorMapper {
    Object map(ApiException serviceErrorList);
}
