/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class PaySlipNote extends Entity{
    private Date dtPaySlipNote;
    private long periodId;
    private long paySlipId;
    private long employeeId;
    private String note;

    /**
     * @return the dtPaySlipNote
     */
    public Date getDtPaySlipNote() {
        return dtPaySlipNote;
    }

    /**
     * @param dtPaySlipNote the dtPaySlipNote to set
     */
    public void setDtPaySlipNote(Date dtPaySlipNote) {
        this.dtPaySlipNote = dtPaySlipNote;
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
}
