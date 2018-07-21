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
public class LeaveConfigurationDetailPosition extends Entity{
    private long leaveConfigurationMainId;
    private long employeeId;
    private long positionMin;
    private long positionMax;
    private String namaPosMin;
    private String namaPosMax;

    /**
     * @return the leaveConfigurationMainId
     */
    public long getLeaveConfigurationMainId() {
        return leaveConfigurationMainId;
    }

    /**
     * @param leaveConfigurationMainId the leaveConfigurationMainId to set
     */
    public void setLeaveConfigurationMainId(long leaveConfigurationMainId) {
        this.leaveConfigurationMainId = leaveConfigurationMainId;
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
     * @return the positionMin
     */
    public long getPositionMin() {
        return positionMin;
    }

    /**
     * @param positionMin the positionMin to set
     */
    public void setPositionMin(long positionMin) {
        this.positionMin = positionMin;
    }

    /**
     * @return the positionMax
     */
    public long getPositionMax() {
        return positionMax;
    }

    /**
     * @param positionMax the positionMax to set
     */
    public void setPositionMax(long positionMax) {
        this.positionMax = positionMax;
    }

    /**
     * @return the namaPosMin
     */
    public String getNamaPosMin() {
        if(namaPosMin==null){
            return "-";
        }
        return namaPosMin;
    }

    /**
     * @param namaPosMin the namaPosMin to set
     */
    public void setNamaPosMin(String namaPosMin) {
        this.namaPosMin = namaPosMin;
    }

    /**
     * @return the namaPosMax
     */
    public String getNamaPosMax() {
        if(namaPosMax==null){
            return "-";
        }
        return namaPosMax;
    }

    /**
     * @param namaPosMax the namaPosMax to set
     */
    public void setNamaPosMax(String namaPosMax) {
        this.namaPosMax = namaPosMax;
    }
}
