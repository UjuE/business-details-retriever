package com.ujuezeoke.businessdetails.yelp.requestbuilders;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class UrlHelper {
    private UrlHelper(){}

    public static String combine(String baseUrl, String requestPath) {
        if(baseUrl.endsWith("/") || requestPath.startsWith("/")){
            return baseUrl + requestPath;
        }

        return baseUrl + "/" + requestPath;
    }
}
