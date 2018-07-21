/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Hendra Putu
 */
import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class KpiAchievScore extends Entity {

    private long kpiListId = 0;
    private float achievPctgMin = 0;
    private float achievPctgMax = 0;
    private float achievDurationMin = 0;
    private float achievDurationMax = 0;
    private float score = 0;
    private Date validStart = null;
    private Date validEnd = null;

    public long getKpiListId() {
        return kpiListId;
    }

    public void setKpiListId(long kpiListId) {
        this.kpiListId = kpiListId;
    }

    public float getAchievPctgMin() {
        return achievPctgMin;
    }

    public void setAchievPctgMin(float achievPctgMin) {
        this.achievPctgMin = achievPctgMin;
    }

    public float getAchievPctgMax() {
        return achievPctgMax;
    }

    public void setAchievPctgMax(float achievPctgMax) {
        this.achievPctgMax = achievPctgMax;
    }

    public float getAchievDurationMin() {
        return achievDurationMin;
    }

    public void setAchievDurationMin(float achievDurationMin) {
        this.achievDurationMin = achievDurationMin;
    }

    public float getAchievDurationMax() {
        return achievDurationMax;
    }

    public void setAchievDurationMax(float achievDurationMax) {
        this.achievDurationMax = achievDurationMax;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getValidStart() {
        return validStart;
    }

    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    public Date getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }
}