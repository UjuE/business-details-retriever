package com.ujuezeoke.businessdetails.yelp.tinytypes.jsonObjects;

import java.util.Date;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public class BusinessListing {
    private final String id;
    private final String name;
    private final String phone;
    private final BusinessLocation location;
    private String currentEnergySupplier;
    private Date contractEndDate;

    public BusinessListing(String id, String name, String phone, BusinessLocation location) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public BusinessListing(String id, String businessName, String phone,
                           BusinessLocation location, String currentEnergySupplier, Date date) {

        this.id = id;
        name = businessName;
        this.phone = phone;
        this.location = location;
        this.currentEnergySupplier = currentEnergySupplier;
        this.contractEndDate = date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCurrentEnergySupplier() {
        return currentEnergySupplier;
    }

    public void setCurrentEnergySupplier(String currentEnergySupplier) {
        this.currentEnergySupplier = currentEnergySupplier;
    }

    public BusinessLocation getLocation() {
        return location;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessListing that = (BusinessListing) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        return location != null ? location.equals(that.location) : that.location == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BusinessListing{" +
                "\nid='" + id + '\'' +
                ",\nname='" + name + '\'' +
                ",\nphone='" + phone + '\'' +
                ",\nlocation=" + location +
                ",\ncurrentEnergySupplier='" + currentEnergySupplier + '\'' +
                ",\ncontractEndDate=" + contractEndDate +
                '}';
    }
}
