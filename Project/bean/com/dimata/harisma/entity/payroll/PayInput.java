/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.form.payroll.FrmPayInput;
import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class PayInput extends Entity{
    private long employeeId;
    private long departementId;
    private String employeeNumber;
    private String employeeName;
    private String positionName;
    
    private long paySlipId;
    private long positionId;
    
    
    private int presenceOntimeIdx;
    private double presenceOntimeTime;
    private int presenceOntimeIdxAdjust;
    private double presenceOntimeTimeAdjust;
    private boolean presenceOntimeChekedAdjust;
    
    private int lateIdx;
    private double lateTime;
    private int lateIdxAdjust;
    private double lateTimeAdjust;
    private boolean lateChekedAdjust;
    
    private int earlyHomeIdx;
    private double earlyHomeTime;
    private int earlyHomeIdxAdjust;
    private double earlyHomeTimeAdjust;
    private boolean earlyHomeChekedAdjust;
    
    private int lateEarlyIdx;
    private double lateEarlyTime;
    private int lateEarlyIdxAdjust;
    private double lateEarlyTimeAdjust;
    private boolean lateEarlyChekedAdjust;
    
    private Hashtable  reasonIdx = new Hashtable();
    private Hashtable reasonTime= new Hashtable();
    private Hashtable reasonIdxAdjust= new Hashtable();
    private Hashtable reasonTimeAdjust= new Hashtable();
    //private boolean reasonChekedAdjust;
    
    private int absenceIdx;
    private double absenceTime;
    private int absenceIdxAdjust;
    private double absenceTimeAdjust;
    private boolean absenceChekedAdjust;
    
    private double prosentaseOK;
     private double dayOffSchedule;
    
    //private double paidLeave;
    //private double paidLeaveAdjust;
    //private boolean paidLeaveChekedAdjust;
    
     private double unPaidLeave;
    private double unPaidLeaveAdjust;
    private boolean unPaidLeaveChekedAdjust;
    
    
    private double otIdxPaidSalary;
    private double otIdxPaidSalaryAdjust;
     private Date otIdxPaidSalaryAdjustDt; 
    private boolean otIdxPaidSalaryChekedAdjust;
    
    private double otIdxPaidDp;
    
    private double otAllowanceMoney;
    private double otAllowanceMoneyAdjust;
    private Date otAllowanceMoneyAdjustDt;
    private boolean otAllowanceMoneyChekedAdjust;
    
    private double otAllowanceFood;
    private double otAllowanceFoodAdjust;
    private boolean otAllowanceFoodChekedAdjust;
    
    private String privateNote="";
    
    private String levelCode;
    
    private double empWorkDays;
    
    private String note;

    private double insentif;
    private double insentifAdjusment;
    
    private Hashtable positionIdx = new Hashtable();//bisa bertambahBanyak karena memakai centang sesuai master data
    private Hashtable positionAdjust= new Hashtable();
    
     private float alIdx;
    private float alTime;
    private float llIdx;
    private float llTime;
    private float dpIdx;
    private float dpTime;
    
    private long periodId;
    // add mchen
    private int nightAllowance;
    private int transportAllowance;
    
    private double nightAllowanceAdjusment;
    private double transportAllowanceAdjusment;
    
    private boolean nightAllowanceChecked;
    private boolean transportAllowanceChecked;
    
    private Date nightAllowanceAdjustDt;
    private Date transportAllowanceAdjustDt;
   
    private int OnlyInIdx;
    private double OnlyInTime;
    private int OnlyInIdxAdjust;
    private double OnlyInTimeAdjust;
    private boolean OnlyInChekedAdjust;
    
    private int OnlyOutIdx;
    private double OnlyOutTime;
    private int OnlyOutIdxAdjust;
    private double OnlyOutTimeAdjust;
    private boolean OnlyOutChekedAdjust;
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
     * @return the employeeNumber
     */
    public String getEmployeeNumber() {
        if(employeeNumber==null){
            return "";
        }
        return employeeNumber;
    }

    /**
     * @param employeeNumber the employeeNumber to set
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        if(employeeName==null){
            return "";
        }
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        if(positionName==null){
            return "";
        }
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the presenceOntimeIdx
     */
    public int getPresenceOntimeIdx() {
        return presenceOntimeIdx;
    }

    /**
     * @param presenceOntimeIdx the presenceOntimeIdx to set
     */
    public void setPresenceOntimeIdx(int presenceOntimeIdx) {
        this.presenceOntimeIdx = presenceOntimeIdx;
    }

    /**
     * @return the presenceOntimeTime
     */
    public double getPresenceOntimeTime() {
        return presenceOntimeTime;
    }

    /**
     * @param presenceOntimeTime the presenceOntimeTime to set
     */
    public void setPresenceOntimeTime(double presenceOntimeTime) {
        this.presenceOntimeTime = presenceOntimeTime;
    }

    /**
     * @return the presenceOntimeIdxAdjust
     */
    public int getPresenceOntimeIdxAdjust() {
        return presenceOntimeIdxAdjust;
    }

    /**
     * @param presenceOntimeIdxAdjust the presenceOntimeIdxAdjust to set
     */
    public void setPresenceOntimeIdxAdjust(int presenceOntimeIdxAdjust) {
        this.presenceOntimeIdxAdjust = presenceOntimeIdxAdjust;
    }

    /**
     * @return the presenceOntimeTimeAdjust
     */
    public double getPresenceOntimeTimeAdjust() {
        return presenceOntimeTimeAdjust;
    }

    /**
     * @param presenceOntimeTimeAdjust the presenceOntimeTimeAdjust to set
     */
    public void setPresenceOntimeTimeAdjust(double presenceOntimeTimeAdjust) {
        this.presenceOntimeTimeAdjust = presenceOntimeTimeAdjust;
    }

    /**
     * @return the presenceOntimeChekedAdjust
     */
    public boolean isPresenceOntimeChekedAdjust() {
        return presenceOntimeChekedAdjust;
    }

    /**
     * @param presenceOntimeChekedAdjust the presenceOntimeChekedAdjust to set
     */
    public void setPresenceOntimeChekedAdjust(boolean presenceOntimeChekedAdjust) {
        this.presenceOntimeChekedAdjust = presenceOntimeChekedAdjust;
    }

   

    /**
     * @return the lateTime
     */
    public double getLateTime() {
        return Math.abs(lateTime);
    }

    /**
     * @param lateTime the lateTime to set
     */
    public void setLateTime(double lateTime) {
        this.lateTime = lateTime;
    }

    /**
     * @return the lateIdxAdjust
     */
    public int getLateIdxAdjust() {
        return lateIdxAdjust;
    }

    /**
     * @param lateIdxAdjust the lateIdxAdjust to set
     */
    public void setLateIdxAdjust(int lateIdxAdjust) {
        this.lateIdxAdjust = lateIdxAdjust;
    }

    /**
     * @return the lateTimeAdjust
     */
    public double getLateTimeAdjust() {
        return lateTimeAdjust;
    }

    /**
     * @param lateTimeAdjust the lateTimeAdjust to set
     */
    public void setLateTimeAdjust(double lateTimeAdjust) {
        this.lateTimeAdjust = lateTimeAdjust;
    }

    /**
     * @return the lateChekedAdjust
     */
    public boolean isLateChekedAdjust() {
        return lateChekedAdjust;
    }

    /**
     * @param lateChekedAdjust the lateChekedAdjust to set
     */
    public void setLateChekedAdjust(boolean lateChekedAdjust) {
        this.lateChekedAdjust = lateChekedAdjust;
    }

    /**
     * @return the earlyHomeIdx
     */
    public int getEarlyHomeIdx() {
        return Math.abs(earlyHomeIdx);
    }

    /**
     * @param earlyHomeIdx the earlyHomeIdx to set
     */
    public void setEarlyHomeIdx(int earlyHomeIdx) {
        this.earlyHomeIdx = earlyHomeIdx;
    }

    /**
     * @return the earlyHomeTime
     */
    public double getEarlyHomeTime() {
        return Math.abs(earlyHomeTime);
    }

    /**
     * @param earlyHomeTime the earlyHomeTime to set
     */
    public void setEarlyHomeTime(double earlyHomeTime) {
        this.earlyHomeTime = earlyHomeTime;
    }

    /**
     * @return the earlyHomeIdxAdjust
     */
    public int getEarlyHomeIdxAdjust() {
        return earlyHomeIdxAdjust;
    }

    /**
     * @param earlyHomeIdxAdjust the earlyHomeIdxAdjust to set
     */
    public void setEarlyHomeIdxAdjust(int earlyHomeIdxAdjust) {
        this.earlyHomeIdxAdjust = earlyHomeIdxAdjust;
    }

    /**
     * @return the earlyHomeTimeAdjust
     */
    public double getEarlyHomeTimeAdjust() {
        return earlyHomeTimeAdjust;
    }

    /**
     * @param earlyHomeTimeAdjust the earlyHomeTimeAdjust to set
     */
    public void setEarlyHomeTimeAdjust(double earlyHomeTimeAdjust) {
        this.earlyHomeTimeAdjust = earlyHomeTimeAdjust;
    }

    /**
     * @return the earlyHomeChekedAdjust
     */
    public boolean isEarlyHomeChekedAdjust() {
        return earlyHomeChekedAdjust;
    }

    /**
     * @param earlyHomeChekedAdjust the earlyHomeChekedAdjust to set
     */
    public void setEarlyHomeChekedAdjust(boolean earlyHomeChekedAdjust) {
        this.earlyHomeChekedAdjust = earlyHomeChekedAdjust;
    }

    /**
     * @return the lateEarlyIdx
     */
    public int getLateEarlyIdx() {
        return lateEarlyIdx;
    }

    /**
     * @param lateEarlyIdx the lateEarlyIdx to set
     */
    public void setLateEarlyIdx(int lateEarlyIdx) {
        this.lateEarlyIdx = lateEarlyIdx;
    }

    /**
     * @return the lateEarlyTime
     */
    public double getLateEarlyTime() {
        return Math.abs(lateEarlyTime);
    }

    /**
     * @param lateEarlyTime the lateEarlyTime to set
     */
    public void setLateEarlyTime(double lateEarlyTime) {
        this.lateEarlyTime = lateEarlyTime;
    }

    /**
     * @return the lateEarlyIdxAdjust
     */
    public int getLateEarlyIdxAdjust() {
        return lateEarlyIdxAdjust;
    }

    /**
     * @param lateEarlyIdxAdjust the lateEarlyIdxAdjust to set
     */
    public void setLateEarlyIdxAdjust(int lateEarlyIdxAdjust) {
        this.lateEarlyIdxAdjust = lateEarlyIdxAdjust;
    }

    /**
     * @return the lateEarlyTimeAdjust
     */
    public double getLateEarlyTimeAdjust() {
        return lateEarlyTimeAdjust;
    }

    /**
     * @param lateEarlyTimeAdjust the lateEarlyTimeAdjust to set
     */
    public void setLateEarlyTimeAdjust(double lateEarlyTimeAdjust) {
        this.lateEarlyTimeAdjust = lateEarlyTimeAdjust;
    }

    /**
     * @return the lateEarlyChekedAdjust
     */
    public boolean isLateEarlyChekedAdjust() {
        return lateEarlyChekedAdjust;
    }

    /**
     * @param lateEarlyChekedAdjust the lateEarlyChekedAdjust to set
     */
    public void setLateEarlyChekedAdjust(boolean lateEarlyChekedAdjust) {
        this.lateEarlyChekedAdjust = lateEarlyChekedAdjust;
    }

    
    /**
     * @return the absenceIdx
     */
    public int getAbsenceIdx() {
        return absenceIdx;
    }

    /**
     * @param absenceIdx the absenceIdx to set
     */
    public void setAbsenceIdx(int absenceIdx) {
        this.absenceIdx = absenceIdx;
    }

    /**
     * @return the absenceTime
     */
    public double getAbsenceTime() {
        return Math.abs(absenceTime);
    }

    /**
     * @param absenceTime the absenceTime to set
     */
    public void setAbsenceTime(double absenceTime) {
        this.absenceTime = absenceTime;
    }

    /**
     * @return the absenceIdxAdjust
     */
    public int getAbsenceIdxAdjust() {
        return absenceIdxAdjust;
    }

    /**
     * @param absenceIdxAdjust the absenceIdxAdjust to set
     */
    public void setAbsenceIdxAdjust(int absenceIdxAdjust) {
        this.absenceIdxAdjust = absenceIdxAdjust;
    }

    /**
     * @return the absenceTimeAdjust
     */
    public double getAbsenceTimeAdjust() {
        return absenceTimeAdjust;
    }

    /**
     * @param absenceTimeAdjust the absenceTimeAdjust to set
     */
    public void setAbsenceTimeAdjust(double absenceTimeAdjust) {
        this.absenceTimeAdjust = absenceTimeAdjust;
    }

    /**
     * @return the absenceChekedAdjust
     */
    public boolean isAbsenceChekedAdjust() {
        return absenceChekedAdjust;
    }

    /**
     * @param absenceChekedAdjust the absenceChekedAdjust to set
     */
    public void setAbsenceChekedAdjust(boolean absenceChekedAdjust) {
        this.absenceChekedAdjust = absenceChekedAdjust;
    }

    /**
     * @return the prosentaseOK
     */
    public double getProsentaseOK() {
        return prosentaseOK;
    }

    /**
     * @param prosentaseOK the prosentaseOK to set
     */
    public void setProsentaseOK(double prosentaseOK) {
        this.prosentaseOK = prosentaseOK;
    }

    /**
     * @return the dayOffSchedule
     */
    public double getDayOffSchedule() {
        return dayOffSchedule;
    }

    /**
     * @param dayOffSchedule the dayOffSchedule to set
     */
    public void setDayOffSchedule(double dayOffSchedule) {
        this.dayOffSchedule = dayOffSchedule;
    }

   
    /**
     * @return the unPaidLeave
     */
    public double getUnPaidLeave() {
        return unPaidLeave;
    }

    /**
     * @param unPaidLeave the unPaidLeave to set
     */
    public void setUnPaidLeave(double unPaidLeave) {
        this.unPaidLeave = unPaidLeave;
    }

    /**
     * @return the unPaidLeaveAdjust
     */
    public double getUnPaidLeaveAdjust() {
        return unPaidLeaveAdjust;
    }

    /**
     * @param unPaidLeaveAdjust the unPaidLeaveAdjust to set
     */
    public void setUnPaidLeaveAdjust(double unPaidLeaveAdjust) {
        this.unPaidLeaveAdjust = unPaidLeaveAdjust;
    }

    /**
     * @return the unPaidLeaveChekedAdjust
     */
    public boolean isUnPaidLeaveChekedAdjust() {
        return unPaidLeaveChekedAdjust;
    }

    /**
     * @param unPaidLeaveChekedAdjust the unPaidLeaveChekedAdjust to set
     */
    public void setUnPaidLeaveChekedAdjust(boolean unPaidLeaveChekedAdjust) {
        this.unPaidLeaveChekedAdjust = unPaidLeaveChekedAdjust;
    }

    /**
     * @return the otIdxPaidSalary
     */
    public double getOtIdxPaidSalary() {
        return otIdxPaidSalary;
    }

    /**
     * @param otIdxPaidSalary the otIdxPaidSalary to set
     */
    public void setOtIdxPaidSalary(double otIdxPaidSalary) {
        this.otIdxPaidSalary = otIdxPaidSalary;
    }

    /**
     * @return the otIdxPaidSalaryAdjust
     */
    public double getOtIdxPaidSalaryAdjust() {
        return otIdxPaidSalaryAdjust;
    }

    /**
     * @param otIdxPaidSalaryAdjust the otIdxPaidSalaryAdjust to set
     */
    public void setOtIdxPaidSalaryAdjust(double otIdxPaidSalaryAdjust) {
        this.otIdxPaidSalaryAdjust = otIdxPaidSalaryAdjust;
    }

    /**
     * @return the otIdxPaidSalaryChekedAdjust
     */
    public boolean isOtIdxPaidSalaryChekedAdjust() {
        return otIdxPaidSalaryChekedAdjust;
    }

    /**
     * @param otIdxPaidSalaryChekedAdjust the otIdxPaidSalaryChekedAdjust to set
     */
    public void setOtIdxPaidSalaryChekedAdjust(boolean otIdxPaidSalaryChekedAdjust) {
        this.otIdxPaidSalaryChekedAdjust = otIdxPaidSalaryChekedAdjust;
    }

    /**
     * @return the otIdxPaidDp
     */
    public double getOtIdxPaidDp() {
        return otIdxPaidDp;
    }

    /**
     * @param otIdxPaidDp the otIdxPaidDp to set
     */
    public void setOtIdxPaidDp(double otIdxPaidDp) {
        this.otIdxPaidDp = otIdxPaidDp;
    }

    /**
     * @return the otAllowanceMoney
     */
    public double getOtAllowanceMoney() {
        return otAllowanceMoney;
    }

    /**
     * @param otAllowanceMoney the otAllowanceMoney to set
     */
    public void setOtAllowanceMoney(double otAllowanceMoney) {
        this.otAllowanceMoney = otAllowanceMoney;
    }

    /**
     * @return the otAllowanceMoneyAdjust
     */
    public double getOtAllowanceMoneyAdjust() {
        return otAllowanceMoneyAdjust;
    }

    /**
     * @param otAllowanceMoneyAdjust the otAllowanceMoneyAdjust to set
     */
    public void setOtAllowanceMoneyAdjust(double otAllowanceMoneyAdjust) {
        this.otAllowanceMoneyAdjust = otAllowanceMoneyAdjust;
    }

    /**
     * @return the otAllowanceMoneyChekedAdjust
     */
    public boolean isOtAllowanceMoneyChekedAdjust() {
        return otAllowanceMoneyChekedAdjust;
    }

    /**
     * @param otAllowanceMoneyChekedAdjust the otAllowanceMoneyChekedAdjust to set
     */
    public void setOtAllowanceMoneyChekedAdjust(boolean otAllowanceMoneyChekedAdjust) {
        this.otAllowanceMoneyChekedAdjust = otAllowanceMoneyChekedAdjust;
    }

    /**
     * @return the otAllowanceFood
     */
    public double getOtAllowanceFood() {
        return otAllowanceFood;
    }

    /**
     * @param otAllowanceFood the otAllowanceFood to set
     */
    public void setOtAllowanceFood(double otAllowanceFood) {
        this.otAllowanceFood = otAllowanceFood;
    }

    /**
     * @return the otAllowanceFoodAdjust
     */
    public double getOtAllowanceFoodAdjust() {
        return otAllowanceFoodAdjust;
    }

    /**
     * @param otAllowanceFoodAdjust the otAllowanceFoodAdjust to set
     */
    public void setOtAllowanceFoodAdjust(double otAllowanceFoodAdjust) {
        this.otAllowanceFoodAdjust = otAllowanceFoodAdjust;
    }

    /**
     * @return the otAllowanceFoodChekedAdjust
     */
    public boolean isOtAllowanceFoodChekedAdjust() {
        return otAllowanceFoodChekedAdjust;
    }

    /**
     * @param otAllowanceFoodChekedAdjust the otAllowanceFoodChekedAdjust to set
     */
    public void setOtAllowanceFoodChekedAdjust(boolean otAllowanceFoodChekedAdjust) {
        this.otAllowanceFoodChekedAdjust = otAllowanceFoodChekedAdjust;
    }

    /**
     * @return the privateNote
     */
    public String getPrivateNote() {
        if(privateNote==null || privateNote.length()==0){
            return "";
        }
        return privateNote;
    }

    /**
     * @param privateNote the privateNote to set
     */
    public void setPrivateNote(String privateNote) {
        this.privateNote = privateNote;
    }

    /**
     * @return the empWorkDays
     */
    public double getEmpWorkDays() {
        return empWorkDays;
    }

    /**
     * @param empWorkDays the empWorkDays to set
     */
    public void setEmpWorkDays(double empWorkDays) {
        this.empWorkDays = empWorkDays;
    }

    /**
     * @return the note
     */
    public String getNote() {
        if(note==null){
            return "";
        }
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the departementId
     */
    public long getDepartementId() {
        return departementId;
    }

    /**
     * @param departementId the departementId to set
     */
    public void setDepartementId(long departementId) {
        this.departementId = departementId;
    }

    /**
     * @return the paySlipId
     */
    public long getPaySlipId() {
        return paySlipId;
    }

    /**
     * @param paySlipId the paySlipId to set
     */
    public void setPaySlipId(long paySlipId) {
        this.paySlipId = paySlipId;
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
     * @return the lateIdx
     */
    public int getLateIdx() {
        return lateIdx;
    }

    /**
     * @param lateIdx the lateIdx to set
     */
    public void setLateIdx(int lateIdx) {
        this.lateIdx = lateIdx;
    }

    /**
     * @return the levelCode
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @param levelCode the levelCode to set
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * @return the insentif
     */
    public double getInsentif() {
        return insentif;
    }

    /**
     * @param insentif the insentif to set
     */
    public void setInsentif(double insentif) {
        this.insentif = insentif;
    }

    /**
     * @return the insentifAdjusment
     */
    public double getInsentifAdjusment() {
        return insentifAdjusment;
    }

    /**
     * @param insentifAdjusment the insentifAdjusment to set
     */
    public void setInsentifAdjusment(double insentifAdjusment) {
        this.insentifAdjusment = insentifAdjusment;
    }

    /**
     * @return the positionIdx
     */
    public double getPositionIdx(long positionId,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
         if(positionId!=0 && positionIdx!=null && positionIdx.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX]+"_"+positionId+"_"+periodId+"_"+employeeId)){ 
            return (Double)positionIdx.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX]+"_"+positionId+"_"+periodId+"_"+employeeId);//tergantung dari reasonId
        }
        else{
        return 0;
        }
    }

    /**
     * @param positionIdx the positionIdx to set
     */
    public void addPositionIdx(Hashtable positionIdx) {
        this.positionIdx = positionIdx;
    }
    
    public void addPositionHashIdx(long positionId,double value,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(this.positionIdx!=null){
            this.positionIdx.put(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX]+"_"+positionId+"_"+periodId+"_"+employeeId,value);
        }
        
    }

    /**
     * @return the positionAdjust
     */
    public double getPositionAdjust(long positionId,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
       if(positionId!=0 && positionAdjust!=null && positionAdjust.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT]+"_"+positionId+"_"+periodId+"_"+employeeId)){ 
            return (Double)positionAdjust.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT]+"_"+positionId+"_"+periodId+"_"+employeeId);//tergantung dari reasonId
        }else{
        return 0;
       }
    }

    /**
     * @param positionAdjust the positionAdjust to set
     */
    public void addPositionAdjust(Hashtable positionAdjust) {
        this.positionAdjust = positionAdjust;
    }
    
    public void addPositionHashAdjust(long positionId,double value,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(this.positionAdjust!=null){
            this.positionAdjust.put(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT]+"_"+positionId+"_"+periodId+"_"+employeeId, value);
        }
        
    }

    /**
     * @return the reasonIdx
     */ 
    public double getReasonIdx(int reasonNo,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(reasonNo!=0 && reasonIdx!=null && reasonIdx.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX]+"_"+reasonNo+"_"+periodId+"_"+employeeId)){ 
            return (Double)reasonIdx.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX]+"_"+reasonNo+"_"+periodId+"_"+employeeId);//tergantung dari reasonId
        }
        return 0;
    }

    /**
     * @param reasonIdx the reasonIdx to set
     */
    public void addReasonIdx(Hashtable reasonIdx) {
        this.reasonIdx=reasonIdx;
    }
    
    public void addReasonHashIdx(int reasonNo,double value,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(this.reasonIdx!=null){
            this.reasonIdx.put(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX]+"_"+reasonNo+"_"+periodId+"_"+employeeId, value);
        }
        
    }

    /**
     * @return the reasonTime
     */
    public double getReasonTime(int reasonNo,long periodId,long employeeId) {
         /*if(reasonId!=0){
            return reasonTime;
        }*/
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(reasonNo!=0 && reasonTime!=null && reasonTime.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME]+"_"+reasonNo+"_"+periodId+"_"+employeeId)){ 
            return Math.abs((Double)reasonTime.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME]+"_"+reasonNo+"_"+periodId+"_"+employeeId));//tergantung dari reasonId
        }
        return 0;
    }

    /**
     * @param reasonTime the reasonTime to set
     */
    public void addReasonTime(Hashtable reasonTime) {
        this.reasonTime = reasonTime;
    }
    
    public void addReasonHashTime(int reasonNo,double value,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(this.reasonTime!=null){
            this.reasonTime.put(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME]+"_"+reasonNo+"_"+periodId+"_"+employeeId,value);
        }
        
    }

    /**
     * @return the reasonIdxAdjust
     */
    public double getReasonIdxAdjust(int reasonNo,long periodId,long employeeId) {
//         if(reasonId!=0){
//            return reasonIdxAdjust;
//        }
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(reasonNo!=0 && reasonIdxAdjust!=null && reasonIdxAdjust.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT]+"_"+reasonNo+"_"+periodId+"_"+employeeId)){ 
            return (Double)reasonIdxAdjust.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT]+"_"+reasonNo+"_"+periodId+"_"+employeeId);//tergantung dari reasonId
        }
        return 0;
    }

    /**
     * @param reasonIdxAdjust the reasonIdxAdjust to set
     */
    public void addReasonIdxAdjust(Hashtable reasonIdxAdjust) {
        this.reasonIdxAdjust = reasonIdxAdjust;
    }
    
    public void addReasonHashIdxAdjust(int reasonNo,double value,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(this.reasonIdxAdjust!=null){
            this.reasonIdxAdjust.put(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT]+"_"+reasonNo+"_"+periodId+"_"+employeeId, value);
        }
        
    }

    /**
     * @return the reasonTimeAdjust
     */
    public double getReasonTimeAdjust(int reasonNo,long periodId,long employeeId) {
//        if(reasonId!=0){
//            return reasonTimeAdjust;
//        }
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(reasonNo!=0 && reasonTimeAdjust!=null && reasonTimeAdjust.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT]+"_"+reasonNo+"_"+periodId+"_"+employeeId)){ 
            return (Double)reasonTimeAdjust.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT]+"_"+reasonNo+"_"+periodId+"_"+employeeId);//tergantung dari reasonId
        }
        return 0;
    }

    /**
     * @param reasonTimeAdjust the reasonTimeAdjust to set
     */
    public void addReasonTimeAdjust(Hashtable reasonTimeAdjust) {
        this.reasonTimeAdjust = reasonTimeAdjust;
    }
    
    public void addReasonHashTimeAdjust(int reasonNo,double value,long periodId,long employeeId) {
        periodId=0;//di buat 0 karena di periode attdance berbeda dengan periode payroll
        if(this.reasonTimeAdjust!=null){
            this.reasonTimeAdjust.put(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT]+"_"+reasonNo+"_"+periodId+"_"+employeeId, value);
        }
        
    }

    /**
     * @return the alIdx
     */
    public float getAlIdx() {
        return alIdx;
    }

    /**
     * @param alIdx the alIdx to set
     */
    public void setAlIdx(float alIdx) {
        this.alIdx = alIdx;
    }

    /**
     * @return the alTime
     */
    public float getAlTime() {
        return alTime;
    }

    /**
     * @param alTime the alTime to set
     */
    public void setAlTime(float alTime) {
        this.alTime = alTime;
    }

    /**
     * @return the llIdx
     */
    public float getLlIdx() {
        return llIdx;
    }

    /**
     * @param llIdx the llIdx to set
     */
    public void setLlIdx(float llIdx) {
        this.llIdx = llIdx;
    }

    /**
     * @return the llTime
     */
    public float getLlTime() {
        return llTime;
    }

    /**
     * @param llTime the llTime to set
     */
    public void setLlTime(float llTime) {
        this.llTime = llTime;
    }

    /**
     * @return the dpIdx
     */
    public float getDpIdx() {
        return dpIdx;
    }

    /**
     * @param dpIdx the dpIdx to set
     */
    public void setDpIdx(float dpIdx) {
        this.dpIdx = dpIdx;
    }

    /**
     * @return the dpTime
     */
    public float getDpTime() {
        return dpTime;
    }

    /**
     * @param dpTime the dpTime to set
     */
    public void setDpTime(float dpTime) {
        this.dpTime = dpTime;
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
     * @return the otIdxPaidSalaryAdjustDt
     */
    public Date getOtIdxPaidSalaryAdjustDt() {
        return otIdxPaidSalaryAdjustDt;
    }

    /**
     * @param otIdxPaidSalaryAdjustDt the otIdxPaidSalaryAdjustDt to set
     */
    public void setOtIdxPaidSalaryAdjustDt(Date otIdxPaidSalaryAdjustDt) {
        this.otIdxPaidSalaryAdjustDt = otIdxPaidSalaryAdjustDt;
    }

    /**
     * @return the otAllowanceMoneyAdjustDt
     */
    public Date getOtAllowanceMoneyAdjustDt() {
        return otAllowanceMoneyAdjustDt;
    }

    /**
     * @param otAllowanceMoneyAdjustDt the otAllowanceMoneyAdjustDt to set
     */
    public void setOtAllowanceMoneyAdjustDt(Date otAllowanceMoneyAdjustDt) {
        this.otAllowanceMoneyAdjustDt = otAllowanceMoneyAdjustDt;
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

    /**
     * @return the nightAllowanceAdjusment
     */
    public double getNightAllowanceAdjusment() {
        return nightAllowanceAdjusment;
    }

    /**
     * @param nightAllowanceAdjusment the nightAllowanceAdjusment to set
     */
    public void setNightAllowanceAdjusment(double nightAllowanceAdjusment) {
        this.nightAllowanceAdjusment = nightAllowanceAdjusment;
    }

    /**
     * @return the transportAllowanceAdjusment
     */
    public double getTransportAllowanceAdjusment() {
        return transportAllowanceAdjusment;
    }

    /**
     * @param transportAllowanceAdjusment the transportAllowanceAdjusment to set
     */
    public void setTransportAllowanceAdjusment(double transportAllowanceAdjusment) {
        this.transportAllowanceAdjusment = transportAllowanceAdjusment;
    }

    /**
     * @return the nightAllowanceChecked
     */
    public boolean isNightAllowanceChecked() {
        return nightAllowanceChecked;
    }

    /**
     * @param nightAllowanceChecked the nightAllowanceChecked to set
     */
    public void setNightAllowanceChecked(boolean nightAllowanceChecked) {
        this.nightAllowanceChecked = nightAllowanceChecked;
    }

    /**
     * @return the transportAllowanceChecked
     */
    public boolean isTransportAllowanceChecked() {
        return transportAllowanceChecked;
    }

    /**
     * @param transportAllowanceChecked the transportAllowanceChecked to set
     */
    public void setTransportAllowanceChecked(boolean transportAllowanceChecked) {
        this.transportAllowanceChecked = transportAllowanceChecked;
    }

    /**
     * @return the nightAllowanceAdjustDt
     */
    public Date getNightAllowanceAdjustDt() {
        return nightAllowanceAdjustDt;
    }

    /**
     * @param nightAllowanceAdjustDt the nightAllowanceAdjustDt to set
     */
    public void setNightAllowanceAdjustDt(Date nightAllowanceAdjustDt) {
        this.nightAllowanceAdjustDt = nightAllowanceAdjustDt;
    }

    /**
     * @return the transportAllowanceAdjustDt
     */
    public Date getTransportAllowanceAdjustDt() {
        return transportAllowanceAdjustDt;
    }

    /**
     * @param transportAllowanceAdjustDt the transportAllowanceAdjustDt to set
     */
    public void setTransportAllowanceAdjustDt(Date transportAllowanceAdjustDt) {
        this.transportAllowanceAdjustDt = transportAllowanceAdjustDt;
    }


    /**
     * @return the OnlyInIdx
     */
    public int getOnlyInIdx() {
        return OnlyInIdx;
    }

    /**
     * @param OnlyInIdx the OnlyInIdx to set
     */
    public void setOnlyInIdx(int OnlyInIdx) {
        this.OnlyInIdx = OnlyInIdx;
    }

    /**
     * @return the OnlyInTime
     */
    public double getOnlyInTime() {
        return OnlyInTime;
    }

    /**
     * @param OnlyInTime the OnlyInTime to set
     */
    public void setOnlyInTime(double OnlyInTime) {
        this.OnlyInTime = OnlyInTime;
    }

    /**
     * @return the OnlyInIdxAdjust
     */
    public int getOnlyInIdxAdjust() {
        return OnlyInIdxAdjust;
    }

    /**
     * @param OnlyInIdxAdjust the OnlyInIdxAdjust to set
     */
    public void setOnlyInIdxAdjust(int OnlyInIdxAdjust) {
        this.OnlyInIdxAdjust = OnlyInIdxAdjust;
    }

    /**
     * @return the OnlyInTimeAdjust
     */
    public double getOnlyInTimeAdjust() {
        return OnlyInTimeAdjust;
    }

    /**
     * @param OnlyInTimeAdjust the OnlyInTimeAdjust to set
     */
    public void setOnlyInTimeAdjust(double OnlyInTimeAdjust) {
        this.OnlyInTimeAdjust = OnlyInTimeAdjust;
    }

    /**
     * @return the OnlyInChekedAdjust
     */
    public boolean isOnlyInChekedAdjust() {
        return OnlyInChekedAdjust;
    }

    /**
     * @param OnlyInChekedAdjust the OnlyInChekedAdjust to set
     */
    public void setOnlyInChekedAdjust(boolean OnlyInChekedAdjust) {
        this.OnlyInChekedAdjust = OnlyInChekedAdjust;
    }

    /**
     * @return the OnlyOutIdx
     */
    public int getOnlyOutIdx() {
        return OnlyOutIdx;
    }

    /**
     * @param OnlyOutIdx the OnlyOutIdx to set
     */
    public void setOnlyOutIdx(int OnlyOutIdx) {
        this.OnlyOutIdx = OnlyOutIdx;
    }

    /**
     * @return the OnlyOutTime
     */
    public double getOnlyOutTime() {
        return OnlyOutTime;
    }

    /**
     * @param OnlyOutTime the OnlyOutTime to set
     */
    public void setOnlyOutTime(double OnlyOutTime) {
        this.OnlyOutTime = OnlyOutTime;
    }

    /**
     * @return the OnlyOutIdxAdjust
     */
    public int getOnlyOutIdxAdjust() {
        return OnlyOutIdxAdjust;
    }

    /**
     * @param OnlyOutIdxAdjust the OnlyOutIdxAdjust to set
     */
    public void setOnlyOutIdxAdjust(int OnlyOutIdxAdjust) {
        this.OnlyOutIdxAdjust = OnlyOutIdxAdjust;
    }

    /**
     * @return the OnlyOutTimeAdjust
     */
    public double getOnlyOutTimeAdjust() {
        return OnlyOutTimeAdjust;
    }

    /**
     * @param OnlyOutTimeAdjust the OnlyOutTimeAdjust to set
     */
    public void setOnlyOutTimeAdjust(double OnlyOutTimeAdjust) {
        this.OnlyOutTimeAdjust = OnlyOutTimeAdjust;
    }

    /**
     * @return the OnlyOutChekedAdjust
     */
    public boolean isOnlyOutChekedAdjust() {
        return OnlyOutChekedAdjust;
    }

    /**
     * @param OnlyOutChekedAdjust the OnlyOutChekedAdjust to set
     */
    public void setOnlyOutChekedAdjust(boolean OnlyOutChekedAdjust) {
        this.OnlyOutChekedAdjust = OnlyOutChekedAdjust;
    }

   
    
    
}
