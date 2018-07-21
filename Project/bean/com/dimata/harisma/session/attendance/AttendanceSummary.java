/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import java.util.Date;

/**
 *
 * @author satrya Ramayu
 */
public class AttendanceSummary {
    private long employeeId;
    private String employeeNum;
    private String fullName;
    private String division;
    private String department;
    private String section;
    private long periodId;
    private int reason;
    private int status;
    private long departmentId;
    private Date schDate;
    
    private Date schTimeIn;
    private Date schTimeOut;
    private Date schBreakOut;
    private Date schBreakIn;
    private long scheduleId;
    private Date timeIn;
    private Date timeOut;
    private String symbol;
    
    private long totAttdendace;
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
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
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
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the departmentId
     */
    public long getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the schDate
     */
    public Date getSchDate() {
        return schDate;
    }

    /**
     * @param schDate the schDate to set
     */
    public void setSchDate(Date schDate) {
        this.schDate = schDate;
    }

    /**
     * @return the reason
     */
    public int getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(int reason) {
        this.reason = reason;
    }

    /**
     * @return the schTimeIn
     */
    public Date getSchTimeIn() {
        return schTimeIn;
    }

    /**
     * @param schTimeIn the schTimeIn to set
     */
    public void setSchTimeIn(Date schTimeIn) {
        this.schTimeIn = schTimeIn;
    }

    /**
     * @return the schTimeOut
     */
    public Date getSchTimeOut() {
        return schTimeOut;
    }

    /**
     * @param schTimeOut the schTimeOut to set
     */
    public void setSchTimeOut(Date schTimeOut) {
        this.schTimeOut = schTimeOut;
    }

    /**
     * @return the schBreakOut
     */
    public Date getSchBreakOut() {
        return schBreakOut;
    }

    /**
     * @param schBreakOut the schBreakOut to set
     */
    public void setSchBreakOut(Date schBreakOut) {
        this.schBreakOut = schBreakOut;
    }

    /**
     * @return the schBreakIn
     */
    public Date getSchBreakIn() {
        return schBreakIn;
    }

    /**
     * @param schBreakIn the schBreakIn to set
     */
    public void setSchBreakIn(Date schBreakIn) {
        this.schBreakIn = schBreakIn;
    }

    /**
     * @return the scheduleId
     */
    public long getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId the scheduleId to set
     */
    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return the timeIn
     */
    public Date getTimeIn() {
        return timeIn;
    }

    /**
     * @param timeIn the timeIn to set
     */
    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    /**
     * @return the timeOut
     */
    public Date getTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the totAttdendace
     */
    public long getTotAttdendace() {
        return totAttdendace;
    }

    /**
     * @param totAttdendace the totAttdendace to set
     */
    public void setTotAttdendace(long totAttdendace) {
        this.totAttdendace = totAttdendace;
    }
   
    
}
