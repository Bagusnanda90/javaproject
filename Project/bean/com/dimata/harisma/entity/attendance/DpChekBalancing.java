/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

/**
 *
 * @author Satrya Ramayu
 */
/* package qdep */
import com.dimata.qdep.entity.*;
import java.util.Date;
public class DpChekBalancing extends  Entity{
    private long dp_stokTakenId;
    private float dp_takenQty;
    private Date dp_paidDate;
    //private long dp_stokId_taken;
    private long dp_stokId;
    private float dp_qty;
    private Date dp_expiredDate;
    private Date dp_expiredDateExc;
    private long dp_employee_id;
    private float dp_qtyUsed;
    private float dp_qtyResidue;
    private int dp_exceptionFlag;
    private Date dp_takenDate;
    private Date dp_finishDate;
    //private float dp_toBetaken;
    private Date dp_owningDate;
    private int docStatus;
    private long leaveApplicationId;
    /**
     * @return the dp_stokTakenId
     */
    public long getDp_stokTakenId() {
        return dp_stokTakenId;
    }

    /**
     * @param dp_stokTakenId the dp_stokTakenId to set
     */
    public void setDp_stokTakenId(long dp_stokTakenId) {
        this.dp_stokTakenId = dp_stokTakenId;
    }

    /**
     * @return the dp_takenQty
     */
    public float getDp_takenQty() {
        return dp_takenQty;
    }

    /**
     * @param dp_takenQty the dp_takenQty to set
     */
    public void setDp_takenQty(float dp_takenQty) {
        this.dp_takenQty = dp_takenQty;
    }

    /**
     * @return the dp_paidDate
     */
    public Date getDp_paidDate() {
        return dp_paidDate;
    }

    /**
     * @param dp_paidDate the dp_paidDate to set
     */
    public void setDp_paidDate(Date dp_paidDate) {
        this.dp_paidDate = dp_paidDate;
    }

   
    /**
     * @return the dp_qty
     */
    public float getDp_qty() {
        return dp_qty;
    }

    /**
     * @param dp_qty the dp_qty to set
     */
    public void setDp_qty(float dp_qty) {
        this.dp_qty = dp_qty;
    }

    /**
     * @return the dp_expiredDate
     */
    public Date getDp_expiredDate() {
        return dp_expiredDate;
    }

    /**
     * @param dp_expiredDate the dp_expiredDate to set
     */
    public void setDp_expiredDate(Date dp_expiredDate) {
        this.dp_expiredDate = dp_expiredDate;
    }

    /**
     * @return the dp_expiredDateExc
     */
    public Date getDp_expiredDateExc() {
        return dp_expiredDateExc;
    }

    /**
     * @param dp_expiredDateExc the dp_expiredDateExc to set
     */
    public void setDp_expiredDateExc(Date dp_expiredDateExc) {
        this.dp_expiredDateExc = dp_expiredDateExc;
    }

    /**
     * @return the dp_employee_id
     */
    public long getDp_employee_id() {
        return dp_employee_id;
    }

    /**
     * @param dp_employee_id the dp_employee_id to set
     */
    public void setDp_employee_id(long dp_employee_id) {
        this.dp_employee_id = dp_employee_id;
    }

    /**
     * @return the dp_qtyUsed
     */
    public float getDp_qtyUsed() {
        return dp_qtyUsed;
    }

    /**
     * @param dp_qtyUsed the dp_qtyUsed to set
     */
    public void setDp_qtyUsed(float dp_qtyUsed) {
        this.dp_qtyUsed = dp_qtyUsed;
    }

    /**
     * @return the dp_qtyResidue
     */
    public float getDp_qtyResidue() {
        return dp_qtyResidue;
    }

    /**
     * @param dp_qtyResidue the dp_qtyResidue to set
     */
    public void setDp_qtyResidue(float dp_qtyResidue) {
        this.dp_qtyResidue = dp_qtyResidue;
    }

    /**
     * @return the dp_exceptionFlag
     */
    public int getDp_exceptionFlag() {
        return dp_exceptionFlag;
    }

    /**
     * @param dp_exceptionFlag the dp_exceptionFlag to set
     */
    public void setDp_exceptionFlag(int dp_exceptionFlag) {
        this.dp_exceptionFlag = dp_exceptionFlag;
    }

    /**
     * @return the dp_takenDate
     */
    public Date getDp_takenDate() {
        return dp_takenDate;
    }

    /**
     * @param dp_takenDate the dp_takenDate to set
     */
    public void setDp_takenDate(Date dp_takenDate) {
        this.dp_takenDate = dp_takenDate;
    }

    /**
     * @return the dp_owningDate
     */
    public Date getDp_owningDate() {
        return dp_owningDate;
    }

    /**
     * @param dp_owningDate the dp_owningDate to set
     */
    public void setDp_owningDate(Date dp_owningDate) {
        this.dp_owningDate = dp_owningDate;
    }

    /**
     * @return the dp_finishDate
     */
    public Date getDp_finishDate() {
        return dp_finishDate;
    }

    /**
     * @param dp_finishDate the dp_finishDate to set
     */
    public void setDp_finishDate(Date dp_finishDate) {
        this.dp_finishDate = dp_finishDate;
    }

    /**
     * @return the leaveApplicationId
     */
    public long getLeaveApplicationId() {
        return leaveApplicationId;
    }

    /**
     * @param leaveApplicationId the leaveApplicationId to set
     */
    public void setLeaveApplicationId(long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }

    /**
     * @return the dp_stokId
     */
    public long getDp_stokId() {
        return dp_stokId;
    }

    /**
     * @param dp_stokId the dp_stokId to set
     */
    public void setDp_stokId(long dp_stokId) {
        this.dp_stokId = dp_stokId;
    }

    /**
     * @return the docStatus
     */
    public int getDocStatus() {
        return docStatus;
    }

    /**
     * @param docStatus the docStatus to set
     */
    public void setDocStatus(int docStatus) {
        this.docStatus = docStatus;
    }
    
}
