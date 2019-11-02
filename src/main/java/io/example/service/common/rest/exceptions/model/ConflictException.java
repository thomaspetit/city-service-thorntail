package io.example.service.common.rest.exceptions.model;

import javax.ws.rs.core.Response.Status;
import java.util.List;

public final class ConflictException extends ApiException {

    private static final Status status = Status.CONFLICT;

    public ConflictException(ApiError.Type type, String description ) {
        super(status, type, description);
    }

    public ConflictException(ApiError.Type type, String description, Throwable throwable ) {
        super(status, type, description, throwable);
    }

    public ConflictException(ApiError.Type type, String description, String fieldName, String originalValue ) {
        super(status, type, description, fieldName, originalValue);
    }

    public ConflictException(ApiError.Type type, String description, String fieldName, String originalValue, Throwable throwable ) {
        super(status, type, description, fieldName, originalValue, throwable);
    }

    public ConflictException(List<ApiError> apiErrors) {
        super(status, apiErrors);
    }
}
