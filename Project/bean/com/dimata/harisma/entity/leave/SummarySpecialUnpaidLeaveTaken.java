/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.leave;

/**
 *
 * @author Satrya Ramayu
 */
public class SummarySpecialUnpaidLeaveTaken {
      private float totTakenQty;
      private long employeeId = 0;
      private long scheduleSpecialId;
      private int countScheduleId;
      private String symbole;

    /**
     * @return the totTakenQty
     */
    public float getTotTakenQty() {
        return totTakenQty;
    }

    /**
     * @param totTakenQty the totTakenQty to set
     */
    public void setTotTakenQty(float totTakenQty) {
        this.totTakenQty = totTakenQty;
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
     * @return the scheduleSpecialId
     */
    public long getScheduleSpecialId() {
        return scheduleSpecialId;
    }

    /**
     * @param scheduleSpecialId the scheduleSpecialId to set
     */
    public void setScheduleSpecialId(long scheduleSpecialId) {
        this.scheduleSpecialId = scheduleSpecialId;
    }

    /**
     * @return the countScheduleId
     */
    public int getCountScheduleId() {
        return countScheduleId;
    }

    /**
     * @param countScheduleId the countScheduleId to set
     */
    public void setCountScheduleId(int countScheduleId) {
        this.countScheduleId = countScheduleId;
    }

  

    /**
     * @return the symbole
     */
    public String getSymbole() {
        return symbole;
    }

    /**
     * @param symbole the symbole to set
     */
    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

   
}
