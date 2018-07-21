/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 * Date : 2015-07-29
 * @author Putu Hendra McHen
 */
import com.dimata.qdep.entity.Entity;

public class PositionDepartment extends Entity {

    private long departmentId = 0;
    private long positionId = 0;

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }
}
