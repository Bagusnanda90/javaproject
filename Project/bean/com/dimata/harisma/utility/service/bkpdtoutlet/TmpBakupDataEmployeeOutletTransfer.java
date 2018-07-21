/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.service.bkpdtoutlet;

/**
 *
 * @author Satrya Ramayu
 */
public class TmpBakupDataEmployeeOutletTransfer {
    private String employeeId;
    private String sQuery="";
    private String scheduleId;

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the sQuery
     */
    public String getsQuery() {
        return sQuery;
    }

    /**
     * @param sQuery the sQuery to set
     */
    public void setsQuery(String sQuery) {
        this.sQuery = sQuery;
    }

    /**
     * @return the scheduleId
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId the scheduleId to set
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}
