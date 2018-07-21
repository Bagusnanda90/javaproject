
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.entity.employee;

/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class EmpEducation extends Entity {

    private long educationId;
    private long employeeId;
    private int startDate;
    private int endDate;
    private String graduation = "";
    private String educationDesc = "";
    private float point = 0;
    private long institutionId = 0;

    public long getEducationId() {
        return educationId;
    }

    public void setEducationId(long educationId) {
        this.educationId = educationId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        if (graduation == null) {
            graduation = "";
        }
        this.graduation = graduation;
    }

    public String getEducationDesc() {
        return educationDesc;
    }

    public void setEducationDesc(String educationDesc) {
        if (educationDesc == null) {
            educationDesc = "";
        }
        this.educationDesc = educationDesc;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(long institutionId) {
        this.institutionId = institutionId;
    }
}
