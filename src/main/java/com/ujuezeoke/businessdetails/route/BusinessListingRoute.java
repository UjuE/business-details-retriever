package com.ujuezeoke.businessdetails.route;

import com.ujuezeoke.businessdetails.dao.BusinessListingStore;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessListing;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Obianuju Ezeoke on 03/10/2016.
 */
public class BusinessListingRoute implements Route {
    private final BusinessListingStore businessListingStore;

    public BusinessListingRoute(BusinessListingStore businessListingStore) {
        this.businessListingStore = businessListingStore;
    }

    public static Route usingStore(BusinessListingStore businessListingStore) {
        return new BusinessListingRoute(businessListingStore);
    }

    @Override
    public BusinessListing handle(Request request, Response response) throws Exception {
        return businessListingStore.getById(request.params(":id"));
    }
}
