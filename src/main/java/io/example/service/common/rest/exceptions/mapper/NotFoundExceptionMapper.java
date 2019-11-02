package io.example.service.common.rest.exceptions.mapper;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
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
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Inject
    private transient Logger log;

    @Inject
    private DelegateApiErrorMapper mapper;

    @Override
    public Response toResponse(final NotFoundException e) {
        log.error("Resource not found", e);

        return Response.status(Status.NOT_FOUND).build();
    }
}