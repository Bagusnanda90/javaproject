/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.overtime;

import java.util.Date;
import com.dimata.qdep.entity.Entity;
import java.util.Vector;
/**
 *
 * @author Wiweka
 */
public class Overtime extends Entity{
   
    private long companyId;
    private long divisionId;
    private long departmentId;
    private long costDepartmentId;
    private long sectionId;
    private Date requestDate;
    private String overtimeNum = "";
    private String objective = "";
    private int statusDoc = 0;
    private long customerTaskId;
    private long logbookId;
    private long requestId;
    private long approvalId;
    private long ackId;
    private int countIdx=0;
    private int allowence=0;
    private Date restTimeStart= null;
    private float restTimeHR =0.0F;
    private int doRestTimeStart= 0;
    private int doUpdateAllowence =0;
    private Date timeReqOt;
    private Date timeApproveOt;
    private Date timeAckOt;
    
    private long approval1Id = 0;
    private Date timeApproval1 = new Date();
    private long approval2Id = 0;
    private Date timeApproval2 = new Date();
    private long approval3Id = 0;
    private Date timeApproval3 = new Date();
    private long approval4Id = 0;
    private Date timeApproval4 = new Date();
    private long approval5Id = 0;
    private Date timeApproval5 = new Date();
    private long approval6Id = 0;
    private Date timeApproval6 = new Date();
    
    
    public static final int ALLOWANCE_FOOD = 0;
    public static final int ALLOWANCE_MONEY = 1;
    public static final String allowanceType[] = { "Food", "Money" };
    public static final int allowanceValue[] = { 0, 1 };


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
    /*public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    /*public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }*/

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
        if(objective==null){
            return "";
        }
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
     * @return the countIdx
     */
    public int getCountIdx() {
        return countIdx;
    }

    /**
     * @param countIdx the countIdx to set
     */
    public void setCountIdx(int countIdx) {
        this.countIdx = countIdx;
    }

    /**
     * @return the costDepartmentId
     */
    public long getCostDepartmentId() {
        return costDepartmentId;
    }

    /**
     * @param costDepartmentId the costDepartmentId to set
     */
    public void setCostDepartmentId(long costDepartmentId) {
        this.costDepartmentId = costDepartmentId;
    }

    /**
     * @return the allowence
     */
    public int getAllowence() {
        return allowence;
    }

    /**
     * @param allowence the allowence to set
     */
    public void setAllowence(int allowence) {
        this.allowence = allowence;
    }

    /**
     * @return the restTimeStart
     */
    public Date getRestTimeStart() {
        return restTimeStart;
    }

    /**
     * @param restTimeStart the restTimeStart to set
     */
    public void setRestTimeStart(Date restTimeStart) {
        this.restTimeStart = restTimeStart;
    }

    /**
     * @return the restTimeHR
     */
    public float getRestTimeHR() {
        return restTimeHR;
    }

    /**
     * @param restTimeHR the restTimeHR to set
     */
    public void setRestTimeHR(float restTimeHR) {
        this.restTimeHR = restTimeHR;
    }

    /**
     * @return the doRestTimeStart
     */
    public int getDoRestTimeStart() {
        return doRestTimeStart;
    }

    /**
     * @param doRestTimeStart the doRestTimeStart to set
     */
    public void setDoRestTimeStart(int doRestTimeStart) {
        this.doRestTimeStart = doRestTimeStart;
    }
   

    /**
     * @return the doUpdateAllowence
     */
    public int getDoUpdateAllowence() {
        return doUpdateAllowence;
    }

    /**
     * @param doUpdateAllowence the doUpdateAllowence to set
     */
    public void setDoUpdateAllowence(int doUpdateAllowence) {
        this.doUpdateAllowence = doUpdateAllowence;
    }

    /**
     * @return the timeReqOt
     */
    public Date getTimeReqOt() {
        return timeReqOt;
    }

    /**
     * @param timeReqOt the timeReqOt to set
     */
    public void setTimeReqOt(Date timeReqOt) {
        this.timeReqOt = timeReqOt;
    }

    /**
     * @return the timeApproveOt
     */
    public Date getTimeApproveOt() {
        return timeApproveOt;
    }

    /**
     * @param timeApproveOt the timeApproveOt to set
     */
    public void setTimeApproveOt(Date timeApproveOt) {
        this.timeApproveOt = timeApproveOt;
    }

    /**
     * @return the timeAckOt
     */
    public Date getTimeAckOt() {
        return timeAckOt;
    }

    /**
     * @param timeAckOt the timeAckOt to set
     */
    public void setTimeAckOt(Date timeAckOt) {
        this.timeAckOt = timeAckOt;
    }

    /**
     * @return the approval1Id
     */
    public long getApproval1Id() {
        return approval1Id;
    }

    /**
     * @param approval1Id the approval1Id to set
     */
    public void setApproval1Id(long approval1Id) {
        this.approval1Id = approval1Id;
    }

    /**
     * @return the timeApproval1
     */
    public Date getTimeApproval1() {
        return timeApproval1;
    }

    /**
     * @param timeApproval1 the timeApproval1 to set
     */
    public void setTimeApproval1(Date timeApproval1) {
        this.timeApproval1 = timeApproval1;
    }

    /**
     * @return the approval2Id
     */
    public long getApproval2Id() {
        return approval2Id;
    }

    /**
     * @param approval2Id the approval2Id to set
     */
    public void setApproval2Id(long approval2Id) {
        this.approval2Id = approval2Id;
    }

    /**
     * @return the timeApproval2
     */
    public Date getTimeApproval2() {
        return timeApproval2;
    }

    /**
     * @param timeApproval2 the timeApproval2 to set
     */
    public void setTimeApproval2(Date timeApproval2) {
        this.timeApproval2 = timeApproval2;
    }

    /**
     * @return the approval3Id
     */
    public long getApproval3Id() {
        return approval3Id;
    }

    /**
     * @param approval3Id the approval3Id to set
     */
    public void setApproval3Id(long approval3Id) {
        this.approval3Id = approval3Id;
    }

    /**
     * @return the timeApproval3
     */
    public Date getTimeApproval3() {
        return timeApproval3;
    }

    /**
     * @param timeApproval3 the timeApproval3 to set
     */
    public void setTimeApproval3(Date timeApproval3) {
        this.timeApproval3 = timeApproval3;
    }

    /**
     * @return the approval4Id
     */
    public long getApproval4Id() {
        return approval4Id;
    }

    /**
     * @param approval4Id the approval4Id to set
     */
    public void setApproval4Id(long approval4Id) {
        this.approval4Id = approval4Id;
    }

    /**
     * @return the timeApproval4
     */
    public Date getTimeApproval4() {
        return timeApproval4;
    }

    /**
     * @param timeApproval4 the timeApproval4 to set
     */
    public void setTimeApproval4(Date timeApproval4) {
        this.timeApproval4 = timeApproval4;
    }

    /**
     * @return the approval5Id
     */
    public long getApproval5Id() {
        return approval5Id;
    }

    /**
     * @param approval5Id the approval5Id to set
     */
    public void setApproval5Id(long approval5Id) {
        this.approval5Id = approval5Id;
    }

    /**
     * @return the timeApproval5
     */
    public Date getTimeApproval5() {
        return timeApproval5;
    }

    /**
     * @param timeApproval5 the timeApproval5 to set
     */
    public void setTimeApproval5(Date timeApproval5) {
        this.timeApproval5 = timeApproval5;
    }

    /**
     * @return the approval6Id
     */
    public long getApproval6Id() {
        return approval6Id;
    }

    /**
     * @param approval6Id the approval6Id to set
     */
    public void setApproval6Id(long approval6Id) {
        this.approval6Id = approval6Id;
    }

    /**
     * @return the timeApproval6
     */
    public Date getTimeApproval6() {
        return timeApproval6;
    }

    /**
     * @param timeApproval6 the timeApproval6 to set
     */
    public void setTimeApproval6(Date timeApproval6) {
        this.timeApproval6 = timeApproval6;
    }

    


}
