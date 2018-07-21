
package com.dimata.harisma.entity.search;

import java.util.Date;

/**
 *
 * @author bayu
 */

public class SrcTrainingRpt {
    
    private long departmentId = 0;
    private long sectionId = 0;
    private String payroll = "";
    private String name = "";
    private Date startDate = new Date();
    private Date endDate = new Date();
    private int sortBy = 0;
    private String trainer = "";
    private long trainingId = 0;
    private Date trainingMonth = new Date();
    

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayroll() {
        return payroll;
    }

    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public Date getTrainingMonth() {
        return trainingMonth;
    }

    public void setTrainingMonth(Date trainingMonth) {
        this.trainingMonth = trainingMonth;
    }
 
}
