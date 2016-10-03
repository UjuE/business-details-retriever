package com.ujuezeoke.businessdetails.dao;

import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessListing;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessLocation;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Obianuju Ezeoke on 30/09/2016.
 */
public class BusinessListingStoreTest {

    private Path file;

    @Before
    public void setUp() throws IOException {
        file = Files.createTempFile("testDb", ".db");
    }

    @After
    public void tearDown() throws IOException {
        if (file != null) {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void createStoreCreatesFile() throws Exception {
        BusinessListingStore.createOrConnectTo(file);
        assertThat(file.toFile().exists(), is(true));
    }

    @Test
    public void storeAndRetrieveBusinessListing() {
        final BusinessListingStore businessListingStore = BusinessListingStore.createOrConnectTo(file)
                .createBusinessListingTableIfNotExists();

        final BusinessLocation location = new BusinessLocation("address1", "address2",
                "address3", "e1q 7bh", "city", "UK");
        final BusinessListing expectedBusinessListing = new BusinessListing("id", "name", "phone", location);

        businessListingStore
                .saveOrUpDate(expectedBusinessListing);


        BusinessListing businessListing = businessListingStore.getById("id");
        assertThat(businessListing, is(expectedBusinessListing));
    }

    @Test
    public void retrieveAllBusinessListings() {
        final BusinessListingStore businessListingStore = BusinessListingStore.createOrConnectTo(file)
                .createBusinessListingTableIfNotExists();

        final BusinessLocation location = new BusinessLocation("address1", "address2", "address3",
                "e1q 7bh", "city", "UK");
        final BusinessListing expectedBusinessListing1 = new BusinessListing("id", "name", "phone", location);
        final BusinessListing expectedBusinessListing2 = new BusinessListing("id2", "name2", "phone2", location);

        businessListingStore
                .saveOrUpDate(expectedBusinessListing1);
        businessListingStore
                .saveOrUpDate(expectedBusinessListing2);


        final Collection<BusinessListing> allBusinessListings = businessListingStore.getAllBusinessListings();
        assertThat(allBusinessListings.size(), is(2));
    }

    @Test
    public void updatesExistingListings() {
        final BusinessListingStore businessListingStore = BusinessListingStore.createOrConnectTo(file)
                .createBusinessListingTableIfNotExists();

        final BusinessLocation location = new BusinessLocation("address1", "address2", "address3", "e1q 7bh",
                "city", "UK");
        final BusinessListing expectedBusinessListing1 = new BusinessListing("id", "name", "phone", location);
        final Date contractEndDate = DateTime.now().toDate();


        businessListingStore
                .saveOrUpDate(expectedBusinessListing1);

//        expectedBusinessListing1.setContractEndDate(contractEndDate);

        businessListingStore
                .saveOrUpDate(expectedBusinessListing1);


        final Collection<BusinessListing> allBusinessListings = businessListingStore.getAllBusinessListings();
        assertThat(allBusinessListings.size(), is(1));
//        assertThat(allBusinessListings.iterator().next().getContractEndDate().toString(), is(contractEndDate.toString()));
    }
}