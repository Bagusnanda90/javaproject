/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;


/**
 *
 * @author Tu Roy
 */
public class LeaveAlClosing {
    
    private long stockId = 0;
    private long employeeId = 0;
    private int expiredDate; // in month
    private float extended = 0;
    private String commencingDate;
    private String entitledDate;
    private int status;

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public int getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(int expiredDate) {
        this.expiredDate = expiredDate;
    }

    public float getExtended() {
        return extended;
    }

    public void setExtended(float extended) {
        this.extended = extended;
    }

    public String getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(String commencingDate) {
        this.commencingDate = commencingDate;
    }

    public String getEntitledDate() {
        return entitledDate;
    }

    public void setEntitledDate(String entitledDate) {
        this.entitledDate = entitledDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
