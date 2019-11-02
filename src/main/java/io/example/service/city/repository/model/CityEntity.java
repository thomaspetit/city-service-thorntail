package io.example.service.city.repository.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CITY")
@NamedQueries({
        @NamedQuery(
                name = CityEntity.QUERY_BY_COUNTRY,
                query = "SELECT c " +
                        "FROM CityEntity c " +
                        "WHERE c.countryCode = :" + CityEntity.PARAM_COUNTRY_CODE + " " +
                        "ORDER BY c.zipCode ASC"
        )
})
public class CityEntity {
  public static final String QUERY_BY_COUNTRY = "cityByCountry";
  public static final String PARAM_COUNTRY_CODE = "countryCode";

  @Id
  @Column(name = "CITY_IDN", nullable = false)
  private String cityIdn;

  @Column(name = "ZIP_COD")
  private String zipCode;

  @Column(name = "COUNTRY_IDN")
  private String countryCode;

  public String getCityIdn() {
    return cityIdn;
  }

  public void setCityIdn(String cityIdn) {
    this.cityIdn = cityIdn;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  @Override
  public final boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof CityEntity)) {
      return false;
    }
    CityEntity that = (CityEntity) object;
    return Objects.equals(cityIdn, that.cityIdn);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(cityIdn);
  }

}

