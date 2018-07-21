/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

/**
 *
 * @author roy ajus
 */
public class EmployeeLeave {

    private long employeeId = 0;
    private long leaveApplicationID = 0;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getLeaveApplicationID() {
        return leaveApplicationID;
    }

    public void setLeaveApplicationID(long leaveApplicationID) {
        this.leaveApplicationID = leaveApplicationID;
    }
        
}
