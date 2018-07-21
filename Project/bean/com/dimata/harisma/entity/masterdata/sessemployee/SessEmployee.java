/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.sessemployee;

import com.dimata.harisma.entity.employee.Employee;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class SessEmployee {
     private Vector listEmployee= new Vector();
 
    /**
     * @return the listEmployee
     */
    public Vector getListEmployee() {
        return listEmployee;
    }

    /**
     * @param listEmployee the listEmployee to set
     */
    public void addObjEmployee(EmployeeMinimalis employeeMinimalis) {
        this.listEmployee.add(employeeMinimalis);
    }
}
