/*
 * DpApplication.java
 *
 * Created on October 21, 2004, 12:05 PM
 */

package com.dimata.harisma.entity.leave;

import com.dimata.qdep.entity.Entity;

import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class DpApplication extends Entity
{
    
    /** Holds value of property submissionDate. */
    private Date submissionDate;
    
    /** Holds value of property employeeId. */
    private long employeeId = 0;
    
    /** Holds value of property dpId. */
    private long dpId = 0;
    
    /** Holds value of property takenDate. */
    private Date takenDate;
    
    /** Holds value of property approvalId. */
    private long approvalId = 0;  
    
    /** Holds value of property docStatus. */
    private int docStatus;
    
    //Add for Hardrock
    private int balance;
    private int newBalance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(int newBalance) {
        this.newBalance = newBalance;
    }
    
    
    /** Creates a new instance of DpApplication */
    public DpApplication() {
    }
    
    /** Getter for property submissionDate.
     * @return Value of property submissionDate.
     *
     */
    public Date getSubmissionDate() {
        return this.submissionDate;
    }
    
    /** Setter for property submissionDate.
     * @param submissionDate New value of property submissionDate.
     *
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
    
    /** Getter for property employeeId.
     * @return Value of property employeeId.
     *
     */
    public long getEmployeeId() {
        return this.employeeId;
    }
    
    /** Setter for property employeeId.
     * @param employeeId New value of property employeeId.
     *
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    /** Getter for property dpId.
     * @return Value of property dpId.
     *
     */
    public long getDpId() {
        return this.dpId;
    }
    
    /** Setter for property dpId.
     * @param dpId New value of property dpId.
     *
     */
    public void setDpId(long dpId) {
        this.dpId = dpId;
    }
    
    /** Getter for property takenDate.
     * @return Value of property takenDate.
     *
     */
    public Date getTakenDate() {
        return this.takenDate;
    }
    
    /** Setter for property takenDate.
     * @param takenDate New value of property takenDate.
     *
     */
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }
    
    /** Getter for property approvalId.
     * @return Value of property approvalId.
     *
     */
    public long getApprovalId() {
        return this.approvalId;
    }
    
    /** Setter for property approvalId.
     * @param approvalId New value of property approvalId.
     *
     */
    public void setApprovalId(long approvalId) {
        this.approvalId = approvalId;
    }
    
    /** Getter for property docStatus.
     * @return Value of property docStatus.
     *
     */
    public int getDocStatus() {
        return this.docStatus;
    }
    
    /** Setter for property docStatus.
     * @param docStatus New value of property docStatus.
     *
     */
    public void setDocStatus(int docStatus) {
        this.docStatus = docStatus;
    }
    
}
