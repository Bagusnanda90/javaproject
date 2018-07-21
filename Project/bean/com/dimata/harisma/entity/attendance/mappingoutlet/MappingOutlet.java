/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance.mappingoutlet;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class MappingOutlet extends Entity{
    private long employeeId;
    private long locationOutletId;
    private long positionId;
    private long scheduleId;
    private Date dtStart;
    private Date dtdEnd;
    private Hashtable<String,Boolean> hashCheckedSchedule = new Hashtable() ; 

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the locationOutletId
     */
    public long getLocationOutletId() {
        return locationOutletId;
    }

    /**
     * @param locationOutletId the locationOutletId to set
     */
    public void setLocationOutletId(long locationOutletId) {
        this.locationOutletId = locationOutletId;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the scheduleId
     */
    public long getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId the scheduleId to set
     */
    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return the dtStart
     */
    public Date getDtStart() {
        return dtStart;
    }

    /**
     * @param dtStart the dtStart to set
     */
    public void setDtStart(Date dtStart) {
        this.dtStart = dtStart;
    }

    /**
     * @return the dtdEnd
     */
    public Date getDtdEnd() {
        return dtdEnd;
    }

    /**
     * @param dtdEnd the dtdEnd to set
     */
    public void setDtdEnd(Date dtdEnd) {
        this.dtdEnd = dtdEnd;
    }

    /**
     * @return the hashCheckedSchedule
     */
    public boolean getHashCheckedSchedule(long oidEmployee) {
        return hashCheckedSchedule.containsKey(""+oidEmployee);
    }

    /**
     * @param hashCheckedSchedule the hashCheckedSchedule to set
     */
    public void addHashCheckedSchedule(long oidEmployee) {
        this.hashCheckedSchedule.put(""+oidEmployee, true);
        //this.hashCheckedSchedule = hashCheckedSchedule;
    }
}
