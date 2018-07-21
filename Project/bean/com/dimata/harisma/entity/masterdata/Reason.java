/*
 * reason.java
 *
 * Created on June 20, 2007, 4:20 PM
 */

package com.dimata.harisma.entity.masterdata;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class Reason extends Entity{
        private int no = 0;
        private String reason = "";
        private String description = "";
        private long scheduleId = 0;
        //update by satrya 2012-10-19
        private String kodeReason="";
       //update by satrya 2013-02-03
        private int timeReason=0;
        
        //update by satrya 2014-04-21
        private int flagShowInPayInput;
        private int numberOfShow = 0;
        private int countAble = 0;
    
    /**
     * Getter for property reason.
     * @return Value of property reason.
     */
    public String getReason() {
        return reason;
    }
    
    /**
     * Setter for property reason.
     * @param reason New value of property reason.
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Getter for property no.
     * @return Value of property no.
     */
    public int getNo() {
        return no;
    }
    
    
    /**
     * Setter for property no.
     * @param no New value of property no.
     */
    public void setNo(int no) {
        this.no = no;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return the kodeReason
     */
    public String getKodeReason() {
        return kodeReason;
    }

    /**
     * @param kodeReason the kodeReason to set
     */
    public void setKodeReason(String kodeReason) {
        this.kodeReason = kodeReason;
    }

    /**
     * @return the timeReason
     */
    public int getTimeReason() {
        return timeReason;
    }

    /**
     * @param timeReason the timeReason to set
     */
    public void setTimeReason(int timeReason) {
        this.timeReason = timeReason;
    }

    /**
     * @return the flagShowInPayInput
     */
    public int getFlagShowInPayInput() {
        return flagShowInPayInput;
    }

    /**
     * @param flagShowInPayInput the flagShowInPayInput to set
     */
    public void setFlagShowInPayInput(int flagShowInPayInput) {
        this.flagShowInPayInput = flagShowInPayInput;
    }

    /**
     * @return the numberOfShow
     */
    public int getNumberOfShow() {
        return numberOfShow;
    }

    /**
     * @param numberOfShow the numberOfShow to set
     */
    public void setNumberOfShow(int numberOfShow) {
        this.numberOfShow = numberOfShow;
    }

    /**
     * @return the countAble
     */
    public int getCountAble() {
        return countAble;
    }

    /**
     * @param countAble the countAble to set
     */
    public void setCountAble(int countAble) {
        this.countAble = countAble;
    }
    
}
