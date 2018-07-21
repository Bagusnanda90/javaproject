/*
 * LLStockReporting.java
 *
 * Created on December 19, 2004, 6:00 PM
 */

package com.dimata.harisma.entity.attendance;

// import core java package
import java.util.Date;

/**
 *
 * @author  Administrator
 * @version 
 */
public class LLStockReporting {

    /** Holds value of property payroll. */
    private String payroll = "";
    
    /** Holds value of property name. */
    private String name = "";
    
    /** Holds value of property entitle1. */
    private float entitle1 = 0;
    
    /** Holds value of property entitle2. */  
    private float entitle2 = 0;
    
     /** Holds value of property entitle2. */  
    private float entitle3 = 0;
    
    /** Holds value of property takenMtd. */
    private float takenMtd = 0;
    
    /** Holds value of property takenYtd. */
    private float takenYtd = 0;
    
    /** Holds value of property commDate. */
    private Date commDate;
    
    /** Creates new LLStockReporting */
    public LLStockReporting() {
    }

    /** Getter for property payroll.
     * @return Value of property payroll.
     */
    public String getPayroll() {
        return payroll;
    }
    
    /** Setter for property payroll.
     * @param payroll New value of property payroll.
     */
    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Getter for property entitle1.
     * @return Value of property entitle1.
     */
    public float getEntitle1() {
        return entitle1;
    }
    
    /** Setter for property entitle1.
     * @param entitle1 New value of property entitle1.
     */
    public void setEntitle1(float entitle1) {
        this.entitle1 = entitle1;
    }
    
    /** Getter for property entitle2.
     * @return Value of property entitle2.
     */
    public float getEntitle2() {
        return entitle2;
    }
    
    /** Setter for property entitle2.
     * @param entitle2 New value of property entitle2.
     */
    public void setEntitle2(float entitle2) {
        this.entitle2 = entitle2;
    }
    
    /** Getter for property takenMtd.
     * @return Value of property takenMtd.
     */
    public float getTakenMtd() {
        return takenMtd;
    }
    
    /** Setter for property takenMtd.
     * @param takenMtd New value of property takenMtd.
     */
    public void setTakenMtd(float takenMtd) {
        this.takenMtd = takenMtd;
    }
    
    /** Getter for property takenYtd.
     * @return Value of property takenYtd.
     */
    public float getTakenYtd() {
        return takenYtd;
    }
    
    /** Setter for property takenYtd.
     * @param takenYtd New value of property takenYtd.
     */
    public void setTakenYtd(float takenYtd) {
        this.takenYtd = takenYtd;
    }
    
    /** Getter for property commDate.
     * @return Value of property commDate.
     *
     */
    public Date getCommDate() {
        return this.commDate;
    }
    
    /** Setter for property commDate.
     * @param commDate New value of property commDate.
     *
     */
    public void setCommDate(Date commDate) {
        this.commDate = commDate;
    }
    
    /**
     * Getter for property entitle3.
     * @return Value of property entitle3.
     */
    public float getEntitle3() {
        return entitle3;
    }
    
    /**
     * Setter for property entitle3.
     * @param entitle3 New value of property entitle3.
     */
    public void setEntitle3(float entitle3) {
        this.entitle3 = entitle3;
    }
    
}
