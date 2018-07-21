package com.dimata.harisma.entity.search;

/**
 *
 * @author guest
 */

public class SrcEndTraining {
    
    private int endPeriod = 0;
    private long departmentId = 0;
    private long sectionId = 0;
    private String sortField = "";
    
    public int getEndPeriod() {
        return endPeriod;
    }
    
    public void setEndPeriod(int endPeriod) {
        this.endPeriod = endPeriod;
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
    
    public String getSortField() {
        return sortField;
    }
    
    public void setSortField(String sortField)  {
        this.sortField = sortField;
    }
    
}
