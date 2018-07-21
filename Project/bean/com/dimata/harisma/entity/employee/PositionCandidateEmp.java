/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.entity.Entity;

public class PositionCandidateEmp extends Entity {

    private long posCandidateEmpId = 0;
    private long posCandidateId = 0;
    private long employeeId = 0;
    private int rank = 0;
    private float score = 0;
    private float scoreNeed = 0;
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private long positionId = 0;
    private int candidateStatus = 0;

    public long getPosCandidateEmpId() {
        return posCandidateEmpId;
    }

    public void setPosCandidateEmpId(long posCandidateEmpId) {
        this.posCandidateEmpId = posCandidateEmpId;
    }

    public long getPosCandidateId() {
        return posCandidateId;
    }

    public void setPosCandidateId(long posCandidateId) {
        this.posCandidateId = posCandidateId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getScoreNeed() {
        return scoreNeed;
    }

    public void setScoreNeed(float scoreNeed) {
        this.scoreNeed = scoreNeed;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * @return the candidateStatus
     */
    public int getCandidateStatus() {
        return candidateStatus;
    }

    /**
     * @param candidateStatus the candidateStatus to set
     */
    public void setCandidateStatus(int candidateStatus) {
        this.candidateStatus = candidateStatus;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }
}
