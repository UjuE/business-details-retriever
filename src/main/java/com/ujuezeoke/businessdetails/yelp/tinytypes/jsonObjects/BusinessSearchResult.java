package com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects;

import java.util.List;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class BusinessSearchResult {
    private final Integer total;

    private final List<BusinessListing> businesses;

    public BusinessSearchResult(Integer total, List<BusinessListing> businesses) {
        this.total = total;
        this.businesses = businesses;
    }

    public List<BusinessListing> getBusinesses() {
        return businesses;
    }

    public Integer getTotal() {
        return total;
    }
}
