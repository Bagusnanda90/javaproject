/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Kartika
 */
public class EmployeeSum {
    private long reportItemId = 0;
    private String reportItemName = "";
    private String reportItemDesc = "";
    private double reportItemNumber = 0.0;

    /**
     * @return the reportItemId
     */
    public long getReportItemId() {
        return reportItemId;
    }

    /**
     * @param reportItemId the reportItemId to set
     */
    public void setReportItemId(long reportItemId) {
        this.reportItemId = reportItemId;
    }

    /**
     * @return the reportItemName
     */
    public String getReportItemName() {
        return reportItemName;
    }

    /**
     * @param reportItemName the reportItemName to set
     */
    public void setReportItemName(String reportItemName) {
        this.reportItemName = reportItemName;
    }

    /**
     * @return the reportItemDesc
     */
    public String getReportItemDesc() {
        return reportItemDesc;
    }

    /**
     * @param reportItemDesc the reportItemDesc to set
     */
    public void setReportItemDesc(String reportItemDesc) {
        this.reportItemDesc = reportItemDesc;
    }

    /**
     * @return the reportItemNumber
     */
    public double getReportItemNumber() {
        return reportItemNumber;
    }

    /**
     * @param reportItemNumber the reportItemNumber to set
     */
    public void setReportItemNumber(double reportItemNumber) {
        this.reportItemNumber = reportItemNumber;
    }

}
