/*
 * DpStockTaken.java
 *
 * Created on December 22, 2004, 11:57 AM
 */

package com.dimata.harisma.entity.attendance;

// import core java package
import java.util.Date;

// package qdep
import com.dimata.qdep.entity.*;
import java.util.Vector;

/**
 *
 * @author  gedhy
 */
public class DpStockTaken extends Entity {
    
    /** Holds value of property dpStockTakenId. */
    private long dpStockTakenId;
    
    /** Holds value of property dpStockId. */
    private long dpStockId;
    
    /** Holds value of property takenDate. */
    private Date takenDate;
    
    /** Holds value of property takenQty. */
    private float takenQty;
    
    /** Holds value of property employeeId. */
    private long employeeId;
    
    /** Holds value of property paidDate. */
    private Date paidDate;
    
    private long leaveApplicationId;
    
    private Date takenFinnishDate ;
    
    private Vector check= new Vector();
    
    private float tmpWillBeTaken;
    
    private float tmpEligible;
    
    //tambahan by satrya 2013-01-11
    //untuk check dp balance
    private int docStatus; // di tambahkan guna DP balancing
    private Date subMissionDate; // di tambahkan guna DP balancing
    //update by satrya 2013-03-19
    //private int flagDpBalance=0;
    
    //update by satrya 2013-12-13
    private Vector vTakenDate = new Vector();
    private Vector vTakenFinishDate = new Vector();
    
    private int sizeTakenDate;
    private int sizeTakenDateFinish;
    
    private int flagFullSchedule;
    /** Creates a new instance of DpStockTaken */
    public DpStockTaken() {
    }
    
    /** Getter for property dpStockTakenId.
     * @return Value of property dpStockTakenId.
     *
     */
    public long getDpStockTakenId() {
        return this.dpStockTakenId;
    }
    
    /** Setter for property dpStockTakenId.
     * @param dpStockTakenId New value of property dpStockTakenId.
     *
     */
    public void setDpStockTakenId(long dpStockTakenId) {
        this.dpStockTakenId = dpStockTakenId;
    }
    
    /** Getter for property dpStockId.
     * @return Value of property dpStockId.
     *
     */
    public long getDpStockId() {
        return this.dpStockId;
    }
    
    /** Setter for property dpStockId.
     * @param dpStockId New value of property dpStockId.
     *
     */
    public void setDpStockId(long dpStockId) {
        this.dpStockId = dpStockId;
    }
    
    /** Getter for property takenDate.
     * @return Value of property takenDate.
     *
     */
    public Date getTakenDate() {
        return this.takenDate;
    }
    
    /** Setter for property takenDate.
     * @param takenDate New value of property takenDate.
     *
     */
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }
    
    /** Getter for property takenQty.
     * @return Value of property takenQty.
     *
     */
    public float getTakenQty() {
        return this.takenQty;
    }
    
    /** Setter for property takenQty.
     * @param takenQty New value of property takenQty.
     *
     */
    public void setTakenQty(float takenQty) {
        this.takenQty = takenQty;
    }
    
    /** Getter for property employeeId.
     * @return Value of property employeeId.
     *
     */
    public long getEmployeeId() {
        return this.employeeId;
    }
    
    /** Setter for property employeeId.
     * @param employeeId New value of property employeeId.
     *
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    /** Getter for property paidDate.
     * @return Value of property paidDate.
     *
     */
    public Date getPaidDate() {
        return this.paidDate;
    }
    
    /** Setter for property paidDate.
     * @param paidDate New value of property paidDate.
     *
     */
    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getTakenFinnishDate() {
        return takenFinnishDate;
    }

    public void setTakenFinnishDate(Date takenFinnishDate) {
        this.takenFinnishDate = takenFinnishDate;
    }

    public long getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public void setLeaveApplicationId(long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }

    /**
     * @return the check
     */
    public Vector getCheck() {
        return check;
    }

    /**
     * @param check the check to set
     */
    public void setCheck(Vector check) {
        this.check = check;
    }

    /**
     * @return the tmpWillBeTaken
     */
    public float getTmpWillBeTaken() {
        return tmpWillBeTaken;
    }

    /**
     * @param tmpWillBeTaken the tmpWillBeTaken to set
     */
    public void setTmpWillBeTaken(float tmpWillBeTaken) {
        this.tmpWillBeTaken = tmpWillBeTaken;
    }

    /**
     * @return the tmpEligible
     */
    public float getTmpEligible() {
        return tmpEligible;
    }

    /**
     * @param tmpEligible the tmpEligible to set
     */
    public void setTmpEligible(float tmpEligible) {
        this.tmpEligible = tmpEligible;
    }

    /**
     * @return the docStatus
     */
    public int getDocStatus() {
        return docStatus;
    }

    /**
     * @param docStatus the docStatus to set
     */
    public void setDocStatus(int docStatus) {
        this.docStatus = docStatus;
    }

    /**
     * @return the subMissionDate
     */
    public Date getSubMissionDate() {
        return subMissionDate;
    }

    /**
     * @param subMissionDate the subMissionDate to set
     */
    public void setSubMissionDate(Date subMissionDate) {
        this.subMissionDate = subMissionDate;
    }

    /**
     * @return the vTakenDate
     */
    public Date getvTakenDate(int idx) {
        Date dtTaken=null;
        if(vTakenDate!=null && vTakenDate.size()>0){
            dtTaken = (Date)vTakenDate.get(idx);
        }
        return dtTaken;
    }

    /**
     * @param vTakenDate the vTakenDate to set
     */
    public void addvTakenDate(Date dtTakenDate) {
        this.vTakenDate.add(dtTakenDate);
    }

    /**
     * @return the vTakenFinishDate
     */
    public Date getvTakenFinishDate(int idx) {
        Date dtTakenFinish=null;
        if(vTakenFinishDate!=null && vTakenFinishDate.size()>0){
            dtTakenFinish = (Date)vTakenFinishDate.get(idx);
        }
        return dtTakenFinish;
    }

    /**
     * @param vTakenFinishDate the vTakenFinishDate to set
     */
    public void addvTakenFinishDate(Date dtTakenFinishDate) {
        this.vTakenFinishDate.add(dtTakenFinishDate);
    }

    /**
     * @return the sizeTakenDate
     */
    public int getSizeTakenDate() {
        return vTakenDate==null?0:vTakenDate.size();
    }

    /**
     * @return the sizeTakenDateFinish
     */
    public int getSizeTakenDateFinish() {
        return vTakenFinishDate==null?0:vTakenFinishDate.size();
    }

    /**
     * @return the flagFullSchedule
     */
    public int getFlagFullSchedule() {
        return flagFullSchedule;
    }

    /**
     * @param flagFullSchedule the flagFullSchedule to set
     */
    public void setFlagFullSchedule(int flagFullSchedule) {
        this.flagFullSchedule = flagFullSchedule;
    }


    
}
