
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class ScheduleSymbol extends Entity { 

	private long scheduleCategoryId;
	private String schedule = "";
	private String symbol = "";
	private Date timeIn;
	private Date timeOut;
        private Date timeIn2nd;
        private Date timeOut2nd;
        // add field from mchen
        private int transportAllowance;
        private int nightAllowance;
        private int workDays;
        //Tambahan untuk special leave pada hardrock
	private int maxEntitle;
	private int periode;
	private int periodeType;
	private int minService;
        private Date breakOut; // ditambah karena request KTI 2011
        private Date breakIn;
        private long scheduleSymbolId; /// berdasarkan schedule SymbolId

    public int getMaxEntitle() {
        return maxEntitle;
    }

    public void setMaxEntitle(int maxEntitle) {
        this.maxEntitle = maxEntitle;
    }

    public int getMinService() {
        return minService;
    }

    public void setMinService(int minService) {
        this.minService = minService;
    }

    public int getPeriode() {
        return periode;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public int getPeriodeType() {
        return periodeType;
    }

    public void setPeriodeType(int periodeType) {
        this.periodeType = periodeType;
    }
        
        
        
	public long getScheduleCategoryId(){ 
		return scheduleCategoryId; 
	} 

	public void setScheduleCategoryId(long scheduleCategoryId){ 
		this.scheduleCategoryId = scheduleCategoryId; 
	} 

	public String getSchedule(){ 
		return schedule; 
	} 

	public void setSchedule(String schedule){ 
		if ( schedule == null ) {
			schedule = ""; 
		} 
		this.schedule = schedule; 
	} 

	public String getSymbol(){ 
		return symbol; 
	} 

	public void setSymbol(String symbol){ 
		if ( symbol == null ) {
			symbol = ""; 
		} 
		this.symbol = symbol; 
	} 

	public Date getTimeIn(){ 
		return timeIn; 
	} 

	public void setTimeIn(Date timeIn){ 
		this.timeIn = timeIn; 
	} 

	public Date getTimeOut(){ 
		return timeOut; 
	} 

	public void setTimeOut(Date timeOut){ 
		this.timeOut = timeOut; 
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

    /**
     * @return the transportAllowance
     */
    public int getTransportAllowance() {
        return transportAllowance;
    }

    /**
     * @param transportAllowance the transportAllowance to set
     */
    public void setTransportAllowance(int transportAllowance) {
        this.transportAllowance = transportAllowance;
    }

    /**
     * @return the nightAllowance
     */
    public int getNightAllowance() {
        return nightAllowance;
    }

    /**
     * @param nightAllowance the nightAllowance to set
     */
    public void setNightAllowance(int nightAllowance) {
        this.nightAllowance = nightAllowance;
    }

    /**
     * @return the workDays
     */
    public int getWorkDays() {
        return workDays;
    }

    /**
     * @param workDays the workDays to set
     */
    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }

}
