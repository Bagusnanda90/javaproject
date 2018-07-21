/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import java.util.Date;

/**
 *
 * @author Dimata 007
 */
public class SessEmpEducation {
    private long employee_id;
    private int startDate;
    private int endDate;
    private String graduation;
    private String education;

    /**
     * @return the startDate
     */
    public int getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public int getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the graduation
     */
    public String getGraduation() {
        return graduation;
    }

    /**
     * @param graduation the graduation to set
     */
    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    /**
     * @return the education
     */
    public String getEducation() {
        return education;
    }

    /**
     * @param education the education to set
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * @return the employee_id
     */
    public long getEmployee_id() {
        return employee_id;
    }

    /**
     * @param employee_id the employee_id to set
     */
    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }

   
}
