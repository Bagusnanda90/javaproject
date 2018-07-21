/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance.mappingoutlet;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class ExtraScheduleOutletDetail extends Entity{
    private long extraScheduleMappingId;
    private long employeeId;
    private Date startDatePlan;
    private Date endDatePlan;
    private Date startDateReal;
    private Date endDateReal;
    private Date restTimeStart;
    private float restTimeHr;
    private String jobDesc;
    //private int typeOffSchedule;
    private long locationId;
    private int statusDocDetail;
    
    private String docNumber;
    private String fullName;
    
    //update by satrya 2014-08-05
    private long positionId;

    /**
     * @return the extraScheduleMappingId
     */
    public long getExtraScheduleMappingId() {
        return extraScheduleMappingId;
    }

    /**
     * @param extraScheduleMappingId the extraScheduleMappingId to set
     */
    public void setExtraScheduleMappingId(long extraScheduleMappingId) {
        this.extraScheduleMappingId = extraScheduleMappingId;
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
     * @return the startDatePlan
     */
    public Date getStartDatePlan() {
        return startDatePlan;
    }

    /**
     * @param startDatePlan the startDatePlan to set
     */
    public void setStartDatePlan(Date startDatePlan) {
        this.startDatePlan = startDatePlan;
    }

    /**
     * @return the endDatePlan
     */
    public Date getEndDatePlan() {
        return endDatePlan;
    }

    /**
     * @param endDatePlan the endDatePlan to set
     */
    public void setEndDatePlan(Date endDatePlan) {
        this.endDatePlan = endDatePlan;
    }

    /**
     * @return the startDateReal
     */
    public Date getStartDateReal() {
        return startDateReal;
    }

    /**
     * @param startDateReal the startDateReal to set
     */
    public void setStartDateReal(Date startDateReal) {
        this.startDateReal = startDateReal;
    }

    /**
     * @return the endDateReal
     */
    public Date getEndDateReal() {
        return endDateReal;
    }

    /**
     * @param endDateReal the endDateReal to set
     */
    public void setEndDateReal(Date endDateReal) {
        this.endDateReal = endDateReal;
    }

    /**
     * @return the restTimeStart
     */
    public Date getRestTimeStart() {
        return restTimeStart;
    }

    /**
     * @param restTimeStart the restTimeStart to set
     */
    public void setRestTimeStart(Date restTimeStart) {
        this.restTimeStart = restTimeStart;
    }

    /**
     * @return the restTimeHr
     */
    public float getRestTimeHr() {
        return restTimeHr;
    }

    /**
     * @param restTimeHr the restTimeHr to set
     */
    public void setRestTimeHr(float restTimeHr) {
        this.restTimeHr = restTimeHr;
    }

    /**
     * @return the jobDesc
     */
    public String getJobDesc() {
        if(jobDesc==null){
            return "";
        }
        return jobDesc;
    }

    /**
     * @param jobDesc the jobDesc to set
     */
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the statusDocDetail
     */
    public int getStatusDocDetail() {
        return statusDocDetail;
    }

    /**
     * @param statusDocDetail the statusDocDetail to set
     */
    public void setStatusDocDetail(int statusDocDetail) {
        this.statusDocDetail = statusDocDetail;
    }

   
    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @param docNumber the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
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
