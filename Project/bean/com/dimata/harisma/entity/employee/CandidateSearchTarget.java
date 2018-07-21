/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

/**
 *
 * @author Dimata 007
 */
import com.dimata.qdep.entity.Entity;

public class CandidateSearchTarget extends Entity {

    private long candidateMainId = 0;
    private String companyIds = "";
    private String divisionIds = "";
    private String departmentIds = "";
    private String sectionIds = "";
    private String positionIds = "";
    private String levelIds = "";
    private String empCategoryIds = "";
    private int sex = 0;

    public long getCandidateMainId() {
        return candidateMainId;
    }

    public void setCandidateMainId(long candidateMainId) {
        this.candidateMainId = candidateMainId;
    }

    public String getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    public String getDivisionIds() {
        return divisionIds;
    }

    public void setDivisionIds(String divisionIds) {
        this.divisionIds = divisionIds;
    }

    public String getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }

    public String getSectionIds() {
        return sectionIds;
    }

    public void setSectionIds(String sectionIds) {
        this.sectionIds = sectionIds;
    }

    public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }

    public String getLevelIds() {
        return levelIds;
    }

    public void setLevelIds(String levelIds) {
        this.levelIds = levelIds;
    }

    public String getEmpCategoryIds() {
        return empCategoryIds;
    }

    public void setEmpCategoryIds(String empCategoryIds) {
        this.empCategoryIds = empCategoryIds;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}