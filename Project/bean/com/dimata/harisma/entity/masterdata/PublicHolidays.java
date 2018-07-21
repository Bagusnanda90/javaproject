/**
 * User: wardana
 * Date: Apr 8, 2004
 * Time: 8:46:01 AM
 * Version: 1.0 
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

import java.util.Calendar;
import java.util.Date;

public class PublicHolidays extends Entity {

    private Date dtHolidayDate;
    private Date dtHolidayDateTo;
    private int days;
    private String stDesc = "";
    private long iHolidaySts;
    private String entitlement="";
    public PublicHolidays() {
    }

    public Date getDtHolidayDate() {
        return dtHolidayDate;
    }

    public void setDtHolidayDate(Date dtHolidayDate) {
        this.dtHolidayDate = dtHolidayDate;
        if(this.dtHolidayDateTo!=null && this.dtHolidayDate!=null){
            this.days = (int) (com.dimata.util.DateCalc.dayDifference(dtHolidayDate, dtHolidayDateTo)) + 1;
        }        
    }

    public String getStDesc() {
        return stDesc;
    }

    public void setStDesc(String stDesc) {
        this.stDesc = stDesc;
    }

    public long getiHolidaySts() {
        return iHolidaySts;
    }

    public void setiHolidaySts(long iHolidaySts) {
        this.iHolidaySts = iHolidaySts;
    }

    /**
     * @return the dtHolidayDateTo
     */
    public Date getDtHolidayDateTo() {
        return dtHolidayDateTo;
    }

    /**
     * @param dtHolidayDateTo the dtHolidayDateTo to set
     */
    public void setDtHolidayDateTo(Date dtHolidayDateTo) {
        this.dtHolidayDateTo = dtHolidayDateTo;
        if(this.dtHolidayDateTo!=null && this.dtHolidayDate!=null){
            this.days = (int) (com.dimata.util.DateCalc.dayDifference(dtHolidayDate, dtHolidayDateTo))+1;
        }
    }

    /**
     * @return the days
     */
    public int getDays() {
        if(this.dtHolidayDateTo!=null && this.dtHolidayDate!=null){
            this.days = (int) (com.dimata.util.DateCalc.dayDifference(dtHolidayDate, dtHolidayDateTo))+1;
            return days;
        } else{
        return 0;
        }
    }

    /**
     * @return the entitlement
     */
    public String getEntitlement() {
        return entitlement;
    }

    /**
     * @param entitlement the entitlement to set
     */
    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    /**
     * @param days the days to set
     */
    /*public void setDays(int days) {
        this.days = days;
    }*/
}
