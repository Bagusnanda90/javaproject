/*
 * LeaveStockTaken.java
 *
 * Created on July 23, 2004, 3:37 PM
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author  gedhy
 */
public class LeaveStockTaken  extends Entity{
    
    private long leaveStockId = 0;
    private int idxLeaveTaken = 0;
    private long empScheduleId = 0;
    private int idxDateSchedule = 0;
    private int leaveType = 0;

    public long getLeaveStockId() {
        return leaveStockId;
    }

    public void setLeaveStockId(long leaveStockId) {
        this.leaveStockId = leaveStockId;
    }

    public int getIdxLeaveTaken() {
        return idxLeaveTaken;
    }

    public void setIdxLeaveTaken(int idxLeaveTaken) {
        this.idxLeaveTaken = idxLeaveTaken;
    }

    public long getEmpScheduleId() {
        return empScheduleId;
    }

    public void setEmpScheduleId(long empScheduleId) {
        this.empScheduleId = empScheduleId;
    }

    public int getIdxDateSchedule() {
        return idxDateSchedule;
    }

    public void setIdxDateSchedule(int idxDateSchedule) {
        this.idxDateSchedule = idxDateSchedule;
    }

    public int getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
    }
}
