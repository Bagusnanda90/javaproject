/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance.mappingoutlet;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Satrya Ramayu
 */
public class ExtraScheduleOutlet extends Entity{
    private Date requestDate;
    private String numberForm;
    private long companyId;
    private long divisionId;
    private long departmentId;
    private long sectionId;
    private long costCenterId;//diambil dari departmentId
    private String extraScheduleObjctive;
    private long empApprovall;
    private long empApprovall1;
    private long empApprovall2;
    private Date dtEmpApprovall;
    private Date dtEmpApprovall1;
    private Date dtEmpApprovall2;
    private int countIdx;
    private int docStsForm;
    
    private String flagSaveEmployee;

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
     * @return the numberForm
     */
    public String getNumberForm() {
        return numberForm;
    }

    /**
     * @param numberForm the numberForm to set
     */
    public void setNumberForm(String numberForm) {
        this.numberForm = numberForm;
    }

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
     * @return the costCenterId
     */
    public long getCostCenterId() {
        return costCenterId;
    }

    /**
     * @param costCenterId the costCenterId to set
     */
    public void setCostCenterId(long costCenterId) {
        this.costCenterId = costCenterId;
    }

    /**
     * @return the extraScheduleObjctive
     */
    public String getExtraScheduleObjctive() {
        if(extraScheduleObjctive==null){
            return "";
        }
        return extraScheduleObjctive;
    }

    /**
     * @param extraScheduleObjctive the extraScheduleObjctive to set
     */
    public void setExtraScheduleObjctive(String extraScheduleObjctive) {
        this.extraScheduleObjctive = extraScheduleObjctive;
    }

    /**
     * @return the empApprovall
     */
    public long getEmpApprovall() {
        return empApprovall;
    }

    /**
     * @param empApprovall the empApprovall to set
     */
    public void setEmpApprovall(long empApprovall) {
        this.empApprovall = empApprovall;
    }

    /**
     * @return the empApprovall1
     */
    public long getEmpApprovall1() {
        return empApprovall1;
    }

    /**
     * @param empApprovall1 the empApprovall1 to set
     */
    public void setEmpApprovall1(long empApprovall1) {
        this.empApprovall1 = empApprovall1;
    }

    /**
     * @return the empApprovall2
     */
    public long getEmpApprovall2() {
        return empApprovall2;
    }

    /**
     * @param empApprovall2 the empApprovall2 to set
     */
    public void setEmpApprovall2(long empApprovall2) {
        this.empApprovall2 = empApprovall2;
    }

    /**
     * @return the dtEmpApprovall
     */
    public Date getDtEmpApprovall() {
        return dtEmpApprovall;
    }

    /**
     * @param dtEmpApprovall the dtEmpApprovall to set
     */
    public void setDtEmpApprovall(Date dtEmpApprovall) {
        this.dtEmpApprovall = dtEmpApprovall;
    }

    /**
     * @return the dtEmpApprovall1
     */
    public Date getDtEmpApprovall1() {
        return dtEmpApprovall1;
    }

    /**
     * @param dtEmpApprovall1 the dtEmpApprovall1 to set
     */
    public void setDtEmpApprovall1(Date dtEmpApprovall1) {
        this.dtEmpApprovall1 = dtEmpApprovall1;
    }

    /**
     * @return the dtEmpApprovall2
     */
    public Date getDtEmpApprovall2() {
        return dtEmpApprovall2;
    }

    /**
     * @param dtEmpApprovall2 the dtEmpApprovall2 to set
     */
    public void setDtEmpApprovall2(Date dtEmpApprovall2) {
        this.dtEmpApprovall2 = dtEmpApprovall2;
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
     * @return the docStsForm
     */
    public int getDocStsForm() {
        return docStsForm;
    }

    /**
     * @param docStsForm the docStsForm to set
     */
    public void setDocStsForm(int docStsForm) {
        this.docStsForm = docStsForm;
    }

    /**
     * @return the flagSaveEmployee
     */
    public String getFlagSaveEmployee() {
        return flagSaveEmployee;
    }

    /**
     * @param flagSaveEmployee the flagSaveEmployee to set
     */
    public void setFlagSaveEmployee(String flagSaveEmployee) {
        this.flagSaveEmployee = flagSaveEmployee;
    }
}
