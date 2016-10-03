package com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects;

import org.joda.time.DateTime;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class BusinessLocation {
    private final String address1;
    private final String address2;
    private final String address3;
    private final String zip_code;
    private final String city;
    private final String country;

    public BusinessLocation(String address1, String address2, String address3,
                            String zip_code, String city, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.zip_code = zip_code;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t address1='" + address1 + '\'' +
                ",\n\t address2='" + address2 + '\'' +
                ",\n\t address3='" + address3 + '\'' +
                ",\n\t zip_code='" + zip_code + '\'' +
                ",\n\t city='" + city + '\'' +
                ",\n\t country='" + country + '\'' +
                "\n\t}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessLocation that = (BusinessLocation) o;

        if (address1 != null ? !address1.equals(that.address1) : that.address1 != null) return false;
        if (address2 != null ? !address2.equals(that.address2) : that.address2 != null) return false;
        if (address3 != null ? !address3.equals(that.address3) : that.address3 != null) return false;
        if (zip_code != null ? !zip_code.equals(that.zip_code) : that.zip_code != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return country != null ? country.equals(that.country) : that.country == null;

    }

    @Override
    public int hashCode() {
        int result = address1 != null ? address1.hashCode() : 0;
        result = 31 * result + (address2 != null ? address2.hashCode() : 0);
        result = 31 * result + (address3 != null ? address3.hashCode() : 0);
        result = 31 * result + (zip_code != null ? zip_code.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getZipCode() {
        return zip_code;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
