/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Kartika
 */
public class OutSourceCost extends Entity {
    private String title = "";
    private long companyId = 0;
    private String companyName = "";
    private Date createdDate = null;
    private long createdById = 0;
    private String createByName = "";
    private Date approvedDate = null;
    private long approvedById = 0;
    private String approvedByName = "";
    private String note = "";
    private int statusDoc = 0;
    private long divisionId = 0;
    private String divisionName = "";
    private long departmentId = 0;
    private String departmentName = "";
    private long sectionId = 0;
    private String sectionName = "";
    private Date dateOfPay = null;
    private long periodId = 0;
    private String periodName = "";

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the createdById
     */
    public long getCreatedById() {
        return createdById;
    }

    /**
     * @param createdById the createdById to set
     */
    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    /**
     * @return the approvedDate
     */
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     * @param approvedDate the approvedDate to set
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     * @return the approvedById
     */
    public long getApprovedById() {
        return approvedById;
    }

    /**
     * @param approvedById the approvedById to set
     */
    public void setApprovedById(long approvedById) {
        this.approvedById = approvedById;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
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
     * @return the dateOfEval
     */
    public Date getDateOfPay() {
        return dateOfPay;
    }

    /**
     * @param dateOfEval the dateOfEval to set
     */
    public void setDateOfPay(Date dateOfPay) {
        this.dateOfPay = dateOfPay;
    }

    /**
     * @return the periodId
     */
    public long getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the createByName
     */
    public String getCreateByName() {
        return createByName;
    }

    /**
     * @param createByName the createByName to set
     */
    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    /**
     * @return the approvedByName
     */
    public String getApprovedByName() {
        return approvedByName;
    }

    /**
     * @param approvedByName the approvedByName to set
     */
    public void setApprovedByName(String approvedByName) {
        this.approvedByName = approvedByName;
    }

    /**
     * @return the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the periodName
     */
    public String getPeriodName() {
        return periodName;
    }

    /**
     * @param periodName the periodName to set
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }
}
