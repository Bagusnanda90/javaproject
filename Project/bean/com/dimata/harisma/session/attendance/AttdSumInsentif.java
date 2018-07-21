/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

/**
 *
 * @author Dimata 007
 */
public class AttdSumInsentif {
    private int totalInsentif=0;
    private long employeeId;

    /**
     * @return the totalInsentif
     */
    public int getTotalInsentif() {
        return totalInsentif;
    }

    /**
     * @param totalInsentif the totalInsentif to set
     */
    public void setTotalInsentif(int totalInsentif) {
        this.totalInsentif = totalInsentif;
    }

    /**
     * @return the empId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
   
  
      
}
