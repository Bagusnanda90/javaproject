/*
 * Ovt_Employee.java
 *
 * Created on April 6, 2007, 11:54 AM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  emerliana
 */
public class Ovt_Employee extends Entity {
    
    private long periodId = 0;
    private String employee_num = "";
    private Date workDate;
    private String work_schedule = "";
    private Date ovt_Start;
    private Date ovt_End;
    private double duration;
    private String ovt_doc_nr = "";
    private int status = 0;
    private long pay_slip_id = 0;
    private String ovt_code = "";
    private double tot_Idx = 0.0;
    
    /**
     * Getter for property periodId.
     * @return Value of property periodId.
     */
    public long getPeriodId() {
        return periodId;
    }    
    
    /**
     * Setter for property periodId.
     * @param periodId New value of property periodId.
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }    
    
    /**
     * Getter for property employee_num.
     * @return Value of property employee_num.
     */
    public java.lang.String getEmployee_num() {
        return employee_num;
    }
    
    /**
     * Setter for property employee_num.
     * @param employee_num New value of property employee_num.
     */
    public void setEmployee_num(java.lang.String employee_num) {
        this.employee_num = employee_num;
    }
    
    /**
     * Getter for property workDate.
     * @return Value of property workDate.
     */
    public java.util.Date getWorkDate() {
        return workDate;
    }
    
    /**
     * Setter for property workDate.
     * @param workDate New value of property workDate.
     */
    public void setWorkDate(java.util.Date workDate) {
        this.workDate = workDate;
    }
    
    /**
     * Getter for property work_schedule.
     * @return Value of property work_schedule.
     */
    public java.lang.String getWork_schedule() {
        return work_schedule;
    }
    
    /**
     * Setter for property work_schedule.
     * @param work_schedule New value of property work_schedule.
     */
    public void setWork_schedule(java.lang.String work_schedule) {
        this.work_schedule = work_schedule;
    }
    
    /**
     * Getter for property ovt_Start.
     * @return Value of property ovt_Start.
     */
    public java.util.Date getOvt_Start() {
        return ovt_Start;
    }
    
    /**
     * Setter for property ovt_Start.
     * @param ovt_Start New value of property ovt_Start.
     */
    public void setOvt_Start(java.util.Date ovt_Start) {
        this.ovt_Start = ovt_Start;
    }
    
    /**
     * Getter for property ovt_End.
     * @return Value of property ovt_End.
     */
    public java.util.Date getOvt_End() {
        return ovt_End;
    }
    
    /**
     * Setter for property ovt_End.
     * @param ovt_End New value of property ovt_End.
     */
    public void setOvt_End(java.util.Date ovt_End) {
        this.ovt_End = ovt_End;
    }
    
    /**
     * Getter for property duration.
     * @return Value of property duration.
     */
    public double getDuration() {
        return duration;
    }
    
    /**
     * Setter for property duration.
     * @param duration New value of property duration.
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }
    
    /**
     * Getter for property ovt_doc_nr.
     * @return Value of property ovt_doc_nr.
     */
    public java.lang.String getOvt_doc_nr() {
        return ovt_doc_nr;
    }
    
    /**
     * Setter for property ovt_doc_nr.
     * @param ovt_doc_nr New value of property ovt_doc_nr.
     */
    public void setOvt_doc_nr(java.lang.String ovt_doc_nr) {
        this.ovt_doc_nr = ovt_doc_nr;
    }
    
    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {
        return status;
    }
    
    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * Getter for property pay_slip_id.
     * @return Value of property pay_slip_id.
     */
    public long getPay_slip_id() {
        return pay_slip_id;
    }
    
    /**
     * Setter for property pay_slip_id.
     * @param pay_slip_id New value of property pay_slip_id.
     */
    public void setPay_slip_id(long pay_slip_id) {
        this.pay_slip_id = pay_slip_id;
    }
    
    /**
     * Getter for property ovt_code.
     * @return Value of property ovt_code.
     */
    public java.lang.String getOvt_code() {
        return ovt_code;
    }
    
    /**
     * Setter for property ovt_code.
     * @param ovt_code New value of property ovt_code.
     */
    public void setOvt_code(java.lang.String ovt_code) {
        this.ovt_code = ovt_code;
    }
    
    /**
     * Getter for property tot_Idx.
     * @return Value of property tot_Idx.
     */
    public double getTot_Idx() {
        return tot_Idx;
    }
    
    /**
     * Setter for property tot_Idx.
     * @param tot_Idx New value of property tot_Idx.
     */
    public void setTot_Idx(double tot_Idx) {
        this.tot_Idx = tot_Idx;
    }
    
}
