/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author IPAG
 */
import com.dimata.qdep.entity.Entity;

public class PositionSubSection extends Entity {
    private long subSectionId = 0;
    private long positionId = 0;

    public long getSubSectionId() {
        return subSectionId;
    }

    public void setSubSectionId(long subSectionId) {
        this.subSectionId = subSectionId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }
    
}
