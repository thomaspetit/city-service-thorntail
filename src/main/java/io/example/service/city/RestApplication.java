package io.example.service.city;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ApplicationPath;
import javax.enterprise.inject.Produces;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class RestApplication extends Application {
    @PersistenceContext
    @Produces
    private EntityManager entityManager;
}