/*
 * SpecialDispWeekly.java
 *
 * Created on June 17, 2004, 6:27 PM
 */

package com.dimata.harisma.session.specialdisp;

import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class SpecialDispWeekly {
    
    private String empNum;    
    private String empName;  
    private Vector empSchedules = new Vector(1,1);
    private Vector absStatus = new Vector(1,1);
    private Vector absReason = new Vector(1,1);
    
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
    
    /** Getter for property absStatus.
     * @return Value of property absStatus.
     *
     */
    public Vector getAbsStatus() {
        return this.absStatus;
    }
    
    /** Setter for property absStatus.
     * @param absStatus New value of property absStatus.
     *
     */
    public void setAbsStatus(Vector absStatus) {
        this.absStatus = absStatus;
    }
    
    /** Getter for property absReason.
     * @return Value of property absReason.
     *
     */
    public Vector getAbsReason() {
        return this.absReason;
    }
    
    /** Setter for property absReason.
     * @param absReason New value of property absReason.
     *
     */
    public void setAbsReason(Vector absReason) {
        this.absReason = absReason;
    }
    
}
