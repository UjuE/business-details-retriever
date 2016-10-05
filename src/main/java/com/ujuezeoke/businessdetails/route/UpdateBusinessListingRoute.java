package com.ujuezeoke.businessdetails.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ujuezeoke.businessdetails.dao.BusinessListingStore;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessListing;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Obianuju Ezeoke on 03/10/2016.
 */
public class UpdateBusinessListingRoute implements Route {
    private final BusinessListingStore businessListingStore;

    private UpdateBusinessListingRoute(BusinessListingStore businessListingStore) {
        this.businessListingStore = businessListingStore;
    }

    public static Route usingStore(BusinessListingStore businessListingStore) {
        return new UpdateBusinessListingRoute(businessListingStore);
    }

    @Override
    public Response handle(Request request, Response response) throws Exception {
        final String id = request.params(":id");
        final BusinessListing businessListingOnDatabase = businessListingStore.getById(id);
        final BusinessListing businessListingFromRequest = new ObjectMapper().readValue(request.body(), BusinessListing.class);

        if (id.equals(businessListingFromRequest.getId()) && id.equals(businessListingOnDatabase.getId())) {
            businessListingStore.saveOrUpDate(businessListingFromRequest);
        }
        return null;
    }
}
