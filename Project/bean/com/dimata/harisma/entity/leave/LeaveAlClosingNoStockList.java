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

public class LeaveAlClosingNoStockList {
    
    private long empId;
    private String empNum = "";
    private String fullName = "";
    private Date commancingDate;
    
    private long departmentId;
    private String department;
    private float entitled;
    
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


}
