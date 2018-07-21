/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.search;

/**
 * Date : 2015-09-11
 *
 * @author Hendra Putu
 */
public class SrcStructure {
    /*
     * current|history = rb_time
     * startdate = start_date
     * enddate = end_date
     * pusat|cabang = structure_op
     * selection structure = structure_select
     * until level = level_rank
     * company = company_id
     * division = division_id
     * department = department_id
     * section = section_id
     * show foto = chk_photo
     * show gap = chk_gap
     */

    private int rbTime = 0;
    private String startDate = "";
    private String endDate = "";
    private int structureOp = 0;
    private int structureSelect = 0;
    private int levelRank = 0;
    private long companyId = 0;
    private long divisionId = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private int chkPhoto = 0;
    private int chkGap = 0;

    /**
     * @return the rbTime
     */
    public int getRbTime() {
        return rbTime;
    }

    /**
     * @param rbTime the rbTime to set
     */
    public void setRbTime(int rbTime) {
        this.rbTime = rbTime;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the structureOp
     */
    public int getStructureOp() {
        return structureOp;
    }

    /**
     * @param structureOp the structureOp to set
     */
    public void setStructureOp(int structureOp) {
        this.structureOp = structureOp;
    }

    /**
     * @return the structureSelect
     */
    public int getStructureSelect() {
        return structureSelect;
    }

    /**
     * @param structureSelect the structureSelect to set
     */
    public void setStructureSelect(int structureSelect) {
        this.structureSelect = structureSelect;
    }

    /**
     * @return the levelRank
     */
    public int getLevelRank() {
        return levelRank;
    }

    /**
     * @param levelRank the levelRank to set
     */
    public void setLevelRank(int levelRank) {
        this.levelRank = levelRank;
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
     * @return the chkPhoto
     */
    public int getChkPhoto() {
        return chkPhoto;
    }

    /**
     * @param chkPhoto the chkPhoto to set
     */
    public void setChkPhoto(int chkPhoto) {
        this.chkPhoto = chkPhoto;
    }

    /**
     * @return the chkGap
     */
    public int getChkGap() {
        return chkGap;
    }

    /**
     * @param chkGap the chkGap to set
     */
    public void setChkGap(int chkGap) {
        this.chkGap = chkGap;
    }
}
