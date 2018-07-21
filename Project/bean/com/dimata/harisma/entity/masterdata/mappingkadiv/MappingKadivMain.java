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
public class MappingKadivMain extends Entity{
        private long employeeId;
    private String nameEmployee; 
    private String namePosition;

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the nameEmployee
     */
    public String getNameEmployee() {
        if(nameEmployee==null){
            return "";
        }
        return nameEmployee;
    }

    /**
     * @param nameEmployee the nameEmployee to set
     */
    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    /**
     * @return the namePosition
     */
    public String getNamePosition() {
        if(namePosition==null){
            return "";
        }
        return namePosition;
    }

    /**
     * @param namePosition the namePosition to set
     */
    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }
}
