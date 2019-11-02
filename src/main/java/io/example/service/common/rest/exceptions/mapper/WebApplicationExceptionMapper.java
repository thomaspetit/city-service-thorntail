package io.example.service.common.rest.exceptions.mapper;

import io.example.service.common.rest.exceptions.model.ApiError;
import io.example.service.common.rest.exceptions.model.ApiException;
import io.example.service.common.tracing.Trace;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * In the event an unknown path is passed we cannot
 * return a SpanID, hence we elegantly catch these errors.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    private transient Logger log;

    @Inject
    private DelegateApiErrorMapper mapper;

    @Override
    public Response toResponse(WebApplicationException e) {
        log.error("Unexpected or uncatched exception (HTTP 404)", e);

        String spanId = Trace.get().getSpanId() == null ? "" : Trace.get().getSpanId();

        ApiError apiError = new ApiError();
        apiError.setType(ApiError.Type.TECHNICAL);
        apiError.setDescription(e.getMessage());
        apiError.setSpanId(spanId);

        Status status = Status.NOT_FOUND;

        return Response
                .status(status)
                .entity(mapper.map(new ApiException(status, apiError)))
                .build();
    }
}