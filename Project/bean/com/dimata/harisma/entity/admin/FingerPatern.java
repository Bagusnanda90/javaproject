/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.admin;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Acer
 */
public class FingerPatern 
extends Entity{
   
    private long employeeId =0;
    private int fingerType =0;
    private String fingerPatern ="";

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public int getFingerType() {
        return fingerType;
    }

    public void setFingerType(int fingerType) {
        this.fingerType = fingerType;
    }

    public String getFingerPatern() {
        return fingerPatern;
    }

    public void setFingerPatern(String fingerPatern) {
        this.fingerPatern = fingerPatern;
    }
    
}
