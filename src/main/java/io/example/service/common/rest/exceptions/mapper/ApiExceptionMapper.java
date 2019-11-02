package io.example.service.common.rest.exceptions.mapper;

import io.example.service.common.rest.exceptions.model.ApiException;
import io.example.service.common.tracing.Trace;
import io.example.service.common.tracing.TracedResponse;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@TracedResponse
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Inject
    private transient Logger log;

    @Inject
    private DelegateApiErrorMapper exceptionMapper;

    @Override
    public Response toResponse(final ApiException e) {
        log.error("Service exception (HTTP {})",e.getStatus().getStatusCode(), e);

        String spanId = Trace.get().getSpanId() == null ? "" : Trace.get().getSpanId();
        e.getApiErrors().forEach(exc -> {
            exc.setSpanId(spanId);
            log.error(exc.toString());
            if ( exc.getThrowable() == null ) {
                log.error("Cause: ", exc.getThrowable());
            }
        });

        return Response
                .status(e.getStatus())
                .entity(exceptionMapper.map(e))
                .build();
    }
}