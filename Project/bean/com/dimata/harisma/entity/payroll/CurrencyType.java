/*
 * CurrencyType.java
 *
 * Created on March 30, 2007, 12:58 PM
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
public class CurrencyType  extends Entity{
    
        private String code="";
	private String name="";
	private int tabIndex ;
	private int includeInfProcess;
	private String description="";
        private String formatCurrency;
        
        
     
    
    /** Creates a new instance of CurrencyType */
    public CurrencyType() {
    }
    
    /**
     * Getter for property code.
     * @return Value of property code.
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Setter for property code.
     * @param code New value of property code.
     */
    public void setCode(String code) {
      if (code == null) {
            code = ""; 
	} 
        this.code = code;
    }
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        if (name == null) {
            name = ""; 
	} 
        this.name = name;
    }
    
    /**
     * Getter for property tabIndex.
     * @return Value of property tabIndex.
     */
    public int getTabIndex() {
        return tabIndex;
    }
    
    /**
     * Setter for property tabIndex.
     * @param tabIndex New value of property tabIndex.
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }
    
    /**
     * Getter for property includeInfProcess.
     * @return Value of property includeInfProcess.
     */
    public int getIncludeInfProcess() {
        return includeInfProcess;
    }
    
    /**
     * Setter for property includeInfProcess.
     * @param includeInfProcess New value of property includeInfProcess.
     */
    public void setIncludeInfProcess(int includeInfProcess) {
        this.includeInfProcess = includeInfProcess;
    }
    
    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        if (description == null) {
            description = ""; 
	} 
        this.description = description;
    }

    /**
     * @return the formatCurrency
     */
    public String getFormatCurrency() {
        if(formatCurrency==null){
            return "#,##0";
        }
        return formatCurrency;
    }

    /**
     * @param formatCurrency the formatCurrency to set
     */
    public void setFormatCurrency(String formatCurrency) {
        this.formatCurrency = formatCurrency;
    }
    
}
