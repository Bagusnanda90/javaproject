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
public class KPI_Group extends Entity{
      	private long kpi_group_id ;
       	private long kpi_type_id ;
        private String group_title = "";
        private String description = "";

    /**
     * @return the kpi_group_id
     */
    public long getKpi_group_id() {
        return kpi_group_id;
    }

    /**
     * @param kpi_group_id the kpi_group_id to set
     */
    public void setKpi_group_id(long kpi_group_id) {
        this.kpi_group_id = kpi_group_id;
    }

    /**
     * @return the kpi_type_id
     */
    public long getKpi_type_id() {
        return kpi_type_id;
    }

    /**
     * @param kpi_type_id the kpi_type_id to set
     */
    public void setKpi_type_id(long kpi_type_id) {
        this.kpi_type_id = kpi_type_id;
    }

    /**
     * @return the group_title
     */
    public String getGroup_title() {
        return group_title;
    }

    /**
     * @param group_title the group_title to set
     */
    public void setGroup_title(String group_title) {
        this.group_title = group_title;
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
