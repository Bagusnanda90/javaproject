/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author GUSWIK
 */
public class KPI_Division_Target extends Entity{
    private long kpiDivisionTargetId ;
    private long kpiListId ;
    private Date startDate ;
    private Date endDate ;  
    private long divisionId;
    private double target;
    private double achievement;

    /**
     * @return the kpiDivisionTargetId
     */
    public long getKpiDivisionTargetId() {
        return kpiDivisionTargetId;
    }

    /**
     * @param kpiDivisionTargetId the kpiDivisionTargetId to set
     */
    public void setKpiDivisionTargetId(long kpiDivisionTargetId) {
        this.kpiDivisionTargetId = kpiDivisionTargetId;
    }

    /**
     * @return the kpiListId
     */
    public long getKpiListId() {
        return kpiListId;
    }

    /**
     * @param kpiListId the kpiListId to set
     */
    public void setKpiListId(long kpiListId) {
        this.kpiListId = kpiListId;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
     * @return the target
     */
    public double getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(double target) {
        this.target = target;
    }

    /**
     * @return the achievement
     */
    public double getAchievement() {
        return achievement;
    }

    /**
     * @param achievement the achievement to set
     */
    public void setAchievement(double achievement) {
        this.achievement = achievement;
    }

  
}
