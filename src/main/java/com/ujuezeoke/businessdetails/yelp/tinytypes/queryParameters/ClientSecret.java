package com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class ClientSecret extends QueryParameters<String> {

    public ClientSecret(String clientSecret) {
        super("client_secret", clientSecret);
    }
}
