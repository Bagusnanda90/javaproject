/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

/**
 *
 * @author Dimata 007
 */
import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class CandidateMain extends Entity {

    private String title = "";
    private String objective = "";
    private Date dueDate = null;
    private long requestedBy = 0;
    private Date dateOfRequest = null;
    private double scoreTolerance = 0;
    private long createdBy = 0;
    private Date createdDate = null;
    private int statusDoc = 0;
    private int candidateType = 0;
    private long approveById1 = 0;
    private long approveById2 = 0;
    private long approveById3 = 0;
    private long approveById4 = 0;
    private Date approveDate1 = null;
    private Date approveDate2 = null;
    private Date approveDate3 = null;
    private Date approveDate4 = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public long getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Date getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(Date dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public double getScoreTolerance() {
        return scoreTolerance;
    }

    public void setScoreTolerance(double scoreTolerance) {
        this.scoreTolerance = scoreTolerance;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatusDoc() {
        return statusDoc;
    }

    public void setStatusDoc(int statusDoc) {
        this.statusDoc = statusDoc;
    }

    public int getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(int candidateType) {
        this.candidateType = candidateType;
    }

    public long getApproveById1() {
        return approveById1;
    }

    public void setApproveById1(long approveById1) {
        this.approveById1 = approveById1;
    }

    public long getApproveById2() {
        return approveById2;
    }

    public void setApproveById2(long approveById2) {
        this.approveById2 = approveById2;
    }

    public long getApproveById3() {
        return approveById3;
    }

    public void setApproveById3(long approveById3) {
        this.approveById3 = approveById3;
    }

    public long getApproveById4() {
        return approveById4;
    }

    public void setApproveById4(long approveById4) {
        this.approveById4 = approveById4;
    }

    public Date getApproveDate1() {
        return approveDate1;
    }

    public void setApproveDate1(Date approveDate1) {
        this.approveDate1 = approveDate1;
    }

    public Date getApproveDate2() {
        return approveDate2;
    }

    public void setApproveDate2(Date approveDate2) {
        this.approveDate2 = approveDate2;
    }

    public Date getApproveDate3() {
        return approveDate3;
    }

    public void setApproveDate3(Date approveDate3) {
        this.approveDate3 = approveDate3;
    }

    public Date getApproveDate4() {
        return approveDate4;
    }

    public void setApproveDate4(Date approveDate4) {
        this.approveDate4 = approveDate4;
    }
}
