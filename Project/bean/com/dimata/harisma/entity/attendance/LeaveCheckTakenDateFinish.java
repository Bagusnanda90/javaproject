/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import java.util.Date;

/**
 *
 * @author DEV04
 */
public class LeaveCheckTakenDateFinish {
    private long employeeId = 0;
    private Date takenDate;
    private Date finishDate;
    private String leaveSymbol;
    private long leaveAppId;
    private long oidDetailLeave;
    
    private Date submissionDate;
    private float takenQty=0.0F;
    
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
     * @return the takenDate
     */
    public Date getTakenDate() {
        return takenDate;
    }

    /**
     * @param takenDate the takenDate to set
     */
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    /**
     * @return the finishDate
     */
    public Date getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * @return the leaveSymbol
     */
    public String getLeaveSymbol() {
        return leaveSymbol;
    }

    /**
     * @param leaveSymbol the leaveSymbol to set
     */
    public void setLeaveSymbol(String leaveSymbol) {
        this.leaveSymbol = leaveSymbol;
    }

    /**
     * @return the leaveAppId
     */
    public long getLeaveAppId() {
        return leaveAppId;
    }

    /**
     * @param leaveAppId the leaveAppId to set
     */
    public void setLeaveAppId(long leaveAppId) {
        this.leaveAppId = leaveAppId;
    }

    /**
     * @return the oidDetailLeave
     */
    public long getOidDetailLeave() {
        return oidDetailLeave;
    }

    /**
     * @param oidDetailLeave the oidDetailLeave to set
     */
    public void setOidDetailLeave(long oidDetailLeave) {
        this.oidDetailLeave = oidDetailLeave;
    }

   

    /**
     * @return the submissionDate
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @param submissionDate the submissionDate to set
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * @return the takenQty
     */
    public float getTakenQty() {
        return takenQty;
    }

    /**
     * @param takenQty the takenQty to set
     */
    public void setTakenQty(float takenQty) {
        this.takenQty = takenQty;
    }
}
