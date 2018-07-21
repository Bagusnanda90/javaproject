/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class EmpScheduleReport extends Entity{
    private long employeeId = 0;
    private Vector empSchedules1st = new Vector(1,1);
    private Vector empIn1st = new Vector(1,1);
    private Vector empOut1st = new Vector(1,1);
    private Vector empReason = new Vector(1,1);
    private Vector empStatus = new Vector(1,1);
    private int totStatus;
    private int totReason;
    private Vector dtIDate = new Vector(1,1);//mengetahui dia ada d tanggal berapa tpi dlm int
    
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
     * @return the empSchedules1st
     */
    public long getEmpSchedules1st(int idx) {
        if(idx >=empSchedules1st.size()){
            return 0;
        }
        return (Long)empSchedules1st.get(idx);
    }

    public int getEmpSchedules1stSize() {
        return (Integer)empSchedules1st.size();
    }    
    
    /**
     * @param empSchedules1st the empSchedules1st to set
     */
    public void addEmpSchedules1st( long empSchedules1stOID) {
        this.empSchedules1st.add( empSchedules1stOID);
    }

    /**
     * @param empIn1st the empIn1st to set
     */
     public Date getEmpIn1st(int idx) {
        if(idx >=empIn1st.size())
            return null;
        return (Date)empIn1st.get(idx);
    }

    public int getEmpIn1stSize() {
        return (Integer)empIn1st.size();
    }    
    
    /**
     * @param empIn1st the empIn1st to set
     */
    public void addEmpIn1st( Date dtIN) {
        this.empIn1st.add(dtIN);
    }
    
    /**
     * @param empOut1st the empIn1st to set
     */
     public Date getEmpOut1st(int idx) {
        if(idx >=empOut1st.size())
            return null;
        return (Date)empOut1st.get(idx);
    }

    public int getEmpOut1stSize() {
        return (Integer)empOut1st.size();
    }    
    
    /**
     * @param empOut1st the empIn1st to set
     */
    public void addEmpOut1st( Date dtOut) {
        this.empOut1st.add(dtOut);
    }
    

        /**
     * @param empReason the empIn1st to set
     */
     public int getEmpReason1st(int idx) {
        if(idx >=empReason.size())
            return 0;
        return (Integer)empReason.get(idx);
    }

    public int getEmpReason1stSize() {
        return (Integer)empReason.size();
    }    
    
    /**
     * @param empReason the empIn1st to set
     */
    public void addEmpReason1st( int reason) {
        this.empReason.add(reason);
    }
    
     /**
     * @param empStatus the empIn1st to set
     */
     public int getEmpStatus1st(int idx) {
        if(idx >=empStatus.size())
            return 0;
        return (Integer)empStatus.get(idx);
    }

    public int getEmpStatus1stSize() {
        return (Integer)empStatus.size();
    }    
    
    /**
     * @param empStatus the empIn1st to set
     */
    public void addEmpStatus1st( int reason) {
        this.empStatus.add(reason);
    }
    
     /**
     * @return the dtIDate
     */
    public Date getDtIDate(int idx) {
        if(idx >=dtIDate.size())
            return null;
        
        return (Date)dtIDate.get(idx);
        //return dtIDate;
    }

    /**
     * @param dtIDate the dtIDate to set
     */
    public void addDtDate(Date dtDate) {
        this.dtIDate.add(dtDate);
       
    }
    

    /**
     * @return the totStatus
     */
    public int getTotStatus() {
        return totStatus;
    }

    /**
     * @param totStatus the totStatus to set
     */
    public void setTotStatus(int totStatus) {
        this.totStatus = totStatus;
    }

    /**
     * @return the totReason
     */
    public int getTotReason() {
        return totReason;
    }

    /**
     * @param totReason the totReason to set
     */
    public void setTotReason(int totReason) {
        this.totReason = totReason;
    }

   
}
