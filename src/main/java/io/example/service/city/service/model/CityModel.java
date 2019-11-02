package io.example.service.city.service.model;

public class CityModel {
  private String idn;
  private String name;
  private String zipCode;
  private String country;
  private String countryCode;

  public String getIdn() {
    return idn;
  }

  public void setIdn(final String idn) {
    this.idn = idn;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(final String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCountry() {
        return country;
    }

  public void setCountry(String country) {
        this.country = country;
    }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }
}
