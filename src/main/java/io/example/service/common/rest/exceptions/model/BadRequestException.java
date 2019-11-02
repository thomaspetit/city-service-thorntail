package io.example.service.common.rest.exceptions.model;

import javax.ws.rs.core.Response.Status;
import java.util.List;

public final class BadRequestException extends ApiException {

    private static final Status status = Status.BAD_REQUEST;

    public BadRequestException(ApiError.Type type, String description ) {
        super(status, type, description);
    }

    public BadRequestException(ApiError.Type type, String description, Throwable throwable ) {
        super(status, type, description, throwable);
    }

    public BadRequestException(ApiError.Type type, String description, String fieldName, String originalValue ) {
        super(status, type, description, fieldName, originalValue);
    }

    public BadRequestException(ApiError.Type type, String description, String fieldName, String originalValue, Throwable throwable ) {
        super(status, type, description, fieldName, originalValue, throwable);
    }

    public BadRequestException(List<ApiError> apiErrors) {
        super(status, apiErrors);
    }
}
