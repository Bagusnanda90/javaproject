/*
 * NightShiftWeekly.java
 *
 * Created on June 1, 2004, 3:36 PM
 */

package com.dimata.harisma.session.attendance;

import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class NightShiftWeekly {
    
    private String empNum;    
    private String empName;  
    private Vector empSchedules = new Vector(1,1);
    private Vector empIn = new Vector(1,1);
    private Vector empOut = new Vector(1,1);
    
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
        
}
