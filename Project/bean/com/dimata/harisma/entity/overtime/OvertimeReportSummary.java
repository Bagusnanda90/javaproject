/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

import com.dimata.harisma.session.payroll.SessOvertime;
import com.dimata.qdep.entity.I_DocStatus;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class OvertimeReportSummary {
    private long employee_id;
    private String empNumber;
    private String fullName;
    private String religion;
    private String departmentEmployee;
    private String costDept;
    private double ovtDuration=0;
    private double totIdx=0;;
    private int allowance;
    private int paidBy;
    private Date dateFrom;
    private Date dateTo;
    private String company;
    private String division;
    private String sectionName;
    private double ovtRoundDuration=0;
    
    private String statusDoc;
    /**
     * @return the empNumber
     */
    public String getEmpNumber() {
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
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion the religion to set
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return the departmentEmployee
     */
    public String getDepartmentEmployee() {
        return departmentEmployee;
    }

    /**
     * @param departmentEmployee the departmentEmployee to set
     */
    public void setDepartmentEmployee(String departmentEmployee) {
        this.departmentEmployee = departmentEmployee;
    }

    /**
     * @return the costDept
     */
    public String getCostDept() {
        return costDept;
    }

    /**
     * @param costDept the costDept to set
     */
    public void setCostDept(String costDept) {
        this.costDept = costDept;
    }

    /**
     * @return the ovtDuration
     */
    public double getOvtDuration() {
        return ovtDuration;
    }

    /**
     * @param ovtDuration the ovtDuration to set
     */
    public void setOvtDuration(double ovtDuration) {
        this.ovtDuration = ovtDuration;
    }

    /**
     * @return the totIdx
     */
    public double getTotIdx() {
        return totIdx;
    }

    /**
     * @param totIdx the totIdx to set
     */
    public void setTotIdx(double totIdx) {
        this.totIdx = totIdx;
    }

    /**
     * @return the allowance
     */
    public int getAllowance() {
        return allowance;
    }

    /**
     * @param allowance the allowance to set
     */
    public void setAllowance(int allowance) {
        this.allowance = allowance;
    }

    /**
     * @return the paidBy
     */
    public int getPaidBy() {
        return paidBy;
    }

    /**
     * @param paidBy the paidBy to set
     */
    public void setPaidBy(int paidBy) {
        this.paidBy = paidBy;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the employee_id
     */
    public long getEmployee_id() {
        return employee_id;
    }

    /**
     * @param employee_id the employee_id to set
     */
    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
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
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the ovtRoundDuration
     */
    public double getOvtRoundDuration() {
            ovtRoundDuration =ovtDuration;
        if(SessOvertime.getOvertimeRoundTo()>0 && (ovtRoundDuration*60)>SessOvertime.getOvertimeRoundStart() ){ 
            
            ovtRoundDuration = ((double)((Math.round(ovtRoundDuration*60)/ SessOvertime.getOvertimeRoundTo())*SessOvertime.getOvertimeRoundTo()))/60d;
            return ovtRoundDuration;
        } else{
            return ovtRoundDuration;
        }
        //return ovtRoundDuration;
    }

    /**
     * @return the statusDoc
     */
    public String getStatusDoc() {
        return statusDoc;
    }

    /**
     * @param statusDoc the statusDoc to set
     */
    public void setStatusDoc(int statusDoc) {
        if(I_DocStatus.fieldDocumentStatus!=null && I_DocStatus.fieldDocumentStatus.length>0){
            this.statusDoc= I_DocStatus.fieldDocumentStatus[statusDoc];
        }
        
        
    }
}
