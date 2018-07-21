/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.search;
import java.util.Date;
/**
 *
 * @author Tu Roy
 */
public class SrcLeaveAppAlClosing {
    
    /** Holds value of property empNum. */
    private String empNum = "";
    
    /** Holds value of property fullName. */
    private String fullName = "";
    
    /** Holds value of property departmentId. */
    private long departmentId = 0;
    
    /** Holds value of property sectionId. */
    private long sectionId = 0;
    
    /** Holds value of property positionId. */
    private long positionId = 0;
    
    /** Holds value of property submissionDate. */
    private Date empCommancingDateStart;
    
    private Date empCommancingDateEnd;
    
    private long categoryId = 0;
    
    private int resigned = 0;
    
    private int orderBy = 0;
    
    private long divisionId = 0;
    
    private long periodId = 0;
    
    private int RadioBtn = 0; 
    
    //update by satrya 2013-10-01
    private int status;

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public Date getEmpCommancingDateStart() {
        return empCommancingDateStart;
    }

    public void setEmpCommancingDateStart(Date empCommancingDateStart) {
        this.empCommancingDateStart = empCommancingDateStart;
    }

    public Date getEmpCommancingDateEnd() {
        return empCommancingDateEnd;
    }

    public void setEmpCommancingDateEnd(Date empCommancingDateEnd) {
        this.empCommancingDateEnd = empCommancingDateEnd;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getResigned() {
        return resigned;
    }

    public void setResigned(int resigned) {
        this.resigned = resigned;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    public int getRadioBtn() {
        return RadioBtn;
    }

    public void setRadioBtn(int RadioBtn) {
        this.RadioBtn = RadioBtn;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
