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
public class CompetencyLevel extends Entity {
    private long competencyId = 0;
    private float scoreValue = 0;
    private String description = "";
    private int levelMin = 0;
    private int levelMax = 0;
    private String levelUnit = "";
    private Date validStart = new Date();
    private Date validEnd = new Date();

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
     * @return the scoreValue
     */
    public float getScoreValue() {
        return scoreValue;
    }

    /**
     * @param scoreValue the scoreValue to set
     */
    public void setScoreValue(float scoreValue) {
        this.scoreValue = scoreValue;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the levelMin
     */
    public int getLevelMin() {
        return levelMin;
    }

    /**
     * @param levelMin the levelMin to set
     */
    public void setLevelMin(int levelMin) {
        this.levelMin = levelMin;
    }

    /**
     * @return the levelMax
     */
    public int getLevelMax() {
        return levelMax;
    }

    /**
     * @param levelMax the levelMax to set
     */
    public void setLevelMax(int levelMax) {
        this.levelMax = levelMax;
    }

    /**
     * @return the levelUnit
     */
    public String getLevelUnit() {
        return levelUnit;
    }

    /**
     * @param levelUnit the levelUnit to set
     */
    public void setLevelUnit(String levelUnit) {
        this.levelUnit = levelUnit;
    }

    /**
     * @return the validStart
     */
    public Date getValidStart() {
        return validStart;
    }

    /**
     * @param validStart the validStart to set
     */
    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    /**
     * @return the validEnd
     */
    public Date getValidEnd() {
        return validEnd;
    }

    /**
     * @param validEnd the validEnd to set
     */
    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }
}
