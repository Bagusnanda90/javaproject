/*
 * AbsenteeismDaily.java
 *
 * Created on June 7, 2004, 12:03 PM
 */

package com.dimata.harisma.session.absenteeism;   

import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class AbsenteeismDaily {  
    
    private String empNum;    
    private String empName;
    private String schldSymbol;
    private Date schldIn;
    private Date schldOut;
    //update by satrya 2012-10-19
   private Date selectedDate;
    private String note1st;
    private String note2nd;
     private int noReason1st;
     private int noReason2nd;
     
     private String reasonCode;
    /** Holds value of property remark. */
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

    /** Getter for property remark.
     * @return Value of property remark.
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

    
    /**
     * @return the note1st
     */
    public String getNote1st() {
        return note1st;
    }

    /**
     * @param note1st the note1st to set
     */
    public void setNote1st(String note1st) {
        this.note1st = note1st;
    }

    /**
     * @return the noReason1st
     */
    public int getNoReason1st() {
        return noReason1st;
    }

    /**
     * @param noReason1st the noReason1st to set
     */
    public void setNoReason1st(int noReason1st) {
        this.noReason1st = noReason1st;
    }

    /**
     * @return the note2nd
     */
    public String getNote2nd() {
        return note2nd;
    }

    /**
     * @param note2nd the note2nd to set
     */
    public void setNote2nd(String note2nd) {
        this.note2nd = note2nd;
    }

    /**
     * @return the noReason2nd
     */
    public int getNoReason2nd() {
        return noReason2nd;
    }

    /**
     * @param noReason2nd the noReason2nd to set
     */
    public void setNoReason2nd(int noReason2nd) {
        this.noReason2nd = noReason2nd;
    }

    /**
     * @return the reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * @param reasonCode the reasonCode to set
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * @return the selectedDate
     */
    public Date getSelectedDate() {
        return selectedDate;
    }

    /**
     * @param selectedDate the selectedDate to set
     */
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }
    
}
