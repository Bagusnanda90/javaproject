
package com.dimata.harisma.entity.configrewardnpunisment;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class SrcRewardPunishment {
 
       
    private String empnumber;
    private String fullNameEmp;
    private Vector arrCompany = new Vector();
    private Vector arrDivision = new Vector();
    private Vector arrDepartmentId = new Vector();
    private Vector arrSectionId = new Vector();
    private Vector arrLocationId = new Vector();
    private Vector arrJenisSo = new Vector();
    private int statusOpname;
    private Vector dtPeriodfrom = new Vector();
    private Vector dtPeriodto = new Vector();
    private int order;
    private Vector sort = new Vector();
    private Vector groupBy = new Vector();
    private int radioperiode;
    private int radiostatusso;
    private String rewardPunismentMainId;
    
    public static final int ORDER_EMPLOYEE_NAME = 0;
    public static final int ORDER_EMPLOYEE_NUM = 1;
    public static final int ORDER_TOTAL = 2;
    public static final int ORDER_BEBAN = 3;
    public static final int ORDER_TUNAI = 4;

        public static final int[] orderValue = { 0,1,2,3,4 };

        public static final String[] orderKey  = {"Name", "Employee Number", "Total","Beban","Tunai"};

        public static Vector getOrderValue() {
            Vector order = new Vector();
            for(int i=0;i<orderValue.length;i++) {
                order.add(String.valueOf(orderValue[i]));
            }
            return order;
        }

        public static Vector getOrderKey() {
            Vector order = new Vector();
            for(int i=0;i<orderKey.length;i++) {
                order.add(orderKey[i]);
            }
            return order;
        }
    
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
    public String[] getArrDivision(int idx) {
        //return arrDivision;
          if(idx>=arrDivision.size()){
            return null;
        }
         return (String[])arrDivision.get(idx);
    }

    /**
     * @param arrDivision the arrDivision to set
     */
    public void addArrDivision(String[] arrDivision) {
        //this.arrDivision = arrDivision;
         this.arrDivision.add(arrDivision);
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
     * @return the dtPeriodfrom
     */
    public Date getDtPeriodFrom(int idx) {
       // return endcontractto;
        if(idx>=dtPeriodfrom.size()){
            return null;
        }
        return (Date)dtPeriodfrom.get(idx);
    }

    /**
     * @param dtPeriodfrom
     */
    public void addDtPeriodFrom(Date dtperiodfrom) {
        //this.commdateto = commdateto;
        this.dtPeriodfrom.add(dtperiodfrom);
    }

 /**
     * @return the dtperiodto
     */
    public Date getDtPeriodTo(int idx) {
       // return endcontractto;
        if(idx>=dtPeriodto.size()){
            return null;
        }
        return (Date)dtPeriodto.get(idx);
    }

    /**
     * @param dtperiodto the dtperiodto to set
     */
    public void addDtPeriodTo(Date dtperiodto) {
        //this.commdateto = commdateto;
        this.dtPeriodto.add(dtperiodto);
    }
   
   /**
     * @return the order
     */
    public String getGroupBy(int idx) {
       // return groupBy;
        if(idx>=groupBy.size()){
            return null;
        }
        return (String)groupBy.get(idx);
    }

    /**
     * @param order the groupBy to set
     */
    public void addGroupBy(String groupBy) {
       // this.groupBy = groupBy;
         this.groupBy.add(groupBy);
    }
    

public String[] getArrJenisSo(int idx) {
        if(idx>=arrJenisSo.size()){
            return null;
        }
        return (String[])arrJenisSo.get(idx);
        //return arrLocationId;
    }
    

    public void addArrJenisSo(String[] arrJenisSo) {
         this.arrJenisSo.add(arrJenisSo);
        //this.arrLocationId = arrLocationId;
    }   

    /**
     * @return the radioperiode
     */
    public int getRadioperiode() {
        return radioperiode;
    }

    /**
     * @param radioperiode the radioperiode to set
     */
    public void setRadioperiode(int radioperiode) {
        this.radioperiode = radioperiode;
    }

    /**
     * @return the radiostatusso
     */
    public int getRadiostatusso() {
        return radiostatusso;
    }

    /**
     * @param radiostatusso the radiostatusso to set
     */
    public void setRadiostatusso(int radiostatusso) {
        this.radiostatusso = radiostatusso;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }
  /**
     * @return the sort
     */
    public String getSort(int idx) {
       // return sort;
        if(idx>=sort.size()){
            return null;
        }
        return (String)sort.get(idx);
    }

    /**
     * @param sort the sort to set
     */
    public void addSort(String sort) {
       // this.sort = sort;
         this.sort.add(sort);
    }

    /**
     * @return the rewardPunismentMainId
     */
    public String getRewardPunismentMainId() {
        return rewardPunismentMainId;
    }

    /**
     * @param rewardPunismentMainId the rewardPunismentMainId to set
     */
    public void setRewardPunismentMainId(String rewardPunismentMainId) {
        this.rewardPunismentMainId = rewardPunismentMainId;
    }

   
}