/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.logrpt;

/**
 *
 * @author user
 */
public class LogReportListItem extends LogReport {
   private String logTypeName;
    private String logCategoryName;
    private String logPasalUmum;
    private String logPasalKhusus;
    private String logLocation;
    private String logCustomer;

    public String getLogTypeName() {
        return logTypeName;
    }

    public void setLogTypeName(String logTypeName) {
        this.logTypeName = logTypeName;
    }

    public String getLogCategoryName() {
        return logCategoryName;
    }

    public void setLogCategoryName(String logCategoryName) {
        this.logCategoryName = logCategoryName;
    }

    public String getLogPasalUmum() {
        return logPasalUmum;
    }

    public void setLogPasalUmum(String logPasalUmum) {
        this.logPasalUmum = logPasalUmum;
    }

    public String getLogPasalKhusus() {
        return logPasalKhusus;
    }

    public void setLogPasalKhusus(String logPasalKhusus) {
        this.logPasalKhusus = logPasalKhusus;
    }

    public String getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(String logLocation) {
        this.logLocation = logLocation;
    }

    /**
     * @return the logCustomer
     */
    public String getLogCustomer() {
        return logCustomer;
    }

    /**
     * @param logCustomer the logCustomer to set
     */
    public void setLogCustomer(String logCustomer) {
        this.logCustomer = logCustomer;
    }
}
