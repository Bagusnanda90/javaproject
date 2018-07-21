/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import java.util.Date;

/**
 *
 * @author GUSWIK
 */
public class SessPresenceReportDaily {
    
    private Date selectedDateFrom;
    private Date selectedDateTo;
    private String empNum = "";
    private String fullName = "";
    private int reason_sts = 0;       
    private long oidDepartment = 0;        
    private long oidSection = 0;
    private long oidCompany = 0;
    private long oidDivision = 0;
    private Date date ;
    private String sStatusResign = ""; 

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
     * @return the reason_sts
     */
    public int getReason_sts() {
        return reason_sts;
    }

    /**
     * @param reason_sts the reason_sts to set
     */
    public void setReason_sts(int reason_sts) {
        this.reason_sts = reason_sts;
    }

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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the sStatusResign
     */
    public String getsStatusResign() {
        return sStatusResign;
    }

    /**
     * @param sStatusResign the sStatusResign to set
     */
    public void setsStatusResign(String sStatusResign) {
        this.sStatusResign = sStatusResign;
    }
    
    
}
