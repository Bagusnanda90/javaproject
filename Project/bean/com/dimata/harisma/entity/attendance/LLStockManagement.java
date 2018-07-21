/**
 * User: gadnyana
 * Date: Apr 8, 2004
 * Time: 12:32:33 PM
 * Version: 1.0 
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;

import java.util.Date;

public class LLStockManagement extends Entity{
    private float LLQty = 0;
    private Date dtOwningDate;
    private int LLStatus;
    private String stNote = "";
    private float entitled = 0;
    private float qtyResidue = 0;
    private float qtyUsed = 0;
    private long OidEmployee = 0;
    private long OidLeavePeriod = 0;
    private Date ExpiredDate;    
    private Date recordDate; 
    private Date entitledDate;
    private float prevBalance;
    private float openingLL = 0;
    private float entitle2 = 0;
    private Date expiredDate2;
    private Date entitleDate2;
    private Date recordDate2;    
    
    private float toBeTaken = 0;
    
    public void setOpeningLL(float openingLL){
        this.openingLL = openingLL;
    }
    
    public float getOpeningLL(){
        return openingLL;
    }
    
    public void setRecordDate(Date recordDate){
        this.recordDate = recordDate;
    }
    public Date getRecordDate(){
        return recordDate;
    }    
    public void setEntitledDate(Date entitledDate){
        this.entitledDate = entitledDate;
    }
    public Date getEntitledDate(){
        return entitledDate;
    }
    
    public void setPrevBalance(float prevBalance){
        this.prevBalance = prevBalance;
    }    
    public float getPrevBalance(){
        return prevBalance;
    }    
    public Date getExpiredDate() {
        return ExpiredDate;
    }

    public void setExpiredDate(Date ExpiredDate) {
        this.ExpiredDate = ExpiredDate;
    }
    
    public long getEmployeeId() {
        return OidEmployee;
    }

    public void setEmployeeId(long iOidEmployee) {
        this.OidEmployee = iOidEmployee;
    }

    public long getLeavePeriodeId() {
        return OidLeavePeriod;
    }

    public void setLeavePeriodeId(long iOidLeavePeriod) {
        this.OidLeavePeriod = iOidLeavePeriod;
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

    public LLStockManagement() {
    }

    public float getEntitled() {
        return entitled;
    }

    public void setEntitled(float entitled) {
        this.entitled = entitled;
    }

    public float getLLQty() {
        return LLQty;
    }

    public void setLLQty(float LLQty) {
        this.LLQty = LLQty;
    }

    public Date getDtOwningDate() {
        return dtOwningDate;
    }

    public void setDtOwningDate(Date dtOwningDate) {
        this.dtOwningDate = dtOwningDate;
    }

    public int getLLStatus() {
        return LLStatus;
    }

    public void setLLStatus(int LLStatus) {
        this.LLStatus = LLStatus;
    }

    public String getStNote() {
        return stNote;
    }

    public void setStNote(String stNote) {
        this.stNote = stNote;
    }

    public float getEntitle2() {
        return entitle2;
    }

    public void setEntitle2(float entitle2) {
        this.entitle2 = entitle2;
    }

    public Date getExpiredDate2() {
        return expiredDate2;
    }

    public void setExpiredDate2(Date expiredDate2) {
        this.expiredDate2 = expiredDate2;
    }

    public Date getEntitleDate2() {
        return entitleDate2;
    }

    public void setEntitleDate2(Date entitleDate2) {
        this.entitleDate2 = entitleDate2;
    }

    public Date getRecordDate2() {
        return recordDate2;
    }

    public void setRecordDate2(Date recordDate2) {
        this.recordDate2 = recordDate2;
    }

    public float getToBeTaken() {
        return toBeTaken;
    }

    public void setToBeTaken(float toBeTaken) {
        this.toBeTaken = toBeTaken;
    }
}
