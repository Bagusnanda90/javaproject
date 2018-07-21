/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 * date : 2015-07-29
 *
 * @author Putu Hendra McHen
 */
import com.dimata.qdep.entity.Entity;

public class PositionDivision extends Entity {

    private long divisionId = 0;
    private long positionId = 0;

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }
}
