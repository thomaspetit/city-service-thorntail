package io.example.service.common.tracing;

import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@TracedResponse
@Interceptor
public class TraceInLogInterceptor {

    @Inject
    private Logger log;

    @AroundInvoke
    public Object addHeaders(InvocationContext invocationContext)
            throws Exception {
        setTraceInMdc();
        try {
            return invocationContext.proceed();
        } finally {
            clearMdc();
        }
    }

    private void clearMdc() {
        try {
            MDC.clear();
        } catch (Exception e) {
            log.error("Exception while trying to clear MDC.", e);
        }
    }

    private void setTraceInMdc() {
        try {
            Trace trace = Trace.get();
            MDC.put("tracing", String.format("[traceId:%s;spanId:%s]",trace.getTraceId(), trace.getSpanId()));
        } catch (Exception e) {
            log.warn("Exception while trying to configure MDC with TraceId. Attempting to continue without.", e);
        }
    }
}
