/*
 * PayAdditional.java
 *
 * Created on November 23, 2007, 1:36 PM
 */

package com.dimata.harisma.entity.payroll;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class PayAdditional extends Entity{
    private long periodId=0;
    private String summaryName="";
    private double value;
   
    /** Creates a new instance of PayAdditional */
    public PayAdditional() {
    }
    
    /**
     * Getter for property periodId.
     * @return Value of property periodId.
     */
    public long getPeriodId() {
        return periodId;
    }
    
    /**
     * Setter for property periodId.
     * @param periodId New value of property periodId.
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }
    
    /**
     * Getter for property summaryName.
     * @return Value of property summaryName.
     */
    public java.lang.String getSummaryName() {
        return summaryName;
    }
    
    /**
     * Setter for property summaryName.
     * @param summaryName New value of property summaryName.
     */
    public void setSummaryName(java.lang.String summaryName) {
        this.summaryName = summaryName;
    }
    
    /**
     * Getter for property value.
     * @return Value of property value.
     */
    public double getValue() {
        return value;
    }
    
    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(double value) {
        this.value = value;
    }
    
}
