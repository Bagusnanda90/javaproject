/*
 * SpecialDispDaily.java
 *
 * Created on June 17, 2004, 6:26 PM
 */

package com.dimata.harisma.session.specialdisp;

import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class SpecialDispDaily {
    
    private String empNum;    
    private String empName;
    private String schldSymbol;
    private Date schldIn;
    private Date schldOut;
    private String remark;
    
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
    
    /** Getter for property Remark.
     * @return Value of property Remark.
     *
     */
    public String getRemark() {
        return this.remark;
    }
    
    /** Setter for property remark.
     * @param remark New value of property remark.
     *
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }    
    
}
