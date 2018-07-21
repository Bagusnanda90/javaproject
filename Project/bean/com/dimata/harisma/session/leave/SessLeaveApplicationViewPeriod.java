/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.leave;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class SessLeaveApplicationViewPeriod {
    private long leaveAppId;
    private long employeeId;
    private Date alStartDate;
    private Date alEndDate;
    private Date llStartDate;
    private Date llEndDate;
    private Date dpStartDate;
    private Date dpEndDate;
    private Date spStartDate;
    private Date spEndDate;

    /**
     * @return the leaveAppId
     */
    public long getLeaveAppId() {
        return leaveAppId;
    }

    /**
     * @param leaveAppId the leaveAppId to set
     */
    public void setLeaveAppId(long leaveAppId) {
        this.leaveAppId = leaveAppId;
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
     * @return the alStartDate
     */
    public Date getAlStartDate() {
        return alStartDate;
    }

    /**
     * @param alStartDate the alStartDate to set
     */
    public void setAlStartDate(Date alStartDate) {
        this.alStartDate = alStartDate;
    }

    /**
     * @return the alEndDate
     */
    public Date getAlEndDate() {
        return alEndDate;
    }

    /**
     * @param alEndDate the alEndDate to set
     */
    public void setAlEndDate(Date alEndDate) {
        this.alEndDate = alEndDate;
    }

    /**
     * @return the llStartDate
     */
    public Date getLlStartDate() {
        return llStartDate;
    }

    /**
     * @param llStartDate the llStartDate to set
     */
    public void setLlStartDate(Date llStartDate) {
        this.llStartDate = llStartDate;
    }

    /**
     * @return the llEndDate
     */
    public Date getLlEndDate() {
        return llEndDate;
    }

    /**
     * @param llEndDate the llEndDate to set
     */
    public void setLlEndDate(Date llEndDate) {
        this.llEndDate = llEndDate;
    }

    /**
     * @return the dpStartDate
     */
    public Date getDpStartDate() {
        return dpStartDate;
    }

    /**
     * @param dpStartDate the dpStartDate to set
     */
    public void setDpStartDate(Date dpStartDate) {
        this.dpStartDate = dpStartDate;
    }

    /**
     * @return the dpEndDate
     */
    public Date getDpEndDate() {
        return dpEndDate;
    }

    /**
     * @param dpEndDate the dpEndDate to set
     */
    public void setDpEndDate(Date dpEndDate) {
        this.dpEndDate = dpEndDate;
    }

    /**
     * @return the spStartDate
     */
    public Date getSpStartDate() {
        return spStartDate;
    }

    /**
     * @param spStartDate the spStartDate to set
     */
    public void setSpStartDate(Date spStartDate) {
        this.spStartDate = spStartDate;
    }

    /**
     * @return the spEndDate
     */
    public Date getSpEndDate() {
        return spEndDate;
    }

    /**
     * @param spEndDate the spEndDate to set
     */
    public void setSpEndDate(Date spEndDate) {
        this.spEndDate = spEndDate;
    }
}
