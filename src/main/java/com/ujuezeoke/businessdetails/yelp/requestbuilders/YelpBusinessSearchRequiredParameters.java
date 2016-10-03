package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpBusinessSearchRequiredParameters {
    private final YelpToken token;
    private final YelpRequestSender sender;
    private final String requestUrl;
    private Map<String, String> queryParams = new HashMap<>();

    public YelpBusinessSearchRequiredParameters(YelpToken token, YelpRequestSender sender, String requestUrl) {
        this.token = token;
        this.sender = sender;
        this.requestUrl = requestUrl;
    }


    public YelpBusinessSearchOptionalParameterApi withLocation(String location){
        queryParams.put("location", location);
        return new YelpBusinessSearchOptionalParameterApi(token, sender,requestUrl, queryParams);
    }

    public YelpBusinessSearchOptionalParameterApi withLatitudeAndLongitude(Double latitude, Double longitude){
        queryParams.put("latitude", latitude.toString());
        queryParams.put("longitude", longitude.toString());
        return new YelpBusinessSearchOptionalParameterApi(token, sender, requestUrl, queryParams);
    }

}
