/*
 * SicknessDaily.java
 *
 * Created on June 17, 2004, 6:25 PM
 */

package com.dimata.harisma.session.sickness;

import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class SicknessDaily {
    private long empId;
    private String empNum;    
    private String empName;
    private String schldSymbol;
    private Date schldIn;
    private Date schldOut;
    private String remark;
    private int reason;
    private long scheduleID;
    //update by satrya 2012-10-26
    private Date selectedDate;
    /** Getter for property empNum.
     * @return Value of property empNum.
     *
     */
    public String getEmpNum() {
        return this.empNum;
    }
    
    /** Setter for property empNum.
     * @param empNum New value of property empNum.
     *
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }
    
    /** Getter for property empName.
     * @return Value of property empName.
     *
     */
    public String getEmpName() {
        return this.empName;
    }
    
    /** Setter for property empName.
     * @param empName New value of property empName.
     *
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    
    /** Getter for property schldSymbol.
     * @return Value of property schldSymbol.
     *
     */
    public String getSchldSymbol() {
        return this.schldSymbol;
    }
    
    /** Setter for property schldSymbol.
     * @param schldSymbol New value of property schldSymbol.
     *
     */
    public void setSchldSymbol(String schldSymbol) {
        this.schldSymbol = schldSymbol;
    }
    
    /** Getter for property schldIn.
     * @return Value of property schldIn.
     *
     */
    public Date getSchldIn() {
        return this.schldIn;
    }
    
    /** Setter for property schldIn.
     * @param schldIn New value of property schldIn.
     *
     */
    public void setSchldIn(Date schldIn) {
        this.schldIn = schldIn;
    }
    
    /** Getter for property schldOut.
     * @return Value of property schldOut.
     *
     */
    public Date getSchldOut() {
        return this.schldOut;
    }
    
    /** Setter for property schldOut.
     * @param schldOut New value of property schldOut.
     *
     */
    public void setSchldOut(Date schldOut) {
        this.schldOut = schldOut;
    }    
    
    /** Getter for property Remark.
     * @return Value of property Remark.
     *
     */
    public String getRemark() {
        return this.remark;
    }
    
    /** Setter for property remark.
     * @param remark New value of property remark.
     *
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }    
   
    /**
     * Getter for property reason.
     * @return Value of property reason.
     */
    public int getReason() {
        return reason;
    }    
    
    /**
     * Setter for property reason.
     * @param reason New value of property reason.
     */
    public void setReason(int reason) {
        this.reason = reason;
    }

    public long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(long scheduleID) {
        this.scheduleID = scheduleID;
    }

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
