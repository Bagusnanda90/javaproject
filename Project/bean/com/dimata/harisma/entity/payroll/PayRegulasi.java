/*
 * PayRegulasi.java
 *
 * Created on August 29, 2007, 3:55 PM
 */

package com.dimata.harisma.entity.payroll;

/**
 *
 * @author  emerliana
 */

/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class PayRegulasi extends Entity{
     
    private long regulasi_id;
    private String period;
    private Date startDate;
    private Date endDate;
    private int status;
    
    
    /**
     * Getter for property regulasi_id.
     * @return Value of property regulasi_id.
     */
    public long getRegulasi_id() {
        return regulasi_id;
    }    
    
    /**
     * Setter for property regulasi_id.
     * @param regulasi_id New value of property regulasi_id.
     */
    public void setRegulasi_id(long regulasi_id) {
        this.regulasi_id = regulasi_id;
    }    
    
    /**
     * Getter for property period.
     * @return Value of property period.
     */
    public java.lang.String getPeriod() {
        return period;
    }
    
    /**
     * Setter for property period.
     * @param period New value of property period.
     */
    public void setPeriod(java.lang.String period) {
        this.period = period;
    }
    
    /**
     * Getter for property startDate.
     * @return Value of property startDate.
     */
    public java.util.Date getStartDate() {
        return startDate;
    }
    
    /**
     * Setter for property startDate.
     * @param startDate New value of property startDate.
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }
    
    /**
     * Getter for property endDate.
     * @return Value of property endDate.
     */
    public java.util.Date getEndDate() {
        return endDate;
    }
    
    /**
     * Setter for property endDate.
     * @param endDate New value of property endDate.
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {
        return status;
    }
    
    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
}
