/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.adjusmentworkingday;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Devin
 */
public class AdjusmentWorkingDay extends Entity {
    private long employeeId;
    private long locationId;
    private double sistemWorkHours;
    private double adjusmentWorkingDay;
    private String fullName;
    private String locationName;
    private String employeeNum;

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
     * @return the sistemWorkHours
     */
    public double getSistemWorkHours() {
        return sistemWorkHours;
    }

    /**
     * @param sistemWorkHours the sistemWorkHours to set
     */
    public void setSistemWorkHours(double sistemWorkHours) {
        this.sistemWorkHours = sistemWorkHours;
    }

    /**
     * @return the adjusmentWorkingDay
     */
    public double getAdjusmentWorkingDay() {
        return adjusmentWorkingDay;
    }

    /**
     * @param adjusmentWorkingDay the adjusmentWorkingDay to set
     */
    public void setAdjusmentWorkingDay(double adjusmentWorkingDay) {
        this.adjusmentWorkingDay = adjusmentWorkingDay;
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
     * @return the employeeNum
     */
    public String getEmployeeNum() {
        return employeeNum;
}

    /**
     * @param employeeNum the employeeNum to set
     */
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }
}
