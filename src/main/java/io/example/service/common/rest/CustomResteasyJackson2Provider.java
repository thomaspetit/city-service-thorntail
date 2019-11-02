package io.example.service.common.rest;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

public class CustomResteasyJackson2Provider extends ResteasyJackson2Provider {
  CustomResteasyJackson2Provider(ObjectMapperContextResolver objectMapperContextResolver) {
    super();
    this.setMapper(objectMapperContextResolver.getContext(Object.class));
  }
}
