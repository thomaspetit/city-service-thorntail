package io.example.service.common.rest.exceptions.model;

import javax.ws.rs.core.Response.Status;
import java.util.List;

public final class InternalServerErrorException extends ApiException {

    private static final Status status = Status.INTERNAL_SERVER_ERROR;

    public InternalServerErrorException(ApiError.Type type, String description ) {
        super(status, type, description);
    }

    public InternalServerErrorException(ApiError.Type type, String description, String fieldName, String originalValue ) {
        super(status, type, description, fieldName, originalValue);
    }

    public InternalServerErrorException(List<ApiError> apiErrors) {
        super(status, apiErrors);
    }
}
