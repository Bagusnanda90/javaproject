/*
 * AlStockReport.java
 *
 * Created on October 2, 2004, 9:22 AM
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author  gedhy
 */
public class AlStockReport extends Entity{
    
    /** Holds value of property periodId. */
    private long periodId;    
    
    /** Holds value of property employeeId. */
    private long employeeId;
    
    /** Holds value of property lastYearQty. */
    private float lastYearQty;
    
    /** Holds value of property entThisYearQty. */
    private float entThisYearQty;
    
    /** Holds value of property earnedYtdQty. */
    private float earnedYtdQty;
    
    /** Holds value of property takenMtdQty. */
    private float takenMtdQty;
    
    /** Holds value of property takenYtdQty. */
    private float takenYtdQty;
    
    /** Getter for property periodId.
     * @return Value of property periodId.
     *
     */
    public long getPeriodId() {
        return this.periodId;
    }
    
    /** Setter for property periodId.
     * @param periodId New value of property periodId.
     *
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }
    
    /** Getter for property employeeId.
     * @return Value of property employeeId.
     *
     */
    public long getEmployeeId() {
        return this.employeeId;
    }
    
    /** Setter for property employeeId.
     * @param employeeId New value of property employeeId.
     *
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    /** Getter for property lastYearQty.
     * @return Value of property lastYearQty.
     *
     */
    public float getLastYearQty() {
        return this.lastYearQty;
    }
    
    /** Setter for property lastYearQty.
     * @param lastYearQty New value of property lastYearQty.
     *
     */
    public void setLastYearQty(float lastYearQty) {
        this.lastYearQty = lastYearQty;
    }
    
    /** Getter for property entThisYearQty.
     * @return Value of property entThisYearQty.
     *
     */
    public float getEntThisYearQty() {
        return this.entThisYearQty;
    }
    
    /** Setter for property entThisYearQty.
     * @param entThisYearQty New value of property entThisYearQty.
     *
     */
    public void setEntThisYearQty(float entThisYearQty) {
        this.entThisYearQty = entThisYearQty;
    }
    
    /** Getter for property earnedYtdQty.
     * @return Value of property earnedYtdQty.
     *
     */
    public float getEarnedYtdQty() {
        return this.earnedYtdQty;
    }
    
    /** Setter for property earnedYtdQty.
     * @param earnedYtdQty New value of property earnedYtdQty.
     *
     */
    public void setEarnedYtdQty(float earnedYtdQty) {
        this.earnedYtdQty = earnedYtdQty;
    }
    
    /** Getter for property takenMtdQty.
     * @return Value of property takenMtdQty.
     *
     */
    public float getTakenMtdQty() {
        return this.takenMtdQty;
    }
    
    /** Setter for property takenMtdQty.
     * @param takenMtdQty New value of property takenMtdQty.
     *
     */
    public void setTakenMtdQty(float takenMtdQty) {
        this.takenMtdQty = takenMtdQty;
    }
    
    /** Getter for property takenYtdQty.
     * @return Value of property takenYtdQty.
     *
     */
    public float getTakenYtdQty() {
        return this.takenYtdQty;
    }
    
    /** Setter for property takenYtdQty.
     * @param takenYtdQty New value of property takenYtdQty.
     *
     */
    public void setTakenYtdQty(float takenYtdQty) {
        this.takenYtdQty = takenYtdQty;
    }
    
}
