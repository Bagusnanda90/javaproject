/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.attendance;

import com.dimata.harisma.entity.attendance.EmpScheduleReport;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class TmpListParamAttdSummary {
    private Vector listAttdAbsensi= new Vector();
    private Hashtable hashReason = new Hashtable();
    private Hashtable hashPersonalInOut = new Hashtable();
    private Vector  listEmployee = new Vector();
    private Vector vReason = new Vector();
    private Hashtable listTakenLeave = new Hashtable();
    private Hashtable listTotSUmLeaveSpecial = new Hashtable();
    private Hashtable hashAdjusmentForPayInput = new Hashtable();
    private Hashtable hashAdjusmentForPaySlip = new Hashtable();
    private Hashtable sumOvertimeDailyPaidBySalary = new Hashtable();
    private Hashtable sumOvertimeDailyPaidByDayOff = new Hashtable();
    private Hashtable sumOvertimeDailyAllowanceFood = new Hashtable();
    private Hashtable sumOvertimeDailyAllowanceMoney = new Hashtable();
    private Hashtable hashTblSchedule = new Hashtable();
    
    private Hashtable hashReasonHitung = new Hashtable();
    private Hashtable hashReasonCalulateBySistem = new Hashtable();
 
    
    
    //private static  Hashtable allQuery = new Hashtable();
    /**
     * @return the listAttdAbsensi
     */
    public Vector getListAttdAbsensi() {
        return listAttdAbsensi;
    }

    /**
     * @param listAttdAbsensi the listAttdAbsensi to set
     */
    public void setListAttdAbsensi(Vector listAttdAbsensi) {
        this.listAttdAbsensi = listAttdAbsensi;
    }

    /**
     * @return the hashReason
     */
    public Hashtable getHashReason() {
        return hashReason;
    }

    /**
     * @param hashReason the hashReason to set
     */
    public void setHashReason(Hashtable hashReason) {
        this.hashReason = hashReason;
    }

    /**
     * @return the hashPersonalInOut
     */
    public Hashtable getHashPersonalInOut() {
        return hashPersonalInOut;
    }

    /**
     * @param hashPersonalInOut the hashPersonalInOut to set
     */
    public void setHashPersonalInOut(Hashtable hashPersonalInOut) {
        this.hashPersonalInOut = hashPersonalInOut;
    }

    /**
     * @return the listEmployee
     */
    public Vector getListEmployee() {
        return listEmployee;
    }

    /**
     * @param listEmployee the listEmployee to set
     */
    public void setListEmployee(Vector listEmployee) {
        this.listEmployee = listEmployee;
    }

    /**
     * @return the vReason
     */
    public Vector getvReason() {
        return vReason;
    }

    /**
     * @param vReason the vReason to set
     */
    public void setvReason(Vector vReason) {
        this.vReason = vReason;
    }

    /**
     * @return the listTakenLeave
     */
    public Hashtable getListTakenLeave() {
        return listTakenLeave;
    }

    /**
     * @param listTakenLeave the listTakenLeave to set
     */
    public void setListTakenLeave(Hashtable listTakenLeave) {
        this.listTakenLeave = listTakenLeave;
    }

    /**
     * @return the listTotSUmLeaveSpecial
     */
    public Hashtable getListTotSUmLeaveSpecial() {
        return listTotSUmLeaveSpecial;
    }

    /**
     * @param listTotSUmLeaveSpecial the listTotSUmLeaveSpecial to set
     */
    public void setListTotSUmLeaveSpecial(Hashtable listTotSUmLeaveSpecial) {
        this.listTotSUmLeaveSpecial = listTotSUmLeaveSpecial;
    }

    /**
     * @return the hashAdjusmentForPayInput
     */
    public Hashtable getHashAdjusmentForPayInput() {
        return hashAdjusmentForPayInput;
    }

    /**
     * @param hashAdjusmentForPayInput the hashAdjusmentForPayInput to set
     */
    public void setHashAdjusmentForPayInput(Hashtable hashAdjusmentForPayInput) {
        this.hashAdjusmentForPayInput = hashAdjusmentForPayInput;
    }

    /**
     * @return the hashAdjusmentForPaySlip
     */
    public Hashtable getHashAdjusmentForPaySlip() {
        return hashAdjusmentForPaySlip;
    }

    /**
     * @param hashAdjusmentForPaySlip the hashAdjusmentForPaySlip to set
     */
    public void setHashAdjusmentForPaySlip(Hashtable hashAdjusmentForPaySlip) {
        this.hashAdjusmentForPaySlip = hashAdjusmentForPaySlip;
    }

    /**
     * @return the sumOvertimeDailyPaidBySalary
     */
    public Hashtable getSumOvertimeDailyPaidBySalary() {
        return sumOvertimeDailyPaidBySalary;
    }

    /**
     * @param sumOvertimeDailyPaidBySalary the sumOvertimeDailyPaidBySalary to set
     */
    public void setSumOvertimeDailyPaidBySalary(Hashtable sumOvertimeDailyPaidBySalary) {
        this.sumOvertimeDailyPaidBySalary = sumOvertimeDailyPaidBySalary;
    }

    /**
     * @return the sumOvertimeDailyPaidByDayOff
     */
    public Hashtable getSumOvertimeDailyPaidByDayOff() {
        return sumOvertimeDailyPaidByDayOff;
    }

    /**
     * @param sumOvertimeDailyPaidByDayOff the sumOvertimeDailyPaidByDayOff to set
     */
    public void setSumOvertimeDailyPaidByDayOff(Hashtable sumOvertimeDailyPaidByDayOff) {
        this.sumOvertimeDailyPaidByDayOff = sumOvertimeDailyPaidByDayOff;
    }

    /**
     * @return the sumOvertimeDailyAllowanceFood
     */
    public Hashtable getSumOvertimeDailyAllowanceFood() {
        return sumOvertimeDailyAllowanceFood;
    }

    /**
     * @param sumOvertimeDailyAllowanceFood the sumOvertimeDailyAllowanceFood to set
     */
    public void setSumOvertimeDailyAllowanceFood(Hashtable sumOvertimeDailyAllowanceFood) {
        this.sumOvertimeDailyAllowanceFood = sumOvertimeDailyAllowanceFood;
    }

    /**
     * @return the sumOvertimeDailyAllowanceMoney
     */
    public Hashtable getSumOvertimeDailyAllowanceMoney() {
        return sumOvertimeDailyAllowanceMoney;
    }

    /**
     * @param sumOvertimeDailyAllowanceMoney the sumOvertimeDailyAllowanceMoney to set
     */
    public void setSumOvertimeDailyAllowanceMoney(Hashtable sumOvertimeDailyAllowanceMoney) {
        this.sumOvertimeDailyAllowanceMoney = sumOvertimeDailyAllowanceMoney;
    }

    /**
     * @return the hashTblSchedule
     */
    public Hashtable getHashTblSchedule() {
        return hashTblSchedule;
    }

    /**
     * @param hashTblSchedule the hashTblSchedule to set
     */
    public void setHashTblSchedule(Hashtable hashTblSchedule) {
        this.hashTblSchedule = hashTblSchedule;
    }

    
//    public Hashtable getParameterAll(TmpListParamAttdSummary tmp){
//        try{
//            loadParameterAll(tmp);
//        }catch(Exception ex){
//        
//        }finally{
//          return allQuery;
//        }
//        
//        
//        
//    }
    
    
//    public static  void loadParameterAll(TmpListParamAttdSummary temp){
//        try{
//            if(allQuery==null){
//                    allQuery = new Hashtable();
//             }
//                allQuery.put(""+1, temp);
//                
//        }catch(Exception ex){
//        
//        }finally {
//            return;
//        }
//        
//    }

    /**
     * @return the hashReasonHitung
     */
    public Vector getHashReasonHitung(String employeeIdnNoReason) {
        Vector vListHasil= new Vector();
        if(hashReasonHitung!=null && hashReasonHitung.size()>0 && hashReasonHitung.containsKey(employeeIdnNoReason)){ 
            vListHasil = (Vector)hashReasonHitung.get(employeeIdnNoReason);
        }
        return vListHasil;
    }

    /**
     * @param hashReasonHitung the hashReasonHitung to set
     */
    public void addHashReasonHitung(Vector hashReasonHitung,String employeeIdnNoReason) {
        this.hashReasonHitung.put(employeeIdnNoReason, hashReasonHitung);
    }

    /**
     * @return the hashReasonCalulateBySistem
     */
    public EmpScheduleReport getHashReasonCalulateBySistem(String employeeIdnNumberReason) {
        EmpScheduleReport empScheduleReport = new EmpScheduleReport();
        if(hashReasonCalulateBySistem!=null && hashReasonCalulateBySistem.size()>0 && hashReasonCalulateBySistem.containsKey(employeeIdnNumberReason)){
            empScheduleReport = (EmpScheduleReport)hashReasonCalulateBySistem.get(employeeIdnNumberReason);
        }
        return empScheduleReport;
    }

    /**
     * @param hashReasonCalulateBySistem the hashReasonCalulateBySistem to set
     */
    public void addHashReasonCalulateBySistem(EmpScheduleReport empScheduleReport,String employeeIdnNumberReason) {
        this.hashReasonCalulateBySistem.put(employeeIdnNumberReason, empScheduleReport);
    }
    
}
