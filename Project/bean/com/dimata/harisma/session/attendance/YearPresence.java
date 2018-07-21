/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.attendance;

/**
 * @author Roy Andika
 * 
 */
public class YearPresence {

    private long employeeId;
    private String employeeNum;
    private String fullName;
    private long depId;
    private String dep;
    private long periodId;
    private String period;
    private int[] valueSchedule;

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
     * @return the employeeNum
     */
    public String getEmployeeNum() {
        return employeeNum;
    }

    /**
     * @param employeeNum the employeeNum to set
     */
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the depId
     */
    public long getDepId() {
        return depId;
    }

    /**
     * @param depId the depId to set
     */
    public void setDepId(long depId) {
        this.depId = depId;
    }

    /**
     * @return the dep
     */
    public String getDep() {
        return dep;
    }

    /**
     * @param dep the dep to set
     */
    public void setDep(String dep) {
        this.dep = dep;
    }

    /**
     * @return the periodId
     */
    public long getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * @return the valueSchedule
     */
    public int[] getValueSchedule() {
        return valueSchedule;
    }

    /**
     * @param valueSchedule the valueSchedule to set
     */
    public void setValueSchedule(int[] valueSchedule) {
        this.valueSchedule = valueSchedule;
    }

   

}
