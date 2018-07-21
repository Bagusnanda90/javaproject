/*
 * EarlyWeekly.java
 *
 * Created on December 26, 2007, 1:56 PM
 */

package com.dimata.harisma.session.early;

import java.util.Vector;

/**
 *
 * @author  Yunny
 */
public class EarlyWeekly {
    private String empNum;    
    private String empName;  
    private Vector empSchedules1st = new Vector(1,1);
    private Vector empIn1st = new Vector(1,1);
    private Vector empOut1st = new Vector(1,1);
    private Vector empSchedules2nd = new Vector(1,1);
    private Vector empIn2nd = new Vector(1,1);
    private Vector empOut2nd = new Vector(1,1);   
    /** Creates a new instance of EarlyWeekly */
    public EarlyWeekly() {
    }
    
    /**
     * Getter for property empNum.
     * @return Value of property empNum.
     */
    public java.lang.String getEmpNum() {
        return empNum;
    }
    
    /**
     * Setter for property empNum.
     * @param empNum New value of property empNum.
     */
    public void setEmpNum(java.lang.String empNum) {
        this.empNum = empNum;
    }
    
    /**
     * Getter for property empName.
     * @return Value of property empName.
     */
    public java.lang.String getEmpName() {
        return empName;
    }
    
    /**
     * Setter for property empName.
     * @param empName New value of property empName.
     */
    public void setEmpName(java.lang.String empName) {
        this.empName = empName;
    }
    
    /**
     * Getter for property empSchedules1st.
     * @return Value of property empSchedules1st.
     */
    public java.util.Vector getEmpSchedules1st() {
        return empSchedules1st;
    }
    
    /**
     * Setter for property empSchedules1st.
     * @param empSchedules1st New value of property empSchedules1st.
     */
    public void setEmpSchedules1st(java.util.Vector empSchedules1st) {
        this.empSchedules1st = empSchedules1st;
    }
    
    /**
     * Getter for property empIn1st.
     * @return Value of property empIn1st.
     */
    public java.util.Vector getEmpIn1st() {
        return empIn1st;
    }
    
    /**
     * Setter for property empIn1st.
     * @param empIn1st New value of property empIn1st.
     */
    public void setEmpIn1st(java.util.Vector empIn1st) {
        this.empIn1st = empIn1st;
    }
    
    /**
     * Getter for property empOut1st.
     * @return Value of property empOut1st.
     */
    public java.util.Vector getEmpOut1st() {
        return empOut1st;
    }
    
    /**
     * Setter for property empOut1st.
     * @param empOut1st New value of property empOut1st.
     */
    public void setEmpOut1st(java.util.Vector empOut1st) {
        this.empOut1st = empOut1st;
    }
    
    /**
     * Getter for property empSchedules2nd.
     * @return Value of property empSchedules2nd.
     */
    public java.util.Vector getEmpSchedules2nd() {
        return empSchedules2nd;
    }
    
    /**
     * Setter for property empSchedules2nd.
     * @param empSchedules2nd New value of property empSchedules2nd.
     */
    public void setEmpSchedules2nd(java.util.Vector empSchedules2nd) {
        this.empSchedules2nd = empSchedules2nd;
    }
    
    /**
     * Getter for property empIn2nd.
     * @return Value of property empIn2nd.
     */
    public java.util.Vector getEmpIn2nd() {
        return empIn2nd;
    }
    
    /**
     * Setter for property empIn2nd.
     * @param empIn2nd New value of property empIn2nd.
     */
    public void setEmpIn2nd(java.util.Vector empIn2nd) {
        this.empIn2nd = empIn2nd;
    }
    
    /**
     * Getter for property empOut2nd.
     * @return Value of property empOut2nd.
     */
    public java.util.Vector getEmpOut2nd() {
        return empOut2nd;
    }
    
    /**
     * Setter for property empOut2nd.
     * @param empOut2nd New value of property empOut2nd.
     */
    public void setEmpOut2nd(java.util.Vector empOut2nd) {
        this.empOut2nd = empOut2nd;
    }
    
}
