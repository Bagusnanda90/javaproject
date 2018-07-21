/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

import com.dimata.harisma.session.payroll.SessOvertime;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class TableHitungOvertimeDetail {
    private long employee_id;
    private Vector vDuration=new Vector();
    private Vector vTotIdx=new Vector();
    private Vector vAllowanceMoney=new Vector();
    private Vector vAllowanceFood=new Vector();
    private Vector vPaidDp=new Vector();
    private Vector vPaidSalary=new Vector();

    /**
     * @return the employee_id
     */
    public long getEmployee_id() {
        return employee_id;
    }

    /**
     * @param employee_id the employee_id to set
     */
    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }

    /**
     * @return the vDuration
     */
    public double getvDuration(int idx) {
        if(idx>=vDuration.size()){
            return 0;
        }
        double dur = (Double)vDuration.get(idx);
        if(false){ 
        //update by satrya 2014-02-11 yg diambil real'nya buka pembulatannya if(SessOvertime.getOvertimeRoundTo()>0 && (dur*60)>SessOvertime.getOvertimeRoundStart() ){ 
            dur = ((double)((Math.round(dur*60)/ SessOvertime.getOvertimeRoundTo())*SessOvertime.getOvertimeRoundTo()))/60d;
            return dur;
        } else{
            return dur;
        }
        //return ;
        //return vDuration;
    }

    /**
     * @param vDuration the vDuration to set
     */
    public void addvDuration(Date realDateFrom,Date realDateTo,Date dateFrom,Date dateTo,double restTimeHr) {
         double durationX=0;
          if(realDateFrom!=null && realDateTo!=null && dateFrom!=null && dateTo!=null){
       
             durationX = (Formater.formatDurationTime2(
                 realDateFrom.getTime() >=dateFrom.getTime() ? realDateFrom : dateFrom 
                , realDateTo.getTime() < dateTo.getTime()  ? realDateTo : dateTo , true)- restTimeHr);
      } 
        this.vDuration.add(durationX);
    }

    public int getSizeDuration(){
        return vDuration.size();
    }
    /**
     * @return the vTotIdx
     */
    public double getvTotIdx(int idx) {
        if(idx>=vTotIdx.size()){
            return 0;
        }
        return (Double)vTotIdx.get(idx);
        //return vTotIdx;
    }

    /**
     * @param vTotIdx the vTotIdx to set
     */
    public void addvTotIdx(double vTotIdx) {
        this.vTotIdx.add(vTotIdx);
    }
    
    public int getSizeTotIdx(){
        return vTotIdx.size();
    }
    /**
     * @return the vAllowanceMoney
     */
    public int getvAllowanceMoney(int idx) {
        
         if(idx>=vAllowanceMoney.size()){
            return 0;
        }
        return (Integer)vAllowanceMoney.get(idx);
        //return vAllowanceMoney;
    }

    /**
     * @param vAllowanceMoney the vAllowanceMoney to set
     */
    public void addvAllowanceMoney(int vAllowanceMoney) {
        this.vAllowanceMoney.add(vAllowanceMoney);
    }
     public int getSizeAllowanceMoney(){
        return vAllowanceMoney.size();
    }
    /**
     * @return the vAllowanceFood
     */
    public int getvAllowanceFood(int idx) {
          if(idx>=vAllowanceFood.size()){
            return 0;
        }
        return (Integer)vAllowanceFood.get(idx);
        //return vAllowanceFood;
    }

    /**
     * @param vAllowanceFood the vAllowanceFood to set
     */
    public void addvAllowanceFood(int vAllowanceFood) {
        this.vAllowanceFood.add(vAllowanceFood);
    }

     public int getSizeAllowanceFood(){
        return vAllowanceFood.size();
    }
    /**
     * @return the vPaidDp
     */
    public int getvPaidDp(int idx) {
          if(idx>=vPaidDp.size()){
            return 0;
        }
        return (Integer)vPaidDp.get(idx);
        //return vPaidDp;
    }

    /**
     * @param vPaidDp the vPaidDp to set
     */
    public void addvPaidDp(int vPaidDp) {
        this.vPaidDp.add(vPaidDp);
    }

    public int getSizePaidDp(){
        return vPaidDp.size();
    }
    /**
     * @return the vPaidSalary
     */
    public int getvPaidSalary(int idx) {
          if(idx>=vPaidSalary.size()){
            return 0;
        }
        return (Integer)vPaidSalary.get(idx);
       // return vPaidSalary;
    }

    /**
     * @param vPaidSalary the vPaidSalary to set
     */
    public void addvPaidSalary(int vPaidSalary) {
        this.vPaidSalary.add(vPaidSalary);
    }
    
    public int getSizePaidSalary(){
        return vPaidSalary.size();
    }
}
