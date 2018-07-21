/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import java.util.Date;

/**
 *
 * @author SATRYA
 */
public class OutPermision {
    private int inOutType;
    private Date typeScheduleDateTime;//keluar dari jam kantor
    private String scheduleType;
    private long scheduleSymbolId;
    //update by satrya 2012-09-27
    private Date dtPresenceDateTime;
    private long empId;
    
    
    public static final int INOUT_TYPE_OUT = 0;
    public static final int INOUT_TYPE_IN = 1;
   // public static final String aScheduleType[] = { "Break Out", "Break In" };

    /**
     * @return the inOutType
     */
    public int getInOutType() {
        return inOutType;
    }

    /**
     * @param inOutType the inOutType to set
     */
    public void setInOutType(int inOutType) {
        this.inOutType = inOutType;
    }

    /**
     * @return the typeScheduleDateTime
     */
    public Date getTypeScheduleDateTime() {
        return typeScheduleDateTime;
    }

    /**
     * @param typeScheduleDateTime the typeScheduleDateTime to set
     */
    public void setTypeScheduleDateTime(Date typeScheduleDateTime) {
        this.typeScheduleDateTime = typeScheduleDateTime;
    }

    /**
     * @return the scheduleType
     */
    public String getScheduleType() {
        return scheduleType;
    }

    /**
     * @param scheduleType the scheduleType to set
     */
    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
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
     * @return the dtPresenceDateTime
     */
    public Date getDtPresenceDateTime() {
        return dtPresenceDateTime;
    }
    
    /**
     * @param dtPresenceDateTime the dtPresenceDateTime to set
     */
    public void setDtPresenceDateTime(Date dtPresenceDateTime) {
        this.dtPresenceDateTime = dtPresenceDateTime;
}

    /**
     * @return the empId
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(long empId) {
        this.empId = empId;
    }

    
}
