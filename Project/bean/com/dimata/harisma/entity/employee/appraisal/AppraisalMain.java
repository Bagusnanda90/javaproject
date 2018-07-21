/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.employee.appraisal;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author artha
 */
public class AppraisalMain extends Entity{
    private long employeeId;
    private long empPositionId;
    private long empDepartmentId;
    private long empLevelId;
    private long assesorId;
    private long assesorPositionId;
    private Date dateAssumedPosition;
    private Date dateOfAssessment;
    private Date dateOfLastAssessment;
    private Date dateOfNextAssessment;
    private Date dateJoinedHotel;
    private int totalAssessment;;
    private double totalScore;
    private double scoreAverage;
    private long divisionHeadId;
    private Date employeeSignDate;
    private Date assessorSignDate;
    private Date divisionHeadSignDate;

    public Date getAssessorSignDate() {
        return assessorSignDate;
    }

    public void setAssessorSignDate(Date assessorSignDate) {
        this.assessorSignDate = assessorSignDate;
    }

    public long getDivisionHeadId() {
        return divisionHeadId;
    }

    public void setDivisionHeadId(long divisionHeadId) {
        this.divisionHeadId = divisionHeadId;
    }

    public Date getDivisionHeadSignDate() {
        return divisionHeadSignDate;
    }

    public void setDivisionHeadSignDate(Date divisionHeadSignDate) {
        this.divisionHeadSignDate = divisionHeadSignDate;
    }

    public Date getEmployeeSignDate() {
        return employeeSignDate;
    }

    public void setEmployeeSignDate(Date employeeSignDate) {
        this.employeeSignDate = employeeSignDate;
    }
    

    
    
    
    public double getScoreAverage() {
        return scoreAverage;
    }

    public void setScoreAverage(double scoreAverage) {
        this.scoreAverage = scoreAverage;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
    
    public int getTotalAssessment() {
        return totalAssessment;
    }

    public void setTotalAssessment(int totalAssessment) {
        this.totalAssessment = totalAssessment;
    }

    public Date getDateJoinedHotel() {
        return dateJoinedHotel;
    }

    public void setDateJoinedHotel(Date dateJoinedHotel) {
        this.dateJoinedHotel = dateJoinedHotel;
    }
    
    public long getAssesorId() {
        return assesorId;
    }

    public void setAssesorId(long assesorId) {
        this.assesorId = assesorId;
    }

    public long getAssesorPositionId() {
        return assesorPositionId;
    }

    public void setAssesorPositionId(long assesorPositionId) {
        this.assesorPositionId = assesorPositionId;
    }

    public Date getDateAssumedPosition() {
        return dateAssumedPosition;
    }

    public void setDateAssumedPosition(Date dateAssumedPosition) {
        this.dateAssumedPosition = dateAssumedPosition;
    }

    public Date getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(Date dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public Date getDateOfLastAssessment() {
        return dateOfLastAssessment;
    }

    public void setDateOfLastAssessment(Date dateOfLastAssessment) {
        this.dateOfLastAssessment = dateOfLastAssessment;
    }

    public Date getDateOfNextAssessment() {
        return dateOfNextAssessment;
    }

    public void setDateOfNextAssessment(Date dateOfNextAssessment) {
        this.dateOfNextAssessment = dateOfNextAssessment;
    }

    public long getEmpDepartmentId() {
        return empDepartmentId;
    }

    public void setEmpDepartmentId(long empDepartmentId) {
        this.empDepartmentId = empDepartmentId;
    }

    public long getEmpLevelId() {
        return empLevelId;
    }

    public void setEmpLevelId(long empLevelId) {
        this.empLevelId = empLevelId;
    }

    public long getEmpPositionId() {
        return empPositionId;
    }

    public void setEmpPositionId(long empPositionId) {
        this.empPositionId = empPositionId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    
    
}
