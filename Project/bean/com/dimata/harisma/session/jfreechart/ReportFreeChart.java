/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.jfreechart;

/**
 *
 * @author dimata005
 */
import com.dimata.qdep.entity.*;
public class ReportFreeChart extends Entity{
     private long  userId;
     private String fullName;
     private double  countReport;
     private long employeeId;
     private String departmentName;
     private long categoryId;
     private String categoryName;
    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
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
        if(fullName!=null && fullName.length()>0 && !fullName.equalsIgnoreCase("null")){
            this.fullName = fullName;
        } else {
            this.fullName = "Other";
        }
    }

    /**
     * @return the countReport
     */
    public double getCountReport() {
        return countReport;
    }

    /**
     * @param countReport the countReport to set
     */
    public void setCountReport(double countReport) {
        this.countReport = countReport;
    }

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
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the categoryId
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    
     
}
