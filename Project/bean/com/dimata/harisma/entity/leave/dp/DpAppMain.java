/*
 * LeaveApplication.java
 *
 * Created on October 27, 2004, 11:51 AM
 */

package com.dimata.harisma.entity.leave.dp;

import com.dimata.harisma.entity.leave.*;
import com.dimata.qdep.entity.Entity; 
import java.util.Date;



/**
 *
 * @author  artha
 */
public class DpAppMain extends Entity
{
    
   long employeeId;
   Date submissionDate;
   long approvalId;
   long approval2Id;
   long approval3Id;
   Date approvalDate;
   Date approval2Date;
   Date approval3Date;
   int balance;
   int documentStatus;

    public Date getApproval2Date() {
        return approval2Date;
    }

    public void setApproval2Date(Date approval2Date) {
        this.approval2Date = approval2Date;
    }

    public long getApproval2Id() {
        return approval2Id;
    }

    public void setApproval2Id(long approval2Id) {
        this.approval2Id = approval2Id;
    }

    public Date getApproval3Date() {
        return approval3Date;
    }

    public void setApproval3Date(Date approval3Date) {
        this.approval3Date = approval3Date;
    }

    public long getApproval3Id() {
        return approval3Id;
    }

    public void setApproval3Id(long approval3Id) {
        this.approval3Id = approval3Id;
    }

   
   
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(long approvalId) {
        this.approvalId = approvalId;
    }

    public int getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(int documentStatus) {
        this.documentStatus = documentStatus;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
   
   
   
}
