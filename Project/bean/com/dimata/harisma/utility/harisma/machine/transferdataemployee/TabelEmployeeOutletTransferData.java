/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class TabelEmployeeOutletTransferData {
 private long employeeId;
 private String fullName;
 private String employeeNumber;
 private String phone;
 private String phoneEmergency;
 private String address;
 private String permanentAddress;
 private String addressEmg;
 private String empPin;
 
 private String loginId;
 private String password;
 private long userId;
 private String handPhone;
 private int sex;
 private Date commencingDate;
 private int resigned;
 private Date resignedDate;
 private String barcodeNumber;
 
 //hr_working histori
 private long workHistoryId;
 private Date workHistoryDateStart;
 private Date workHistoryDateEnd;
 private long empCategoryId;
 
 //hr_position
 private long positionId;
 private String positionName;
 
 //hr_location
private long locationId;
 private String locationName;
 private String locationCode;
 
 
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

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the employeeNumber
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * @param employeeNumber the employeeNumber to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the phoneEmergency
     */
    public String getPhoneEmergency() {
        return phoneEmergency;
    }

    /**
     * @param phoneEmergency the phoneEmergency to set
     */
    public void setPhoneEmergency(String phoneEmergency) {
        this.phoneEmergency = phoneEmergency;
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
     * @return the permanentAddress
     */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /**
     * @param permanentAddress the permanentAddress to set
     */
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    /**
     * @return the addressEmg
     */
    public String getAddressEmg() {
        return addressEmg;
    }

    /**
     * @param addressEmg the addressEmg to set
     */
    public void setAddressEmg(String addressEmg) {
        this.addressEmg = addressEmg;
    }

    /**
     * @return the empPin
     */
    public String getEmpPin() {
        return empPin;
    }

    /**
     * @param empPin the empPin to set
     */
    public void setEmpPin(String empPin) {
        this.empPin = empPin;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * @param loginId the loginId to set
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the workHistoryId
     */
    public long getWorkHistoryId() {
        return workHistoryId;
    }

    /**
     * @param workHistoryId the workHistoryId to set
     */
    public void setWorkHistoryId(long workHistoryId) {
        this.workHistoryId = workHistoryId;
    }

    /**
     * @return the workHistoryDateStart
     */
    public Date getWorkHistoryDateStart() {
        return workHistoryDateStart;
    }

    /**
     * @param workHistoryDateStart the workHistoryDateStart to set
     */
    public void setWorkHistoryDateStart(Date workHistoryDateStart) {
        this.workHistoryDateStart = workHistoryDateStart;
    }

    /**
     * @return the workHistoryDateEnd
     */
    public Date getWorkHistoryDateEnd() {
        return workHistoryDateEnd;
    }

    /**
     * @param workHistoryDateEnd the workHistoryDateEnd to set
     */
    public void setWorkHistoryDateEnd(Date workHistoryDateEnd) {
        this.workHistoryDateEnd = workHistoryDateEnd;
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName the locationName to set
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the empCategoryId
     */
    public long getEmpCategoryId() {
        return empCategoryId;
    }

    /**
     * @param empCategoryId the empCategoryId to set
     */
    public void setEmpCategoryId(long empCategoryId) {
        this.empCategoryId = empCategoryId;
    }

    /**
     * @return the handPhone
     */
    public String getHandPhone() {
        return handPhone;
    }

    /**
     * @param handPhone the handPhone to set
     */
    public void setHandPhone(String handPhone) {
        this.handPhone = handPhone;
    }

    /**
     * @return the sex
     */
    public int getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * @return the commencingDate
     */
    public Date getCommencingDate() {
        return commencingDate;
    }

    /**
     * @param commencingDate the commencingDate to set
     */
    public void setCommencingDate(Date commencingDate) {
        this.commencingDate = commencingDate;
    }

    /**
     * @return the resigned
     */
    public int getResigned() {
        return resigned;
    }

    /**
     * @param resigned the resigned to set
     */
    public void setResigned(int resigned) {
        this.resigned = resigned;
    }

    /**
     * @return the resignedDate
     */
    public Date getResignedDate() {
        return resignedDate;
    }

    /**
     * @param resignedDate the resignedDate to set
     */
    public void setResignedDate(Date resignedDate) {
        this.resignedDate = resignedDate;
    }

    /**
     * @return the barcodeNumber
     */
    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    /**
     * @param barcodeNumber the barcodeNumber to set
     */
    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    /**
     * @return the locationCode
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * @param locationCode the locationCode to set
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    
}
