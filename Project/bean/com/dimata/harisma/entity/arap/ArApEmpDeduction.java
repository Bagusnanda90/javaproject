/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.arap;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class ArApEmpDeduction extends Entity {
    //  he.`EMPLOYEE_NUM`,
//  he.`FULL_NAME`,
//  hd.`DEPARTMENT`,
//  ham.`amount`,
//  ham.`description` 
    
    private long arapMainId  = 0;
    private String empNum  = "";
    private String empName = "";
    private String Department = "";
    private double amount = 0;
    private String description = "";
    private long employeeId  = 0;
    private long compdeductionId = 0;

    /**
     * @return the empNum
     */
    public String getEmpNum() {
        return empNum;
    }

    /**
     * @param empNum the empNum to set
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    /**
     * @return the empName
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @return the Department
     */
    public String getDepartment() {
        return Department;
    }

    /**
     * @param Department the Department to set
     */
    public void setDepartment(String Department) {
        this.Department = Department;
    }



    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the arapMainId
     */
    public long getArapMainId() {
        return arapMainId;
    }

    /**
     * @param arapMainId the arapMainId to set
     */
    public void setArapMainId(long arapMainId) {
        this.arapMainId = arapMainId;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the compdeductionId
     */
    public long getCompdeductionId() {
        return compdeductionId;
    }

    /**
     * @param compdeductionId the compdeductionId to set
     */
    public void setCompdeductionId(long compdeductionId) {
        this.compdeductionId = compdeductionId;
    }
     
}
