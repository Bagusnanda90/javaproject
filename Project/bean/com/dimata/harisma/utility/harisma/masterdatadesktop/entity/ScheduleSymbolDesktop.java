/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.masterdatadesktop.entity;

import com.dimata.qdep.entity.Entity;
import com.dimata.util.Formater;
import java.util.Date;

/**
 *
 * @author Dimata 007
 */
public class ScheduleSymbolDesktop extends Entity{
    	private long scheduleCategoryId;
	private String schedule = "";
	private String symbol = "";
	private Date timeIn;
	private Date timeOut;
        private Date timeIn2nd;
        private Date timeOut2nd;
        //Tambahan untuk special leave pada hardrock
	private int maxEntitle;
	private int periode;
	private int periodeType;
	private int minService;
        private Date breakOut; // ditambah karena request KTI 2011
        private Date breakIn;
        private long scheduleSymbolId; /// berdasarkan schedule SymbolId

    /**
     * @return the scheduleCategoryId
     */
    public long getScheduleCategoryId() {
        return scheduleCategoryId;
    }

    /**
     * @param scheduleCategoryId the scheduleCategoryId to set
     */
    public void setScheduleCategoryId(long scheduleCategoryId) {
        this.scheduleCategoryId = scheduleCategoryId;
    }
    public String getSschTimeIn() {
        String sDt="";
        if(timeIn!=null){
            sDt=Formater.formatDate(timeIn, "HH:mm");
        }
        return sDt;
    }
    
    public String getSschTimeOut() {
        String sDt="";
        if(timeOut!=null){
            sDt=Formater.formatDate(timeOut, "HH:mm");
        }
        return sDt;
    }

    /**
     * @return the schedule
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * @param schedule the schedule to set
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the timeIn
     */
    public Date getTimeIn() {
        return timeIn;
    }

    /**
     * @param timeIn the timeIn to set
     */
    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    /**
     * @return the timeOut
     */
    public Date getTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * @return the timeIn2nd
     */
    public Date getTimeIn2nd() {
        return timeIn2nd;
    }

    /**
     * @param timeIn2nd the timeIn2nd to set
     */
    public void setTimeIn2nd(Date timeIn2nd) {
        this.timeIn2nd = timeIn2nd;
    }

    /**
     * @return the timeOut2nd
     */
    public Date getTimeOut2nd() {
        return timeOut2nd;
    }

    /**
     * @param timeOut2nd the timeOut2nd to set
     */
    public void setTimeOut2nd(Date timeOut2nd) {
        this.timeOut2nd = timeOut2nd;
    }

    /**
     * @return the maxEntitle
     */
    public int getMaxEntitle() {
        return maxEntitle;
    }

    /**
     * @param maxEntitle the maxEntitle to set
     */
    public void setMaxEntitle(int maxEntitle) {
        this.maxEntitle = maxEntitle;
    }

    /**
     * @return the periode
     */
    public int getPeriode() {
        return periode;
    }

    /**
     * @param periode the periode to set
     */
    public void setPeriode(int periode) {
        this.periode = periode;
    }

    /**
     * @return the periodeType
     */
    public int getPeriodeType() {
        return periodeType;
    }

    /**
     * @param periodeType the periodeType to set
     */
    public void setPeriodeType(int periodeType) {
        this.periodeType = periodeType;
    }

    /**
     * @return the minService
     */
    public int getMinService() {
        return minService;
    }

    /**
     * @param minService the minService to set
     */
    public void setMinService(int minService) {
        this.minService = minService;
    }

    /**
     * @return the breakOut
     */
    public Date getBreakOut() {
        return breakOut;
    }

    /**
     * @param breakOut the breakOut to set
     */
    public void setBreakOut(Date breakOut) {
        this.breakOut = breakOut;
    }

    /**
     * @return the breakIn
     */
    public Date getBreakIn() {
        return breakIn;
    }

    /**
     * @param breakIn the breakIn to set
     */
    public void setBreakIn(Date breakIn) {
        this.breakIn = breakIn;
    }

    /**
     * @return the scheduleSymbolId
     */
    public long getScheduleSymbolId() {
        return scheduleSymbolId;
    }

    /**
     * @param scheduleSymbolId the scheduleSymbolId to set
     */
    public void setScheduleSymbolId(long scheduleSymbolId) {
        this.scheduleSymbolId = scheduleSymbolId;
    }
}
