/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.util.Date;

/**
 *
 * @author GUSWIK
 */
public class LosReportEntity {
    
    private String payrollNumber = "";
    private String fullName = "";
    private String division = "";
    private String department = "";
    private String section = "";
    private String position = "";
    private String empCategory = "";
    private String level = "";
    private String payrollG = "";
    private Date commencing = new Date();
    private String workYear = "";
    private String workMonth = "";
    private double amount = 0;

    
    private Hashtable paycompValue = new Hashtable();
    /**
     * @return the payrollNumber
     */
    public String getPayrollNumber() {
        return payrollNumber;
    }

    /**
     * @param payrollNumber the payrollNumber to set
     */
    public void setPayrollNumber(String payrollNumber) {
        this.payrollNumber = payrollNumber;
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

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
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
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the empCategory
     */
    public String getEmpCategory() {
        return empCategory;
    }

    /**
     * @param empCategory the empCategory to set
     */
    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the payrollG
     */
    public String getPayrollG() {
        return payrollG;
    }

    /**
     * @param payrollG the payrollG to set
     */
    public void setPayrollG(String payrollG) {
        this.payrollG = payrollG;
    }

    /**
     * @return the commencing
     */
    public Date getCommencing() {
        return commencing;
    }

    /**
     * @param commencing the commencing to set
     */
    public void setCommencing(Date commencing) {
        this.commencing = commencing;
    }

    /**
     * @return the workYear
     */
    public String getWorkYear() {
        return workYear;
    }

    /**
     * @param workYear the workYear to set
     */
    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    /**
     * @return the workMonth
     */
    public String getWorkMonth() {
        return workMonth;
    }

    /**
     * @param workMonth the workMonth to set
     */
    public void setWorkMonth(String workMonth) {
        this.workMonth = workMonth;
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

    
    public void addCompValue(String  compCode, double value) {
            this.paycompValue.put(compCode,value);
    }
    
    public String getCompValue(String key) {
        return ""+paycompValue.get(key); 
    }
}
