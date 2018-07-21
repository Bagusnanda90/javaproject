/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author bayu
 */

public class SpecialLeaveTaken extends Entity{
    private long employeeId;
    private Date takenDate;
    private int takenQty;       
    private Date paidDate;
    private long symbolId;

    public long getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(long symbolId) {
        this.symbolId = symbolId;
    }
    
    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
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

   
}
