/*
 * Ovt_Employee_Idx.java
 *
 * Created on April 6, 2007, 5:09 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  emerliana
 */

public class Ovt_Employee_Idx extends Entity{
    
    private long ovt_idx_id = 0;
    private long employee_id = 0;
    private double value_idx;
    
    /**
     * Getter for property ovt_idx_id.
     * @return Value of property ovt_idx_id.
     */
    public long getOvt_idx_id() {
        return ovt_idx_id;
    }    
    
    /**
     * Setter for property ovt_idx_id.
     * @param ovt_idx_id New value of property ovt_idx_id.
     */
    public void setOvt_idx_id(long ovt_idx_id) {
        this.ovt_idx_id = ovt_idx_id;
    }
    
    /**
     * Getter for property employee_id.
     * @return Value of property employee_id.
     */
    public long getEmployee_id() {
        return employee_id;
    }
    
    /**
     * Setter for property employee_id.
     * @param employee_id New value of property employee_id.
     */
    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }
    
    /**
     * Getter for property value_idx.
     * @return Value of property value_idx.
     */
    public double getValue_idx() {
        return value_idx;
    }
    
    /**
     * Setter for property value_idx.
     * @param value_idx New value of property value_idx.
     */
    public void setValue_idx(double value_idx) {
        this.value_idx = value_idx;
    }
    
}
