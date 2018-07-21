/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.location;

/**
 *
 * @author Satrya Ramayu
 */
import java.util.Vector;
public class LocationIdnNameList {
    private Vector locationIDs= new Vector();
    private Vector locationNames= new Vector();
    private long subRegencyId;

    /**
     * @return the locationIDs
     */
    public Vector getLocationIDs() {
        return locationIDs;
    }

    /**
     * @param locationIDs the locationIDs to set
     */
    public void addLocationIDs(long locationIDs) {
        this.locationIDs.add(locationIDs);
    }

    /**
     * @return the locationNames
     */
    public Vector getLocationNames() {
        return locationNames;
    }

    /**
     * @param locationNames the locationNames to set
     */
    public void addLocationNames(String locationNames) {
        this.locationNames.add(locationNames);
    }

    /**
     * @return the subRegencyId
     */
    public long getSubRegencyId() {
        return subRegencyId;
    }

    /**
     * @param subRegencyId the subRegencyId to set
     */
    public void setSubRegencyId(long subRegencyId) {
        this.subRegencyId = subRegencyId;
    }

    
    
}
