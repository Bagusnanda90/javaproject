/*
 * PayComponentIn.java
 *
 * Created on June 13, 2007, 9:17 AM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class PayComponentIn extends Entity {
    
    private String compCode="";
    private String compName="";
    
    /** Creates a new instance of PayComponentIn */
    public PayComponentIn() {
    }
    
    /**
     * Getter for property compCode.
     * @return Value of property compCode.
     */
    public String getCompCode() {
        return compCode;
    }    
    
    /**
     * Setter for property compCode.
     * @param compCode New value of property compCode.
     */
    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }
    
    /**
     * Getter for property compName.
     * @return Value of property compName.
     */
    public String getCompName() {
        return compName;
    }
    
    /**
     * Setter for property compName.
     * @param compName New value of property compName.
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }
    
}
