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

public class CandidatePosition extends Entity {

    private long candidateMainId = 0;
    private long candidateLocId = 0;
    private long positionId = 0;
    private int numberOfCandidate = 0;
    private Date dueDate = null;
    private String objectives = "";
    private int candidateType = 0;

    public long getCandidateMainId() {
        return candidateMainId;
    }

    public void setCandidateMainId(long candidateMainId) {
        this.candidateMainId = candidateMainId;
    }

    public long getCandidateLocId() {
        return candidateLocId;
    }

    public void setCandidateLocId(long candidateLocId) {
        this.candidateLocId = candidateLocId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public int getNumberOfCandidate() {
        return numberOfCandidate;
    }

    public void setNumberOfCandidate(int numberOfCandidate) {
        this.numberOfCandidate = numberOfCandidate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public int getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(int candidateType) {
        this.candidateType = candidateType;
    }
}
