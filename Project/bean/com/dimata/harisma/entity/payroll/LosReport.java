/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class LosReport {
    private String payrollNumber = "";
    private String fullName = "";
    private long payPeriodId = 0;
    private Date asPerDate = new Date();
    private Vector arrDivision = new Vector();
    private Vector arrDepartment = new Vector();
    private Vector arrSection = new Vector();
    private Vector arrEmpCategory = new Vector();
    private Vector arrPayrollGroup = new Vector();
    

    /**
     * @return the payrollNumber
     */
    public String getPayrollNumber() {
        return payrollNumber;
    }

    /**
     * @param payrollNumber the payrollNumber to set
     */
    public void setPayrollNumber(String payrollNumber) {
        this.payrollNumber = payrollNumber;
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
        this.fullName = fullName;
    }

    /**
     * @return the payPeriodId
     */
    public long getPayPeriodId() {
        return payPeriodId;
    }

    /**
     * @param payPeriodId the payPeriodId to set
     */
    public void setPayPeriodId(long payPeriodId) {
        this.payPeriodId = payPeriodId;
    }

    /**
     * @return the asPerDate
     */
    public Date getAsPerDate() {
        return asPerDate;
    }

    /**
     * @param asPerDate the asPerDate to set
     */
    public void setAsPerDate(Date asPerDate) {
        this.asPerDate = asPerDate;
    }
    
          /**
     * @return the arrDivision
     */
    public String[] getArrDivision(int idx) {
         if(idx>=arrDivision.size()){
            return null;
        }
        return (String[])arrDivision.get(idx); 
    }

    /**
     * @param arrDivision the arrDivision to set
     */
    public void addArrDivision(String[]  arrDivision) {
        
            this.arrDivision.add(arrDivision);
        
    }
    
     /**
     * @return the arrDepartment
     */
    public String[] getArrDepartment(int idx) {
         if(idx>=arrDivision.size()){
            return null;
        }
        return (String[])arrDepartment.get(idx); 
    }

    /**
     * @param arrDepartment the arrDepartment to set
     */
    public void addArrDepartment(String[]  arrDepartment) {
       
            this.arrDepartment.add(arrDepartment);
        
    }
    
    
     /**
     * @return the arrDepartment
     */
    public String[] getArrSection(int idx) {
         if(idx>=arrSection.size()){
            return null;
        }
        return (String[])arrSection.get(idx); 
    }

    /**
     * @param arrDepartment the arrDepartment to set
     */
    public void addArrSection(String[]  arrSection) {
            this.arrSection.add(arrSection);
        
    }
    
    
     /**
     * @return the arrDepartment
     */
    public String[] getArrEmpCategory(int idx) {
         if(idx>=arrEmpCategory.size()){
            return null;
        }
        return (String[])arrEmpCategory.get(idx); 
    }

    /**
     * @param arrDepartment the arrDepartment to set
     */
    public void addArrEmpCategory(String[]  arrEmpCategory) {
        
            this.arrEmpCategory.add(arrEmpCategory);
        
    }
    
    
     /**
     * @return the arrDepartment
     */
    public String[] getArrPayrollGroup(int idx) {
         if(idx>=arrPayrollGroup.size()){
            return null;
        }
        return (String[])arrPayrollGroup.get(idx); 
    }

    /**
     * @param arrDepartment the arrDepartment to set
     */
    public void addArrPayrollGroup(String[]  arrPayrollGroup) {
       
            this.arrPayrollGroup.add(arrPayrollGroup);
        
    }
    public String[] getArrPayrollGroupAll() {
        return (String[])arrPayrollGroup.get(0); 
    }
    public String[] getArrDivisionAll() {
        return (String[])arrDivision.get(0); 
    }
    public String[] getArrDepartmentAll() {
        return (String[])arrDepartment.get(0); 
    }
    public String[] getArrSectionAll() {
        return (String[])arrSection.get(0); 
    }
    public String[] getArrEmpCatAll() {
        return (String[])arrEmpCategory.get(0); 
    }
    
    public Vector getCompShowLos() {
        Vector listPayComp = PstPayComponent.list(0, 0, PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_LOS_REPORT] + " = " + 1, "");
        return listPayComp; 
    }
    
}
