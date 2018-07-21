/*
 * Ovt_Type.java
 *
 * Created on April 11, 2007, 4:04 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import java.util.Date;
import java.util.Vector;

/* package qdep */
import com.dimata.qdep.entity.*;


/**
 *
 * @author  emerliana
 */
public class Ovt_Type extends Entity {        
    private String ovt_Type_Code = "";
    private int type_of_day = 0;
    private String description = "";
    private int owrite_by_schdl;
    private Date std_work_hour_begin = new Date();
    private Date std_work_hour_end = new Date();
    private int empLevelMin=0; // minimum level of employee level using this ov type
    private int empLevelMax=0; // maximum level of employee level using this ov type
    private Vector<Ovt_Idx> ovIndex= new Vector();
    
    private long masterLevelMin =0;
    private long masterLevelMax =0;
    /**
     * Getter for property ovt_Type_Code.
     * @return Value of property ovt_Type_Code.
     */
    public java.lang.String getOvt_Type_Code() {
        return ovt_Type_Code;
    }    
    
    /**
     * Setter for property ovt_Type_Code.
     * @param ovt_Type_Code New value of property ovt_Type_Code.
     */
    public void setOvt_Type_Code(java.lang.String ovt_Type_Code) {
        this.ovt_Type_Code = ovt_Type_Code;
    }
    
    /**
     * Getter for property type_of_day.
     * @return Value of property type_of_day.
     */
    public int getType_of_day() {
        return type_of_day;
    }
    
    /**
     * Setter for property type_of_day.
     * @param type_of_day New value of property type_of_day.
     */
    public void setType_of_day(int type_of_day) {
        this.type_of_day = type_of_day;
    }
    
    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return description;
    }
    
    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
     /**
     * Getter for property owrite_by_schdl.
     * @return Value of property owrite_by_schdl.
     */
    public int getOwrite_by_schdl() {
        return owrite_by_schdl;
    }
    
    /**
     * Setter for property owrite_by_schdl.
     * @param owrite_by_schdl New value of property owrite_by_schdl.
     */
    public void setOwrite_by_schdl(int owrite_by_schdl) {
        this.owrite_by_schdl = owrite_by_schdl;
    }
    
    /**
     * Getter for property std_work_hour_begin.
     * @return Value of property std_work_hour_begin.
     */
    public java.util.Date getStd_work_hour_begin() {
        return std_work_hour_begin;
    }    
  
    /**
     * Setter for property std_work_hour_begin.
     * @param std_work_hour_begin New value of property std_work_hour_begin.
     */
    public void setStd_work_hour_begin(java.util.Date std_work_hour_begin) {
        this.std_work_hour_begin = std_work_hour_begin;
    }    
    
    /**
     * Getter for property std_work_hour_end.
     * @return Value of property std_work_hour_end.
     */
    public java.util.Date getStd_work_hour_end() {
        return std_work_hour_end;
    }
    
    /**
     * Setter for property std_work_hour_end.
     * @param std_work_hour_end New value of property std_work_hour_end.
     */
    public void setStd_work_hour_end(java.util.Date std_work_hour_end) {
        this.std_work_hour_end = std_work_hour_end;
    }

    /**
     * @return the empLevelMin
     */
    public int getEmpLevelMin() {
        return empLevelMin;
    }

    /**
     * @param empLevelMin the empLevelMin to set
     */
    public void setEmpLevelMin(int empLevelMin) {
        this.empLevelMin = empLevelMin;
    }

    /**
     * @return the empLevelMax
     */
    public int getEmpLevelMax() {
        return empLevelMax;
    }

    /**
     * @param empLevelMax the empLevelMax to set
     */
    public void setEmpLevelMax(int empLevelMax) {
        this.empLevelMax = empLevelMax;
    }

    /**
     * @return the ovIndex
     */
    public Vector<Ovt_Idx> getOvIndex() {
        return ovIndex;
    }

    /**
     * @param ovIndex the ovIndex to set
     */
    public void setOvIndex(Vector<Ovt_Idx> ovIndex) {
        this.ovIndex = ovIndex;
    }        

    /**
     * @return the masterLevelMin
     */
    public long getMasterLevelMin() {
        return masterLevelMin;
    }

    /**
     * @param masterLevelMin the masterLevelMin to set
     */
    public void setMasterLevelMin(long masterLevelMin) {
        this.masterLevelMin = masterLevelMin;
    }

    /**
     * @return the masterLevelMax
     */
    public long getMasterLevelMax() {
        return masterLevelMax;
    }

    /**
     * @param masterLevelMax the masterLevelMax to set
     */
    public void setMasterLevelMax(long masterLevelMax) {
        this.masterLevelMax = masterLevelMax;
    }
    
}
