/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

import java.util.Date;

/**
 *
 * @author Roy Andika
 */
public class AlAutomaticStart {
    
    private long alStockId;
    private long employeeId;
    private int alStatus;
    private Date entitleDate;
    private int prevBalance;
    private int entitle;
    private Date recordDate;
    private int qtyUsed;
    private Date commencingDate;
    private int alQty;
    private String level;
    private String empCategory;

    /**
     * @return the alStockId
     */
    public long getAlStockId(){
        return alStockId;
    }

    /**
     * @param alStockId the alStockId to set
     */
    public void setAlStockId(long alStockId) {
        this.alStockId = alStockId;
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

    /**
     * @return the alStatus
     */
    public int getAlStatus() {
        return alStatus;
    }

    /**
     * @param alStatus the alStatus to set
     */
    public void setAlStatus(int alStatus) {
        this.alStatus = alStatus;
    }

    /**
     * @return the entitleDate
     */
    public Date getEntitleDate() {
        return entitleDate;
    }

    /**
     * @param entitleDate the entitleDate to set
     */
    public void setEntitleDate(Date entitleDate) {
        this.entitleDate = entitleDate;
    }

    /**
     * @return the prevBalance
     */
    public int getPrevBalance() {
        return prevBalance;
    }

    /**
     * @param prevBalance the prevBalance to set
     */
    public void setPrevBalance(int prevBalance) {
        this.prevBalance = prevBalance;
    }

    /**
     * @return the entitle
     */
    public int getEntitle() {
        return entitle;
    }

    /**
     * @param entitle the entitle to set
     */
    public void setEntitle(int entitle) {
        this.entitle = entitle;
    }

    /**
     * @return the recordDate
     */
    public Date getRecordDate() {
        return recordDate;
    }

    /**
     * @param recordDate the recordDate to set
     */
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * @return the qtyUsed
     */
    public int getQtyUsed() {
        return qtyUsed;
    }

    /**
     * @param qtyUsed the qtyUsed to set
     */
    public void setQtyUsed(int qtyUsed) {
        this.qtyUsed = qtyUsed;
    }

    /**
     * @return the commencingDate
     */
    public Date getCommencingDate() {
        return commencingDate;
    }

    /**
     * @param commencingDate the commencingDate to set
     */
    public void setCommencingDate(Date commencingDate) {
        this.commencingDate = commencingDate;
    }

    /**
     * @return the alQty
     */
    public int getAlQty() {
        return alQty;
    }

    /**
     * @param alQty the alQty to set
     */
    public void setAlQty(int alQty) {
        this.alQty = alQty;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the empCategory
     */
    public String getEmpCategory() {
        return empCategory;
    }

    /**
     * @param empCategory the empCategory to set
     */
    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }






}
