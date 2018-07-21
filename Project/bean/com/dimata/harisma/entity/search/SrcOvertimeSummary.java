/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.search;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class SrcOvertimeSummary {
    private String empNumber;
    private String fullName;
    private long companyId;
    private long divisionId;
    private long departementId;
    private long sectionId;
    private long religionId;
    private Date startOvertime;
    private Date endOvertime;
    private int sortBy;
    private long costCenterDptId;
    private int groupBy;
    private int resignStatus;
    //update bu devin 2014-02-12
    private boolean cekUser;
    /**
     * @return the empNumber
     */
    public String getEmpNumber() {
        if(empNumber!=null && empNumber.length()>0){
                return empNumber;
        }
        return "";
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
        if(fullName!=null && fullName.length()>0){
            return fullName;
        }
        return "";
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     * @return the departementId
     */
    public long getDepartementId() {
        return departementId;
    }

    /**
     * @param departementId the departementId to set
     */
    public void setDepartementId(long departementId) {
        this.departementId = departementId;
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
     * @return the startOvertime
     */
    public Date getStartOvertime() {
        return startOvertime;
    }

    /**
     * @param startOvertime the startOvertime to set
     */
    public void setStartOvertime(Date startOvertime) {
        this.startOvertime = startOvertime;
    }

    /**
     * @return the endOvertime
     */
    public Date getEndOvertime() {
        return endOvertime;
    }

    /**
     * @param endOvertime the endOvertime to set
     */
    public void setEndOvertime(Date endOvertime) {
        this.endOvertime = endOvertime;
    }

    /**
     * @return the sortBy
     */
    public int getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the religionId
     */
    public long getReligionId() {
        return religionId;
    }

    /**
     * @param religionId the religionId to set
     */
    public void setReligionId(long religionId) {
        this.religionId = religionId;
    }

    /**
     * @return the costCenterDptId
     */
    public long getCostCenterDptId() {
        return costCenterDptId;
    }

    /**
     * @param costCenterDptId the costCenterDptId to set
     */
    public void setCostCenterDptId(long costCenterDptId) {
        this.costCenterDptId = costCenterDptId;
    }

    /**
     * @return the groupBy
     */
    public int getGroupBy() {
        return groupBy;
    }

    /**
     * @param groupBy the groupBy to set
     */
    public void setGroupBy(int groupBy) {
        this.groupBy = groupBy;
    }

    /**
     * @return the resignStatus
     */
    public int getResignStatus() {
        return resignStatus;
    }

    /**
     * @param resignStatus the resignStatus to set
     */
    public void setResignStatus(int resignStatus) {
        this.resignStatus = resignStatus;
    }

    /**
     * @return the cekUser
     */
    public boolean isCekUser() {
        return cekUser;
    }
   
    /**
     * @param cekUser the cekLogin to set
     */
    public void setCekUser(boolean cekUser) {
        this.cekUser = cekUser;
}

   
}
