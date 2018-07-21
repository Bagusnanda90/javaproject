
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 * *****************************************************************
 */
package com.dimata.harisma.entity.masterdata;

/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class Division extends Entity {

    private String division = "";
    private String description = "";
    /*Add Field by Hendra Putu | 2015-07-27 | TYPE_OF_DIVISION*/
    private long divisionTypeId = 0;
    private long companyId = 0;
    private String address = "";
    private String city = "";
    private String npwp = "";
    private String province = "";
    private String region = "";
    private String subRegion = "";
    private String village = "";
    private String area = "";
    private String telphone = "";
    private String faxNumber = "";
    private int validStatus = 0;
    private Date validStart = null;
    private Date validEnd = null;
    private String pemotong = "";
    /* Update by Hendra Putu | 2016-09-27 | emp yg menanda tangani slip gaji */
    private long employeeId = 0;

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        if (division == null) {
            division = "";
        }
        this.division = division;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }
        this.description = description;
    }

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

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
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the npwp
     */
    public String getNpwp() {
        return npwp;
    }

    /**
     * @param npwp the npwp to set
     */
    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the subRegion
     */
    public String getSubRegion() {
        return subRegion;
    }

    /**
     * @param subRegion the subRegion to set
     */
    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    /**
     * @return the village
     */
    public String getVillage() {
        return village;
    }

    /**
     * @param village the village to set
     */
    public void setVillage(String village) {
        this.village = village;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone the telphone to set
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * @return the faxNumber
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * @param faxNumber the faxNumber to set
     */
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    /**
     * @return the divisionTypeId
     */
    public long getDivisionTypeId() {
        return divisionTypeId;
    }

    /**
     * @param divisionTypeId the divisionTypeId to set
     */
    public void setDivisionTypeId(long divisionTypeId) {
        this.divisionTypeId = divisionTypeId;
    }

    /**
     * @return the validStatus
     */
    public int getValidStatus() {
        return validStatus;
    }

    /**
     * @param validStatus the validStatus to set
     */
    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    /**
     * @return the validStart
     */
    public Date getValidStart() {
        return validStart;
    }

    /**
     * @param validStart the validStart to set
     */
    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    /**
     * @return the validEnd
     */
    public Date getValidEnd() {
        return validEnd;
    }

    /**
     * @param validEnd the validEnd to set
     */
    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }

    /**
     * @return the pemotong
     */
    public String getPemotong() {
        return pemotong;
    }

    /**
     * @param pemotong the pemotong to set
     */
    public void setPemotong(String pemotong) {
        this.pemotong = pemotong;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
}

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
}
