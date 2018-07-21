/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.sessdivision;

import com.dimata.harisma.entity.masterdata.Division;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessDivision {
    private Vector listDivision= new Vector();
 
    /**
     * @return the listDivision
     */
    public Vector getListDivision() {
        return listDivision;
    }

    /**
     * @param listDivision the listDivision to set
     */
    public void addObjDivision(Division division) {
        this.listDivision.add(division);
    }

}
