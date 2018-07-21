/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Hendra McHen | 2015-02-02
 */
public class PositionEducationRequired extends Entity {
    private long positionId = 0;
    private long educationId = 0;
    private int durationMin = 0;
    private int durationRecommended = 0;
    private int pointMin = 0;
    private int pointRecommended = 0;
    private String note = "";

    /**
     * @return the educationId
     */
    public long getEducationId() {
        return educationId;
    }

    /**
     * @param educationId the educationId to set
     */
    public void setEducationId(long educationId) {
        this.educationId = educationId;
    }

    /**
     * @return the durationMin
     */
    public int getDurationMin() {
        return durationMin;
    }

    /**
     * @param durationMin the durationMin to set
     */
    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    /**
     * @return the durationRecommended
     */
    public int getDurationRecommended() {
        return durationRecommended;
    }

    /**
     * @param durationRecommended the durationRecommended to set
     */
    public void setDurationRecommended(int durationRecommended) {
        this.durationRecommended = durationRecommended;
    }

    /**
     * @return the pointMin
     */
    public int getPointMin() {
        return pointMin;
    }

    /**
     * @param pointMin the pointMin to set
     */
    public void setPointMin(int pointMin) {
        this.pointMin = pointMin;
    }

    /**
     * @return the pointRecommended
     */
    public int getPointRecommended() {
        return pointRecommended;
    }

    /**
     * @param pointRecommended the pointRecommended to set
     */
    public void setPointRecommended(int pointRecommended) {
        this.pointRecommended = pointRecommended;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
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
