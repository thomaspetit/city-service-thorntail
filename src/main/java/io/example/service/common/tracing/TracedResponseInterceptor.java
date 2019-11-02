package io.example.service.common.tracing;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

@TracedResponse
@Interceptor
public class TracedResponseInterceptor {

    private static final String TRACE_ID_HEADER = "X-B3-TraceId";
    private static final String SPAN_ID_HEADER = "X-B3-SpanID";

    @Inject
    private transient Logger log;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext invocationContext)
            throws Exception {
        Object returnObject = invocationContext.proceed();
        if (returnObject instanceof Response) {
            Trace trace =  Trace.get();
            Response response = (Response) returnObject;
            response.getHeaders().add(TRACE_ID_HEADER, trace.getTraceId());
            response.getHeaders().add(SPAN_ID_HEADER, trace.getSpanId());
        }
        return returnObject;
    }
}
