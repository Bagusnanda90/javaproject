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
public class KPI_Type extends Entity{
    
       	private long kpi_type_id ;
        private String type_name = "";
        private String description = "";

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
     * @return the type_name
     */
    public String getType_name() {
        return type_name;
    }

    /**
     * @param type_name the type_name to set
     */
    public void setType_name(String type_name) {
        this.type_name = type_name;
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
