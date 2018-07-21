/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class PositionCandidateLocation extends Entity {

    private long posCandidateLocationId = 0;
    private long posCandidateId = 0;
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private int numberNeeded = 0;
    private Date dueDate = null;

    public long getPosCandidateLocationId() {
        return posCandidateLocationId;
    }

    public void setPosCandidateLocationId(long posCandidateLocationId) {
        this.posCandidateLocationId = posCandidateLocationId;
    }

    public long getPosCandidateId() {
        return posCandidateId;
    }

    public void setPosCandidateId(long posCandidateId) {
        this.posCandidateId = posCandidateId;
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

    public int getNumberNeeded() {
        return numberNeeded;
    }

    public void setNumberNeeded(int numberNeeded) {
        this.numberNeeded = numberNeeded;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
