/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */
import com.dimata.qdep.entity.Entity;

public class OutSourcePlanCost extends Entity {

    private long outSourcePlanLocId = 0;
    private long outSourceCostId = 0;
    private double incrsToPrevYear = 0;
    private double planAvrgCost = 0;
    private long  planLocDivisionId=0; // only to view
    private long  planLocPositionId=0; // only to view

    public long getOutSourcePlanLocId() {
        return outSourcePlanLocId;
    }

    public void setOutSourcePlanLocId(long outSourcePlanLocId) {
        this.outSourcePlanLocId = outSourcePlanLocId;
    }


    public double getIncrsToPrevYear() {
        return incrsToPrevYear;
    }

    public void setIncrsToPrevYear(double incrsToPrevYear) {
        this.incrsToPrevYear = incrsToPrevYear;
    }

    public double getPlanAvrgCost() {
        return planAvrgCost;
    }

    public void setPlanAvrgCost(double planAvrgCost) {
        this.planAvrgCost = planAvrgCost;
    }

    /**
     * @return the outSourceCostId
     */
    public long getOutSourceCostId() {
        return outSourceCostId;
    }

    /**
     * @param outSourceCostId the outSourceCostId to set
     */
    public void setOutSourceCostId(long outSourceCostId) {
        this.outSourceCostId = outSourceCostId;
    }

    /**
     * @return the planLocDivisionId
     */
    public long getPlanLocDivisionId() {
        return planLocDivisionId;
    }

    /**
     * @param planLocDivisionId the planLocDivisionId to set
     */
    public void setPlanLocDivisionId(long planLocDivisionId) {
        this.planLocDivisionId = planLocDivisionId;
    }

    /**
     * @return the planLocPositionId
     */
    public long getPlanLocPositionId() {
        return planLocPositionId;
    }

    /**
     * @param planLocPositionId the planLocPositionId to set
     */
    public void setPlanLocPositionId(long planLocPositionId) {
        this.planLocPositionId = planLocPositionId;
    }
}