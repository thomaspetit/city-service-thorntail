package io.example.service.common.rest;

import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class RestClientBuilderProducer {
  /**
   * Priority is used to determine which reader/writer is used by the Client to parse/write a certain content type.
   * The default configured readers/writers have priority 5000. As we want our Jackson configuration to have higher
   * priority, we need a number smaller than 5000.
   */
  private static final int JACKSON_PROVIDER_PRIORITY = 100;

  @Inject
  private ObjectMapperContextResolver jacksonContextResolver;

  @Produces
  public RestClientBuilder produceRestClientBuilder() {
    return RestClientBuilder.newBuilder()
        .register(new CustomResteasyJackson2Provider(jacksonContextResolver), JACKSON_PROVIDER_PRIORITY)
        .register(ClientTracingFeature.class);
  }
}
