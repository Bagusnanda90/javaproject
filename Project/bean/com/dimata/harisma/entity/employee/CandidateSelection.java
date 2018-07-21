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

public class CandidateSelection extends Entity {
    private long candidateMainId = 0;
    private long candidateEducationId = 0;
    private long candidateTrainingId = 0;
    private long candidateCompetencyId = 0;
    private long candidateKpiId = 0;
    
    public long getCandidateMainId() {
        return candidateMainId;
    }

    public void setCandidateMainId(long candidateMainId) {
        this.candidateMainId = candidateMainId;
    }

    public long getCandidateEducationId() {
        return candidateEducationId;
    }

    public void setCandidateEducationId(long candidateEducationId) {
        this.candidateEducationId = candidateEducationId;
    }

    public long getCandidateTrainingId() {
        return candidateTrainingId;
    }

    public void setCandidateTrainingId(long candidateTrainingId) {
        this.candidateTrainingId = candidateTrainingId;
    }

    public long getCandidateCompetencyId() {
        return candidateCompetencyId;
    }

    public void setCandidateCompetencyId(long candidateCompetencyId) {
        this.candidateCompetencyId = candidateCompetencyId;
    }

    public long getCandidateKpiId() {
        return candidateKpiId;
    }

    public void setCandidateKpiId(long candidateKpiId) {
        this.candidateKpiId = candidateKpiId;
    }

}