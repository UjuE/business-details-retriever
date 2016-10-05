package com.ujuezeoke.businessdetails.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ujuezeoke.businessdetails.dao.BusinessListingStore;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessListing;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by Obianuju Ezeoke on 03/10/2016.
 */

public class AllBusinessesRoute implements Route {
    private final BusinessListingStore businessListingStore;

    private AllBusinessesRoute(BusinessListingStore businessListingStore) {
        this.businessListingStore = businessListingStore;
    }

    public static Route usingStore(BusinessListingStore businessListingStore) {
        return new AllBusinessesRoute(businessListingStore);
    }

    @Override
    public String handle(Request request, Response response) throws Exception {
        response.header("Content-Type", "application/json");
        final Set<BusinessListing> businessListings = businessListingStore
                .getAllBusinessListings()
                .stream()
                .filter(it -> it.getPhone() != null)
                .collect(toSet());
        return new ObjectMapper()
                .writeValueAsString(businessListings);
    }
}
