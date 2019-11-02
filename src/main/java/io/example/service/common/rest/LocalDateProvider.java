package io.example.service.common.rest;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Provider
public class LocalDateProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.isAssignableFrom(LocalDate.class)) {
            return (ParamConverter<T>) new LocalDateParamConverter();
        }
        return null;
    }

    private static class LocalDateParamConverter implements ParamConverter<LocalDate> {
        @Override
        public LocalDate fromString(String value) {
            return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
        }

        @Override
        public String toString(LocalDate value) {
            return value.format(DateTimeFormatter.ISO_DATE);
        }
    }
}

