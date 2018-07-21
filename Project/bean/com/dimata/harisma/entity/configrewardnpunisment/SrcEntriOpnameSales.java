/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.configrewardnpunisment;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class SrcEntriOpnameSales {

    private long locationId;
    private long jenisSoId;
    private int typeTolerance=-1;
    private double netSalesPeriod;
    private double prosentaseTolerance;
    private double barangHilang;
    private String createLocationName;
    private double plusMinus;
    private String statusOpname;
    private Date dtFromPeriod;
    private Date dtToPeriod;

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
     * @return the jenisSoId
     */
    public long getJenisSoId() {
        return jenisSoId;
    }

    /**
     * @param jenisSoId the jenisSoId to set
     */
    public void setJenisSoId(long jenisSoId) {
        this.jenisSoId = jenisSoId;
    }

    /**
     * @return the typeTolerance
     */
    public int getTypeTolerance() {
        return typeTolerance;
    }

    /**
     * @param typeTolerance the typeTolerance to set
     */
    public void setTypeTolerance(int typeTolerance) {
        this.typeTolerance = typeTolerance;
    }

    /**
     * @return the netSalesPeriod
     */
    public double getNetSalesPeriod() {
        return netSalesPeriod;
    }

    /**
     * @param netSalesPeriod the netSalesPeriod to set
     */
    public void setNetSalesPeriod(double netSalesPeriod) {
        this.netSalesPeriod = netSalesPeriod;
    }

    /**
     * @return the prosentaseTolerance
     */
    public double getProsentaseTolerance() {
        return prosentaseTolerance;
    }

    /**
     * @param prosentaseTolerance the prosentaseTolerance to set
     */
    public void setProsentaseTolerance(double prosentaseTolerance) {
        this.prosentaseTolerance = prosentaseTolerance;
    }

    /**
     * @return the barangHilang
     */
    public double getBarangHilang() {
        return barangHilang;
    }

    /**
     * @param barangHilang the barangHilang to set
     */
    public void setBarangHilang(double barangHilang) {
        this.barangHilang = barangHilang;
    }

    /**
     * @return the createLocationName
     */
    public String getCreateLocationName() {
        if(createLocationName==null){
            return "";
        }
        return createLocationName;
    }

    /**
     * @param createLocationName the createLocationName to set
     */
    public void setCreateLocationName(String createLocationName) {
        this.createLocationName = createLocationName;
    }

   
     public void setPlusMinus(double plusMinus) {
        this.plusMinus = plusMinus;
    }
    /**
     * @return the plusMinus
     */
    public double getPlusMinus() {
        //plusMinus = Math.abs(prosentaseTolerance - barangHilang);
        return plusMinus;
    }

    public void setStatusOpname(String statusOpname) {
        this.statusOpname = statusOpname;
    }
    /**
     * @return the statusOpname
     */
    public String getStatusOpname() {
        if (prosentaseTolerance > barangHilang) {
            statusOpname = EntriOpnameSales.fieldNamesStatus[EntriOpnameSales.STATUS_REWARD];
        } else {
            statusOpname = EntriOpnameSales.fieldNamesStatus[EntriOpnameSales.STATUS_PUNISMENT];
        }
        return statusOpname;
    }

   

    /**
     * @return the dtFromPeriod
     */
    public Date getDtFromPeriod() {
        return dtFromPeriod;
    }

    /**
     * @param dtFromPeriod the dtFromPeriod to set
     */
    public void setDtFromPeriod(Date dtFromPeriod) {
        this.dtFromPeriod = dtFromPeriod;
    }

    /**
     * @return the dtToPeriod
     */
    public Date getDtToPeriod() {
        return dtToPeriod;
    }

    /**
     * @param dtToPeriod the dtToPeriod to set
     */
    public void setDtToPeriod(Date dtToPeriod) {
        this.dtToPeriod = dtToPeriod;
    }
}
