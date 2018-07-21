/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

/**
 *
 * @author Gunadi
 */
import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class EmpAssetInventory extends Entity {

    private long employeeId = 0;
    private Date assignmentDate = null;
    private long checkBy = 0;
    private long approvedBy = 0;
    private long receivedBy = 0;
    private Date approvedDate = null;
    private Date returnDate = null;
    private Date receivedDate = null;
    private Date checkDate = null;
    private String detail = "";
    private int status = 0;
    private int docType = 0;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public long getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(long checkBy) {
        this.checkBy = checkBy;
    }

    public long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public long getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(long receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the docType
     */
    public int getDocType() {
        return docType;
    }

    /**
     * @param docType the docType to set
     */
    public void setDocType(int docType) {
        this.docType = docType;
    }

    /**
     * @return the checkDate
     */
    public Date getCheckDate() {
        return checkDate;
    }

    /**
     * @param checkDate the checkDate to set
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}