/*
 * NightShiftDaily.java
 *
 * Created on June 1, 2004, 3:36 PM
 */

package com.dimata.harisma.session.attendance;

import java.util.Date;


/**
 *
 * @author  gedhy
 */
public class NightShiftDaily {
    
    /** Holds value of property empNum. */
    private String empNum;    
    
    /** Holds value of property empName. */
    private String empName;
    
    /** Holds value of property schldSymbol. */
    private String schldSymbol;
    
    /** Holds value of property schldIn. */
    private Date schldIn;
    
    /** Holds value of property schldOut. */
    private Date schldOut;
    
    /** Holds value of property actualIn. */
    private Date actualIn;
    
    /** Holds value of property actualOut. */
    private Date actualOut;
    
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
    
    /** Getter for property schldSymbol.
     * @return Value of property schldSymbol.
     *
     */
    public String getSchldSymbol() {
        return this.schldSymbol;
    }
    
    /** Setter for property schldSymbol.
     * @param schldSymbol New value of property schldSymbol.
     *
     */
    public void setSchldSymbol(String schldSymbol) {
        this.schldSymbol = schldSymbol;
    }
    
    /** Getter for property schldIn.
     * @return Value of property schldIn.
     *
     */
    public Date getSchldIn() {
        return this.schldIn;
    }
    
    /** Setter for property schldIn.
     * @param schldIn New value of property schldIn.
     *
     */
    public void setSchldIn(Date schldIn) {
        this.schldIn = schldIn;
    }
    
    /** Getter for property schldOut.
     * @return Value of property schldOut.
     *
     */
    public Date getSchldOut() {
        return this.schldOut;
    }
    
    /** Setter for property schldOut.
     * @param schldOut New value of property schldOut.
     *
     */
    public void setSchldOut(Date schldOut) {
        this.schldOut = schldOut;
    }
    
    /** Getter for property actualIn.
     * @return Value of property actualIn.
     *
     */
    public Date getActualIn() {
        return this.actualIn;
    }
    
    /** Setter for property actualIn.
     * @param actualIn New value of property actualIn.
     *
     */
    public void setActualIn(Date actualIn) {
        this.actualIn = actualIn;
    }
    
    /** Getter for property actualOut.
     * @return Value of property actualOut.
     *
     */
    public Date getActualOut() {
        return this.actualOut;
    }
    
    /** Setter for property actualOut.
     * @param actualOut New value of property actualOut.
     *
     */
    public void setActualOut(Date actualOut) {
        this.actualOut = actualOut;
    }
    
}
