/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Dimata 007
 */
public class CompetencyDetail extends Entity {
    private long competencyId = 0;
    private String description = "";

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
}
