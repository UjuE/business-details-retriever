package com.ujuezeoke.businessdetails;

import com.ujuezeoke.businessdetails.dao.BusinessListingStore;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpBusinessSearchRequestBuilder;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpTokenRetriever;
import com.ujuezeoke.businessdetails.yelp.tinytypes.Result;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessSearchResult;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.YelpToken;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Obianuju Ezeoke on 02/10/2016.
 */
public class RetrieveAndStoreDetails {

    private static final Logger LOGGER = Logger.getLogger(RetrieveAndStoreDetails.class.getName());
    private final YelpTokenRetriever tokenRetriever;
    private final YelpBusinessSearchRequestBuilder yelpBusinessSearchRequestBuilder;
    private final BusinessListingStore businessListingStore;
    private final ClientIdAndSecretRetriever clientIdAndSecretRetriever;

    public RetrieveAndStoreDetails(YelpTokenRetriever tokenRetriever,
                                   YelpBusinessSearchRequestBuilder yelpBusinessSearchRequestBuilder,
                                   BusinessListingStore businessListingStore, ClientIdAndSecretRetriever clientIdAndSecretRetriever) {

        this.tokenRetriever = tokenRetriever;
        this.yelpBusinessSearchRequestBuilder = yelpBusinessSearchRequestBuilder;
        this.businessListingStore = businessListingStore;
        this.clientIdAndSecretRetriever = clientIdAndSecretRetriever;
    }

    public void retrieveAndStore(String location) {
        Integer offset = 0;
        Integer maximumNumberOfBusinesses = 1000;
        Integer responseSize = -1;
        final YelpToken token = tokenRetriever
                .newTokenFor(
                        clientIdAndSecretRetriever.getClientId(),
                        clientIdAndSecretRetriever.getClientSecret());

        while (offset >= 0 && (!maximumNumberOfBusinesses.equals(offset) || responseSize == 0)){
             Result<BusinessSearchResult> businessSearchResultResult = yelpBusinessSearchRequestBuilder
                    .withToken(
                            token)
                    .withLocation(location)
                    .withOffset(offset)
                    .execute();

            businessSearchResultResult
                    .getResponseBody()
                    .getBusinesses()
                    .stream()
                    .filter(businessListing -> businessListing.getPhone() != null)
                    .forEach(businessListingStore::saveOrUpDate);


            responseSize = businessSearchResultResult
                    .getResponseBody()
                    .getBusinesses().size();
            offset = offset + responseSize;
            LOGGER.log(Level.INFO, "{0} inserted results for location \"{1}\"", new Object[]{offset, location});
        }
    }
}
