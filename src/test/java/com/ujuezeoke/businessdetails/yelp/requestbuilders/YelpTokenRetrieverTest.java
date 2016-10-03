package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpRequestSender;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpTokenRetriever;
import com.ujuezeoke.businessdetails.yelp.tinytypes.*;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientId;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientSecret;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpTokenRetrieverTest {

    private final YelpRequestSender requestSender = mock(YelpRequestSender.class);
    private final YelpTokenRetriever underTest = new YelpTokenRetriever("http://localhost:1122", requestSender);

    @Test
    public void successfulResponse() {
        when(requestSender.postRequest(anyString(), any()))
                .thenReturn(new Result<>(200, new YelpToken("Bearer", "someToken", "15547411")));

        YelpToken yelpToken = underTest.newTokenFor(new ClientId("1221"), new ClientSecret("122123"));

        verify(requestSender).postRequest("http://localhost:1122/oauth2/token?client_id=1221&client_secret=122123", YelpToken.class);
        assertThat(yelpToken.getTokenType(), is(new YelpTokenType("Bearer")));
        assertThat(yelpToken.getAccessToken(), is(new YelpAccessToken("someToken")));
        assertThat(yelpToken.getExpiresIn(), is(new YelpTokenExpiryTime("15547411")));
    }


}