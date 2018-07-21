/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.mappingkadiv;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Satrya Ramayu
 */
public class MappingKadivDetail extends Entity{
    private long mappingkadivMainId;
    private long locationId;
    private String[] locationIds;

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the locationIds
     */
    public String[] getLocationIds() {
        return locationIds;
    }

    /**
     * @param locationIds the locationIds to set
     */
    public void setLocationIds(String[] locationIds) {
        this.locationIds = locationIds;
    }

    /**
     * @return the mappingkadivMainId
     */
    public long getMappingkadivMainId() {
        return mappingkadivMainId;
    }

    /**
     * @param mappingkadivMainId the mappingkadivMainId to set
     */
    public void setMappingkadivMainId(long mappingkadivMainId) {
        this.mappingkadivMainId = mappingkadivMainId;
    }
}
