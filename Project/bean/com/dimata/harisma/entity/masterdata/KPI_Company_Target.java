/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class KPI_Company_Target extends Entity{
    private long kpiCompanyTargetId ;
    private long kpiListId ;
    private Date startDate ;
    private Date endDate ;  
    private long companyId;
    private double  target;
    private double achievement;
    
    private long competitorId;

    private double competitorValue;
    /**
     * @return the kpiCompanyTargetId
     */
    public long getKpiCompanyTargetId() {
        return kpiCompanyTargetId;
    }

    /**
     * @param kpiCompanyTargetId the kpiCompanyTargetId to set
     */
    public void setKpiCompanyTargetId(long kpiCompanyTargetId) {
        this.kpiCompanyTargetId = kpiCompanyTargetId;
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

 
   
    /**
     * @return the competitorValue
     */
    public double getCompetitorValue() {
        return competitorValue;
    }

    /**
     * @param competitorValue the competitorValue to set
     */
    public void setCompetitorValue(double competitorValue) {
        this.competitorValue = competitorValue;
    }

    /**
     * @param competitorId the competitorId to set
     */
    public void setCompetitorId(long competitorId) {
        this.competitorId = competitorId;
    }

    /**
     * @return the competitorId
     */
    public long getCompetitorId() {
        return competitorId;
    }

    
    
}
