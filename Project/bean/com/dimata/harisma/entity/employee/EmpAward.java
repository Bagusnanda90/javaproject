
package com.dimata.harisma.entity.employee;


// import java
import java.util.Date;

// import qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author bayu
 */

public class EmpAward extends Entity {
    
    private long employeeId = 0;
    private long deptId = 0;
    private long sectId = 0;
    private Date awardDate = new Date();
    private long awardType = 0;
    private String awardDesc = "";
    private long providerId=0;
    private String title = "";
    private long divisionId=0;
    private long companyId = 0;
    private String document = "";
    private long docId = 0;
    private int acknowledgeStatus = 0;
    private String externalFrom = "";
    
    
    public EmpAward() {
    }

    public long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    public long getDepartmentId() {
        return deptId;
    }
    
    public void setDepartmentId(long deptId) {
        this.deptId = deptId;
    }
    
    public long getSectionId() {
        return sectId;
    }
    
    public void setSectionId(long sectId) {
        this.sectId = sectId;
    }
    
    public Date getAwardDate() {
        return awardDate;
    }
    
    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }
    
    public long getAwardType() {
        return awardType;
    }
    
    public void setAwardType(long awardType) {
        this.awardType = awardType;
    }
    
    public String getAwardDescription() {
        return awardDesc;
    }
    
    public void setAwardDescription(String awardDesc) {
        this.awardDesc = awardDesc;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the docId
     */
    public long getDocId() {
        return docId;
    }

    /**
     * @param docId the docId to set
     */
    public void setDocId(long docId) {
        this.docId = docId;
    }

    /**
     * @return the acknowledgeStatus
     */
    public int getAcknowledgeStatus() {
        return acknowledgeStatus;
    }

    /**
     * @param acknowledgeStatus the acknowledgeStatus to set
     */
    public void setAcknowledgeStatus(int acknowledgeStatus) {
        this.acknowledgeStatus = acknowledgeStatus;
    }

    /**
     * @return the externalFrom
     */
    public String getExternalFrom() {
        return externalFrom;
    }

    /**
     * @param externalFrom the externalFrom to set
     */
    public void setExternalFrom(String externalFrom) {
        this.externalFrom = externalFrom;
    }
    
}
