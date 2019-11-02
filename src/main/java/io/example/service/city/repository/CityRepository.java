package io.example.service.city.repository;

import io.example.service.city.repository.model.CityEntity;
import org.apache.deltaspike.data.api.EntityPersistenceRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;

import java.util.stream.Stream;

@Repository
public interface CityRepository extends EntityPersistenceRepository<CityEntity, String> {

    @Query(named = CityEntity.QUERY_BY_COUNTRY)
    Stream<CityEntity> getCitiesByCountryIdn(@QueryParam(CityEntity.PARAM_COUNTRY_CODE) String countryCode);
}