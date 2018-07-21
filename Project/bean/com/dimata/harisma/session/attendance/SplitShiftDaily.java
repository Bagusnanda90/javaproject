/*
 * SplitShiftDaily.java
 *
 * Created on May 27, 2004, 12:28 PM
 */

package com.dimata.harisma.session.attendance;

import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class SplitShiftDaily {
    
    private String empNum;    
    private String empFullName;
    private Date scheduleIn1st;
    private Date scheduleIn2nd;
    private Date scheduleOut1st;
    private Date scheduleOut2nd;
    private String scheduleSymbol1;
    private String scheduleSymbol2;
    private Date actualIn1st;
    private Date actualIn2nd;
    private Date actualOut1st;
    private Date actualOut2nd;
    
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
    
    /** Getter for property empFullName.
     * @return Value of property empFullName.
     *
     */
    public String getEmpFullName() {
        return this.empFullName;
    }
    
    /** Setter for property empFullName.
     * @param empFullName New value of property empFullName.
     *
     */
    public void setEmpFullName(String empFullName) {
        this.empFullName = empFullName;
    }
    
    /** Getter for property in1st.
     * @return Value of property in1st.
     *
     */
    public Date getScheduleIn1st() {
        return this.scheduleIn1st;
    }
    
    /** Setter for property in1st.
     * @param in1st New value of property in1st.
     *
     */
    public void setScheduleIn1st(Date scheduleIn1st) {
        this.scheduleIn1st = scheduleIn1st;
    }
    
    /** Getter for property in2nd.
     * @return Value of property in2nd.
     *
     */
    public Date getScheduleIn2nd() {
        return this.scheduleIn2nd;
    }
    
    /** Setter for property in2nd.
     * @param in2nd New value of property in2nd.
     *
     */
    public void setScheduleIn2nd(Date scheduleIn2nd) {
        this.scheduleIn2nd = scheduleIn2nd;
    }
    
    /** Getter for property out1st.
     * @return Value of property out1st.
     *
     */
    public Date getScheduleOut1st() {
        return this.scheduleOut1st;
    }
    
    /** Setter for property out1st.
     * @param out1st New value of property out1st.
     *
     */
    public void setScheduleOut1st(Date scheduleOut1st) {
        this.scheduleOut1st = scheduleOut1st;
    }
    
    /** Getter for property out2nd.
     * @return Value of property out2nd.
     *
     */
    public Date getScheduleOut2nd() {
        return this.scheduleOut2nd;
    }
    
    /** Setter for property out2nd.
     * @param out2nd New value of property out2nd.
     *
     */
    public void setScheduleOut2nd(Date scheduleOut2nd) {
        this.scheduleOut2nd = scheduleOut2nd;
    }
    
    /** Getter for property symbol1.
     * @return Value of property symbol1.
     *
     */
    public String getScheduleSymbol1() {
        return this.scheduleSymbol1;
    }
    
    /** Setter for property symbol1.
     * @param symbol1 New value of property symbol1.
     *
     */
    public void setScheduleSymbol1(String scheduleSymbol1) {
        this.scheduleSymbol1 = scheduleSymbol1;
    }
    
    /** Getter for property symbol2.
     * @return Value of property symbol2.
     *
     */
    public String getScheduleSymbol2() {
        return this.scheduleSymbol2;
    }
    
    /** Setter for property symbol2.
     * @param symbol2 New value of property symbol2.
     *
     */
    public void setScheduleSymbol2(String scheduleSymbol2) {
        this.scheduleSymbol2 = scheduleSymbol2;
    }
    
    /** Getter for property actualIn1st.
     * @return Value of property actualIn1st.
     *
     */
    public Date getActualIn1st() {
        return this.actualIn1st;
    }
    
    /** Setter for property actualIn1st.
     * @param actualIn1st New value of property actualIn1st.
     *
     */
    public void setActualIn1st(Date actualIn1st) {
        this.actualIn1st = actualIn1st;
    }
    
    /** Getter for property actualIn2nd.
     * @return Value of property actualIn2nd.
     *
     */
    public Date getActualIn2nd() {
        return this.actualIn2nd;
    }
    
    /** Setter for property actualIn2nd.
     * @param actualIn2nd New value of property actualIn2nd.
     *
     */
    public void setActualIn2nd(Date actualIn2nd) {
        this.actualIn2nd = actualIn2nd;
    }
    
    /** Getter for property actualOut1st.
     * @return Value of property actualOut1st.
     *
     */
    public Date getActualOut1st() {
        return this.actualOut1st;
    }
    
    /** Setter for property actualOut1st.
     * @param actualOut1st New value of property actualOut1st.
     *
     */
    public void setActualOut1st(Date actualOut1st) {
        this.actualOut1st = actualOut1st;
    }
    
    /** Getter for property actualOut2nd.
     * @return Value of property actualOut2nd.
     *
     */
    public Date getActualOut2nd() {
        return this.actualOut2nd;
    }
    
    /** Setter for property actualOut2nd.
     * @param actualOut2nd New value of property actualOut2nd.
     *
     */
    public void setActualOut2nd(Date actualOut2nd) {
        this.actualOut2nd = actualOut2nd;
    }
    
}
