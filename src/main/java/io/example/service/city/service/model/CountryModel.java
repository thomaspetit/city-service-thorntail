package io.example.service.city.service.model;

import java.util.ArrayList;
import java.util.List;

public class CountryModel {
  private String name;
  private String code;
  private List<CityModel> cities = new ArrayList<>();

  public String getCode() {
    return code;
  }

  public CountryModel setCode(String code) {
    this.code = code;
    return this;
  }

  public String getName() {
    return name;
  }

  public CountryModel setName(String name) {
    this.name = name;
    return this;
  }

  public List<CityModel> getCities() {
    return cities;
  }

  public CountryModel setCities(List<CityModel> cities) {
    this.cities = cities;
    return this;
  }
}
