/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author khirayinnura
 */
public class PositionKPI extends Entity {
    private long posKpiId = 0;
    private int kpiMinAchievment = 0;
    private int kpiRecommendAchiev = 0;
    private int durationMonth = 0;
    private long positionId = 0;
    private long kpiListId = 0;
    private float scoreMin = 0;
    private float scoreRecommended = 0;

    /**
     * @return the posKpiId
     */
    public long getPosKpiId() {
        return posKpiId;
    }

    /**
     * @param posKpiId the posKpiId to set
     */
    public void setPosKpiId(long posKpiId) {
        this.posKpiId = posKpiId;
    }

    /**
     * @return the kpiMinAchievment
     */
    public int getKpiMinAchievment() {
        return kpiMinAchievment;
    }

    /**
     * @param kpiMinAchievment the kpiMinAchievment to set
     */
    public void setKpiMinAchievment(int kpiMinAchievment) {
        this.kpiMinAchievment = kpiMinAchievment;
    }

    /**
     * @return the kpiRecommendAchiev
     */
    public int getKpiRecommendAchiev() {
        return kpiRecommendAchiev;
    }

    /**
     * @param kpiRecommendAchiev the kpiRecommendAchiev to set
     */
    public void setKpiRecommendAchiev(int kpiRecommendAchiev) {
        this.kpiRecommendAchiev = kpiRecommendAchiev;
    }

    /**
     * @return the durationMonth
     */
    public int getDurationMonth() {
        return durationMonth;
    }

    /**
     * @param durationMonth the durationMonth to set
     */
    public void setDurationMonth(int durationMonth) {
        this.durationMonth = durationMonth;
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
     * @return the scoreMin
     */
    public float getScoreMin() {
        return scoreMin;
    }

    /**
     * @param scoreMin the scoreMin to set
     */
    public void setScoreMin(float scoreMin) {
        this.scoreMin = scoreMin;
    }

    /**
     * @return the scoreRecommended
     */
    public float getScoreRecommended() {
        return scoreRecommended;
    }

    /**
     * @param scoreRecommended the scoreRecommended to set
     */
    public void setScoreRecommended(float scoreRecommended) {
        this.scoreRecommended = scoreRecommended;
    }
}
