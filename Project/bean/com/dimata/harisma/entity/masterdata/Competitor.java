/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class Competitor extends Entity { 

   
	private long competitorId;
        private String companyName="";
        private String address="";
        private String website="";
        private String email="";
        private String telephone="";
        private String fax="";
        private String contact_person="";
        private String description="";
        private long countryId;
        private long provinceId;
        private long  regionId;
        private long  subregionId;
        private String geoAddress;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the competitorId
     */
    public long getCompetitorId() {
        return competitorId;
    }

    /**
     * @param competitorId the competitorId to set
     */
    public void setCompetitorId(long competitorId) {
        this.competitorId = competitorId;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the contact_person
     */
    public String getContact_person() {
        return contact_person;
    }

    /**
     * @param contact_person the contact_person to set
     */
    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the countryId
     */
    public long getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the provinceId
     */
    public long getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId the provinceId to set
     */
    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return the regionId
     */
    public long getRegionId() {
        return regionId;
    }

    /**
     * @param regionId the regionId to set
     */
    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }


    /**
     * @return the subregionId
     */
    public long getSubregionId() {
        return subregionId;
    }

    /**
     * @param subregionId the subregionId to set
     */
    public void setSubregionId(long subregionId) {
        this.subregionId = subregionId;
    }

    /**
     * @return the geoAddress
     */
    public String getGeoAddress() {
        return geoAddress;
    }

    /**
     * @param geoAddress the geoAddress to set
     */
    public void setGeoAddress(String geoAddress) {
        this.geoAddress = geoAddress;
    }



}
