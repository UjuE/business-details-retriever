package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;

import java.util.HashMap;
import java.util.Map;

import static com.ujuezeoke.businessdetails.yelp.requestbuilders.UrlHelper.combine;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpBusinessSearchRequestBuilder {

    private static String BUSINESS_SEARCH_REQUEST_PATH = "/v3/businesses/search";
    private final String requestUrl;
    private final YelpRequestSender sender;

    public YelpBusinessSearchRequestBuilder(String baseUrl, YelpRequestSender sender) {
        requestUrl = combine(baseUrl, BUSINESS_SEARCH_REQUEST_PATH);
        this.sender = sender;
    }

    public YelpBusinessSearchRequiredParameters withToken(YelpToken token) {
        return new YelpBusinessSearchRequiredParameters(token, sender, requestUrl);
    }

}
