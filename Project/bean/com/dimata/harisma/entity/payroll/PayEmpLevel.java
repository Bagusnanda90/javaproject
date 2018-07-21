/*
 * PayEmpLevel.java
 *
 * Created on April 5, 2007, 10:48 AM
 */

package com.dimata.harisma.entity.payroll;
/* package java */ 
import com.dimata.harisma.entity.payroll.*;
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;


/**
 *
 * @author  yunny
 */

public class PayEmpLevel extends Entity{

    
    // private long genId;
	private long employeeId=0;
	private String levelCode="";
        private Date startDate;
        private Date endDate;
        private long bankId=0;
        private String bankAccNr="";
        private String posForTax="";
        private int payPerBegin=0;
        private int payPerEnd=0;
        private int commencingSt=0;
        private double prevIncome;
        private int prevTaxPaid=0;
        private int statusData=0;
        private int mealAllowance=0;
        private int ovtIdxType=0;
        
   
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
     * Getter for property levelCode.
     * @return Value of property levelCode.
     */
    public String getLevelCode() {
        return levelCode;
    }
    
    /**
     * Setter for property levelCode.
     * @param levelCode New value of property levelCode.
     */
    public void setLevelCode(String levelCode) {
         if (levelCode == null) {
	     levelCode = ""; 
	 } 
        this.levelCode = levelCode;
    }
    
    /**
     * Getter for property startDate.
     * @return Value of property startDate.
     */
    public Date getStartDate() {
        return startDate;
    }
    
    /**
     * Setter for property startDate.
     * @param startDate New value of property startDate.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        if(this.startDate!=null && this.endDate==null){
            this.endDate = new Date(this.startDate.getTime() + 365 * 24 * 60 * 60 * 1000);
        }
    }
    
    /**
     * Getter for property bankId.
     * @return Value of property bankId.
     */
    public long getBankId() {
        return bankId;
    }
    
    /**
     * Setter for property bankId.
     * @param bankId New value of property bankId.
     */
    public void setBankId(long bankId) {
        this.bankId = bankId;
    }
    
    /**
     * Getter for property bankAccNr.
     * @return Value of property bankAccNr.
     */
    public String getBankAccNr() {
        return bankAccNr;
    }
    
    /**
     * Setter for property bankAccNr.
     * @param bankAccNr New value of property bankAccNr.
     */
    public void setBankAccNr(String bankAccNr) {
         if (bankAccNr == null) {
	     bankAccNr = ""; 
	 } 
        this.bankAccNr = bankAccNr;
    }
    
    /**
     * Getter for property posForTax.
     * @return Value of property posForTax.
     */
    public String getPosForTax() {
        return posForTax;
    }
    
    /**
     * Setter for property posForTax.
     * @param posForTax New value of property posForTax.
     */
    public void setPosForTax(String posForTax) {
          if (posForTax == null) {
	     posForTax = ""; 
	  } 
        this.posForTax = posForTax;
    }
    
    /**
     * Getter for property payPerBegin.
     * @return Value of property payPerBegin.
     */
    public int getPayPerBegin() {
        return payPerBegin;
    }
    
    /**
     * Setter for property payPerBegin.
     * @param payPerBegin New value of property payPerBegin.
     */
    public void setPayPerBegin(int payPerBegin) {
        this.payPerBegin = payPerBegin;
    }
    
    /**
     * Getter for property payPerEnd.
     * @return Value of property payPerEnd.
     */
    public int getPayPerEnd() {
        return payPerEnd;
    }
    
    /**
     * Setter for property payPerEnd.
     * @param payPerEnd New value of property payPerEnd.
     */
    public void setPayPerEnd(int payPerEnd) {
        this.payPerEnd = payPerEnd;
    }
    
    
    /**
     * Getter for property prevIncome.
     * @return Value of property prevIncome.
     */
    public double getPrevIncome() {
        return prevIncome;
    }
    
    /**
     * Setter for property prevIncome.
     * @param prevIncome New value of property prevIncome.
     */
    public void setPrevIncome(double prevIncome) {
        this.prevIncome = prevIncome;
    }
    
    /**
     * Getter for property prevTaxPaid.
     * @return Value of property prevTaxPaid.
     */
    public int getPrevTaxPaid() {
        return prevTaxPaid;
    }
    
    /**
     * Setter for property prevTaxPaid.
     * @param prevTaxPaid New value of property prevTaxPaid.
     */
    public void setPrevTaxPaid(int prevTaxPaid) {
        this.prevTaxPaid = prevTaxPaid;
    }
    
    /**
     * Getter for property commencingSt.
     * @return Value of property commencingSt.
     */
    public int getCommencingSt() {
        return commencingSt;
    }
    
    /**
     * Setter for property commencingSt.
     * @param commencingSt New value of property commencingSt.
     */
    public void setCommencingSt(int commencingSt) {
        this.commencingSt = commencingSt;
    }
    
    /**
     * Getter for property statusData.
     * @return Value of property statusData.
     */
    public int getStatusData() {
        return statusData;
    }
    
    /**
     * Setter for property statusData.
     * @param statusData New value of property statusData.
     */
    public void setStatusData(int statusData) {
        this.statusData = statusData;
    }
    
    /**
     * Getter for property mealAllowance.
     * @return Value of property mealAllowance.
     */
    public int getMealAllowance() {
        return mealAllowance;
    }
    
    /**
     * Setter for property mealAllowance.
     * @param mealAllowance New value of property mealAllowance.
     */
    public void setMealAllowance(int mealAllowance) {
        this.mealAllowance = mealAllowance;
    }
    
    /**
     * Getter for property ovtIdxType.
     * @return Value of property ovtIdxType.
     */
    public int getOvtIdxType() {
        return ovtIdxType;
    }
    
    /**
     * Setter for property ovtIdxType.
     * @param ovtIdxType New value of property ovtIdxType.
     */
    public void setOvtIdxType(int ovtIdxType) {
        this.ovtIdxType = ovtIdxType;
    }    

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        if(this.endDate!=null && this.startDate==null){
            this.startDate = new Date(this.endDate.getTime() - 365 * 24 * 60 * 60 * 1000);
        }
        if(this.startDate!=null && this.endDate==null){
            this.endDate = new Date(this.startDate.getTime() + 365 * 24 * 60 * 60 * 1000);
        }
    }
}
