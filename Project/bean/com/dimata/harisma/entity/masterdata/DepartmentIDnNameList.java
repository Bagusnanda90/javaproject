/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author ktanjana
 */
import java.util.Vector;
public class DepartmentIDnNameList {
    private Vector depIDs= new Vector();
    private Vector depNames= new Vector();

    /**
     * @return the depIDs
     */
    public Vector getDepIDs() {
        return depIDs;
    }

    /**
     * @param depIDs the depIDs to set
     */
    public void setDepIDs(Vector depIDs) {
        this.depIDs = depIDs;
    }

    /**
     * @return the depNames
     */
    public Vector getDepNames() {
        return depNames;
    }

    /**
     * @param depNames the depNames to set
     */
    public void setDepNames(Vector depNames) {
        this.depNames = depNames;
    }
    
}
