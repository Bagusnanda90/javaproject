/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.qdep.entity.Entity;
import com.dimata.util.Formater;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class EmployeeDummyTransfer extends Entity{
   private String employeeNum;
   private String employeePin;
   private String employeeFullName;
   private int statusEmployee;
   private long positionId;
   private long scheduleId;
   private String positionName;
   private String scheduleName;
   private String schTimeIn;
   private String schTimeOut;
   private String schBreakIn;
   private String schBreakOut;
   private Date dtTransferAdd;
   private long loginUser;
   private Date timeIn;
   private Date timeOut;

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
     * @return the employeeFullName
     */
    public String getEmployeeFullName() {
        return employeeFullName;
    }

    /**
     * @param employeeFullName the employeeFullName to set
     */
    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    /**
     * @return the statusEmployee
     */
    public int getStatusEmployee() {
        return statusEmployee;
    }

    /**
     * @param statusEmployee the statusEmployee to set
     */
    public void setStatusEmployee(int statusEmployee) {
        this.statusEmployee = statusEmployee;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
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
     * @return the employeePin
     */
    public String getEmployeePin() {
        return employeePin;
    }

    /**
     * @param employeePin the employeePin to set
     */
    public void setEmployeePin(String employeePin) {
        this.employeePin = employeePin;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the scheduleName
     */
    public String getScheduleName() {
        return scheduleName;
    }

    /**
     * @param scheduleName the scheduleName to set
     */
    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    /**
     * @return the schTimeIn
     */
    public String getSchTimeIn() {
        return schTimeIn;
    }

    /**
     * @param schTimeIn the schTimeIn to set
     */
    public void setSchTimeIn(Date schTimeIn) {
        if(schTimeIn!=null){
            this.schTimeIn = Formater.formatDate(schTimeIn, "HH:mm");
        }else{
            this.schTimeIn = "-";
        }
        
        //this.schTimeIn = schTimeIn;
    }

    /**
     * @return the schTimeOut
     */
    public String getSchTimeOut() {
        return schTimeOut;
    }

    /**
     * @param schTimeOut the schTimeOut to set
     */
    public void setSchTimeOut(Date schTimeOut) {
        //this.schTimeOut = schTimeOut;
        if(schTimeOut!=null){
            this.schTimeOut = Formater.formatDate(schTimeOut, "HH:mm");
        }else{
            this.schTimeOut = "-";
        }
        
    }

    /**
     * @return the schBreakIn
     */
    public String getSchBreakIn() {
        return schBreakIn;
    }

    /**
     * @param schBreakIn the schBreakIn to set
     */
    public void setSchBreakIn(Date schBreakIn) {
        if(schBreakIn!=null){
            this.schBreakIn = Formater.formatDate(schBreakIn, "HH:mm");
        }else{
            this.schBreakIn = "-";
        }
        
        
    }

    /**
     * @return the schBreakOut
     */
    public String getSchBreakOut() {
        return schBreakOut;
    }

    /**
     * @param schBreakOut the schBreakOut to set
     */
    public void setSchBreakOut(Date schBreakOut) {
        if(schBreakOut!=null){
            this.schBreakOut = Formater.formatDate(schBreakOut, "HH:mm");
        }else{
            this.schBreakOut = "-";
        }
        
        //this.schBreakOut = schBreakOut;
    }

    /**
     * @return the dtTransferAdd
     */
    public Date getDtTransferAdd() {
        return dtTransferAdd;
    }

    /**
     * @param dtTransferAdd the dtTransferAdd to set
     */
    public void setDtTransferAdd(Date dtTransferAdd) {
        this.dtTransferAdd = dtTransferAdd;
    }

    /**
     * @return the loginUser
     */
    public long getLoginUser() {
        return loginUser;
    }

    /**
     * @param loginUser the loginUser to set
     */
    public void setLoginUser(long loginUser) {
        this.loginUser = loginUser;
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
}
