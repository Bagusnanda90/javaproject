/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.payday;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Satrya Ramayu
 */
public class PayDay extends Entity{
    private long empCategoryId;
    private long positionId;
    private double valuePayDay;
    private double valueHitungPayDay;

    /**
     * @return the empCategoryId
     */
    public long getEmpCategoryId() {
        return empCategoryId;
    }

    /**
     * @param empCategoryId the empCategoryId to set
     */
    public void setEmpCategoryId(long empCategoryId) {
        this.empCategoryId = empCategoryId;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the valuePayDay
     */
    public double getValuePayDay() {
        return valuePayDay;
    }

    /**
     * @param valuePayDay the valuePayDay to set
     */
    public void setValuePayDay(double valuePayDay) {
        this.valuePayDay = valuePayDay;
    }

    /**
     * @return the valueHitungPayDay
     */
    
}
