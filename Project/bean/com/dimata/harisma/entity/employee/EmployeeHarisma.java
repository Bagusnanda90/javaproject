/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee;

/**
 *
 * @author dimata
 */
public class EmployeeHarisma {

    private long departmentId;
    private String department;
    private String employeeNumber;
    private String barcodeNUmber;
    private String fullName;

    /**
     * @return the departmentId
     */
    public long getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the employeeNumber
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * @param employeeNumber the employeeNumber to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * @return the barcodeNUmber
     */
    public String getBarcodeNUmber() {
        return barcodeNUmber;
    }

    /**
     * @param barcodeNUmber the barcodeNUmber to set
     */
    public void setBarcodeNUmber(String barcodeNUmber) {
        this.barcodeNUmber = barcodeNUmber;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
