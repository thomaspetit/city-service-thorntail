package io.example.service.common.tracing;

import java.util.concurrent.ForkJoinPool;

import javax.enterprise.inject.Produces;

import io.opentracing.contrib.concurrent.TracedExecutorService;
import io.opentracing.util.GlobalTracer;

public class TracedExecutorServiceProducer {
  @Produces
  public TracedExecutorService produce() {
    return new TracedExecutorService(ForkJoinPool.commonPool(), GlobalTracer.get());
  }
}
