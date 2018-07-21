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

public class LeaveAlClosingList {
    
    private long empId;
    private String empNum = "";
    private String fullName = "";
    private Date commancingDate;
    
    private long departmentId;
    private String department;
    
    private long alStockManagementId;
    private float opening;
    private float prevBalance;
    private float entitled;
    private Date entitledDate;
    private float alQty;
    private float qtyUsed;
    private float qtyResidue;

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

    public long getAlStockManagementId() {
        return alStockManagementId;
    }

    public void setAlStockManagementId(long alStockManagementId) {
        this.alStockManagementId = alStockManagementId;
    }

    public float getOpening() {
        return opening;
    }

    public void setOpening(float opening) {
        this.opening = opening;
    }

    public float getPrevBalance() {
        return prevBalance;
    }

    public void setPrevBalance(float prevBalance) {
        this.prevBalance = prevBalance;
    }

    public float getEntitled() {
        return entitled;
    }

    public void setEntitled(float entitled) {
        this.entitled = entitled;
    }

    public Date getEntitledDate() {
        return entitledDate;
    }

    public void setEntitledDate(Date entitledDate) {
        this.entitledDate = entitledDate;
    }

    public float getAlQty() {
        return alQty;
    }

    public void setAlQty(float alQty) {
        this.alQty = alQty;
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
}
