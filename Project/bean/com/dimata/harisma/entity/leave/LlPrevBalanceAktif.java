/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

import java.util.Date;

/**
 *
 * @author Tu Roy
 */
public class LlPrevBalanceAktif {
    
    private long employeeId = 0;
    private long departementId = 0;
    private long llStockId = 0;
    private Date entitleDate;
    private int prevBalance = 0;
    private int qty = 0;
    private int qtyTkn = 0;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(long departementId) {
        this.departementId = departementId;
    }

    public long getLlStockId() {
        return llStockId;
    }

    public void setLlStockId(long llStockId) {
        this.llStockId = llStockId;
    }

    public Date getEntitleDate() {
        return entitleDate;
    }

    public void setEntitleDate(Date entitleDate) {
        this.entitleDate = entitleDate;
    }

    public int getPrevBalance() {
        return prevBalance;
    }

    public void setPrevBalance(int prevBalance) {
        this.prevBalance = prevBalance;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQtyTkn() {
        return qtyTkn;
    }

    public void setQtyTkn(int qtyTkn) {
        this.qtyTkn = qtyTkn;
    }

}
