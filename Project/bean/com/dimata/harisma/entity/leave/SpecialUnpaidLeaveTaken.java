/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
/**
 *
 * @author Tu Roy
 */
public class SpecialUnpaidLeaveTaken extends Entity{
    
    private long leaveApplicationId = 0;
    private long scheduledId = 0;
    private long employeeId = 0;
    private Date takenDate;
    private float takenQty = 0;
    private int takenStatus = 0;
    private int takenFromStatus = 0;
    private Date takenFinnishDate;
    //update by satrya 2012-12-23
    private float totTakenQty;
    private String symbole;
   

    public long getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public void setLeaveApplicationId(long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }


    public long getScheduledId() {
        return scheduledId;
    }

    public void setScheduledId(long scheduledId) {
        this.scheduledId = scheduledId;
    }


    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }


    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
        //calculateQty();
    }


    public float getTakenQty() {
        return takenQty;
    }

    public void setTakenQty(float takenQty) {        
        //if(takenQty<1)
        //    return;
        this.takenQty = takenQty;
        /*if(takenDate!=null ){
            long fnshDateMs = takenDate.getTime() + (long) (24*60*60*1000*(this.takenQty-1));
            this.takenFinnishDate = new Date(fnshDateMs);
        } else {
        if(takenFinnishDate!=null ){
            long takenDateMs = takenFinnishDate.getTime() - (long) (24*60*60*1000*(this.takenQty-1));
            this.takenDate = new Date(takenDateMs);                                    
         }
        } */               
    }


    public int getTakenStatus() {
        return takenStatus;
    }

    public void setTakenStatus(int takenStatus) {
        this.takenStatus = takenStatus;
    }


    public int getTakenFromStatus() {
        return takenFromStatus;
    }

    public void setTakenFromStatus(int takenFromStatus) {
        this.takenFromStatus = takenFromStatus;
    }

    public Date getTakenFinnishDate() {
        return takenFinnishDate;
    }

    public void setTakenFinnishDate(Date takenFinnishDate) {
        this.takenFinnishDate = takenFinnishDate;
        //calculateQty();
    }
    
    private void calculateQty(){
        if(takenFinnishDate!=null && takenDate!=null ){
            
            float tkQty = (float)((takenFinnishDate.getTime()/(24L*60L*60L*1000L)) - (takenDate.getTime()/(24L*60L*60L*1000L)));
                         
            takenQty=tkQty+1;  
        }
    }

    /**
     * @return the totTakenQty
     */
    public float getTotTakenQty() {
        return totTakenQty;
    }

    /**
     * @param totTakenQty the totTakenQty to set
     */
    public void setTotTakenQty(float totTakenQty) {
        this.totTakenQty = totTakenQty;
    }

    /**
     * @return the symbole
     */
    public String getSymbole() {
        return symbole;
    }

    /**
     * @param symbole the symbole to set
     */
    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

}
