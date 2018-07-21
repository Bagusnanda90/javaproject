/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class EmployeeSchedule {
     private long employeeId = 0;
    private Hashtable empSchedulesId1st = new Hashtable();
    private Hashtable empSchedulesId2st= new Hashtable();

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
     * @return the empSchedulesId1st
     */
    public long getEmpSchedulesId1st(String dtSelectedByEmployee) {
        long schId=0;
        if(empSchedulesId1st!=null && empSchedulesId1st.get(dtSelectedByEmployee)!=null){
            schId = (Long)empSchedulesId1st.get(dtSelectedByEmployee);
            return schId;
        }
        return schId;
    }

    /**
     * @param empSchedulesId1st the empSchedulesId1st to set
     */
    public void addEmpSchedulesId1st(Date dtSelected,long scheduleId,long employeeId) {
        this.empSchedulesId1st.put(dtSelected+"_"+employeeId, scheduleId);
        //this.empSchedulesId1st = empSchedulesId1st;
    }

    /**
     * @return the empSchedulesId2st
     */
    public long getEmpSchedulesId2st(String dtSelectedByEmployee) {
        long schId2nd=0;
        if(empSchedulesId2st!=null && empSchedulesId2st.get(dtSelectedByEmployee)!=null){
            schId2nd = (Long)empSchedulesId2st.get(dtSelectedByEmployee);
            return schId2nd;
        }
        return schId2nd;
        //return empSchedulesId2st;
    }

    /**
     * @param empSchedulesId2st the empSchedulesId2st to set
     */
    public void addEmpSchedulesId2st(Date dtSelected,long scheduleId2nd,long employeeId) {
        //this.empSchedulesId2st = empSchedulesId2st;
        this.empSchedulesId2st.put(dtSelected+"_"+employeeId, scheduleId2nd);
    }

    
}
