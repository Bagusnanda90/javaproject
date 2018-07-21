/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 * @date 2015-08-04
 * @author Hendra Putu
 */
import com.dimata.qdep.entity.Entity;

public class DownPosition extends Entity {

    private long positionId = 0;
    private long positionDownlink = 0;
    private int typeOfLink = 0;

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public int getTypeOfLink() {
        return typeOfLink;
    }

    public void setTypeOfLink(int typeOfLink) {
        this.typeOfLink = typeOfLink;
    }

    /**
     * @return the positionDownlink
     */
    public long getPositionDownlink() {
        return positionDownlink;
    }

    /**
     * @param positionDownlink the positionDownlink to set
     */
    public void setPositionDownlink(long positionDownlink) {
        this.positionDownlink = positionDownlink;
    }
}
