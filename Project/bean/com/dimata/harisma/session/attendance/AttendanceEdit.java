/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import java.util.Date;

/**
 *
 * @author DEV04
 */
public class AttendanceEdit {
    private Date selectedDate;
    private String empNum;    
    private String empFullName;
    private String scheduleSymbol1;
    private String scheduleSymbol2;
    private Date statusIn1st;
    private Date statusIn2nd;
    private Date statusOut1st;
    private Date status2nd;
    private Date statusBreakOut;
    private Date statusBreakIn;
    private long scheduleId1;
    private long scheduleId2;
   private long empId = 0;

    /**
     * @return the selectedDate
     */
    public Date getSelectedDate() {
        return selectedDate;
    }

    /**
     * @param selectedDate the selectedDate to set
     */
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * @return the empNum
     */
    public String getEmpNum() {
        return empNum;
    }

    /**
     * @param empNum the empNum to set
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    /**
     * @return the empFullName
     */
    public String getEmpFullName() {
        return empFullName;
    }

    /**
     * @param empFullName the empFullName to set
     */
    public void setEmpFullName(String empFullName) {
        this.empFullName = empFullName;
    }

    /**
     * @return the scheduleSymbol1
     */
    public String getScheduleSymbol1() {
        return scheduleSymbol1;
    }

    /**
     * @param scheduleSymbol1 the scheduleSymbol1 to set
     */
    public void setScheduleSymbol1(String scheduleSymbol1) {
        this.scheduleSymbol1 = scheduleSymbol1;
    }

    /**
     * @return the scheduleSymbol2
     */
    public String getScheduleSymbol2() {
        return scheduleSymbol2;
    }

    /**
     * @param scheduleSymbol2 the scheduleSymbol2 to set
     */
    public void setScheduleSymbol2(String scheduleSymbol2) {
        this.scheduleSymbol2 = scheduleSymbol2;
    }

    /**
     * @return the statusIn1st
     */
    public Date getStatusIn1st() {
        return statusIn1st;
    }

    /**
     * @param statusIn1st the statusIn1st to set
     */
    public void setStatusIn1st(Date statusIn1st) {
        this.statusIn1st = statusIn1st;
    }

    /**
     * @return the statusIn2nd
     */
    public Date getStatusIn2nd() {
        return statusIn2nd;
    }

    /**
     * @param statusIn2nd the statusIn2nd to set
     */
    public void setStatusIn2nd(Date statusIn2nd) {
        this.statusIn2nd = statusIn2nd;
    }

    /**
     * @return the statusOut1st
     */
    public Date getStatusOut1st() {
        return statusOut1st;
    }

    /**
     * @param statusOut1st the statusOut1st to set
     */
    public void setStatusOut1st(Date statusOut1st) {
        this.statusOut1st = statusOut1st;
    }

    /**
     * @return the status2nd
     */
    public Date getStatus2nd() {
        return status2nd;
    }

    /**
     * @param status2nd the status2nd to set
     */
    public void setStatus2nd(Date status2nd) {
        this.status2nd = status2nd;
    }

    /**
     * @return the statusBreakOut
     */
    public Date getStatusBreakOut() {
        return statusBreakOut;
    }

    /**
     * @param statusBreakOut the statusBreakOut to set
     */
    public void setStatusBreakOut(Date statusBreakOut) {
        this.statusBreakOut = statusBreakOut;
    }

    /**
     * @return the statusBreakIn
     */
    public Date getStatusBreakIn() {
        return statusBreakIn;
    }

    /**
     * @param statusBreakIn the statusBreakIn to set
     */
    public void setStatusBreakIn(Date statusBreakIn) {
        this.statusBreakIn = statusBreakIn;
    }

    /**
     * @return the scheduleId1
     */
    public long getScheduleId1() {
        return scheduleId1;
    }

    /**
     * @param scheduleId1 the scheduleId1 to set
     */
    public void setScheduleId1(long scheduleId1) {
        this.scheduleId1 = scheduleId1;
    }

    /**
     * @return the scheduleId2
     */
    public long getScheduleId2() {
        return scheduleId2;
    }

    /**
     * @param scheduleId2 the scheduleId2 to set
     */
    public void setScheduleId2(long scheduleId2) {
        this.scheduleId2 = scheduleId2;
    }

    /**
     * @return the empId
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(long empId) {
        this.empId = empId;
    }
   

    
}
