/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.log;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class LogSysHistory extends Entity {

    private long logDocumentId = 0;
    private long logUserId = 0;
    private String logLoginName = "";
    private String logDocumentNumber = "";
    private String logDocumentType = "";
    private String logUserAction = "";
    private String logOpenUrl = "";
    private Date logUpdateDate = null;
    private String logApplication = "";
    private String logDetail = "";
    private int logStatus = 0;
    private long approverId = 0;
    private Date approveDate = null;
    private String approverNote = "";
    private String logPrev = "";
    private String logCurr = "";
    private String logModule = "";
    private long logEditedUserId = 0;
    /* Update by Hendra Putu | 20160517 */
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    

    public long getLogDocumentId() {
        return logDocumentId;
    }

    public void setLogDocumentId(long logDocumentId) {
        this.logDocumentId = logDocumentId;
    }

    public long getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(long logUserId) {
        this.logUserId = logUserId;
    }

    public String getLogLoginName() {
        return logLoginName;
    }

    public void setLogLoginName(String logLoginName) {
        this.logLoginName = logLoginName;
    }

    public String getLogDocumentNumber() {
        return logDocumentNumber;
    }

    public void setLogDocumentNumber(String logDocumentNumber) {
        this.logDocumentNumber = logDocumentNumber;
    }

    public String getLogDocumentType() {
        return logDocumentType;
    }

    public void setLogDocumentType(String logDocumentType) {
        this.logDocumentType = logDocumentType;
    }

    public String getLogUserAction() {
        return logUserAction;
    }

    public void setLogUserAction(String logUserAction) {
        this.logUserAction = logUserAction;
    }

    public String getLogOpenUrl() {
        return logOpenUrl;
    }

    public void setLogOpenUrl(String logOpenUrl) {
        this.logOpenUrl = logOpenUrl;
    }

    public Date getLogUpdateDate() {
        return logUpdateDate;
    }

    public void setLogUpdateDate(Date logUpdateDate) {
        this.logUpdateDate = logUpdateDate;
    }

    public String getLogApplication() {
        return logApplication;
    }

    public void setLogApplication(String logApplication) {
        this.logApplication = logApplication;
    }

    public String getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public int getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(int logStatus) {
        this.logStatus = logStatus;
    }

    public long getApproverId() {
        return approverId;
    }

    public void setApproverId(long approverId) {
        this.approverId = approverId;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproverNote() {
        return approverNote;
    }

    public void setApproverNote(String approverNote) {
        this.approverNote = approverNote;
    }

    public String getLogPrev() {
        return logPrev;
    }

    public void setLogPrev(String logPrev) {
        this.logPrev = logPrev;
    }

    public String getLogCurr() {
        return logCurr;
    }

    public void setLogCurr(String logCurr) {
        this.logCurr = logCurr;
    }

    public String getLogModule() {
        return logModule;
    }

    public void setLogModule(String logModule) {
        this.logModule = logModule;
    }

    /**
     * @return the logEditedUserId
     */
    public long getLogEditedUserId() {
        return logEditedUserId;
    }

    /**
     * @param logEditedUserId the logEditedUserId to set
     */
    public void setLogEditedUserId(long logEditedUserId) {
        this.logEditedUserId = logEditedUserId;
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
}
