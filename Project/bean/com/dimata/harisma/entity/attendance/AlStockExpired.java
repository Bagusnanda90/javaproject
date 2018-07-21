/*
 * AlStockExpired.java
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
public class AlStockExpired extends Entity {
    
    /** Holds value of property alStockId. */
    private long alStockId;
    
    /** Holds value of property alStockExpiredId. */
    private long alStockExpiredId;
    
    /** Holds value of property expiredDate. */
    private Date expiredDate;
    
    /** Holds value of property expiredQty. */
    private float expiredQty;
    
    //by Roy Andika
    private Date decisiondate;
    
    private String note;
    
    private String expiredbypic;
    
    private String approvebypic;
    
    public void setApproveByPic(String approvebypic){
        this.approvebypic=approvebypic;
    }
    
    public String getApproveByPic(){
        return approvebypic;
    }
    
    public void setExpiredByPic(String expiredbypic){
        this.expiredbypic=expiredbypic;
    }
    
    public String getExpiredByPic(){
        return expiredbypic;
    }
    
    public void setNote(String note){
        this.note=note;
    }
    
    public String getNote(){
        return note;
    }
    
    public void setDecisionDate(Date decisiondate){
        this.decisiondate=decisiondate;
    }
    
    public Date getDecisionDate(){
        return decisiondate;
    }
    
    /** Creates a new instance of AlStockExpired */
    public AlStockExpired() {
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
    
    /** Getter for property alStockExpiredId.
     * @return Value of property alStockExpiredId.
     *
     */
    public long getAlStockExpiredId() {
        return this.alStockExpiredId;
    }
    
    /** Setter for property alStockExpiredId.
     * @param alStockExpiredId New value of property alStockExpiredId.
     *
     */
    public void setAlStockExpiredId(long alStockExpiredId) {
        this.alStockExpiredId = alStockExpiredId;
    }
    
    /** Getter for property takenDate.
     * @return Value of property takenDate.
     *
     */
    public Date getExpiredDate() {
        return this.expiredDate;
    }
    
    /** Setter for property takenDate.
     * @param takenDate New value of property takenDate.
     *
     */
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    
    /** Getter for property takenQty.
     * @return Value of property takenQty.
     *
     */
    public float getExpiredQty() {
        return this.expiredQty;
    }
    
    /** Setter for property takenQty.
     * @param takenQty New value of property takenQty.
     *
     */
    public void setExpiredQty(float expiredQty) {
        this.expiredQty = expiredQty;
    }
    
}
