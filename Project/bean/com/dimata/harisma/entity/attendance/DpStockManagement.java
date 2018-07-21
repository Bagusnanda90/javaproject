/**
 * User: wardana
 * Date: Apr 8, 2004
 * Time: 12:32:33 PM
 * Version: 1.0 
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;

import java.util.Date;

public class DpStockManagement extends Entity{
    private float iDpQty = 0;
    private Date dtOwningDate;
    private Date dtExpiredDate;
    private int iExceptionFlag;
    private Date dtExpiredDateExc;
    private int iDpStatus;
    private String stNote = "";
    private float qtyResidue = 0;
    private float qtyUsed = 0;
    private long iOidEmployee = 0;
    private long iOidLeavePeriod = 0; 
    private float toBeTaken = 0; // data ini tidak di set ketika fetch lewat persisant. Data ini digunakan untuk menampung data perhitungan/dari query
    private Date dtStartDate;
    //update by satrya 2013-02-24
    private int flagStock=0;//fungsinya jika user telah generate stock lalau user merubahnya di dp_management, nnti user kembali generate lewat overtime  agar nilai dpSTock tidak berubah

    public Date getDtStartDate() {
        return dtStartDate;
    }

    public void setDtStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
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

    public DpStockManagement() {
    }

    public float getiDpQty() {
        return iDpQty;
    }

    public void setiDpQty(float iDpQty) {
        this.iDpQty = iDpQty;
    }

    public Date getDtOwningDate() {
        return dtOwningDate;
    }

    public void setDtOwningDate(Date dtOwningDate) {
        this.dtOwningDate = dtOwningDate;
    }

    public Date getDtExpiredDate() {
        return dtExpiredDate;
    }

    public void setDtExpiredDate(Date dtExpiredDate) {
        this.dtExpiredDate = dtExpiredDate;
    }

    public int getiExceptionFlag() {
        return iExceptionFlag;
    }

    public void setiExceptionFlag(int iExceptionFlag) {
        this.iExceptionFlag = iExceptionFlag;
    }

    public Date getDtExpiredDateExc() {
        return dtExpiredDateExc;
    }

    public void setDtExpiredDateExc(Date dtExpiredDateExc) {
        this.dtExpiredDateExc = dtExpiredDateExc;
    }

    public int getiDpStatus() {
        return iDpStatus;
    }

    public void setiDpStatus(int iDpStatus) {
        this.iDpStatus = iDpStatus;
    }

    public String getStNote() {
        return stNote;
    }

    public void setStNote(String stNote) {
        this.stNote = stNote;
    }

    public float getToBeTaken() {
        return toBeTaken;
    }

    public void setToBeTaken(float toBeTaken) {
        this.toBeTaken = toBeTaken;
    }
    /**
     * 
     * @return data eligile days , yang tidak di set ketika fetch lewat persisant. Data ini digunakan untuk menampung data perhitungan/dari query
     */
    public float getEligible(){        
        
       return iDpQty - qtyUsed - toBeTaken;
       
    }

    /**
     * @return the flagStock
     */
    public int getFlagStock() {
        return flagStock;
    }

    /**
     * @param flagStock the flagStock to set
     */
    public void setFlagStock(int flagStock) {
        this.flagStock = flagStock;
    }
}
