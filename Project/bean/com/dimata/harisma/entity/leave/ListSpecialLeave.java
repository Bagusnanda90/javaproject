/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

/**
 *
 * @author Tu Roy
 */
public class ListSpecialLeave {
    
    private long employeeID;
    private String employeeNum;
    private String fullName;
    private long departmentID;
    private float tknQTY;

    public long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(long departmentID) {
        this.departmentID = departmentID;
    }

    public float getTknQTY() {
        return tknQTY;
    }

    public void setTknQTY(float tknQTY) {
        this.tknQTY = tknQTY;
    }
}
