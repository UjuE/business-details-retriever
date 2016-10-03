package com.ujuezeoke.businessdetails.yelp;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpBusinessSearchRequestBuilder;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpRequestSender;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpTokenRetriever;
import com.ujuezeoke.businessdetails.yelp.tinytypes.Result;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessListing;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessLocation;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessSearchResult;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientId;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientSecret;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.String.format;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpBusinessDetailsTest {

    private final String clientId = "clientId";
    private final String clientSecret = "clientSecret";
    private WireMockServer mockServer;
    private int portNumber = (int) Math.round(Math.random() * 8092) + 1000;
    private final String baseUrl = "http://localhost:" + portNumber;
    private final YelpRequestSender sender = new YelpRequestSender();
    private final YelpTokenRetriever tokenRetriever = new YelpTokenRetriever(baseUrl, sender);
    private final YelpBusinessSearchRequestBuilder underTest =
            new YelpBusinessSearchRequestBuilder(baseUrl, sender);

    @Before
    public void setUp() {
        mockServer = new WireMockServer(wireMockConfig().port(portNumber));
        mockServer.stubFor(post(urlEqualTo(format("/oauth2/token?client_id=%s&client_secret=%s", clientId, clientSecret)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "  \"token_type\": \"Bearer\",\n" +
                                "  \"access_token\": \"someToken\",\n" +
                                "  \"expires_in\": 15547411\n" +
                                "}")));

        mockServer.stubFor(get(urlMatching("/v3/businesses/search?.*"))
                .withHeader("Authorization", equalTo("Bearer someToken"))
                .willReturn(aResponse()
                .withBody("{\n" +
                        "  \"total\": 42238,\n" +
                        "  \"businesses\": [\n" +
                        "    {\n" +
                        "      \"review_count\": 184,\n" +
                        "      \"phone\": \"+443000612300\",\n" +
                        "      \"url\": \"https://www.yelp.com/biz/the-regents-park-london-2?adjust_creative=AoX_dBUIldvHMX6fh-CfBg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=AoX_dBUIldvHMX6fh-CfBg\",\n" +
                        "      \"rating\": 4.5,\n" +
                        "      \"location\": {\n" +
                        "        \"address1\": \"The Storeyard Inner Circle\",\n" +
                        "        \"country\": \"GB\",\n" +
                        "        \"address2\": \"\",\n" +
                        "        \"city\": \"London\",\n" +
                        "        \"state\": \"XGL\",\n" +
                        "        \"address3\": \"\",\n" +
                        "        \"zip_code\": \"NW1 4NR\"\n" +
                        "      },\n" +
                        "      \"categories\": [\n" +
                        "        {\n" +
                        "          \"alias\": \"parks\",\n" +
                        "          \"title\": \"Parks\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/BPUNfoGvt0sOUaClxxvYpQ/o.jpg\",\n" +
                        "      \"coordinates\": {\n" +
                        "        \"longitude\": -0.155611038208008,\n" +
                        "        \"latitude\": 51.5282902069734\n" +
                        "      },\n" +
                        "      \"name\": \"The Regent's Park\",\n" +
                        "      \"id\": \"the-regents-park-london-2\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"price\": \"£££\",\n" +
                        "      \"review_count\": 354,\n" +
                        "      \"phone\": \"+442072194272\",\n" +
                        "      \"url\": \"https://www.yelp.com/biz/big-ben-london?adjust_creative=AoX_dBUIldvHMX6fh-CfBg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=AoX_dBUIldvHMX6fh-CfBg\",\n" +
                        "      \"rating\": 4.5,\n" +
                        "      \"location\": {\n" +
                        "        \"address1\": \"\",\n" +
                        "        \"country\": \"GB\",\n" +
                        "        \"address2\": \"\",\n" +
                        "        \"city\": \"London\",\n" +
                        "        \"state\": \"XGL\",\n" +
                        "        \"address3\": \"\",\n" +
                        "        \"zip_code\": \"SW1A 0AA\"\n" +
                        "      },\n" +
                        "      \"categories\": [\n" +
                        "        {\n" +
                        "          \"alias\": \"landmarks\",\n" +
                        "          \"title\": \"Landmarks & Historical Buildings\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/eYJgmK1jSEAz9d53mga11A/o.jpg\",\n" +
                        "      \"coordinates\": {\n" +
                        "        \"longitude\": -0.122475348503623,\n" +
                        "        \"latitude\": 51.5003787568188\n" +
                        "      },\n" +
                        "      \"name\": \"Big Ben\",\n" +
                        "      \"id\": \"big-ben-london\"\n" +
                        "    }]" +
                        "}")));

        mockServer.start();
    }

    @Test
    public void retrieveListOfBusinesses() {
        final Result<BusinessSearchResult> result = underTest.withToken(
                tokenRetriever.newTokenFor(new ClientId(clientId), new ClientSecret(clientSecret))
        )
                .withLocation("london")
                .withTerm("food")
                .execute();

        assertThat(result.getResponseBody().getBusinesses(), hasItems(
                new BusinessListing("the-regents-park-london-2", "The Regent's Park", "+443000612300",
                        new BusinessLocation("The Storeyard Inner Circle", "", "", "NW1 4NR", "London", "GB")
                ),
                new BusinessListing("big-ben-london", "Big Ben", "+442072194272",
                        new BusinessLocation("", "", "", "SW1A 0AA", "London", "GB")
                )
        ));

    }
}
