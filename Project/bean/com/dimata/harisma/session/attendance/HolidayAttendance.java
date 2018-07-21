/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import java.util.Date;

/**
 *
 * @author SATRYA RAMAYU
 */
public class HolidayAttendance {
    private long holidayId;
    private Date holidayDateFrom;
    private Date holidayDateTo;
    private String holidayName;
    private int holidayStatus;
    private int holidayLen;
    private long employeeId;

    /**
     * @return the holidayDateFrom
     */
    public Date getHolidayDateFrom() {
        return holidayDateFrom;
    }

    /**
     * @param holidayDateFrom the holidayDateFrom to set
     */
    public void setHolidayDateFrom(Date holidayDateFrom) {
        this.holidayDateFrom = holidayDateFrom;
    }

    /**
     * @return the holidayDateTo
     */
    public Date getHolidayDateTo() {
        return holidayDateTo;
    }

    /**
     * @param holidayDateTo the holidayDateTo to set
     */
    public void setHolidayDateTo(Date holidayDateTo) {
        this.holidayDateTo = holidayDateTo;
    }

    /**
     * @return the holidayName
     */
    public String getHolidayName() {
        return holidayName;
    }

    /**
     * @param holidayName the holidayName to set
     */
    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    /**
     * @return the holidayStatus
     */
    public int getHolidayStatus() {
        return holidayStatus;
    }

    /**
     * @param holidayStatus the holidayStatus to set
     */
    public void setHolidayStatus(int holidayStatus) {
        this.holidayStatus = holidayStatus;
    }

    /**
     * @return the holidayLen
     */
    public int getHolidayLen() {
        return holidayLen;
    }

    /**
     * @param holidayLen the holidayLen to set
     */
    public void setHolidayLen(int holidayLen) {
        this.holidayLen = holidayLen;
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

    /**
     * @return the holidayId
     */
    public long getHolidayId() {
        return holidayId;
    }

    /**
     * @param holidayId the holidayId to set
     */
    public void setHolidayId(long holidayId) {
        this.holidayId = holidayId;
    }
}
