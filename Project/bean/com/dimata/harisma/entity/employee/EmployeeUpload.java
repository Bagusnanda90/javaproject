/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;
/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
import java.util.Hashtable;
/**
 *
 * @author Dimata 007
 */
public class EmployeeUpload extends Entity{
    
    private long empId;
    private String empName;
    private String empNumb;
    private String empNameKeyPayrol;
     private Hashtable schedule = new Hashtable();
   private String deptName;
   private String creatorName;
   private String empNumberExcel;
     
     
    public void addSchedule(int idx, String symbol) {
        if (symbol != null && symbol.length()>0) {
            
            getSchedule().put(idx, symbol);
        }
    }
    public boolean getScheduleCheck(int idxDt) {
        if (getSchedule().containsKey(idxDt)) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @param idxDt : berdasarkan colomnya di sana tersetting 3
     * @return 
     */
     public String getSchedule(int idxDt) {
         String symbol="";
       symbol = String.valueOf(getSchedule().get(idxDt));
            return symbol;
    }
    
    /**
     * @return the empId
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(long empId) {
        this.empId = empId;
    }

    /**
     * @return the empName
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @return the empNumb
     */
    public String getEmpNumb() {
        return empNumb;
    }

    /**
     * @param empNumb the empNumb to set
     */
    public void setEmpNumb(String empNumb) {
        this.empNumb = empNumb;
    }

    /**
     * @return the empNameKeyPayrol
     */
    public String getEmpNameKeyPayrol() {
        return empNameKeyPayrol;
    }

    /**
     * @param empNameKeyPayrol the empNameKeyPayrol to set
     */
    public void setEmpNameKeyPayrol(String empNameKeyPayrol) {
        this.empNameKeyPayrol = empNameKeyPayrol;
    }

    /**
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the schedule
     */
    public Hashtable getSchedule() {
        return schedule;
    }

    /**
     * @return the empNumberExcel
     */
    public String getEmpNumberExcel() {
        return empNumberExcel;
    }

    /**
     * @param empNumberExcel the empNumberExcel to set
     */
    public void setEmpNumberExcel(String empNumberExcel) {
        this.empNumberExcel = empNumberExcel;
    }

    
}
