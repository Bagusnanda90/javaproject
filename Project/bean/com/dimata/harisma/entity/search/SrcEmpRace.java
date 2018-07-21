package com.dimata.harisma.entity.search;

/**
 *
 * @author bayu
 */

public class SrcEmpRace {
    
    private long departmentId = 0;
    private String department = "";
    private long sectionId = 0;
    private String section = "";
    
    
    public SrcEmpRace() {
    }
    
    public long getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public long getSectionId() {
        return sectionId;
    }
    
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
    
    public String getSection() {
        return section;
    }
    
    public void setSection(String section) {
        this.section = section;
    }
    
}
