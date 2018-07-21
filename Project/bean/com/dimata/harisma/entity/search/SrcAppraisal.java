
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.entity.search;

/* package java */
import java.util.Date;

public class SrcAppraisal {

    private String employee = "";
    private String appraisor = "";
    private double averagestart;
    private long rank;
    private long departmentId;
    private Date datestart;
    private double averageend;
    private Date dateend;
    private int approved;
    private int orderBy;

    //add for ver x Hardrock
    public int getApproved() {
        return approved;
    }
    //add for ver x Hardrock
    public void setApproved(int approved) {
        this.approved = approved;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getAppraisor() {
        return appraisor;
    }

    public void setAppraisor(String appraisor) {
        this.appraisor = appraisor;
    }

    public double getAveragestart() {
        return averagestart;
    }

    public void setAveragestart(double averagestart) {
        this.averagestart = averagestart;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public Date getDatestart() {
        return datestart;
    }

    public void setDatestart(Date datestart) {
        this.datestart = datestart;
    }

    public double getAverageend() {
        return averageend;
    }

    public void setAverageend(double averageend) {
        this.averageend = averageend;
    }

    public Date getDateend() {
        return dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }
}
