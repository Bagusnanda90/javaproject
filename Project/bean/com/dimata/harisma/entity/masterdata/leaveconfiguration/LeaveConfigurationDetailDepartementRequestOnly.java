/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.leaveconfiguration;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Satrya Ramayu
 */
public class LeaveConfigurationDetailDepartementRequestOnly extends Entity{
    private long leaveConfigurationMainIdRequestOnly;
    private long employeeId;
    private long departementId;
    private String[] departementIds;
    private String departementNama;
    private long sectionId;
    private String sectionName;

    
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
     * @return the departementIds
     */
    public String[] getDepartementIds() {
        return departementIds;
    }

    /**
     * @param departementIds the departementIds to set
     */
    public void setDepartementIds(String[] departementIds) {
        this.departementIds = departementIds;
    }

    /**
     * @return the departementNama
     */
    public String getDepartementNama() {
        return departementNama;
    }

    /**
     * @param departementNama the departementNama to set
     */
    public void setDepartementNama(String departementNama) {
        this.departementNama = departementNama;
    }

    /**
     * @return the sectionName
     */
    public String getSectionName() {
        if(sectionName==null){
            return "-";
        }
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the leaveConfigurationMainIdRequestOnly
     */
    public long getLeaveConfigurationMainIdRequestOnly() {
        return leaveConfigurationMainIdRequestOnly;
    }

    /**
     * @param leaveConfigurationMainIdRequestOnly the leaveConfigurationMainIdRequestOnly to set
     */
    public void setLeaveConfigurationMainIdRequestOnly(long leaveConfigurationMainIdRequestOnly) {
        this.leaveConfigurationMainIdRequestOnly = leaveConfigurationMainIdRequestOnly;
    }
}
