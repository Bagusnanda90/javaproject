/*
 * DpStockExpired.java
 *
 * Created on December 22, 2004, 11:57 AM
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
public class DpStockExpired extends Entity {
    
    /** Holds value of property dpStockExpiredId. */
    private long dpStockExpiredId;
    
    /** Holds value of property dpStockId. */
    private long dpStockId;
    
    /** Holds value of property expiredDate. */
    private Date expiredDate;
    
    /** Holds value of property expiredQty. */
    private float expiredQty;
    
    /** Creates a new instance of DpStockExpired */
    public DpStockExpired() {
    }
    
    /** Getter for property dpStockExpiredId.
     * @return Value of property dpStockExpiredId.
     *
     */
    public long getDpStockExpiredId() {
        return this.dpStockExpiredId;
    }
    
    /** Setter for property dpStockExpiredId.
     * @param dpStockExpiredId New value of property dpStockExpiredId.
     *
     */
    public void setDpStockExpiredId(long dpStockExpiredId) {
        this.dpStockExpiredId = dpStockExpiredId;
    }
    
    /** Getter for property dpStockId.
     * @return Value of property dpStockId.
     *
     */
    public long getDpStockId() {
        return this.dpStockId;
    }
    
    /** Setter for property dpStockId.
     * @param dpStockId New value of property dpStockId.
     *
     */
    public void setDpStockId(long dpStockId) {
        this.dpStockId = dpStockId;
    }
    
    /** Getter for property expiredDate.
     * @return Value of property expiredDate.
     *
     */
    public Date getExpiredDate() {
        return this.expiredDate;
    }
    
    /** Setter for property expiredDate.
     * @param expiredDate New value of property expiredDate.
     *
     */
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    
    /** Getter for property expiredQty.
     * @return Value of property expiredQty.
     *
     */
    public float getExpiredQty() {
        return this.expiredQty;
    }
    
    /** Setter for property expiredQty.
     * @param expiredQty New value of property expiredQty.
     *
     */
    public void setExpiredQty(float expiredQty) {
        this.expiredQty = expiredQty;
    }
    
}
