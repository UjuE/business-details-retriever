package com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects;

import com.ujuezeoke.businessdetails.yelp.requestbuilders.RequestHeader;
import com.ujuezeoke.businessdetails.yelp.tinytypes.YelpAccessToken;
import com.ujuezeoke.businessdetails.yelp.tinytypes.YelpTokenExpiryTime;
import com.ujuezeoke.businessdetails.yelp.tinytypes.YelpTokenType;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpToken implements RequestHeader {

    private final String token_type;
    private final String access_token;
    private final String expires_in;

    public YelpToken(String tokenType, String accessToken, String expiresIn) {
        this.token_type = tokenType;
        this.access_token = accessToken;
        this.expires_in = expiresIn;
    }

    public YelpTokenType getTokenType() {
        return new YelpTokenType(token_type);
    }

    public YelpAccessToken getAccessToken() {
        return new YelpAccessToken(access_token);
    }

    public YelpTokenExpiryTime getExpiresIn() {
        return new YelpTokenExpiryTime(expires_in);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YelpToken yelpToken = (YelpToken) o;

        return token_type.equals(yelpToken.token_type)
                && access_token.equals(yelpToken.access_token)
                && expires_in.equals(yelpToken.expires_in);

    }

    @Override
    public int hashCode() {
        int result = token_type.hashCode();
        result = 31 * result + access_token.hashCode();
        result = 31 * result + expires_in.hashCode();
        return result;
    }

    @Override
    public String getValue() {
        return token_type + " " + access_token;
    }

    @Override
    public String getKey() {
        return "Authorization";
    }
}
