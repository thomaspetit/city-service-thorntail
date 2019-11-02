package io.example.service.common.rest.exceptions.model;

import javax.ws.rs.core.Response.Status;
import java.util.Arrays;
import java.util.List;

public class ApiException extends RuntimeException {

    private final Status status;
    private List<ApiError> apiErrors;

    public ApiException(Status status, ApiError.Type type, String description ) {
        this(status, new ApiError(type, description));
    }

    public ApiException(Status status, ApiError.Type type, String description, Throwable throwable ) {
        this(status, new ApiError(type, description, throwable));
    }

    public ApiException(Status status, ApiError.Type type, String description, String fieldName, String originalValue ) {
        this(status, new ApiError(type, description, fieldName, originalValue));
    }

    public ApiException(Status status, ApiError.Type type, String description, String fieldName, String originalValue, Throwable throwable ) {
        this(status, new ApiError(type, description, fieldName, originalValue, throwable));
    }

    public ApiException(Status status, ApiError... apiError) {
        this(status, Arrays.asList(apiError));
    }

    public ApiException(Status status, List<ApiError> apiErrors) {
        this.status = status;
        this.apiErrors = apiErrors;
    }

    public Status getStatus() {
        return status;
    }

    public List<ApiError> getApiErrors() {
        return apiErrors;
    }
}
