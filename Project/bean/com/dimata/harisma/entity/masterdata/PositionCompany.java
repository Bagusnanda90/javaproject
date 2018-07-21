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

public class PositionCompany extends Entity {

    private long companyId = 0;
    private long positionId = 0;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }
}
