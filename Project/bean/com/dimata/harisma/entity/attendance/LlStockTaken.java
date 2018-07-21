/*
 * LlStockTaken.java
 *
 * Created on December 22, 2004, 6:19 PM
 */

package com.dimata.harisma.entity.attendance;

// import core java package
import java.util.Date;

// package qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author  gedhy
 */
public class LlStockTaken extends Entity {
    
    /** Holds value of property llStockId. */
    private long llStockId;
    
    /** Holds value of property llStockTakenId. */
    private long llStockTakenId;
    
    /** Holds value of property takenDate. */
    private Date takenDate;
    
    /** Holds value of property takenQty. */
    private float takenQty;
    
    /** Holds value of property employeeId. */
    private long employeeId;
    
    /** Holds value of property paidDate. */
    private Date paidDate;
    
    private int takenFromStatus;
    
    
    private Date takenFinnishDate;
    
    private long leaveApplicationId;
    
    /** Creates a new instance of LlStockTaken */
    public LlStockTaken() {
    }
    
    /** Getter for property llStockId.
     * @return Value of property llStockId.
     *
     */
    public long getLlStockId() {
        return this.llStockId;
    }
    
    /** Setter for property llStockId.
     * @param llStockId New value of property llStockId.
     *
     */
    public void setLlStockId(long llStockId) {
        this.llStockId = llStockId;
    }
    
    /** Getter for property llStockTakenId.
     * @return Value of property llStockTakenId.
     *
     */
    public long getLlStockTakenId() {
        return this.llStockTakenId;
    }
    
    /** Setter for property llStockTakenId.
     * @param llStockTakenId New value of property llStockTakenId.
     *
     */
    public void setLlStockTakenId(long llStockTakenId) {
        this.llStockTakenId = llStockTakenId;
    }
    
    /** Getter for property takenDate.
     * @return Value of property takenDate.
     *
     */
    public Date getTakenDate() {
        return this.takenDate;
    }
    
    /** Setter for property takenDate.
     * @param takenDate New value of property takenDate.
     *
     */
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }
    
    /** Getter for property takenQty.
     * @return Value of property takenQty.
     *
     */
    public float getTakenQty() {
        return this.takenQty;
    }
    
    /** Setter for property takenQty.
     * @param takenQty New value of property takenQty.
     *
     */
    public void setTakenQty(float takenQty) {
        this.takenQty = takenQty;
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
    
    /** Getter for property paidDate.
     * @return Value of property paidDate.
     *
     */
    public Date getPaidDate() {
        return this.paidDate;
    }
    
    /** Setter for property paidDate.
     * @param paidDate New value of property paidDate.
     *
     */
    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public int getTakenFromStatus() {
        return takenFromStatus;
    }

    public void setTakenFromStatus(int takenFromStatus) {
        this.takenFromStatus = takenFromStatus;
    }

    public Date getTakenFinnishDate() {
        return takenFinnishDate;
    }

    public void setTakenFinnishDate(Date takenFinnishDate) {
        this.takenFinnishDate = takenFinnishDate;
    }

    public long getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public void setLeaveApplicationId(long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }
    
}
