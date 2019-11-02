package io.example.service.common.tracing;

import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

public class Trace {

    private String traceId;
    private String spanId;
    private String parentSpanId;

    private Trace(Span activeSpan) {
        if (activeSpan != null) {
            String[] parts = activeSpan.toString().split(":");
            if (parts.length >= 3) {
                traceId = parts[0];
                spanId = parts[1];
                parentSpanId = parts[2];
            }
        }
    }

    public static Trace get() {
        return new Trace(GlobalTracer.get().activeSpan());
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }
}
