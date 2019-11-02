package io.example.service.common.rest.exceptions.mapper;

import io.example.service.common.rest.exceptions.model.ApiError;
import io.example.service.common.rest.exceptions.model.ApiException;
import io.example.service.common.tracing.Trace;
import io.example.service.common.tracing.TracedResponse;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@TracedResponse
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ContractViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Inject
    private transient Logger log;

    @Inject
    private DelegateApiErrorMapper mapper;

    @Override
    public Response toResponse(final ConstraintViolationException e) {
        log.error("Contract violation exception", e);

        String spanId = Trace.get().getSpanId() == null ? "" : Trace.get().getSpanId();

        List<ApiError> apiErrors = new ArrayList<>();
        e.getConstraintViolations().forEach(c -> {
            ApiError apiError = new ApiError();
            apiError.setType(ApiError.Type.BUSINESS);
            apiError.setDescription(c.getMessage());
            apiError.setFieldName(c.getPropertyPath().toString());
            apiError.setOriginalValue(c.getInvalidValue().toString());
            apiError.setSpanId(spanId);
            apiError.setThrowable(e);
            apiErrors.add(apiError);
        });

        Status status = Status.BAD_REQUEST;

        return Response
                .status(status)
                .entity(mapper.map(new ApiException(status, apiErrors)))
                .build();
    }
}