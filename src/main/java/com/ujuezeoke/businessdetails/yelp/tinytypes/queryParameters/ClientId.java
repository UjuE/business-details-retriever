package com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class ClientId extends QueryParameters<String>{
    public ClientId(String clientId) {
        super("client_id", clientId);
    }
}
