/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Dimata 007
 */
public class EmployeeCompetency extends Entity {

    private long employeeId = 0;
    private long competencyId = 0;
    private float levelValue = 0;
    private String specialAchievement = "";
    private Date dateOfAchvmt = new Date();
    private int history = 0;
    private long providerId = 0;

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
     * @return the competencyId
     */
    public long getCompetencyId() {
        return competencyId;
    }

    /**
     * @param competencyId the competencyId to set
     */
    public void setCompetencyId(long competencyId) {
        this.competencyId = competencyId;
    }

    /**
     * @return the levelValue
     */
    public float getLevelValue() {
        return levelValue;
    }

    /**
     * @param levelValue the levelValue to set
     */
    public void setLevelValue(float levelValue) {
        this.levelValue = levelValue;
    }

    /**
     * @return the specialAchievement
     */
    public String getSpecialAchievement() {
        return specialAchievement;
    }

    /**
     * @param specialAchievement the specialAchievement to set
     */
    public void setSpecialAchievement(String specialAchievement) {
        this.specialAchievement = specialAchievement;
    }

    /**
     * @return the dateOfAchvmt
     */
    public Date getDateOfAchvmt() {
        return dateOfAchvmt;
    }

    /**
     * @param dateOfAchvmt the dateOfAchvmt to set
     */
    public void setDateOfAchvmt(Date dateOfAchvmt) {
        this.dateOfAchvmt = dateOfAchvmt;
    }

    /**
     * @return the history
     */
    public int getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(int history) {
        this.history = history;
    }

    /**
     * @return the providerId
     */
    public long getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }
}
