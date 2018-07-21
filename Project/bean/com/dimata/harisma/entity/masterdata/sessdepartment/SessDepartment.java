/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.sessdepartment;

import com.dimata.harisma.entity.masterdata.Department;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessDepartment {
    private Vector listDepartment= new Vector();
 
    /**
     * @return the listDivision
     */
    public Vector getListDepartment() {
        return listDepartment;
    }

    /**
     * @param listDivision the listDivision to set
     */
    public void addObjDepartment(Department department) {
        this.listDepartment.add(department);
    }
}
