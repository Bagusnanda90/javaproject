/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.overtime;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class OvertimeTblExis {
    //private long employeeId;
    private long overtimeDetailId;
    private Vector vStartDate = new Vector();
    private Vector vEndDate=new Vector();
    private Vector vRestStart = new Vector();
    private Vector vRestEnd = new Vector();
    private double restTimeHr=0;
    /**
     * @return the startDate
     */
    public Date getStartDate(int idx) {
        //return startDate;
         if(idx >=vStartDate.size())
            return null;
        
        return (Date)vStartDate.get(idx); 
    }
    public int sizeStartDate(){
        if(vStartDate==null){
            return 0;
        }
        return vStartDate.size();
    }

    /**
     * @param startDate the startDate to set
     */
    public void addStartDate(Date startDate) {
        //this.startDate = startDate;
           this.vStartDate.add(startDate);
    }

    public int sizeEndDate(){
        if(vEndDate==null){
            return 0;
        }
        return vEndDate.size();
    }
    /**
     * @return the endDate
     */
    public Date getEndDate(int idx) {
       // return endDate;
        if(idx >=vEndDate.size())
            return null;
        
        return (Date)vEndDate.get(idx); 
    }

    /**
     * @param endDate the endDate to set
     */
    public void addEndDate(Date endDate) {
        this.vEndDate.add(endDate);
    }

    /**
     * @return the overtimeDetailId
     */
    public long getOvertimeDetailId() {
        return overtimeDetailId;
    }

    /**
     * @param overtimeDetailId the overtimeDetailId to set
     */
    public void setOvertimeDetailId(long overtimeDetailId) {
        this.overtimeDetailId = overtimeDetailId;
    }

    /**
     * @return the vRestStart
     */
    public Date getRestStart(int idx) {
        //return vRestStart;
         if(idx >=vRestStart.size())
            return null;
        
        return (Date)vRestStart.get(idx); 
    }

    /**
     * @param vRestStart the vRestStart to set
     */
    public void addRestStart(Date vRestStart) {
        //this.vRestStart = vRestStart;
         this.vRestStart.add(vRestStart);
    }
    public int sizeRestStart(){
        if(vRestStart==null){
            return 0;
        }
        return vRestStart.size();
    }
    /**
     * @return the vRestEnd
     */
    public Date getRestEnd(int idx) {
        //return vRestEnd;
          if(idx >=vRestEnd.size())
            return null;
        
        return (Date)vRestEnd.get(idx); 
    }

    /**
     * @param vRestEnd the vRestEnd to set
     */
    public void addRestEnd(Date RestEnd) {
        //this.vRestEnd = vRestEnd;
        Date restEnd = null;
       if(RestEnd!=null && this.restTimeHr > 0.0001 ){
           restEnd = new Date(RestEnd.getTime() + (long)(this.restTimeHr * 3600000D));
       }
       
       //return restEnd;
          this.vRestEnd.add(restEnd);
    }
     public int sizeRestEnd(){
        if(vRestEnd==null){
            return 0;
        }
        return vRestEnd.size();
    }

    /**
     * @return the restTimeHr
     */
    public double getRestTimeHr() {
        return restTimeHr;
    }

    /**
     * @param restTimeHr the restTimeHr to set
     */
    public void setRestTimeHr(double restTimeHr) {
        this.restTimeHr = restTimeHr;
    }
}
