/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Kartika
 */
public class PaySimulationStructure extends Entity {
    private long paySimulationId=0;
    private long companyId=0;
    private long divisionId=0;
    private long departmentId=0;
    private long sectionId=0;
    private long positionId=0;
    private long salLevelId =0;
    private String levelCode="";
    private long componentId=0;
    private String componentCode="";
    private int employeeCount=0;
    private double salaryAmount=0;
    private int newEmployeeAdd=0;
    private double salaryAmountAdd=0;
    private long sampleEmployeeId=0;
    
    /* only for view , not save to table */
    private String company="";
    private String division ="";
    private String department="";
    private String section ="";
    private String position="";
    
    /**
     * @return the paySimulationId
     */
    public long getPaySimulationId() {
        return paySimulationId;
    }

    /**
     * @param paySimulationId the paySimulationId to set
     */
    public void setPaySimulationId(long paySimulationId) {
        this.paySimulationId = paySimulationId;
    }

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the divisionId
     */
    public long getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

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
     * @return the sectionId
     */
    public long getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * @return the salLevelId
     */
    public long getSalLevelId() {
        return salLevelId;
    }

    /**
     * @param salLevelId the salLevelId to set
     */
    public void setSalLevelId(long salLevelId) {
        this.salLevelId = salLevelId;
    }

    /**
     * @return the levelCode
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @param levelCode the levelCode to set
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * @return the componentId
     */
    public long getComponentId() {
        return componentId;
    }

    /**
     * @param componentId the componentId to set
     */
    public void setComponentId(long componentId) {
        this.componentId = componentId;
    }

    /**
     * @return the componentCode
     */
    public String getComponentCode() {
        return componentCode;
    }

    /**
     * @param componentCode the componentCode to set
     */
    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    /**
     * @return the employeeCount
     */
    public int getEmployeeCount() {
        return employeeCount;
    }

    /**
     * @param employeeCount the employeeCount to set
     */
    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    /**
     * @return the salaryAmount
     */
    public double getSalaryAmount() {
        return salaryAmount;
    }

    /**
     * @param salaryAmount the salaryAmount to set
     */
    public void setSalaryAmount(double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    /**
     * @return the newEmployeeAdd
     */
    public int getNewEmployeeAdd() {
        return newEmployeeAdd;
    }

    /**
     * @param newEmployeeAdd the newEmployeeAdd to set
     */
    public void setNewEmployeeAdd(int newEmployeeAdd) {
        this.newEmployeeAdd = newEmployeeAdd;
    }

    /**
     * @return the salaryAmountAdd
     */
    public double getSalaryAmountAdd() {
        return salaryAmountAdd;
    }

    /**
     * @param salaryAmountAdd the salaryAmountAdd to set
     */
    public void setSalaryAmountAdd(double salaryAmountAdd) {
        this.salaryAmountAdd = salaryAmountAdd;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
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
     * @return the sampleEmployeeId
     */
    public long getSampleEmployeeId() {
        return sampleEmployeeId;
    }

    /**
     * @param sampleEmployeeId the sampleEmployeeId to set
     */
    public void setSampleEmployeeId(long sampleEmployeeId) {
        this.sampleEmployeeId = sampleEmployeeId;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }
    
}
