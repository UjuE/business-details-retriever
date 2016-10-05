package com.ujuezeoke.businessdetails.yelp.requestbuilders;

import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessSearchResult;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class YelpBusinessSearchRequestBuilderTest {

    private final YelpRequestSender sender = mock(YelpRequestSender.class);
    private final YelpToken someToken = new YelpToken("tokenType", "accessToken", "112231");
    private YelpBusinessSearchRequestBuilder underTest =
            new YelpBusinessSearchRequestBuilder("https://localhost:12311", sender);

    @Test
    public void canBuildRequestUsingLocation() {
        underTest
                .withToken(someToken)
                .withLocation("london")
                .execute();

        verify(sender).getRequest("https://localhost:12311/v3/businesses/search?location=london",
                BusinessSearchResult.class, someToken);
    }

    @Test
    public void canBuildRequestUsingLatitudeAndLongitude() {
        underTest
                .withToken(someToken)
                .withLatitudeAndLongitude(11.20D, 20.12D)
                .execute();

        verify(sender).getRequest("https://localhost:12311/v3/businesses/search?latitude=11.2&longitude=20.12",
                BusinessSearchResult.class, someToken);
    }

    @Test
    public void canBuildRequestWithOptionalParameters() {
        underTest
                .withToken(someToken)
                .withLocation("london")
                .withTerm("food")
                .withRadius(2000)
                .withCategories("bars", "french")
                .withLocale("plaistow")
                .withLimit(50)
                .withOffset(12)
                .execute();

        verify(sender).getRequest(argThat(allOf(
                containsString("https://localhost:12311/v3/businesses/search?"),
                containsString("offset=12"),
                containsString("limit=50"),
                containsString("location=london"),
                containsString("term=food"),
                containsString("categories=bars,french"),
                containsString("radius=2000"),
                containsString("locale=plaistow"))),
                eq(BusinessSearchResult.class),
                eq(someToken));
    }
}