/*
 * ALStockReporting.java
 *
 * Created on December 19, 2004, 4:26 PM
 */

package com.dimata.harisma.entity.attendance;
import java.util.Date;


/**
 *
 * @author  Administrator
 * @version 
 */
public class ALStockReporting {

    /** Holds value of property payroll. */
    private String payroll = "";
    
    /** Holds value of property name. */
    private String name = "";
    
    /** Holds value of property toClearLastYear. */
    private int toClearLastYear = 0;
    
    /** Holds value of property entitleCurrYear. */
    private int entitleCurrYear = 0;
    
    /** Holds value of property takenMtd. */
    private int takenMtd = 0;
    
    /** Holds value of property takenYtd. */
    private int takenYtd = 0;
    
    /** Holds value of property earnedYtd. */
    private int earnedYtd = 0;
    
     /** Holds value of property earnedYtd. */
    private Date startingDate;
    
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
    
    /** Getter for property toClearLastYear.
     * @return Value of property toClearLastYear.
     */
    public int getToClearLastYear() {
        return toClearLastYear;
    }
    
    /** Setter for property toClearLastYear.
     * @param toClearLastYear New value of property toClearLastYear.
     */
    public void setToClearLastYear(int toClearLastYear) {
        this.toClearLastYear = toClearLastYear;
    }
    
    /** Getter for property entitleCurrYear.
     * @return Value of property entitleCurrYear.
     */
    public int getEntitleCurrYear() {
        return entitleCurrYear;
    }
    
    /** Setter for property entitleCurrYear.
     * @param entitleCurrYear New value of property entitleCurrYear.
     */
    public void setEntitleCurrYear(int entitleCurrYear) {
        this.entitleCurrYear = entitleCurrYear;
    }
    
    /** Getter for property takenMtd.
     * @return Value of property takenMtd.
     */
    public int getTakenMtd() {
        return takenMtd;
    }
    
    /** Setter for property takenMtd.
     * @param takenMtd New value of property takenMtd.
     */
    public void setTakenMtd(int takenMtd) {
        this.takenMtd = takenMtd;
    }
    
    /** Getter for property takenYtd.
     * @return Value of property takenYtd.
     */
    public int getTakenYtd() {
        return takenYtd;
    }
    
    /** Setter for property takenYtd.
     * @param takenYtd New value of property takenYtd.
     */
    public void setTakenYtd(int takenYtd) {
        this.takenYtd = takenYtd;
    }
    
    /** Getter for property earnedYtd.
     * @return Value of property earnedYtd.
     */
    public int getEarnedYtd() {
        return earnedYtd;
    }
    
    /** Setter for property earnedYtd.
     * @param earnedYtd New value of property earnedYtd.
     */
    public void setEarnedYtd(int earnedYtd) {
        this.earnedYtd = earnedYtd;
    }
    /** Getter for property earnedYtd.
     * @return Value of property earnedYtd.
     */
    public Date  getStartingDate() {
        return startingDate;
    }
    
    /** Setter for property earnedYtd.
     * @param earnedYtd New value of property earnedYtd.
     */
    public Date setStartingDate() {
        return startingDate;
    }
    
    
    
    
}
