/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.search;

import java.util.Date;

/**
 *
 * @author Wiweka
 */
public class SrcOvertime {
     private long companyId;
    private long divisionId;
    private long departmentId;
    private long sectionId;
    private Date requestDate;
    private Date requestDateTo;
    private String overtimeNum = "";
    private String objective = "";
    private int statusDoc = 0;
    private long customerTaskId;
    private long logbookId;
    private long requestId;
    private long approvalId;
    private long ackId;
    private int orderBy;

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the divisionId
     */
    public long getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the departmentId
     */
    public long getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the sectionId
     */
    public long getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the overtimeNum
     */
    public String getOvertimeNum() {
        return overtimeNum;
    }

    /**
     * @param overtimeNum the overtimeNum to set
     */
    public void setOvertimeNum(String overtimeNum) {
        this.overtimeNum = overtimeNum;
    }

    /**
     * @return the objective
     */
    public String getObjective() {
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(String objective) {
        this.objective = objective;
    }

    /**
     * @return the statusDoc
     */
    public int getStatusDoc() {
        return statusDoc;
    }

    /**
     * @param statusDoc the statusDoc to set
     */
    public void setStatusDoc(int statusDoc) {
        this.statusDoc = statusDoc;
    }

    /**
     * @return the customerTaskId
     */
    public long getCustomerTaskId() {
        return customerTaskId;
    }

    /**
     * @param customerTaskId the customerTaskId to set
     */
    public void setCustomerTaskId(long customerTaskId) {
        this.customerTaskId = customerTaskId;
    }

    /**
     * @return the logbookId
     */
    public long getLogbookId() {
        return logbookId;
    }

    /**
     * @param logbookId the logbookId to set
     */
    public void setLogbookId(long logbookId) {
        this.logbookId = logbookId;
    }

    /**
     * @return the requestId
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the approvalId
     */
    public long getApprovalId() {
        return approvalId;
    }

    /**
     * @param approvalId the approvalId to set
     */
    public void setApprovalId(long approvalId) {
        this.approvalId = approvalId;
    }

    /**
     * @return the ackId
     */
    public long getAckId() {
        return ackId;
    }

    /**
     * @param ackId the ackId to set
     */
    public void setAckId(long ackId) {
        this.ackId = ackId;
    }

    /**
     * @return the orderBy
     */
    public int getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return the requestDateTo
     */
    public Date getRequestDateTo() {
        return requestDateTo;
    }

    /**
     * @param requestDateTo the requestDateTo to set
     */
    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }
}
