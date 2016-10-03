package com.ujuezeoke.businessdetails;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.ujuezeoke.businessdetails.dao.BusinessListingStore;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpBusinessSearchRequestBuilder;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpRequestSender;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpTokenRetriever;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientId;
import com.ujuezeoke.businessdetails.yelp.tinytypes.queryParameters.ClientSecret;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Obianuju Ezeoke on 02/10/2016.
 */
public class RetrieveAndStoreDetailsTest {

    private final String clientId = "clientId";
    private final String clientSecret = "clientSecret";
    private WireMockServer mockServer;
    private int portNumber = 8089;
    private final String baseUrl = "http://localhost:" + portNumber;
    private final YelpRequestSender sender = new YelpRequestSender();
    private final YelpTokenRetriever tokenRetriever = new YelpTokenRetriever(baseUrl, sender);
    private final YelpBusinessSearchRequestBuilder yelpBusinessSearchRequestBuilder =
            new YelpBusinessSearchRequestBuilder(baseUrl, sender);
    private BusinessListingStore businessListingStore;
    private RetrieveAndStoreDetails underTest;
    private ClientIdAndSecretRetriever clientIdAndSecretRetriever = mock(ClientIdAndSecretRetriever.class);
    private Path tempFile;

    @Before
    public void setUp() throws IOException {
        when(clientIdAndSecretRetriever.getClientId()).thenReturn(new ClientId(clientId));
        when(clientIdAndSecretRetriever.getClientSecret()).thenReturn(new ClientSecret(clientSecret));

        tempFile = Files.createTempFile("test", ".db");
        businessListingStore = BusinessListingStore.createOrConnectTo(tempFile)
                .createBusinessListingTableIfNotExists();
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
                                "  \"total\": 4,\n" +
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

    @After
    public void tearDown() throws IOException {
        if(tempFile != null){
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void retrieveAndStore() {
        underTest = new RetrieveAndStoreDetails(tokenRetriever, yelpBusinessSearchRequestBuilder, businessListingStore, clientIdAndSecretRetriever);

        underTest.retrieveAndStore("london");

        assertThat(businessListingStore.getAllBusinessListings().size(), is(2));
    }

}