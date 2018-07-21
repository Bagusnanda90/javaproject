/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.periodestokopname;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Devin
 */
public class PeriodeStokOpname extends Entity{
    private String namePeriod;
    private String period;
    private Date startDate;
    private Date endDate;

    /**
     * @return the namePeriod
     */
    public String getNamePeriod() {
        return namePeriod;
    }

    /**
     * @param namePeriod the namePeriod to set
     */
    public void setNamePeriod(String namePeriod) {
        this.namePeriod = namePeriod;
    }

    /**
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

   
    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
