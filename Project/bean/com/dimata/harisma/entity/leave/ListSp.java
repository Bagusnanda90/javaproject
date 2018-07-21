/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

/**
 *
 * @author Tu Roy
 */
public class ListSp {
    
    private long employeeId = 0;
    private String fullName;
    private long departmentId = 0;
    private String employeeNum = "";
    private float toBeTakenSp = 0;
    private float takenSp = 0;
    private float tobeTakenUnp = 0;
    private float takenUnp = 0;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public float getToBeTakenSp() {
        return toBeTakenSp;
    }

    public void setToBeTakenSp(float toBeTakenSp) {
        this.toBeTakenSp = toBeTakenSp;
    }

    public float getTakenSp() {
        return takenSp;
    }

    public void setTakenSp(float takenSp) {
        this.takenSp = takenSp;
    }

    public float getTobeTakenUnp() {
        return tobeTakenUnp;
    }

    public void setTobeTakenUnp(float tobeTakenUnp) {
        this.tobeTakenUnp = tobeTakenUnp;
    }

    public float getTakenUnp() {
        return takenUnp;
    }

    public void setTakenUnp(float takenUnp) {
        this.takenUnp = takenUnp;
    }

}
