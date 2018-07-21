/*
 * PayTaxItemCode.java
 *
 * Created on April 4, 2007, 2:22 PM
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
public class PayTaxItemCode  extends Entity{
    private String taxItemCode="";
    private String taxCode="";
    private String taxItemName="";
  
    
    /** Creates a new instance of PayTaxItemCode */
    public PayTaxItemCode() {
    }
    
    /**
     * Getter for property taxItemCode.
     * @return Value of property taxItemCode.
     */
    public String getTaxItemCode() {
        return taxItemCode;
    }
    
    /**
     * Setter for property taxItemCode.
     * @param taxItemCode New value of property taxItemCode.
     */
    public void setTaxItemCode(String taxItemCode) {
        if (taxItemCode == null) {
	     taxItemCode = ""; 
	 } 
        this.taxItemCode = taxItemCode;
    }
    
    /**
     * Getter for property taxCode.
     * @return Value of property taxCode.
     */
    public String getTaxCode() {
        return taxCode;
    }
    
    /**
     * Setter for property taxCode.
     * @param taxCode New value of property taxCode.
     */
    public void setTaxCode(String taxCode) {
         if (taxCode == null) {
	     taxCode = ""; 
	 } 
        this.taxCode = taxCode;
    }
    
    /**
     * Getter for property taxItemName.
     * @return Value of property taxItemName.
     */
    public String getTaxItemName() {
        return taxItemName;
    }
    
    /**
     * Setter for property taxItemName.
     * @param taxItemName New value of property taxItemName.
     */
    public void setTaxItemName(String taxItemName) {
        if (taxItemName == null) {
	     taxItemName = ""; 
	 } 
        this.taxItemName = taxItemName;
    }
    
}
