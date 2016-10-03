package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.ujuezeoke.businessdetails.yelp.tinytypes.Result;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientId;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientSecret;

import static com.ujuezeoke.businessdetails.yelp.requestbuilders.UrlHelper.combine;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpTokenRetriever {
    private static final String OAUTH_TOKEN_REQUEST_PATH = "/oauth2/token?";
    private final String baseUrl;
    private final YelpRequestSender requestSender;

    public YelpTokenRetriever(String baseUrl, YelpRequestSender requestSender) {
        this.baseUrl = combine(baseUrl, OAUTH_TOKEN_REQUEST_PATH);
        this.requestSender = requestSender;
    }

    public YelpToken newTokenFor(ClientId clientId, ClientSecret clientSecret) {

        final Result<YelpToken> yelpTokenResult = requestSender.postRequest(baseUrl +
                        String.join("&", clientId.getValue(), clientSecret.getValue()),
                YelpToken.class);

        return yelpTokenResult.getResponseBody();
    }
}
