package com.ujuezeoke.businessdetails;

import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientId;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientSecret;

import java.util.Optional;

/**
 * Created by Obianuju Ezeoke on 02/10/2016.
 */
public class EnvironmentVariableClientIdAndSecretRetriever implements ClientIdAndSecretRetriever {

    private static final String YELP_CLIENT_ID_ENVIRONMENT_KEY = "PLOMOS_YELP_CLIENT_ID";
    private static final String YELP_CLIENT_SECRET_ENVIRONMENT_KEY = "PLOMOS_YELP_CLIENT_SECRET";

    @Override
    public ClientId getClientId() {
        final String yelpClientId = Optional
                .ofNullable(System.getenv(YELP_CLIENT_ID_ENVIRONMENT_KEY))
                .orElseThrow(
                        () ->
                                new IllegalStateException(YELP_CLIENT_ID_ENVIRONMENT_KEY +
                                        " environment variable has not been set")
                );

        return new ClientId(yelpClientId);
    }

    @Override
    public ClientSecret getClientSecret() {
        final String yelpClientSecret = Optional
                .ofNullable(System.getenv(YELP_CLIENT_SECRET_ENVIRONMENT_KEY))
                .orElseThrow(
                        () ->
                                new IllegalStateException(YELP_CLIENT_SECRET_ENVIRONMENT_KEY +
                                        " environment variable has not been set")
                );

        return new ClientSecret(yelpClientSecret);
    }
}
