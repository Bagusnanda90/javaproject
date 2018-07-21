/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 * Description : Entity Candidate
 * Date : 2015 Feb 11
 * @author Hendra Putu
 */
public class PositionCandidate extends Entity {

    private long posCandidateId = 0;
    private int candidateType = 0;
    private long positionId = 0;
    private String title = "";
    private String objectives = "";
    private int numCandidates = 0;
    private Date dueDate = null;
    private long requestBy = 0;
    private int docStatus = 0;
    private String company = "";
    private String division = "";
    private String department = "";
    private String section = "";
    private Date searchDate = null;

    public long getPosCandidateId() {
        return posCandidateId;
    }

    public void setPosCandidateId(long posCandidateId) {
        this.posCandidateId = posCandidateId;
    }

    public int getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(int candidateType) {
        this.candidateType = candidateType;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public int getNumCandidates() {
        return numCandidates;
    }

    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public long getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(long requestBy) {
        this.requestBy = requestBy;
    }

    public int getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(int docStatus) {
        this.docStatus = docStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
}
