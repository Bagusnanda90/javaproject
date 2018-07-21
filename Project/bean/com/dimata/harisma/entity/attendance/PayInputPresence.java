/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PayInputPresence {

    private long employeeId = 0;
    private int dayOffSchedule = 0;
    //private int dtOffOvertime = 0;
    private int insentif = 0;
    private int presenceOnTime = 0;
    private float presenceOnTimeTime = 0;
    private int late = 0;//ini late 
    private float lateTime = 0;
    private int earlyHome = 0;//ini late 
    private float earlyHomeTime = 0;
    private int lateEarly = 0;//ini late 
    private float lateEarlyTime = 0;
    private Hashtable reason = new Hashtable();//ini late 
    private Hashtable reasonTime = new Hashtable();//ini late 
    private int absence = 0;
    private float absenceTime = 0;
    private int totalWorkingDays = 0;
    private long timeWorkHour = 0;
    private int totalOnlyIn = 0;
    private float timeOnlyIn = 0;
    private int totalOnlyOut = 0;
    private float timeOnlyOut = 0;
    
    // mchen 2014-11-25
    private int nightAllowance = 0;
    private int transportAllowance = 0;

    //private Hashtable dtIDate = new Vector(1,1);//mengetahui dia ada d tanggal berapa tpi dlm int
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
     * @return the presenceOnTime
     */
    public int getPresenceOnTime() {
        return presenceOnTime;
    }

    /**
     * @param presenceOnTime the presenceOnTime to set
     */
    public void setPresenceOnTime(int presenceOnTime) {
        this.presenceOnTime = presenceOnTime;
    }

    /**
     * @return the dayOffSchedule
     */
    public int getDayOffSchedule() {
        return dayOffSchedule;
    }

    /**
     * @param dayOffSchedule the dayOffSchedule to set
     */
    public void setDayOffSchedule(int dayOffSchedule) {
        this.dayOffSchedule = dayOffSchedule;
    }

    /**
     * @return the insentif
     */
    public int getInsentif() {
        return insentif;
    }

    /**
     * @param insentif the insentif to set
     */
    public void setInsentif(int insentif) {
        this.insentif = insentif;
    }

    /**
     * @return the absence
     */
    public int getAbsence() {
        return absence;
    }

    /**
     * @param absence the absence to set
     */
    public void setAbsence(int absence) {
        this.absence = absence;
    }

    /**
     * @return the late
     */
    public int getLate() {
        return late;
    }

    /**
     * @param late the late to set
     */
    public void setLate(int late) {
        this.late = late;
    }

    /**
     * @return the presenceOnTimeTime
     */
    public float getPresenceOnTimeTime() {
        return presenceOnTimeTime;
    }

    /**
     * @param presenceOnTimeTime the presenceOnTimeTime to set
     */
    public void setPresenceOnTimeTime(float presenceOnTimeTime) {
        this.presenceOnTimeTime = presenceOnTimeTime;
    }

    /**
     * @return the lateTime
     */
    public float getLateTime() {
        return lateTime;
    }

    /**
     * @param lateTime the lateTime to set
     */
    public void setLateTime(float lateTime) {
        this.lateTime = lateTime;
    }

    /**
     * @return the earlyHome
     */
    public int getEarlyHome() {
        return earlyHome;
    }

    /**
     * @param earlyHome the earlyHome to set
     */
    public void setEarlyHome(int earlyHome) {
        this.earlyHome = earlyHome;
    }

    /**
     * @return the earlyHomeTime
     */
    public float getEarlyHomeTime() {
        return earlyHomeTime;
    }

    /**
     * @param earlyHomeTime the earlyHomeTime to set
     */
    public void setEarlyHomeTime(float earlyHomeTime) {
        this.earlyHomeTime = earlyHomeTime;
    }

    /**
     * @return the lateEarly
     */
    public int getLateEarly() {
        return lateEarly;
    }

    /**
     * @param lateEarly the lateEarly to set
     */
    public void setLateEarly(int lateEarly) {
        this.lateEarly = lateEarly;
    }

    /**
     * @return the lateEarlyTime
     */
    public float getLateEarlyTime() {
        return lateEarlyTime;
    }

    /**
     * @param lateEarlyTime the lateEarlyTime to set
     */
    public void setLateEarlyTime(float lateEarlyTime) {
        this.lateEarlyTime = lateEarlyTime;
    }

    /**
     * @return the absenceTime
     */
    public float getAbsenceTime() {
        return absenceTime;
    }

    /**
     * @param absenceTime the absenceTime to set
     */
    public void setAbsenceTime(float absenceTime) {
        this.absenceTime = absenceTime;
    }

    /**
     * @return the reason
     */
    public Hashtable getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Hashtable reason) {
        this.reason = reason;
    }

    /**
     * @return the reasonTime
     */
    public Hashtable getReasonTime() {
        return reasonTime;
    }

    /**
     * @param reasonTime the reasonTime to set
     */
    public void setReasonTime(Hashtable reasonTime) {
        this.reasonTime = reasonTime;
    }

    /**
     * @return the totalWorkingDays
     */
    public int getTotalWorkingDays() {
        return totalWorkingDays;
    }

    /**
     * @param totalWorkingDays the totalWorkingDays to set
     */
    public void setTotalWorkingDays(int totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }

   

    /**
     * @return the totalOnlyIn
     */
    public int getTotalOnlyIn() {
        return totalOnlyIn;
    }

    /**
     * @param totalOnlyIn the totalOnlyIn to set
     */
    public void setTotalOnlyIn(int totalOnlyIn) {
        this.totalOnlyIn = totalOnlyIn;
    }

    /**
     * @return the timeOnlyIn
     */
    public float getTimeOnlyIn() {
        return timeOnlyIn;
    }

    /**
     * @param timeOnlyIn the timeOnlyIn to set
     */
    public void setTimeOnlyIn(float timeOnlyIn) {
        this.timeOnlyIn = timeOnlyIn;
    }

    /**
     * @return the totalOnlyOut
     */
    public int getTotalOnlyOut() {
        return totalOnlyOut;
    }

    /**
     * @param totalOnlyOut the totalOnlyOut to set
     */
    public void setTotalOnlyOut(int totalOnlyOut) {
        this.totalOnlyOut = totalOnlyOut;
    }

    /**
     * @return the timeOnlyOut
     */
    public float getTimeOnlyOut() {
        return timeOnlyOut;
    }

    /**
     * @param timeOnlyOut the timeOnlyOut to set
     */
    public void setTimeOnlyOut(float timeOnlyOut) {
        this.timeOnlyOut = timeOnlyOut;
    }

    /**
     * @return the timeWorkHour
     */
    public long getTimeWorkHour() {
        return timeWorkHour;
    }

    /**
     * @param timeWorkHour the timeWorkHour to set
     */
    public void setTimeWorkHour(long timeWorkHour) {
        this.timeWorkHour = timeWorkHour;
    }

    /**
     * @return the nightAllowance
     */
    public int getNightAllowance() {
        return nightAllowance;
    }

    /**
     * @param nightAllowance the nightAllowance to set
     */
    public void setNightAllowance(int nightAllowance) {
        this.nightAllowance = nightAllowance;
    }

    /**
     * @return the transportAllowance
     */
    public int getTransportAllowance() {
        return transportAllowance;
    }

    /**
     * @param transportAllowance the transportAllowance to set
     */
    public void setTransportAllowance(int transportAllowance) {
        this.transportAllowance = transportAllowance;
    }
}
