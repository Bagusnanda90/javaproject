/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.search;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SrcReportDailyXls {
    private Date selectedDateFrom;
    private Date selectedDateTo;
    private String empNum;
    private String fullName;
    private int reason_sts;
    private long oidDepartment;
    private long oidSection;
    private int statusResign;
    private String stsEmpCategorySel;
    private Vector statusPresence= new Vector();
    private String statusSchedule;
    
    //update by satrya 2013-12-03
    private long oidCompany;
    private long oidDivision;

    public int sizeStsPresenceSel(){
        return statusPresence.size();
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
     * @return the statusResign
     */
    public int getStatusResign() {
        return statusResign;
    }

    /**
     * @param statusResign the statusResign to set
     */
    public void setStatusResign(int statusResign) {
        this.statusResign = statusResign;
    }

    /**
     * @return the stsEmpCategorySel
     */
    public String getStsEmpCategorySel() {
        return stsEmpCategorySel;
    }

    /**
     * @param stsEmpCategorySel the stsEmpCategorySel to set
     */
    public void setStsEmpCategorySel(String stsEmpCategorySel) {
        if(stsEmpCategorySel!=null && stsEmpCategorySel.length()>0){
            stsEmpCategorySel = stsEmpCategorySel.substring(0,stsEmpCategorySel.length()-1);
        }
        this.stsEmpCategorySel = stsEmpCategorySel;
    }

    /**
     * @return the statusSchedule
     */
    public String getStatusSchedule() {
        return statusSchedule;
    }

    /**
     * @param statusSchedule the statusSchedule to set
     */
    public void setStatusSchedule(String statusSchedule) {
         if(statusSchedule!=null && statusSchedule.length()>0){
            statusSchedule = statusSchedule.substring(0,statusSchedule.length()-1);
        }
        this.statusSchedule = statusSchedule;
    }

    /**
     * @return the statusPresence
     */
//    public String getStatusPresence(int idx) {
//        if(idx>=statusPresence.size()){
//            return "";
//        }
//        
//        //return statusPresence;
//        return (String)statusPresence.get(idx);
//    }
public Vector getStatusPresence() {
        return statusPresence;
}

    /**
     * @param statusPresence the statusPresence to set
     */
public void addStatusPresence(Vector statusPresence) {
        this.statusPresence = statusPresence;
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
    
}
