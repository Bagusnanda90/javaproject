/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.search;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Gede115
 */
public class SrcOvertimeReport {
    private long companyId;
    private long divisionId;
    private long departmentId;
    private Date requestDate=new Date();
    private Date requestDateTo=new Date();
    private Vector statusDoc = new Vector();
    private long religionId;
    private String payroll="";
    private String fullname="";
    private long sectionId;

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
     * @return the requestDateTo
     */
    public Date getRequestDateTo() {
        return requestDateTo;
    }

    /**
     * @param requestDateTo the requestDateTo to set
     */
    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    /**
     * @return the statusDoc
     */
    public Vector getStatusDoc() {
        return statusDoc;
    }

    /**
     * @param statusDoc the statusDoc to set
     */
    public void setStatusDoc(Vector statusDoc) {
        this.statusDoc = statusDoc;
    }

    /**
     * @return the religionId
     */
    public long getReligionId() {
        return religionId;
    }

    /**
     * @param religionId the religionId to set
     */
    public void setReligionId(long religionId) {
        this.religionId = religionId;
    }

    /**
     * @return the payroll
     */
    public String getPayroll() {
        return payroll;
    }

    /**
     * @param payroll the payroll to set
     */
    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
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
