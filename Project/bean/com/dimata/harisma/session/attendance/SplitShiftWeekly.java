/*
 * SplitShiftWeekly.java
 *
 * Created on June 1, 2004, 3:37 PM
 */

package com.dimata.harisma.session.attendance;

import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class SplitShiftWeekly {   
    
    private String empNum;    
    private String empName;  
    private Vector empSchedules1st = new Vector(1,1);
    private Vector empIn1st = new Vector(1,1);
    private Vector empOut1st = new Vector(1,1);
    private Vector empSchedules2nd = new Vector(1,1);
    private Vector empIn2nd = new Vector(1,1);
    private Vector empOut2nd = new Vector(1,1);    
    
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
    
    /** Getter for property empSchedules1st.
     * @return Value of property empSchedules1st.
     *
     */
    public Vector getEmpSchedules1st() {
        return this.empSchedules1st;
    }
    
    /** Setter for property empSchedules1st.
     * @param empSchedules1st New value of property empSchedules1st.
     *
     */
    public void setEmpSchedules1st(Vector empSchedules1st) {
        this.empSchedules1st = empSchedules1st;
    }
    
    /** Getter for property empIn1st.
     * @return Value of property empIn1st.
     *
     */
    public Vector getEmpIn1st() {
        return this.empIn1st;
    }
    
    /** Setter for property empIn1st.
     * @param empIn1st New value of property empIn1st.
     *
     */
    public void setEmpIn1st(Vector empIn1st) {
        this.empIn1st = empIn1st;
    }
    
    /** Getter for property empOut1st.
     * @return Value of property empOut1st.
     *
     */
    public Vector getEmpOut1st() {
        return this.empOut1st;
    }
    
    /** Setter for property empOut1st.
     * @param empOut1st New value of property empOut1st.
     *
     */
    public void setEmpOut1st(Vector empOut1st) {
        this.empOut1st = empOut1st;
    }

    /** Getter for property empSchedules2nd.
     * @return Value of property empSchedules2nd.
     *
     */
    public Vector getEmpSchedules2nd() {
        return this.empSchedules2nd;
    }
    
    /** Setter for property empSchedules2nd.
     * @param empSchedules2nd New value of property empSchedules2nd.
     *
     */
    public void setEmpSchedules2nd(Vector empSchedules2nd) {
        this.empSchedules2nd = empSchedules2nd;
    }
    
    /** Getter for property empIn2nd.
     * @return Value of property empIn2nd.
     *
     */
    public Vector getEmpIn2nd() {
        return this.empIn2nd;
    }
    
    /** Setter for property empIn2nd.
     * @param empIn2nd New value of property empIn2nd.
     *
     */
    public void setEmpIn2nd(Vector empIn2nd) {
        this.empIn2nd = empIn2nd;
    }
    
    /** Getter for property empOut2nd.
     * @return Value of property empOut2nd.
     *
     */
    public Vector getEmpOut2nd() {
        return this.empOut2nd;
    }
    
    /** Setter for property empOut2nd.
     * @param empOut2nd New value of property empOut2nd.
     *
     */
    public void setEmpOut2nd(Vector empOut2nd) {
        this.empOut2nd = empOut2nd;
    }
    
}
