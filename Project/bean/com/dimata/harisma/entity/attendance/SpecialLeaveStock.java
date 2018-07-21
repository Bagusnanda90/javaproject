/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author artha
 */
public class SpecialLeaveStock extends Entity{
    private long specialLeaveId;
    private long symbolId; 
    private long employeeId;
    private Date takenDate;
    private int takenQty;       
    private int leaveStatus;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    public int getTakenQty() {
        return takenQty;
    }

    public void setTakenQty(int takenQty) {
        this.takenQty = takenQty;
    }

    public long getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(long symbolId) {
        this.symbolId = symbolId;
    }
    
    public long getSpecialLeaveId() {
        return specialLeaveId;
    }

    public void setSpecialLeaveId(long specialLeaveId) {
        this.specialLeaveId = specialLeaveId;
    }

    public int getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(int leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
    
    
        
}
