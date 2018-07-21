/*
 * LlStockExpired.java
 *
 * Created on December 22, 2004, 6:18 PM
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
public class LlStockExpired extends Entity {
    
    /** Holds value of property llStockExpiredId. */
    private long llStockExpiredId;
    
    /** Holds value of property llStockId. */
    private long llStockId;
    
    /** Holds value of property expiredDate. */
    private Date expiredDate;
    
    /** Holds value of property expiredQty. */
    private float expiredQty;
    
    private float expiredLL;
    
    private float expiredLast;
    
    /** Creates a new instance of LlStockExpired */
    public LlStockExpired() {
    }
    
    /** Getter for property llStockExpiredId.
     * @return Value of property llStockExpiredId.
     *
     */
    public long getLlStockExpiredId() {
        return this.llStockExpiredId;
    }
    
    /** Setter for property llStockExpiredId.
     * @param llStockExpiredId New value of property llStockExpiredId.
     *
     */
    public void setLlStockExpiredId(long llStockExpiredId) {
        this.llStockExpiredId = llStockExpiredId;
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

    public float getExpiredLL() {
        return expiredLL;
    }

    public void setExpiredLL(float expiredLL) {
        this.expiredLL = expiredLL;
    }

    public float getExpiredLast() {
        return expiredLast;
    }

    public void setExpiredLast(float expiredLast) {
        this.expiredLast = expiredLast;
    }
    
}
