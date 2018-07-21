/**
 * User: gadnyana
 * Date: Apr 8, 2004
 * Time: 12:32:33 PM
 * Version: 1.0 
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;

import java.util.Date;

public class AlStockManagement extends Entity{
    private float iAlQty = 0;
    private Date dtOwningDate;
    private int iAlStatus;
    private String stNote = "";
    private float qtyResidue = 0;
    private float qtyUsed = 0;
    private long iOidEmployee = 0;
    private long iOidLeavePeriod = 0;
    private float entitled = 12;    
    private Date commencing_date_have;
    private float opening = 0;
    private Date recordDate;
    private Date entitleDate;
    private float prev_balance = 0;
    private Date expiredDate;
    
    private float ALtoBeTaken = 0;
    //update by satrya 2013-02-25
    private double extraAl=0;
    private Date extraAlDate;
    public void setPrevBalance(float prev_balance){
        this.prev_balance = prev_balance;
    }
    public float getPrevBalance(){
        return prev_balance;
    }
    
    public void setRecordDate(Date recordDate){
        this.recordDate = recordDate;
    }
    public void setEntitleDate(Date entitleDate){
        this.entitleDate = entitleDate;
    }
    public Date getRecordDate(){
        return recordDate;
    }
    public Date getEntitleDate(){
        return entitleDate;
    }
    
    public void setOpening(float opening){
        this.opening=opening;
    }
    
    public float getOpening(){
        return opening;
    }
    public void setCommencingDateHave(Date commencing_date_have){
        this.commencing_date_have=commencing_date_have;
    }
    public Date getCommencingDateHave(){
        return commencing_date_have;
    }   
   
    public float getEntitled() {
        return entitled;
    }

    public void setEntitled(float ent) {
        this.entitled = ent;
    }

    public long getEmployeeId() {
        return iOidEmployee;
    }

    public void setEmployeeId(long iOidEmployee) {
        this.iOidEmployee = iOidEmployee;
    }

    public long getLeavePeriodeId() {
        return iOidLeavePeriod;
    }

    public void setLeavePeriodeId(long iOidLeavePeriod) {
        this.iOidLeavePeriod = iOidLeavePeriod;
    }

    public float getQtyUsed() {
        return qtyUsed;
    }

    public void setQtyUsed(float qtyUsed) {
        this.qtyUsed = qtyUsed;
    }

    public float getQtyResidue() {
        return qtyResidue;
    }

    public void setQtyResidue(float qtyResidue) {
        this.qtyResidue = qtyResidue;
    }

    public AlStockManagement() {
    }

    public float getAlQty() {
        return iAlQty;
    }

    public void setAlQty(float iAlQty) {
        this.iAlQty = iAlQty;
    }

    public Date getDtOwningDate() {
        return dtOwningDate;
    }

    public void setDtOwningDate(Date dtOwningDate) {
        this.dtOwningDate = dtOwningDate;
    }

    public int getAlStatus() {
        return iAlStatus;
    }

    public void setAlStatus(int iDpStatus) {
        this.iAlStatus = iDpStatus;
    }

    public String getStNote() {
        return stNote;
    }

    public void setStNote(String stNote) {
        this.stNote = stNote;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * @return the ALtoBeTaken
     */
    public float getALtoBeTaken() {
        return ALtoBeTaken;
    }

    /**
     * @param ALtoBeTaken the ALtoBeTaken to set
     */
    public void setALtoBeTaken(float ALtoBeTaken) {
        this.ALtoBeTaken = ALtoBeTaken;
    }

    /**
     * @return the extraAl
     */
    public double getExtraAl() {
        return extraAl;
    }

    /**
     * @param extraAl the extraAl to set
     */
    public void setExtraAl(double extraAl) {
        this.extraAl = extraAl;
    }

    /**
     * @return the extraAlDate
     */
    public Date getExtraAlDate() {
        return extraAlDate;
    }

    /**
     * @param extraAlDate the extraAlDate to set
     */
    public void setExtraAlDate(Date extraAlDate) {
        this.extraAlDate = extraAlDate;
    }

    
}
