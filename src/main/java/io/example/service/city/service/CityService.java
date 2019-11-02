package io.example.service.city.service;

import io.example.service.city.adapter.zipcode.ZipCodeAdapter;
import io.example.service.city.repository.CityRepository;
import io.example.service.city.repository.mapper.CityEntityMapper;
import io.example.service.city.service.model.CityModel;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class CityService {

  @Inject
  private transient Logger log;

  @Inject
  private ZipCodeAdapter zipCodeAdapter;

  @Inject
  private CityEntityMapper cityEntityMapper;

  @Inject
  private CityRepository cityRepository;

  /**
   * Example of sequential calls to adapter service
   * 
   * @param countryIdn
   * @return
   */
  public Stream<CityModel> getCitiesForCountrySequential(final String countryIdn) {
    log.info("Searching cities for country: {}", countryIdn);

    return cityRepository.getCitiesByCountryIdn(countryIdn)
        .map(cityEntityMapper::mapCityModel)
        .map(this::enrichCityModel)
        .map(CompletableFuture::join);
  }

  /**
   * Example of parallel calls to adapter service
   * 
   * @param countryIdn
   * @return
   */
  public Stream<CityModel> getCitiesForCountryParallel(final String countryIdn) {
    log.info("Searching cities for country: {}", countryIdn);

    final List<CompletableFuture<CityModel>> f = cityRepository.getCitiesByCountryIdn(countryIdn)
        .map(cityEntityMapper::mapCityModel)
        .map(this::enrichCityModel)
        .collect(Collectors.toList());
    //By collecting the stream before joining we allow the futures to be launched in parallel.
    return f.stream().map(CompletableFuture::join);
  }

  public Stream<CityModel>  throwServiceException() throws IllegalAccessException {
    throw new IllegalAccessException("No permissions to retrieve this data");
  }

  private CompletableFuture<CityModel> enrichCityModel(CityModel city) {
    return zipCodeAdapter.getZipCodeInfo(city.getZipCode(), city.getCountryCode())
        .thenApply(cityModel -> {
            if (city != null && cityModel != null) {
              city.setCountry(cityModel.getCountry());
              city.setName(cityModel.getName());
            }
            return city;
        });
  }

}
