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
public class KPI_List_Group extends Entity{

    	private long kpiListId;
    	private long kpiGroupId;

    /**
     * @return the kpiListId
     */
    public long getKpiListId() {
        return kpiListId;
    }

    /**
     * @param kpiListId the kpiListId to set
     */
    public void setKpiListId(long kpiListId) {
        this.kpiListId = kpiListId;
    }

    /**
     * @return the kpiGroupId
     */
    public long getKpiGroupId() {
        return kpiGroupId;
    }

    /**
     * @param kpiGroupId the kpiGroupId to set
     */
    public void setKpiGroupId(long kpiGroupId) {
        this.kpiGroupId = kpiGroupId;
    }
}
