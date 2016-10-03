package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpRequestSender;
import com.ujuezeoke.businessdetails.yelp.tinytypes.Result;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpRequestSenderTest {

    private WireMockServer mockServer;
    private int portNumber = (int) Math.round(Math.random() * 8092);

    @Before
    public void startStub() {
        mockServer = new WireMockServer(wireMockConfig().port(portNumber));
        mockServer.stubFor(post(anyUrl())
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "  \"token_type\": \"Bearer\",\n" +
                                "  \"access_token\": \"someToken\",\n" +
                                "  \"expires_in\": 15547411\n" +
                                "}")));

        mockServer.start();
    }

    @After
    public void teaDown(){
        mockServer.stop();
    }

    @Test
    public void requestWithType() {
        final Result<YelpToken> yelpTokenResult = new YelpRequestSender()
                .postRequest("http://localhost:" + portNumber, YelpToken.class);

        assertThat(yelpTokenResult.getStatusCode(), is(200));
        assertThat(yelpTokenResult.getResponseBody(), is(new YelpToken("Bearer", "someToken", "15547411")));
    }
}