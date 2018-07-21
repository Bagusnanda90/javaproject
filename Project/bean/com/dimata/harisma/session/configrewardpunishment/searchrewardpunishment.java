/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.configrewardpunishment;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class searchrewardpunishment {
     
    private String empnumber;
    private String fullNameEmp;
    private Vector arrCompany = new Vector();
    private Vector arrDivision = new Vector();
    private Vector arrDepartmentId = new Vector();
    private Vector arrSectionId = new Vector();
    private Vector arrLocationId = new Vector();
    private int statusOpname;
    private Date dtPeriodFrom;
    private Date dtPeriodTo;
    private Vector order = new Vector();
    private Vector groupBy = new Vector();
    
     /**
     * @return the empnumber
     */
    public String getEmpnumber() {
        if(empnumber==null || empnumber.length()==0){
            return "";
        }
        return empnumber;
    }

    /**
     * @param empnumber the empnumber to set
     */
    public void setEmpnumber(String empnumber) {
        this.empnumber = empnumber;
    }
    
      /**
     * @return the fullNameEmp
     */
    public String getFullNameEmp() {
        if(fullNameEmp==null || fullNameEmp.length()==0){
            return "";
        }
        return fullNameEmp;
    }

    /**
     * @param fullNameEmp the fullNameEmp to set
     */
    public void setFullNameEmp(String fullNameEmp) {
        this.fullNameEmp = fullNameEmp;
    }

   /**
     * @return the arrCompany
     */
    public String[] getArrCompany(int idx) {
        if(idx>=arrCompany.size()){
            return null;
        }
        return (String[])arrCompany.get(idx);
    }

    /**
     * @param arrCompany the arrCompany to set
     */
    public void addArrCompany(String[] arrCompany) {
        this.arrCompany.add(arrCompany);
    }
    
      /**
     * @return the arrDivision
     */
    public Vector getArrDivision() {
        return arrDivision;
    }

    /**
     * @param arrDivision the arrDivision to set
     */
    public void setArrDivision(Vector arrDivision) {
        this.arrDivision = arrDivision;
    }

     /**
     * @return the arrDepartmentId
     */
    public String[] getArrDepartmentId(int idx) {
         if(idx>=arrDepartmentId.size()){
            return null;
        }
        return (String[])arrDepartmentId.get(idx); 
        //return arrDepartmentId;
    }

    /**
     * @param arrDepartmentId the arrDepartmentId to set
     */
    public void addArrDepartmentId(String[]  arrDepartmentId) {
        this.arrDepartmentId.add(arrDepartmentId);
        //this.arrDepartmentId = arrDepartmentId;
    }
    
    /**
     * @return the arrSectionId
     */
    public String[] getArrSectionId(int idx) {
         if(idx>=arrSectionId.size()){
            return null;
        }
         return (String[])arrSectionId.get(idx);
        //return arrSectionId;
    }

    /**
     * @param arrSectionId the arrSectionId to set
     */
    public void addArrSectionId(String[] arrSectionId) {
         this.arrSectionId.add(arrSectionId);
        //this.arrSectionId = arrSectionId;
    }

     public String[] getArrLocationId(int idx) {
        if(idx>=arrLocationId.size()){
            return null;
        }
        return (String[])arrLocationId.get(idx);
        //return arrLocationId;
    }
    

    /**
     * @param arrLocationId the arrLocationId to set
     */
    public void addArrLocationId(String[] arrLocationId) {
         this.arrLocationId.add(arrLocationId);
        //this.arrLocationId = arrLocationId;
    }

    /**
     * @return the statusOpname
     */
    public int getStatusOpname() {
        return statusOpname;
    }

    /**
     * @param statusOpname the statusOpname to set
     */
    public void setStatusOpname(int statusOpname) {
        this.statusOpname = statusOpname;
    }

    /**
     * @return the dtPeriodFrom
     */
    public Date getDtPeriodFrom() {
        return dtPeriodFrom;
    }

    /**
     * @param dtPeriodFrom the dtPeriodFrom to set
     */
    public void setDtPeriodFrom(Date dtPeriodFrom) {
        this.dtPeriodFrom = dtPeriodFrom;
    }

    /**
     * @return the dtPeriodTo
     */
    public Date getDtPeriodTo() {
        return dtPeriodTo;
    }

    /**
     * @param dtPeriodTo the dtPeriodTo to set
     */
    public void setDtPeriodTo(Date dtPeriodTo) {
        this.dtPeriodTo = dtPeriodTo;
    }

    /**
     * @return the order
     */
    public Vector getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Vector order) {
        this.order = order;
    }

    /**
     * @return the groupBy
     */
    public Vector getGroupBy() {
        return groupBy;
    }

    /**
     * @param groupBy the groupBy to set
     */
    public void setGroupBy(Vector groupBy) {
        this.groupBy = groupBy;
    }

   
  
}
