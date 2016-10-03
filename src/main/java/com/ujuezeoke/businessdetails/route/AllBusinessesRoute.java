package com.ujuezeoke.businessdetails.route;

import com.sun.tools.javac.util.List;
import com.ujuezeoke.businessdetails.dao.BusinessListingStore;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessListing;
import io.swagger.annotations.*;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * Created by Obianuju Ezeoke on 03/10/2016.
 */

@Api
@Path("/user")
@Produces("application/json")
public class AllBusinessesRoute implements Route {
    private final BusinessListingStore businessListingStore;

    private AllBusinessesRoute(BusinessListingStore businessListingStore) {
        this.businessListingStore = businessListingStore;
    }

    public static Route usingStore(BusinessListingStore businessListingStore) {
        return new AllBusinessesRoute(businessListingStore);
    }

    @GET
    @ApiOperation(value = "Creates a new user", nickname="CreateUserRoute")
    @ApiImplicitParams({ //
            @ApiImplicitParam(required = true, dataType="string", name="auth", paramType = "header"), //
            @ApiImplicitParam(required = true, dataType = "", paramType = "body") //
    }) //
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success", response=Collection.class), //
//            @ApiResponse(code = 400, message = "Invalid input data", response=ApiError.class), //
//            @ApiResponse(code = 401, message = "Unauthorized", response=ApiError.class), //
            @ApiResponse(code = 404, message = "Not found") //
    })
    @Override
    public Collection<BusinessListing> handle(Request request, Response response) throws Exception {
        return businessListingStore
                .getAllBusinessListings()
                .stream()
                .filter(it -> it.getPhone() != null)
                .collect(toSet());
    }
}
