package com.ujuezeoke.businessdetails;

import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientId;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientSecret;

/**
 * Created by Obianuju Ezeoke on 02/10/2016.
 */
public interface ClientIdAndSecretRetriever {
    ClientId getClientId();
    ClientSecret getClientSecret();
}
