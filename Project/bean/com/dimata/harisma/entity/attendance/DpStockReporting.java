/*
 * DpStockReporting.java
 *
 * Created on December 20, 2004, 8:31 AM
 */

package com.dimata.harisma.entity.attendance;

/**
 *
 * @author  gedhy
 */
public class DpStockReporting {
    
    /** Holds value of property payroll. */
    private String payroll = "";
    
    /** Holds value of property name. */
    private String name = "";
    
    /** Holds value of property prevAmount. */
    private float prevAmount = 0;
    
    /** Holds value of property earn. */
    private float earn = 0;
    
    /** Holds value of property used. */
    private float used = 0;
    
    /** Holds value of property expired. */
    private float expired = 0;
    
    /** Creates a new instance of DpStockReporting */
    public DpStockReporting() {
    }
    
    /** Getter for property payroll.
     * @return Value of property payroll.
     *
     */
    public String getPayroll() {
        return this.payroll;
    }
    
    /** Setter for property payroll.
     * @param payroll New value of property payroll.
     *
     */
    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     *
     */
    public String getName() {
        return this.name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     *
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Getter for property prevAmount.
     * @return Value of property prevAmount.
     *
     */
    public float getPrevAmount() {
        return this.prevAmount;
    }
    
    /** Setter for property prevAmount.
     * @param prevAmount New value of property prevAmount.
     *
     */
    public void setPrevAmount(float prevAmount) {
        this.prevAmount = prevAmount;
    }
    
    /** Getter for property earn.
     * @return Value of property earn.
     *
     */
    public float getEarn() {
        return this.earn;
    }
    
    /** Setter for property earn.
     * @param earn New value of property earn.
     *
     */
    public void setEarn(int earn) {
        this.earn = earn;
    }
    
    /** Getter for property used.
     * @return Value of property used.
     *
     */
    public float getUsed() {
        return this.used;
    }
    
    /** Setter for property used.
     * @param used New value of property used.
     *
     */
    public void setUsed(float used) {
        this.used = used;
    }
    
    /** Getter for property expired.
     * @return Value of property expired.
     *
     */
    public float getExpired() {
        return this.expired;
    }
    
    /** Setter for property expired.
     * @param expired New value of property expired.
     *
     */
    public void setExpired(float expired) {
        this.expired = expired;
    }
    
}
