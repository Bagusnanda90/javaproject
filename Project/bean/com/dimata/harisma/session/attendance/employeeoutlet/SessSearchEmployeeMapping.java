/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance.employeeoutlet;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class SessSearchEmployeeMapping {
     public static final String SESS_SRC_EMPLOYEE_OUTLET ="SESS_SRC_EMPLOYEE_OUTLET";
     
     private long oidDepartment;
     private long oidCompany;
     private long oidDivision;
     private long oidSection;
     private Date selectedDateFrom;
     private Date selectedDateTo;
     private String locationSearchId;
     private long positionId;
     private String empNumber;
     private String fullName;
     
     private Date selectedDateFromEmployeeOutlet;
     private Date selectedDateToEmployeeOutlet;
     private long oidEmployeeOutlet;

    /**
     * @return the oidDepartment
     */
    public long getOidDepartment() {
        return oidDepartment;
    }

    /**
     * @param oidDepartment the oidDepartment to set
     */
    public void setOidDepartment(long oidDepartment) {
        this.oidDepartment = oidDepartment;
    }

    /**
     * @return the oidCompany
     */
    public long getOidCompany() {
        return oidCompany;
    }

    /**
     * @param oidCompany the oidCompany to set
     */
    public void setOidCompany(long oidCompany) {
        this.oidCompany = oidCompany;
    }

    /**
     * @return the oidDivision
     */
    public long getOidDivision() {
        return oidDivision;
    }

    /**
     * @param oidDivision the oidDivision to set
     */
    public void setOidDivision(long oidDivision) {
        this.oidDivision = oidDivision;
    }

    /**
     * @return the oidSection
     */
    public long getOidSection() {
        return oidSection;
    }

    /**
     * @param oidSection the oidSection to set
     */
    public void setOidSection(long oidSection) {
        this.oidSection = oidSection;
    }

    /**
     * @return the selectedDateFrom
     */
    public Date getSelectedDateFrom() {
        return selectedDateFrom;
    }

    /**
     * @param selectedDateFrom the selectedDateFrom to set
     */
    public void setSelectedDateFrom(Date selectedDateFrom) {
        this.selectedDateFrom = selectedDateFrom;
    }

    /**
     * @return the selectedDateTo
     */
    public Date getSelectedDateTo() {
        return selectedDateTo;
    }

    /**
     * @param selectedDateTo the selectedDateTo to set
     */
    public void setSelectedDateTo(Date selectedDateTo) {
        this.selectedDateTo = selectedDateTo;
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

    /**
     * @return the empNumber
     */
    public String getEmpNumber() {
        if(empNumber==null){
            return "";
        }
        return empNumber;
    }

    /**
     * @param empNumber the empNumber to set
     */
    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        if(fullName==null){
            return "";
        }
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the selectedDateFromEmployeeOutlet
     */
    public Date getSelectedDateFromEmployeeOutlet() {
        return selectedDateFromEmployeeOutlet;
    }

    /**
     * @param selectedDateFromEmployeeOutlet the selectedDateFromEmployeeOutlet to set
     */
    public void setSelectedDateFromEmployeeOutlet(Date selectedDateFromEmployeeOutlet) {
        this.selectedDateFromEmployeeOutlet = selectedDateFromEmployeeOutlet;
    }

    /**
     * @return the selectedDateToEmployeeOutlet
     */
    public Date getSelectedDateToEmployeeOutlet() {
        return selectedDateToEmployeeOutlet;
    }

    /**
     * @param selectedDateToEmployeeOutlet the selectedDateToEmployeeOutlet to set
     */
    public void setSelectedDateToEmployeeOutlet(Date selectedDateToEmployeeOutlet) {
        this.selectedDateToEmployeeOutlet = selectedDateToEmployeeOutlet;
    }

    /**
     * @return the oidEmployeeOutlet
     */
    public long getOidEmployeeOutlet() {
        return oidEmployeeOutlet;
    }

    /**
     * @param oidEmployeeOutlet the oidEmployeeOutlet to set
     */
    public void setOidEmployeeOutlet(long oidEmployeeOutlet) {
        this.oidEmployeeOutlet = oidEmployeeOutlet;
    }

    /**
     * @return the locationSearchId
     */
    public String getLocationSearchId() {
        return locationSearchId;
}

    /**
     * @param locationSearchId the locationSearchId to set
     */
    public void setLocationSearchId(String locationSearchId) {
        this.locationSearchId = locationSearchId;
    }
}
