/*
 * PayTaxTariff.java
 *
 * Created on April 3, 2007, 1:16 PM
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
public class PayTaxTariff  extends Entity {
    private int taxYear;
    private String level="";
    private double salaryMin;
    private int salaryMax;
    private double taxTariff;
    
  
    /** Creates a new instance of PayTaxTariff */
    public PayTaxTariff() {
    }
    
    /**
     * Getter for property taxYear.
     * @return Value of property taxYear.
     */
    public int getTaxYear() {
        return taxYear;
    }
    
    /**
     * Setter for property taxYear.
     * @param taxYear New value of property taxYear.
     */
    public void setTaxYear(int taxYear) {
        this.taxYear = taxYear;
    }
    
    /**
     * Getter for property level.
     * @return Value of property level.
     */
    public String getLevel() {
        return level;
    }
    
    /**
     * Setter for property level.
     * @param level New value of property level.
     */
    public void setLevel(String level) {
         if (level == null) {
	     level = ""; 
	 } 
        this.level = level;
    }
    
    /**
     * Getter for property salaryMin.
     * @return Value of property salaryMin.
     */
    public double getSalaryMin() {
        return salaryMin;
    }
    
    /**
     * Setter for property salaryMin.
     * @param salaryMin New value of property salaryMin.
     */
    public void setSalaryMin(double salaryMin) {
        this.salaryMin = salaryMin;
    }
    
    /**
     * Getter for property salaryMax.
     * @return Value of property salaryMax.
     */
    public int getSalaryMax() {
        return salaryMax;
    }
    
    /**
     * Setter for property salaryMax.
     * @param salaryMax New value of property salaryMax.
     */
    public void setSalaryMax(int salaryMax) {
        this.salaryMax = salaryMax;
    }
    
    /**
     * Getter for property taxTariff.
     * @return Value of property taxTariff.
     */
    public double getTaxTariff() {
        return taxTariff;
    }
    
    /**
     * Setter for property taxTariff.
     * @param taxTariff New value of property taxTariff.
     */
    public void setTaxTariff(double taxTariff) {
        this.taxTariff = taxTariff;
    }
    
}
