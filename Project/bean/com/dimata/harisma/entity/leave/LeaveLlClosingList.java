/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;
import java.util.Date;
/**
 *
 * @author Tu Roy
 */
public class LeaveLlClosingList {
    private long empId;
    private String empNum = "";
    private String fullName = "";
    private Date commancingDate;    
    private long departmentId;
    private String department;
    private long llStockManagementId;
    private float opening;
    private float prevBalance;
    private float entitled;
    private Date entitledDate;
    private float llQty;
    private float qtyUsed;
    private float qtyResidue;    
    private float entitled2;
    private Date expiredDate;
    private Date expiredDate2;
    private Date entitleDate2;
    private float tobeTaken;
    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCommancingDate() {
        return commancingDate;
    }

    public void setCommancingDate(Date commancingDate) {
        this.commancingDate = commancingDate;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getLlStockManagementId() {
        return llStockManagementId;
    }

    public void setLlStockManagementId(long llStockManagementId) {
        this.llStockManagementId = llStockManagementId;
    }

    
    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
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

    /**
     * @return the opening
     */
    public float getOpening() {
        return opening;
    }

    /**
     * @param opening the opening to set
     */
    public void setOpening(float opening) {
        this.opening = opening;
    }

    /**
     * @return the prevBalance
     */
    public float getPrevBalance() {
        return prevBalance;
    }

    /**
     * @param prevBalance the prevBalance to set
     */
    public void setPrevBalance(float prevBalance) {
        this.prevBalance = prevBalance;
    }

    /**
     * @return the entitled
     */
    public float getEntitled() {
        return entitled;
    }

    /**
     * @param entitled the entitled to set
     */
    public void setEntitled(float entitled) {
        this.entitled = entitled;
    }

    /**
     * @return the entitledDate
     */
    public Date getEntitledDate() {
        return entitledDate;
    }

    /**
     * @param entitledDate the entitledDate to set
     */
    public void setEntitledDate(Date entitledDate) {
        this.entitledDate = entitledDate;
    }

    /**
     * @return the llQty
     */
    public float getLlQty() {
        return llQty;
    }

    /**
     * @param llQty the llQty to set
     */
    public void setLlQty(float llQty) {
        this.llQty = llQty;
    }

    /**
     * @return the qtyUsed
     */
    public float getQtyUsed() {
        return qtyUsed;
    }

    /**
     * @param qtyUsed the qtyUsed to set
     */
    public void setQtyUsed(float qtyUsed) {
        this.qtyUsed = qtyUsed;
    }

    /**
     * @return the qtyResidue
     */
    public float getQtyResidue() {
        return qtyResidue;
    }

    /**
     * @param qtyResidue the qtyResidue to set
     */
    public void setQtyResidue(float qtyResidue) {
        this.qtyResidue = qtyResidue;
    }

    /**
     * @return the entitled2
     */
    public float getEntitled2() {
        return entitled2;
    }

    /**
     * @param entitled2 the entitled2 to set
     */
    public void setEntitled2(float entitled2) {
        this.entitled2 = entitled2;
    }

    /**
     * @return the tobeTaken
     */
    public float getTobeTaken() {
        return tobeTaken;
    }

    /**
     * @param tobeTaken the tobeTaken to set
     */
    public void setTobeTaken(float tobeTaken) {
        this.tobeTaken = tobeTaken;
    }
}
