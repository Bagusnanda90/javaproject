/*
 * SalaryLevel.java
 *
 * Created on April 2, 2007, 11:53 PM
 */

package com.dimata.harisma.entity.payroll;

/* package qdep */
import com.dimata.qdep.entity.*;
import java.util.Vector;

/**
 *
 * @author  Ana
 */
public class SalaryLevel extends Entity {
    
    private String levelCode = "";
    private int sort_Idx;
    private String levelName = "";
    private int amountIs = 0;
    private String cur_Code = "";
    private Vector<SalaryLevelDetail> salDetails = new Vector();
    /* Hendra Putu | 2015-03-17*/
    private int salaryLevelStatus = 0;
    private long levelAssign = 0;
    /* Hendra Putu | 2015-03-20 (hari h sblum Nyepi, for support Dian in Hotel Borobudur) */
    private String salaryLevelNote = "";
    /**
     * Getter for property levelCode.
     *
     * @return Value of property levelCode.
     */
    public java.lang.String getLevelCode() {
        return levelCode;
    }    
    
    /**
     * Setter for property levelCode.
     *
     * @param levelCode New value of property levelCode.
     */
    public void setLevelCode(java.lang.String levelCode) {
        this.levelCode = levelCode;
    }    
    
   /**
     * Getter for property sort_Idx.
     *
     * @return Value of property sort_Idx.
     */
    public int getSort_Idx() {
        return sort_Idx;
    }
    
    /**
     * Setter for property sort_Idx.
     *
     * @param sort_Idx New value of property sort_Idx.
     */
    public void setSort_Idx(int sort_Idx) {
        this.sort_Idx = sort_Idx;
    }
    
    /**
     * Getter for property levelName.
     *
     * @return Value of property levelName.
     */
    public java.lang.String getLevelName() {
        return levelName;
    }
    
    /**
     * Setter for property levelName.
     *
     * @param levelName New value of property levelName.
     */
    public void setLevelName(java.lang.String levelName) {
        this.levelName = levelName;
    }
    
    /**
     * Getter for property amountIs.
     *
     * @return Value of property amountIs.
     */
    public int getAmountIs() {
        return amountIs;
    }
    
    /**
     * Setter for property amountIs.
     *
     * @param amountIs New value of property amountIs.
     */
    public void setAmountIs(int amountIs) {
        this.amountIs = amountIs;
    }
    
    /**
     * Getter for property cur_Code.
     *
     * @return Value of property cur_Code.
     */
    public java.lang.String getCur_Code() {
        return cur_Code;
    }
    
    /**
     * Setter for property cur_Code.
     *
     * @param cur_Code New value of property cur_Code.
     */
    public void setCur_Code(java.lang.String cur_Code) {
        this.cur_Code = cur_Code;
    }

    /**
     * @return the salDetails
     */
    public Vector<SalaryLevelDetail> getSalDetails() {
        return salDetails;
    }

    /**
     * @param salDetails the salDetails to set
     */
    public void addSalDetails(SalaryLevelDetail salDetail) {
        if (salDetail == null) {
            return;
        }
        if (this.salDetails == null) {
            this.salDetails = new Vector();
        }
        this.salDetails.add(salDetail);
    }
    
    public SalaryLevelDetail getSalDetails(int idx) {
        if (this.salDetails == null) {
            return null;       
        }
        if (this.salDetails.size() > idx && idx >= 0) {
            return this.salDetails.get(idx);
        } else {
            return null;
        }        
    }

    public int getSalDetailsSize() {
        if (this.salDetails == null) {
            return 0;               
        }
        return this.salDetails.size();
    }
    
    /**
     * @return the salaryLevelStatus
     */
    public int getSalaryLevelStatus() {
        return salaryLevelStatus;
}

    /**
     * @param salaryLevelStatus the salaryLevelStatus to set
     */
    public void setSalaryLevelStatus(int salaryLevelStatus) {
        this.salaryLevelStatus = salaryLevelStatus;
    }

    /**
     * @return the levelAssign
     */
    public long getLevelAssign() {
        return levelAssign;
    }

    /**
     * @param levelAssign the levelAssign to set
     */
    public void setLevelAssign(long levelAssign) {
        this.levelAssign = levelAssign;
    }

    /**
     * @return the salaryLevelNote
     */
    public String getSalaryLevelNote() {
        return salaryLevelNote;
}

    /**
     * @param salaryLevelNote the salaryLevelNote to set
     */
    public void setSalaryLevelNote(String salaryLevelNote) {
        this.salaryLevelNote = salaryLevelNote;
    }
}
