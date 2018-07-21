/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.canteen;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Roy Andika
 */
public class SumReportDepartment {

    private long divisionId;
    private long departmentId;
    private String division;
    private String department;
    private Vector visitByDate;
    private Date canteenVisitation;
    private int summary;

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
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the visitByDate
     */
    public Vector getVisitByDate() {
        return visitByDate;
    }

    /**
     * @param visitByDate the visitByDate to set
     */
    public void setVisitByDate(Vector visitByDate) {
        this.visitByDate = visitByDate;
    }

    /**
     * @return the canteenVisitation
     */
    public Date getCanteenVisitation() {
        return canteenVisitation;
    }

    /**
     * @param canteenVisitation the canteenVisitation to set
     */
    public void setCanteenVisitation(Date canteenVisitation) {
        this.canteenVisitation = canteenVisitation;
    }

    /**
     * @return the summary
     */
    public int getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(int summary) {
        this.summary = summary;
    }


}
