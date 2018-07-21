/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.entity.*;
import java.util.Vector;
/**
 *
 * @author ktanjana
 */
public class DefaultSchedule extends Entity {
  private int dayIndex =0;
  private long schedule1 =0;
  private long schedule2=0;
  private long employeeId = 0;  

    /**
     * @return the dayIndex
     */
    public int getDayIndex() {
        return dayIndex;
    }

    /**
     * @param dayIndex the dayIndex to set
     */
    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    /**
     * @return the schedule1
     */
    public long getSchedule1() {
        return schedule1;
    }

    /**
     * @param schedule1 the schedule1 to set
     */
    public void setSchedule1(long schedule1) {
        this.schedule1 = schedule1;
    }

    /**
     * @return the schedule2
     */
    public long getSchedule2() {
        return schedule2;
    }

    /**
     * @param schedule2 the schedule2 to set
     */
    public void setSchedule2(long schedule2) {
        this.schedule2 = schedule2;
    }

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
}
