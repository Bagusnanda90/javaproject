/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class AttendanceReportDaily {
    private long employeeId;
    private String payrollNumb;
    private String fullName;
    //update by satrtya 2014-02-01
    private long departmentId;
    private long religionId;
    private String companyName;
    private String divisionName;
    private String departementName;
    private String sectionName;
    private String scheduleSymbol1st;
    private Date timeIn;
    private Date timeOut;
    private String diffIn;
    private String diffOut;
    private String duration;
    private int reason1st;
    private String note1st;
    private String status1st;
    private int istatus1st;
    private Date schTimeIn1st;
    private Date schTimeOut1st;
    private Date schBreakIn1st;
    private Date schBreakOut1st;
    private long schedule1st;
    private Date selectedDt;
    private int schCategory1st;
    private String schSymbolName1st;
    //update by satrya 2014-02-10
    private long empCategoriId;
    /**
     * @return the payrollNumb
     */
    public String getPayrollNumb() {
        return payrollNumb;
    }

    /**
     * @param payrollNumb the payrollNumb to set
     */
    public void setPayrollNumb(String payrollNumb) {
        this.payrollNumb = payrollNumb;
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
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @return the departementName
     */
    public String getDepartementName() {
        return departementName;
    }

    /**
     * @param departementName the departementName to set
     */
    public void setDepartementName(String departementName) {
        this.departementName = departementName;
    }

    /**
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the scheduleSymbol
     */
    public String getScheduleSymbol1st() {
        return scheduleSymbol1st;
    }

    /**
     * @param scheduleSymbol the scheduleSymbol to set
     */
    public void setScheduleSymbol1st(String scheduleSymbol1st) {
        this.scheduleSymbol1st = scheduleSymbol1st;
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
     * @return the diffIn
     */
    public String getDiffIn() {
        return diffIn;
    }

    /**
     * @param diffIn the diffIn to set
     */
    public void setDiffIn(String diffIn) {
        this.diffIn = diffIn;
    }

    /**
     * @return the diffOut
     */
    public String getDiffOut() {
        return diffOut;
    }

    /**
     * @param diffOut the diffOut to set
     */
    public void setDiffOut(String diffOut) {
        this.diffOut = diffOut;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the reason
     */
    public int getReason1st() {
        return reason1st;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason1st(int reason) {
       // String status = PstEmpSchedule.strPresenceStatus[idxReason];
        this.reason1st = reason;
    }

    /**
     * @return the note
     */
    public String getNote1st() {
        return note1st;
    }

    /**
     * @param note the note to set
     */
    public void setNote1st(String note1st) {
        this.note1st = note1st;
    }

    /**
     * @return the status
     */
    public String getStatus1st() {
        return status1st;
    }

    public int getiStatus1st() {
        return istatus1st;
    }
    /**
     * @param status the status to set
     */
    public void setStatus1st(int idxStatus) {
        String status = PstEmpSchedule.strPresenceStatus[idxStatus];
        this.status1st = status;
        this.istatus1st=idxStatus;
    }

    /**
     * @return the schTimeIn
     */
    public Date getSchTimeIn1st() {
        return schTimeIn1st;
    }

    /**
     * @param schTimeIn the schTimeIn to set
     */
    public void setSchTimeIn1st(Date schTimeIn1st) {
        this.schTimeIn1st = schTimeIn1st;
    }

    /**
     * @return the schTimeOut
     */
    public Date getSchTimeOut1st() {
        return schTimeOut1st;
    }

    /**
     * @param schTimeOut the schTimeOut to set
     */
    public void setSchTimeOut1st(Date schTimeOut1st) {
        this.schTimeOut1st = schTimeOut1st;
    }

    /**
     * @return the schBreakIn
     */
    public Date getSchBreakIn1st() {
        return schBreakIn1st;
    }

    /**
     * @param schBreakIn the schBreakIn to set
     */
    public void setSchBreakIn1st(Date schBreakIn1st) {
        this.schBreakIn1st = schBreakIn1st;
    }

    /**
     * @return the schBreakOut
     */
    public Date getSchBreakOut1st() {
        return schBreakOut1st;
    }

    /**
     * @param schBreakOut the schBreakOut to set
     */
    public void setSchBreakOut1st(Date schBreakOut1st) {
        this.schBreakOut1st = schBreakOut1st;
    }

    /**
     * @return the schedule1st
     */
    public long getSchedule1st() {
        return schedule1st;
    }

    /**
     * @param schedule1st the schedule1st to set
     */
    public void setSchedule1st(long schedule1st) {
        this.schedule1st = schedule1st;
    }

    /**
     * @return the selectedDt
     */
    public Date getSelectedDt() {
        return selectedDt;
    }

    /**
     * @param selectedDt the selectedDt to set
     */
    public void setSelectedDt(Date selectedDt) {
        this.selectedDt = selectedDt;
    }

    /**
     * @return the schCategory1st
     */
    public int getSchCategory1st() {
        return schCategory1st;
    }

    /**
     * @param schCategory1st the schCategory1st to set
     */
    public void setSchCategory1st(int schCategory1st) {
        this.schCategory1st = schCategory1st;
    }

    /**
     * @return the schSymbolName
     */
    public String getSchSymbolName1st() {
        return schSymbolName1st;
    }

    /**
     * @param schSymbolName the schSymbolName to set
     */
    public void setSchSymbolName1st(String schSymbolName1st) {
        this.schSymbolName1st = schSymbolName1st;
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
     * @return the religionId
     */
    public long getReligionId() {
        return religionId;
    }

    /**
     * @param religionId the religionId to set
     */
    public void setReligionId(long religionId) {
        this.religionId = religionId;
    }

    /**
     * @return the empCategoriId
     */
    public long getEmpCategoriId() {
        return empCategoriId;
    }

    /**
     * @param empCategoriId the empCategoriId to set
     */
    public void setEmpCategoriId(long empCategoriId) {
        this.empCategoriId = empCategoriId;
    }
}
