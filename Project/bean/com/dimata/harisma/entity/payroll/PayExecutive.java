/*
 * PayExecutive.java
 *
 * Created on March 31, 2007, 1:47 PM
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
public class PayExecutive extends Entity {
     // private long genId;
	private String taxForm="";
	private String executiveName="";
	
    
    /** Creates a new instance of PayExecutive */
    public PayExecutive() {
    }
    
    /**
     * Getter for property taxForm.
     * @return Value of property taxForm.
     */
    public String getTaxForm() {
        return taxForm;
    }
    
    /**
     * Setter for property taxForm.
     * @param taxForm New value of property taxForm.
     */
    public void setTaxForm(String taxForm) {
         if (taxForm == null) {
	     taxForm = ""; 
	 } 
        this.taxForm = taxForm;
    }
    
    /**
     * Getter for property executiveName.
     * @return Value of property executiveName.
     */
    public String getExecutiveName() {
        return executiveName;
    }
    
    /**
     * Setter for property executiveName.
     * @param executiveName New value of property executiveName.
     */
    public void setExecutiveName(String executiveName) {
          if (executiveName == null) {
	     executiveName = ""; 
	  } 
        
        this.executiveName = executiveName;
    }
    
}
