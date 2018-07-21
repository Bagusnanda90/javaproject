/*
 * AlStockTaken.java
 *
 * Created on December 22, 2004, 4:42 PM
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
public class AlStockTaken extends Entity {
    
    /** Holds value of property alStockId. */
    private long alStockId;
    
    /** Holds value of property alStockTakenId. */
    private long alStockTakenId;
    
    /** Holds value of property takenDate. */
    private Date takenDate;
    
    /** Holds value of property takenQty. */
    private float takenQty;
    //update by satrya 2012-10-12
    //private float takenQty;
    
    /** Holds value of property employeeId. */
    private long employeeId;
    
    /** Holds value of property paidDate. */
    private Date paidDate;
    
    private int takenFromStatus;
    
    private long leaveApplicationId;
    
    private Date takenFinnishDate;
    
    /** Creates a new instance of AlStockTaken */
    public AlStockTaken() {
    }
    
    /** Getter for property alStockId.
     * @return Value of property alStockId.
     *
     */
    public long getAlStockId() {
        return this.alStockId;
    }
    
    /** Setter for property alStockId.
     * @param alStockId New value of property alStockId.
     *
     */
    public void setAlStockId(long alStockId) {
        this.alStockId = alStockId;
    }
    
    /** Getter for property alStockTakenId.
     * @return Value of property alStockTakenId.
     *
     */
    public long getAlStockTakenId() {
        return this.alStockTakenId;
    }
    
    /** Setter for property alStockTakenId.
     * @param alStockTakenId New value of property alStockTakenId.
     *
     */
    public void setAlStockTakenId(long alStockTakenId) {
        this.alStockTakenId = alStockTakenId;
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
