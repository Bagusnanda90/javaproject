/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 *
 * @author GUSWIK
 */
public class PayComponenttemp {
    private long compId;
    private String compCode="";
    private double compValue;
    private int compType;
    private String compName; 
    private int decimalFormat=0; 
    /**
     * @return the compId
     */
    public long getCompId() {
        return compId;
    }

    /**
     * @param compId the compId to set
     */
    public void setCompId(long compId) {
        this.compId = compId;
    }

    /**
     * @return the compCode
     */
    public String getCompCode() {
        return compCode;
    }

    /**
     * @param compCode the compCode to set
     */
    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    /**
     * @return the compValue
     */
    public double getCompValue() {
        return compValue;
    }

    /**
     * @param compValue the compValue to set
     */
    public void setCompValue(double compValue) {
        this.compValue = compValue;
    }

    /**
     * @return the compType
     */
    public int getCompType() {
        return compType;
    }

    /**
     * @param compType the compType to set
     */
    public void setCompType(int compType) {
        this.compType = compType;
    }

    /**
     * @return the compName
     */
    public String getCompName() {
        return compName;
    }

    /**
     * @param compName the compName to set
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }

    /**
     * @return the decimalFormat
     */
    public int getDecimalFormat() {
        return decimalFormat;
    }

    /**
     * @param decimalFormat the decimalFormat to set
     */
    public void setDecimalFormat(int decimalFormat) {
        this.decimalFormat = decimalFormat;
    }
}
