package io.example.service.common.rest.exceptions.mapper;

import io.example.service.common.rest.exceptions.model.ApiException;
import io.example.service.common.rest.exceptions.model.ApiError;
import io.example.service.common.tracing.Trace;
import io.example.service.common.tracing.TracedResponse;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import java.util.Optional;

@TracedResponse
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    private transient Logger log;

    @Inject
    private DelegateApiErrorMapper mapper;

    @Context
    private Providers providers;

    @Override
    public Response toResponse(final Throwable t) {
        Throwable throwable = t.getCause() != null ? t.getCause() : t;
        return determineSuitableMapper(throwable.getClass()).map(m -> m.toResponse(throwable)).orElse(buildException(t));
    }

    private Response buildException(Throwable t) {
        log.error("Internal server error", t);
        String spanId = Trace.get().getSpanId() == null ? "" : Trace.get().getSpanId();

        Status status = Status.INTERNAL_SERVER_ERROR;

        ApiError apiError = new ApiError();
        apiError.setType(ApiError.Type.TECHNICAL);
        apiError.setDescription(t.getMessage() != null ? t.getMessage() : status.getReasonPhrase() );
        apiError.setSpanId(spanId);

        return Response
                .status(status)
                .entity(this.mapper.map(new ApiException(status, apiError)))
                .build();
    }

    private Optional<ExceptionMapper> determineSuitableMapper(Class<? extends Throwable> clazz) {
        ExceptionMapper mapper = providers.getExceptionMapper(clazz);
        if ( mapper instanceof GenericExceptionMapper) {
            return Optional.empty();
        } else if (null == mapper) {
            Class<?> superClazz = clazz.getSuperclass();
            if (Throwable.class.isAssignableFrom(superClazz)) {
                return determineSuitableMapper((Class<? extends Throwable>) superClazz);
            }
            return Optional.empty();
        }
        return Optional.of(mapper);
    }
}