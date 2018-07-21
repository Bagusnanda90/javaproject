/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;
import com.dimata.qdep.entity.Entity;
import java.util.Date;
/**
 *
 * @author Dimata 007
 */
public class PublicLeaveDetail extends Entity{
    private long employeeId;
    private long typeLeaveId;
    private long publicLeaveId;
    private long publicHolidayId;

    private String employeeNum;
    private String fullName;
    private String division;
    private String departement;
    private String section;
    private String empCategory;
    private String note;
    private long empCategoryId;
    
    private long appLeaveId;
    private Date dateFrom;
    private Date dateTo;
    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the typeLeaveId
     */
    public long getTypeLeaveId() {
        return typeLeaveId;
    }

    /**
     * @param typeLeaveId the typeLeaveId to set
     */
    public void setTypeLeaveId(long typeLeaveId) {
        this.typeLeaveId = typeLeaveId;
    }

    /**
     * @return the publicLeaveId
     */
    public long getPublicLeaveId() {
        return publicLeaveId;
    }

    /**
     * @param publicLeaveId the publicLeaveId to set
     */
    public void setPublicLeaveId(long publicLeaveId) {
        this.publicLeaveId = publicLeaveId;
    }

    /**
     * @return the publicHolidayId
     */
    public long getPublicHolidayId() {
        return publicHolidayId;
    }

    /**
     * @param publicHolidayId the publicHolidayId to set
     */
    public void setPublicHolidayId(long publicHolidayId) {
        this.publicHolidayId = publicHolidayId;
    }

    /**
     * @return the employeeNum
     */
    public String getEmployeeNum() {
        return employeeNum;
    }

    /**
     * @param employeeNum the employeeNum to set
     */
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * @param departement the departement to set
     */
    public void setDepartement(String departement) {
        this.departement = departement;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the empCategory
     */
    public String getEmpCategory() {
        return empCategory;
    }

    /**
     * @param empCategory the empCategory to set
     */
    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
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
     * @return the empCategoryId
     */
    public long getEmpCategoryId() {
        return empCategoryId;
    }

    /**
     * @param empCategoryId the empCategoryId to set
     */
    public void setEmpCategoryId(long empCategoryId) {
        this.empCategoryId = empCategoryId;
    }

    /**
     * @return the appLeaveId
     */
    public long getAppLeaveId() {
        return appLeaveId;
    }

    /**
     * @param appLeaveId the appLeaveId to set
     */
    public void setAppLeaveId(long appLeaveId) {
        this.appLeaveId = appLeaveId;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
