/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class TrainingScore extends Entity {

    private long trainingId = 0;
    private float pointMin = 0;
    private float pointMax = 0;
    private float durationMin = 0;
    private float durationMax = 0;
    private float frequencyMin = 0;
    private float frequencyMax = 0;
    private float score = 0;
    private String note = "";
    private Date validStart = new Date();
    private Date validEnd = new Date();

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public float getPointMin() {
        return pointMin;
    }

    public void setPointMin(float pointMin) {
        this.pointMin = pointMin;
    }

    public float getPointMax() {
        return pointMax;
    }

    public void setPointMax(float pointMax) {
        this.pointMax = pointMax;
    }

    public float getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(float durationMin) {
        this.durationMin = durationMin;
    }

    public float getDurationMax() {
        return durationMax;
    }

    public void setDurationMax(float durationMax) {
        this.durationMax = durationMax;
    }

    public float getFrequencyMin() {
        return frequencyMin;
    }

    public void setFrequencyMin(float frequencyMin) {
        this.frequencyMin = frequencyMin;
    }

    public float getFrequencyMax() {
        return frequencyMax;
    }

    public void setFrequencyMax(float frequencyMax) {
        this.frequencyMax = frequencyMax;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
