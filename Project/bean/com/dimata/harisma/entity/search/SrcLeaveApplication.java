/*
 * SrcLeaveApplication.java
 *
 * Created on October 22, 2004, 4:11 PM
 */

package com.dimata.harisma.entity.search;

/* package java */ 
import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class SrcLeaveApplication {
    
    /** Holds value of property empNum. */
    private String empNum = "";
    
    /** Holds value of property fullName. */
    private String fullName = "";
    
    /** Holds value of property departmentId. */
    private long departmentId = 0;
    
    /** Holds value of property sectionId. */
    private long sectionId = 0;
    
    /** Holds value of property positionId. */
    private long positionId = 0;
    
    /** Holds value of property submissionDate. */
    private Date submissionDate;
    
    /** Holds value of property takenDate. */
    private Date takenDate;
    
    /** Holds value of property submission. */
    private boolean submission = true;
    
    /** Holds value of property taken. */
    private boolean taken = true;
    
    /** Holds value of property status. */
    private int status = 0;
    
    /** Holds value of property approvalStatus. */
    private int approvalStatus = -1;
    
    
    private int approvalStatusHR = -1;
    
    
    private int approvalStatusGM = -1;
    
       
    /** Getter for property empNum.
     * @return Value of property empNum.
     *
     */
    public String getEmpNum() {
        return this.empNum;
    }
    
    /** Setter for property empNum.
     * @param empNum New value of property empNum.
     *
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }
    
    /** Getter for property fullName.
     * @return Value of property fullName.
     *
     */
    public String getFullName() {
        return this.fullName;
    }
    
    /** Setter for property fullName.
     * @param fullName New value of property fullName.
     *
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;   
    }
    
    /** Getter for property departmentId.
     * @return Value of property departmentId.
     *
     */
    public long getDepartmentId() {
        return this.departmentId;
    }
    
    /** Setter for property departmentId.
     * @param departmentId New value of property departmentId.
     *
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }
    
    /** Getter for property sectionId.
     * @return Value of property sectionId.
     *
     */
    public long getSectionId() {
        return this.sectionId;
    }
    
    /** Setter for property sectionId.
     * @param sectionId New value of property sectionId.
     *
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
    
    /** Getter for property positionId.
     * @return Value of property positionId.
     *
     */
    public long getPositionId() {
        return this.positionId;
    }
    
    /** Setter for property positionId.
     * @param positionId New value of property positionId.
     *
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
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
    
    /** Getter for property submission.
     * @return Value of property submission.
     *
     */
    public boolean isSubmission() {
        return this.submission;
    }
    
    /** Setter for property submission.
     * @param submission New value of property submission.
     *
     */
    public void setSubmission(boolean submission) {
        this.submission = submission;
    }
    
    /** Getter for property taken.
     * @return Value of property taken.
     *
     */
    public boolean isTaken() {
        return this.taken;
    }
    
    /** Setter for property taken.
     * @param taken New value of property taken.
     *
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
    
    /** Getter for property status.
     * @return Value of property status.
     *
     */
    public int getStatus() {
        return this.status;
    }
    
    /** Setter for property status.
     * @param status New value of property status.
     *
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /** Getter for property approvalStatus.
     * @return Value of property approvalStatus.
     *
     */
    public int getApprovalStatus() {
        return this.approvalStatus;
    }
    
    /** Setter for property approvalStatus.
     * @param approvalStatus New value of property approvalStatus.
     *
     */
    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getApprovalStatusHR() {
        return approvalStatusHR;
    }

    public void setApprovalStatusHR(int approvalStatusHR) {
        this.approvalStatusHR = approvalStatusHR;
    }

    public int getApprovalStatusGM() {
        return approvalStatusGM;
    }

    public void setApprovalStatusGM(int approvalStatusGM) {
        this.approvalStatusGM = approvalStatusGM;
    }
    
}
