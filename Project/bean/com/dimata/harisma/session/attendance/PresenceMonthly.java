/*
 * PresenceMonthly.java
 *
 * Created on June 1, 2004, 3:37 PM
 */

package com.dimata.harisma.session.attendance;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author  gedhy 
 */
public class PresenceMonthly {
    
    private String empNum;    
    private String empName;  
    private Vector empSchedules = new Vector(1,1);
    private Vector empIn = new Vector(1,1);
    private Vector empOut = new Vector(1,1);
    private Vector empSchedules2 = new Vector(1,1);
    private Vector empIn2 = new Vector(1,1);
    private Vector empOut2 = new Vector(1,1);
     private long employeId;
    private Vector dayIdx = new Vector();

    public Vector getEmpIn2() {
        return empIn2;
    }

    public void setEmpIn2(Vector empIn2) {
        this.empIn2 = empIn2;
    }

    public Vector getEmpOut2() {
        return empOut2;
    }

    public void setEmpOut2(Vector empOut2) {
        this.empOut2 = empOut2;
    }

    public Vector getEmpSchedules2() {
        return empSchedules2;
    }

    public void setEmpSchedules2(Vector empSchedules2) {
        this.empSchedules2 = empSchedules2;
    }
    
    
    
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
    
    /** Getter for property empSchedules.
     * @return Value of property empSchedules.
     *
     */
    public Vector getEmpSchedules() {
        return this.empSchedules;
    }
    
    /** Setter for property empSchedules.
     * @param empSchedules New value of property empSchedules.
     *
     */
    public void setEmpSchedules(Vector empSchedules) {
        this.empSchedules = empSchedules;
    }
    
    /** Getter for property empIn.
     * @return Value of property empIn.
     *
     */
    public Vector getEmpIn() {
        return this.empIn;
    }
    
    /** Setter for property empIn.
     * @param empIn New value of property empIn.
     *
     */
    public void setEmpIn(Vector empIn) {
        this.empIn = empIn;
    }
    
    /** Getter for property empOut.
     * @return Value of property empOut.
     *
     */
    public Vector getEmpOut() {
        return this.empOut;
    }
    
    /** Setter for property empOut.
     * @param empOut New value of property empOut.
     *
     */
    public void setEmpOut(Vector empOut) {
        this.empOut = empOut;
    }
    
    /**
     * @return the dayIdx
     */
    public Date getDayIdx(int idx) {
      Date dt = null;
        if(this.dayIdx !=null && this.dayIdx.size()>0){
             dt=(Date)this.dayIdx.get(idx);
}
       
        return dt;
    }

    /**
     * @param dayIdx the dayIdx to set
     */
    public void setDayIdx(Date dayIdx) {
       this.dayIdx.add(dayIdx);
    }

    /**
     * @return the employeId
     */
    public long getEmployeId() {
        return employeId;
    }

    /**
     * @param employeId the employeId to set
     */
    public void setEmployeId(long employeId) {
        this.employeId = employeId;
    }
    
}
