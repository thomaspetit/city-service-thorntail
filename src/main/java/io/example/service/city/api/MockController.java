package io.example.service.city.api;

import io.swagger.annotations.*;
import zipcode.api.model.City;
import zipcode.api.model.Place;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * This class and package only exists to easily show the concurrency capabilities of the service city.
 * Do NOT create such a package in your final service implementation.
 */
@ApplicationScoped
@Path("/v1/zipCodes")
@Api(description = "the zipCodes API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-10-10T10:40:17.168+02:00[Europe/Paris]")
public class MockController {

    @GET
    @Path("/{countryCode}/{zipCode}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Find information for a specific zipcode", notes = "Get detailed zipcode information", tags={ "zipcode" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the country code and the list of found cities", response = City.class),
            @ApiResponse(code = 404, message = "Not found", response = Void.class) })
    public Response getZipCodeInfo(
            @PathParam("countryCode") @ApiParam("The country code (ISO)") String countryCode,
            @PathParam("zipCode") @ApiParam("The zipcode") String zipCode) {
        simulateApiCallDuration(100);

        Place place = new Place();
        place.setState("Random state");
        place.setPlaceName("Random place name");
        place.setStateAbbreviation("RS");
        place.setLatitude("90.0000° N");
        place.setLongitude("135.0000° W");

        City city = new City();
        city.setCountry("Lalaland");
        city.setCountryAbbreviation(countryCode);
        city.setPostCode(zipCode);
        city.setPlaces(Arrays.asList(place));

        return Response.ok(city).build();
    }

    /**
     * Simulate an external API call taking place by waiting x ms.
     * Used to demonstrate parallel API calls in the CountryService
     */
    private void simulateApiCallDuration(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
