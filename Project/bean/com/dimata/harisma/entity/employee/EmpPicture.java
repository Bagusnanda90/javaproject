/*
 * EmpPicture.java
 *
 * Created on November 30, 2007, 10:54 AM
 */

package com.dimata.harisma.entity.employee;

/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class EmpPicture extends Entity{
    private long picEmpId;
    private long employeeId;
    private String pic="";
    
    /** Creates a new instance of EmpPicture */
    public EmpPicture() {
    }
    
    /**
     * Getter for property picEmpId.
     * @return Value of property picEmpId.
     */
    public long getPicEmpId() {
        return picEmpId;
    }
    
    /**
     * Setter for property picEmpId.
     * @param picEmpId New value of property picEmpId.
     */
    public void setPicEmpId(long picEmpId) {
        this.picEmpId = picEmpId;
    }
    
    /**
     * Getter for property employeeId.
     * @return Value of property employeeId.
     */
    public long getEmployeeId() {
        return employeeId;
    }
    
    /**
     * Setter for property employeeId.
     * @param employeeId New value of property employeeId.
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    /**
     * Getter for property pic.
     * @return Value of property pic.
     */
    public java.lang.String getPic() {
        return pic;
    }
    
    /**
     * Setter for property pic.
     * @param pic New value of property pic.
     */
    public void setPic(java.lang.String pic) {
        this.pic = pic;
    }
    
}
