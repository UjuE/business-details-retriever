package com.ujuezeoke.businessdetails.dao;

import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessListing;
import com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects.BusinessLocation;
import org.joda.time.DateTime;

import java.nio.file.Path;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Created by Obianuju Ezeoke on 30/09/2016.
 */
public class BusinessListingStore {

    private final static Logger LOGGER = Logger.getLogger(BusinessListingStore.class.getName());

    private static final String BUSINESS_LISTINGS_TABLE_NAME = "business_listings";
    private static final String ID_COLUMN_NAME = "id";
    private static final String BUSINESS_NAME_COLUMN_NAME = "business_name";
    private static final String PHONE_COLUMN_NAME = "phone";
    private static final String CURRENT_ENERGY_SUPPLIER_COLUMN_NAME = "current_energy_supplier";
    private static final String ADDRESS1_COLUMN_NAME = "address1";
    private static final String ADDRESS2_COLUMN_NAME = "address2";
    private static final String ADDRESS3_COLUMN_NAME = "address3";
    private static final String ZIP_CODE_COLUMN_NAME = "zip_code";
    private static final String CITY_COLUMN_NAME = "city";
    private static final String COUNTRY_COLUMN_NAME = "country";
    private static final String CONTRACT_END_DATE_COLUMN_NAME = "contract_end_date";

    private final String jdbcUrl;

    private BusinessListingStore(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    //TODO do not forget all sad paths
    public void saveOrUpDate(BusinessListing businessListing) {
        performTask(connection -> {
            final BusinessLocation location = businessListing.getLocation();
            try (Statement statement = connection.createStatement()) {
                final String sql = "INSERT OR REPLACE INTO " + BUSINESS_LISTINGS_TABLE_NAME +
                        "(" +
                        String.join(",",
                                ID_COLUMN_NAME,
                                BUSINESS_NAME_COLUMN_NAME,
                                PHONE_COLUMN_NAME,
                                CURRENT_ENERGY_SUPPLIER_COLUMN_NAME,
                                CONTRACT_END_DATE_COLUMN_NAME,
                                ADDRESS1_COLUMN_NAME,
                                ADDRESS2_COLUMN_NAME,
                                ADDRESS3_COLUMN_NAME,
                                ZIP_CODE_COLUMN_NAME,
                                CITY_COLUMN_NAME,
                                COUNTRY_COLUMN_NAME
                        ) +
                        ")" +
                        "VALUES (" +
                        String.join(",",
                                wrapInQuotes(businessListing.getId()),
                                wrapInQuotes(businessListing.getName()),
                                wrapInQuotes(businessListing.getPhone()),
                                wrapInQuotes(businessListing.getCurrentEnergySupplier()),
                                safelyRetrieveContractEndDate(businessListing),
                                wrapInQuotes(location.getAddress1()),
                                wrapInQuotes(location.getAddress2()),
                                wrapInQuotes(location.getAddress3()),
                                wrapInQuotes(location.getZipCode()),
                                wrapInQuotes(location.getCity()),
                                wrapInQuotes(location.getCountry())
                        ) +
                        ")";

                statement.execute(sql);
                LOGGER.log(Level.INFO, "Inserted or replaced record with id \"{0}\"", businessListing.getId());
            } catch (SQLException e) {
                throw new UnsupportedOperationException("Not Yet Implemented", e);
            }
            return "";
        });
    }

    private String safelyRetrieveContractEndDate(BusinessListing businessListing) {
//        final java.util.Date contractEndDate = businessListing.getContractEndDate();

//        return contractEndDate != null ? String.valueOf(contractEndDate.toInstant().getEpochSecond())
//                : null;
        return null;
    }

    public static BusinessListingStoreCreator createOrConnectTo(Path file) {
        String url = "jdbc:sqlite:" + file.toString();
        try (Connection conn = DriverManager.getConnection(url)) {

            DatabaseMetaData meta = conn.getMetaData();
            LOGGER.info("Using driver " + meta.getDriverName());
            return new BusinessListingStoreCreator(url);

        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "Unable to connect to database", e);
            throw new RuntimeException(e);
        }

    }

    public BusinessListing getById(String id) {
        return performTask(connection -> {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT * FROM " + BUSINESS_LISTINGS_TABLE_NAME +
                        " WHERE id = '" + id + "'";

                final ResultSet resultSet = statement.executeQuery(sql);
                return businessListingFromResultSet(resultSet);
            } catch (SQLException e) {
                throw new UnsupportedOperationException("Not Yet Implemented", e);
            }
        });
    }

    private BusinessListing businessListingFromResultSet(ResultSet resultSet) {
        try {
            final String idValue = resultSet.getString(ID_COLUMN_NAME);
            final String businessNameValue = resultSet.getString(BUSINESS_NAME_COLUMN_NAME);
            final String phoneValue = resultSet.getString(PHONE_COLUMN_NAME);
            final String address1 = resultSet.getString(ADDRESS1_COLUMN_NAME);
            final String address2 = resultSet.getString(ADDRESS2_COLUMN_NAME);
            final String address3 = resultSet.getString(ADDRESS3_COLUMN_NAME);
            final String zip_code = resultSet.getString(ZIP_CODE_COLUMN_NAME);
            final String city = resultSet.getString(CITY_COLUMN_NAME);
            final String country = resultSet.getString(COUNTRY_COLUMN_NAME);
            final String currentEnergySupplier = resultSet.getString(CURRENT_ENERGY_SUPPLIER_COLUMN_NAME);
            final Long contractEndDateInstant = resultSet.getLong(CONTRACT_END_DATE_COLUMN_NAME);

            final BusinessLocation location = new BusinessLocation(
                    address1,
                    address2,
                    address3,
                    zip_code,
                    city,
                    country
            );

            return new BusinessListing(idValue, businessNameValue, phoneValue, location,
                    currentEnergySupplier, Date.from(Instant.ofEpochSecond(contractEndDateInstant)));
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Not Yet Implemented", e);
        }
    }

    public Collection<BusinessListing> getAllBusinessListings() {
        return performTask(connection -> {
            final Set<BusinessListing> businessListings = new HashSet<>();
            String sql = "SELECT * FROM " + BUSINESS_LISTINGS_TABLE_NAME;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    businessListings.add(businessListingFromResultSet(resultSet));
                }
                return businessListings;

            } catch (SQLException e) {
                throw new UnsupportedOperationException("Not Yet Implemented", e);
            }
        });
    }

    public static class BusinessListingStoreCreator {
        private final String jdbcUrl;

        BusinessListingStoreCreator(String url) {
            this.jdbcUrl = url;
        }

        public BusinessListingStore createBusinessListingTableIfNotExists() {
            String sql = "CREATE TABLE IF NOT EXISTS " + BUSINESS_LISTINGS_TABLE_NAME + " (\n" +
                    ID_COLUMN_NAME + " text PRIMARY KEY,\n" +
                    BUSINESS_NAME_COLUMN_NAME + " text NOT NULL,\n" +
                    PHONE_COLUMN_NAME + " text NOT NULL,\n" +
                    CURRENT_ENERGY_SUPPLIER_COLUMN_NAME + " text, " +
                    CONTRACT_END_DATE_COLUMN_NAME + " INTEGER, " +
                    ADDRESS1_COLUMN_NAME + " text, " +
                    ADDRESS2_COLUMN_NAME + " text, " +
                    ADDRESS3_COLUMN_NAME + " text, " +
                    ZIP_CODE_COLUMN_NAME + " text, " +
                    CITY_COLUMN_NAME + " text, " +
                    COUNTRY_COLUMN_NAME + " text " +
                    ");";

            try (Connection connection = DriverManager.getConnection(jdbcUrl);
                 Statement statement = connection.createStatement()) {
                statement.execute(sql);
                LOGGER.info("Created table " + BUSINESS_LISTINGS_TABLE_NAME);
                return new BusinessListingStore(jdbcUrl);
            } catch (SQLException e) {
                throw new UnsupportedOperationException("Not Yet Implemented", e);
            }
        }
    }


    private String wrapInQuotes(String string) {
        return string != null ? format("\"%s\"", string) : null;
    }

    private <RESULT_TYPE> RESULT_TYPE performTask(DatabaseTask<RESULT_TYPE> databaseTaskToPerform) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            return databaseTaskToPerform.run(conn);
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Not Yet Implemented", e);
        }
    }

}
