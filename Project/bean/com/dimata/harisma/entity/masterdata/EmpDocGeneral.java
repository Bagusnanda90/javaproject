/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Gunadi
 */
public class EmpDocGeneral extends Entity{
     private long empDocGeneralId ; 
     private long empDocId ; 
     private long employeeId ; 
     private int acknowledgeStatus ;

    /**
     * @return the empDocGeneralId
     */
    public long getEmpDocGeneralId() {
        return empDocGeneralId;
    }

    /**
     * @param empDocGeneralId the empDocGeneralId to set
     */
    public void setEmpDocGeneralId(long empDocGeneralId) {
        this.empDocGeneralId = empDocGeneralId;
    }

    /**
     * @return the empDocId
     */
    public long getEmpDocId() {
        return empDocId;
    }

    /**
     * @param empDocId the empDocId to set
     */
    public void setEmpDocId(long empDocId) {
        this.empDocId = empDocId;
    }

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
     * @return the acknowledgeStatus
     */
    public int getAcknowledgeStatus() {
        return acknowledgeStatus;
    }

    /**
     * @param acknowledgeStatus the acknowledgeStatus to set
     */
    public void setAcknowledgeStatus(int acknowledgeStatus) {
        this.acknowledgeStatus = acknowledgeStatus;
    }
     

}

