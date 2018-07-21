
package com.dimata.harisma.entity.attendance;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author bayu
 */

public class SpecialLeave extends Entity {

    private long employeeId = 0;
    private Date requestDate = new Date();
    private String unpaidLeaveReason = "";
    private String otherRemarks = "";
    private long approvalId = 0;
    private long approval2Id = 0;
    private long approval3Id = 0;
    private Date approvalDate = null;
    private Date approval2Date = null;
    private Date approval3Date = null;

    
    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getOtherRemarks() {
        return otherRemarks;
    }

    public void setOtherRemarks(String otherRemarks) {
        this.otherRemarks = otherRemarks;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
   
    public String getUnpaidLeaveReason() {
        return unpaidLeaveReason;
    }

    public void setUnpaidLeaveReason(String unpaidLeaveReason) {
        this.unpaidLeaveReason = unpaidLeaveReason;
    }

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

    public void setApproval3Id(long approval3id) {
        this.approval3Id = approval3id;
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
        
}