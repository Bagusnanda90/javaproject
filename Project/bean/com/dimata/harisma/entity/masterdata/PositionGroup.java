/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class PositionGroup extends Entity {
    private long positionGroupId = 0;
    private String positionGroupName = "";
    private String description = "";

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
     * @return the positionGroupName
     */
    public String getPositionGroupName() {
        return positionGroupName;
    }

    /**
     * @param positionGroupName the positionGroupName to set
     */
    public void setPositionGroupName(String positionGroupName) {
        this.positionGroupName = positionGroupName;
    }
}
