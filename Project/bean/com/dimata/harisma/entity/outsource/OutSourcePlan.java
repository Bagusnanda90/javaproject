/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author Kartika
 */
//public class OutSourcePlan {
//    
//}
import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class OutSourcePlan extends Entity {

    private int planYear = 0;
    private String title = "";
    private long companyId = 0;
    private Date createdDate = null;
    private long createdById = 0;
    private Date approvedDate = null;
    private long approveById = 0;
    private String note = "";
    private int status = 0;
    private Date validFrom=null;
    private Date validTo=null;
    
    private String createName="";
    private String approveName="";
    
    public int getPlanYear() {
        return planYear;
    }

    public void setPlanYear(int planYear) {
        this.planYear = planYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public long getApproveById() {
        return approveById;
    }

    public void setApproveById(long approveById) {
        this.approveById = approveById;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the validFrom
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * @param validFrom the validFrom to set
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * @return the validTo
     */
    public Date getValidTo() {
        return validTo;
    }

    /**
     * @param validTo the validTo to set
     */
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    /**
     * @return the createName
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * @param createName the createName to set
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * @return the approveName
     */
    public String getApproveName() {
        return approveName;
    }

    /**
     * @param approveName the approveName to set
     */
    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }
}