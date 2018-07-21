
package com.dimata.harisma.entity.arap;

// package qdep
import com.dimata.qdep.entity.*;

import java.util.Date;

public class ArApMain extends Entity {

    private String voucherNo = "";
    private Date voucherDate = new Date();
    private long contactId = 0;
    private int numberOfPayment = 0;
    private long idPerkiraanLawan = 0;
    private long idPerkiraan = 0;
    private long idCurrency = 0;
    private int counter = 0;
    private double rate = 0.0;
    private double amount = 0.0;
    private String notaNo = "";
    private Date notaDate = new Date();
    private String description = "";
    private int arApMainStatus = 0;
    private int arApType = 0;
    private int arApDocStatus = 0;
    private long journalId=0;
    private long componentDeductionId=0;
    private long EmployeeId=0;
    private Date entryDate = new Date();
    private int periodeEvery = 0;
    private int periodeEveryDMY = 0;
    private Date startofpaymentdate = new Date();
    private double payment_amount_plan = 0;
    private int periodType = 0;
    private long periodId = 0;
    

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }


    public long getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(long idCurrency) {
        this.idCurrency = idCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public int getNumberOfPayment() {
        return numberOfPayment;
    }

    public void setNumberOfPayment(int numberOfPayment) {
        this.numberOfPayment = numberOfPayment;
    }

    public long getIdPerkiraanLawan() {
        return idPerkiraanLawan;
    }

    public void setIdPerkiraanLawan(long idPerkiraanLawan) {
        this.idPerkiraanLawan = idPerkiraanLawan;
    }

    public long getIdPerkiraan() {
        return idPerkiraan;
    }

    public void setIdPerkiraan(long idPerkiraan) {
        this.idPerkiraan = idPerkiraan;
    }

    public String getNotaNo() {
        return notaNo;
    }

    public void setNotaNo(String notaNo) {
        this.notaNo = notaNo;
    }

    public Date getNotaDate() {
        return notaDate;
    }

    public void setNotaDate(Date notaDate) {
        this.notaDate = notaDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getArApMainStatus() {
        return arApMainStatus;
    }

    public void setArApMainStatus(int arApMainStatus) {
        this.arApMainStatus = arApMainStatus;
    }

    public int getArApType() {
        return arApType;
    }

    public void setArApType(int arApType) {
        this.arApType = arApType;
    }

    public int getArApDocStatus() {
        return arApDocStatus;
    }

    public void setArApDocStatus(int arApDocStatus) {
        this.arApDocStatus = arApDocStatus;
    }

    /**
     * @return the journalId
     */
    public long getJournalId() {
        return journalId;
    }

    /**
     * @param journalId the journalId to set
     */
    public void setJournalId(long journalId) {
        this.journalId = journalId;
    }

    /**
     * @return the componentDeductionId
     */
    public long getComponentDeductionId() {
        return componentDeductionId;
    }

    /**
     * @param componentDeductionId the componentDeductionId to set
     */
    public void setComponentDeductionId(long componentDeductionId) {
        this.componentDeductionId = componentDeductionId;
    }

    /**
     * @return the EmployeeId
     */
    public long getEmployeeId() {
        return EmployeeId;
    }

    /**
     * @param EmployeeId the EmployeeId to set
     */
    public void setEmployeeId(long EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

    /**
     * @return the entryDate
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate the entryDate to set
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * @return the periodeEvery
     */
    public int getPeriodeEvery() {
        return periodeEvery;
    }

    /**
     * @param periodeEvery the periodeEvery to set
     */
    public void setPeriodeEvery(int periodeEvery) {
        this.periodeEvery = periodeEvery;
    }

    /**
     * @return the periodeEveryDMY
     */
    public int getPeriodeEveryDMY() {
        return periodeEveryDMY;
    }

    /**
     * @param periodeEveryDMY the periodeEveryDMY to set
     */
    public void setPeriodeEveryDMY(int periodeEveryDMY) {
        this.periodeEveryDMY = periodeEveryDMY;
    }

    /**
     * @return the startofpaymentdate
     */
    public Date getStartofpaymentdate() {
        return startofpaymentdate;
    }

    /**
     * @param startofpaymentdate the startofpaymentdate to set
     */
    public void setStartofpaymentdate(Date startofpaymentdate) {
        this.startofpaymentdate = startofpaymentdate;
    }

    /**
     * @return the payment_amount_plan
     */
    public double getPayment_amount_plan() {
        return payment_amount_plan;
    }

    /**
     * @param payment_amount_plan the payment_amount_plan to set
     */
    public void setPayment_amount_plan(double payment_amount_plan) {
        this.payment_amount_plan = payment_amount_plan;
    }

    /**
     * @return the periodType
     */
    public int getPeriodType() {
        return periodType;
    }

    /**
     * @param periodType the periodType to set
     */
    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    /**
     * @return the periodId
     */
    public long getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }


}