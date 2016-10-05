package com.ujuezeoke.businessdetails;

import com.ujuezeoke.businessdetails.dao.BusinessListingStore;
import com.ujuezeoke.businessdetails.route.AllBusinessesRoute;
import com.ujuezeoke.businessdetails.route.BusinessListingRoute;
import com.ujuezeoke.businessdetails.route.UpdateBusinessListingRoute;
import com.ujuezeoke.businessdetails.swagger.SwaggerParser;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpBusinessSearchRequestBuilder;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpRequestSender;
import com.ujuezeoke.businessdetails.yelp.requestbuilders.YelpTokenRetriever;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import static spark.Spark.*;

import java.io.File;
import java.util.concurrent.*;

/**
 * Created by Obianuju Ezeoke on 03/10/2016.
 */
@SwaggerDefinition(host = "localhost:4567",
        info = @Info(description = "Business Details Retriever API",
                version = "V1.0",
                title = "Some random api for testing",
                contact = @Contact(name = "Obianuju Vivian Ezeoke", url = "https://ujuezeoke.github.io") ) ,
        schemes = { SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS },
        consumes = { "application/json" },
        produces = { "application/json" },
        tags = { @Tag(name = "swagger") })
public class BusinessDetailsRetrieverService {

    private static final String APP_PACKAGE = "com.ujuezeoke.businessdetails.route";

    public static void main(String[] args) {
        final BusinessListingStore businessListingStore = BusinessListingStore
                .createOrConnectTo(new File(".", "plomo_businesses_db.db").toPath())
                .createBusinessListingTableIfNotExists();
//        retrieveAndStoreBusinessDetails(businessListingStore);

//        configureSwagger();
        get("/business", AllBusinessesRoute.usingStore(businessListingStore));
        get("/business/:id", BusinessListingRoute.usingStore(businessListingStore));
        patch("/business/:id", UpdateBusinessListingRoute.usingStore(businessListingStore));
    }

    private static void configureSwagger() {
        try {
            // Build swagger json description
            get("/swagger", (req, res) -> SwaggerParser.getSwaggerJson(APP_PACKAGE));

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private static void retrieveAndStoreBusinessDetails(BusinessListingStore businessListingStore) {
        final String baseUrl = "https://api.yelp.com";
        final YelpRequestSender requestSender = new YelpRequestSender();
        final RetrieveAndStoreDetails retrieveAndStoreDetails =
                new RetrieveAndStoreDetails(new YelpTokenRetriever(baseUrl, requestSender),
                new YelpBusinessSearchRequestBuilder(baseUrl, requestSender),
                businessListingStore,
                new EnvironmentVariableClientIdAndSecretRetriever()
        );

        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService
                . execute(() ->
                                retrieveAndStoreDetails.retrieveAndStore("York"));


    }
}
