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
public class LlClosingSelected {
    
    private long llStockId      = 0;
    private long employeeId     = 0;
    private Date entitleDate;
    private Date entitleDate2;
    private Date commencingDate;
    private int statusData      = 0;
    private int iterval_date    = 0;
    private int extended_value  = 0;
    private int entitle_1       = 0;
    private int entitle_2       = 0;
    private Date exp_date_1;
    private Date exp_date_2;    
    private int prev_balance    = 0;
    private int qty_taken       = 0;
    private int qty             = 0;

    public long getLlStockId() {
        return llStockId;
    }

    public void setLlStockId(long llStockId) {
        this.llStockId = llStockId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getEntitleDate() {
        return entitleDate;
    }

    public void setEntitleDate(Date entitleDate) {
        this.entitleDate = entitleDate;
    }

    public Date getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(Date commencingDate) {
        this.commencingDate = commencingDate;
    }

    public int getStatusData() {
        return statusData;
    }

    public void setStatusData(int statusData) {
        this.statusData = statusData;
    }

    public int getIterval_date() {
        return iterval_date;
    }

    public void setIterval_date(int iterval_date) {
        this.iterval_date = iterval_date;
    }

    public int getExtended_value() {
        return extended_value;
    }

    public void setExtended_value(int extended_value) {
        this.extended_value = extended_value;
    }

    public int getEntitle_1() {
        return entitle_1;
    }

    public void setEntitle_1(int entitle_1) {
        this.entitle_1 = entitle_1;
    }

    public int getEntitle_2() {
        return entitle_2;
    }

    public void setEntitle_2(int entitle_2) {
        this.entitle_2 = entitle_2;
    }

    public Date getExp_date_1() {
        return exp_date_1;
    }

    public void setExp_date_1(Date exp_date_1) {
        this.exp_date_1 = exp_date_1;
    }

    public Date getExp_date_2() {
        return exp_date_2;
    }

    public void setExp_date_2(Date exp_date_2) {
        this.exp_date_2 = exp_date_2;
    }

    public Date getEntitleDate2() {
        return entitleDate2;
    }

    public void setEntitleDate2(Date entitleDate2) {
        this.entitleDate2 = entitleDate2;
    }

    public int getPrev_balance() {
        return prev_balance;
    }

    public void setPrev_balance(int prev_balance) {
        this.prev_balance = prev_balance;
    }

    public int getQty_taken() {
        return qty_taken;
    }

    public void setQty_taken(int qty_taken) {
        this.qty_taken = qty_taken;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
