/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.employee;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Dimata 007
 */
public class SearchSpecialQuery {

    private Vector arrCompany = new Vector();
    private Vector arrDepartmentId = new Vector();
    private Vector arrPositionId = new Vector();
    private Vector arrLocationId = new Vector();
    private Vector arrGradeId = new Vector();
    private Vector arrSectionId = new Vector();
    private Vector arrSubSectionId = new Vector();
    private Vector arrLevelId = new Vector();
    private Vector arrEducationId = new Vector();
    private Vector arrReligionId = new Vector();
    private Vector arrBlood = new Vector();
    private Vector arrLanguageId = new Vector();
    private Vector arrMaritalId = new Vector();
    private int iSex;
    private int iResigned;
    private int radiocommdate;
    private int radioendcontract;
    private Vector commdatefrom = new Vector();
    private Vector commdateto = new Vector();
    private Vector workyearfrom = new Vector();
    private Vector workmonthfrom = new Vector();
    private Vector workyearto = new Vector();
    private Vector workmonthto = new Vector();
    private Vector ageyearfrom = new Vector();
    private Vector agemonthfrom = new Vector();
    private Vector ageyearto = new Vector();
    private Vector agemonthto = new Vector();
    private Vector sort = new Vector();
    //by priska 29-10-2014
    private Vector style =  new Vector();
    
    
    private Vector arrRaceId = new Vector();
    
    private String empnumber;
    private String fullNameEmp;
    private String addrsEmployee;
    private String AddressPermanent;
    private Vector arrEmpCategory = new Vector();
    private Vector arrDivision = new Vector();
    private boolean birthdayChecked =true;
    private int birthMonth;
    private String salaryLevel;
    private Date startResign  = new Date();
    private Date endResign = new Date();
    
    private Date dtBirthFrom = new Date();
    private Date dtBirthTo  = new Date();
    private Vector arrCountryId = new Vector();
    private Vector arrProvinsiId = new Vector();
    private Vector arrKabupatenId = new Vector();
    private Vector arrKecamatanId = new Vector();
   // private int monthBirth;
    private Vector arrProvinsiIdPermanent = new Vector();
    private Vector arrKabupatenIdPermanent = new Vector();
    private Vector arrKecamatanIdPermanent = new Vector();
    private Vector arrCountryIdPermanent = new Vector();
    
    //priska 30-10-2014
    private Vector endcontractfrom = new Vector();
    private Vector endcontractto = new Vector();
    
    
    private Vector arrPayrollGroupId = new Vector();
    private int birthYearFrom;
    private int birthYearTo;
    
    //update by satrya 2013-10-16
    private Date dtPeriodStart = new Date();
    private Date dtPeriodEnd = new Date();
    
    //update by priska 2014 10 29
    private int stylist;
    
     //update by satrya 2013-11-13
        private Date dtCarrierWorkStart = new Date();
        private Date dtCarrierWorkEnd = new Date();
        private long carrierCategoryEmp;
     /**
     * @return the arrKabupatenId
     */
        // Update by Putu Hendra 2015-01-31
        private long resignedReasonId;
        
    public String[] getArrCountryId(int idx) {
        if(idx>=arrCountryId.size()){
            return null;
        }
        return (String[])arrCountryId.get(idx);
    }

    /**
     * @param arrKecamatanId the arrCompany to set
     */
    public void addArrCountryId(String[] arrCountryId) {
        this.arrCountryId.add(arrCountryId);
    }
    
    
    
     /**
     * @return the arrKabupatenIdPermanent
     */
    public String[] getArrCountryIdPermanent(int idx) {
        if(idx>=arrCountryIdPermanent.size()){
            return null;
        }
        return (String[])arrCountryIdPermanent.get(idx);
    }

    /**
     * @param arrKecamatanIdPermanent the arrCompany to set
     */
    public void addArrCountryIdPermanent(String[] arrCountryIdPermanent) {
        this.arrCountryIdPermanent.add(arrCountryIdPermanent);
    }
    
    
     /**
     * @return the arrKabupatenIdPermanent
     */
    public String[] getArrKecamatanIdPermanent(int idx) {
        if(idx>=arrKecamatanIdPermanent.size()){
            return null;
        }
        return (String[])arrKecamatanIdPermanent.get(idx);
    }

    /**
     * @param arrKecamatanIdPermanent the arrCompany to set
     */
    public void addArrKecamatanIdPermanent(String[] arrKecamatanIdPermanent) {
        this.arrKecamatanIdPermanent.add(arrKecamatanIdPermanent);
    }
    
    /**
     * @return the arrProvinsiIdPermanent
     */
    public String[] getArrProvinsiIdPermanent(int idx) {
        if(idx>=arrProvinsiIdPermanent.size()){
            return null;
        }
        return (String[])arrProvinsiIdPermanent.get(idx);
    }

    /**
     * @param arrProvinsiIdPermanent the arrCompany to set
     */
    public void addArrProvinsiIdPermanent(String[] arrProvinsiIdPermanent) {
        this.arrProvinsiIdPermanent.add(arrProvinsiIdPermanent);
    }
    
      /**
     * @return the arrKabupatenIdPermanent
     */
    public String[] getArrKabupatenIdPermanent(int idx) {
        if(idx>=arrKabupatenIdPermanent.size()){
            return null;
        }
        return (String[])arrKabupatenIdPermanent.get(idx);
    }

    /**
     * @param arrKabupatenIdPermanent the arrCompany to set
     */
    public void addArrKabupatenIdPermanent(String[] arrKabupatenIdPermanent) {
        this.arrKabupatenIdPermanent.add(arrKabupatenIdPermanent);
    }
    
    
    
    
     
      /**
     * @return the arrKabupatenId
     */
    public String[] getArrKecamatanId(int idx) {
        if(idx>=arrKecamatanId.size()){
            return null;
        }
        return (String[])arrKecamatanId.get(idx);
    }

    /**
     * @param arrKecamatanId the arrCompany to set
     */
    public void addArrKecamatanId(String[] arrKecamatanId) {
        this.arrKecamatanId.add(arrKecamatanId);
    }
    
    /**
     * @return the arrProvinsiId
     */
    public String[] getArrProvinsiId(int idx) {
        if(idx>=arrProvinsiId.size()){
            return null;
        }
        return (String[])arrProvinsiId.get(idx);
    }

    /**
     * @param arrProvinsiId the arrCompany to set
     */
    public void addArrProvinsiId(String[] arrProvinsiId) {
        this.arrProvinsiId.add(arrProvinsiId);
    }
    
      /**
     * @return the arrKabupatenId
     */
    public String[] getArrKabupatenId(int idx) {
        if(idx>=arrKabupatenId.size()){
            return null;
        }
        return (String[])arrKabupatenId.get(idx);
    }

    /**
     * @param arrKabupatenId the arrCompany to set
     */
    public void addArrKabupatenId(String[] arrKabupatenId) {
        this.arrKabupatenId.add(arrKabupatenId);
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
     * @return the arrPositionId
     */
    public String[] getArrPositionId(int idx) {
        if(idx>=arrPositionId.size()){
            return null;
        }
        return (String[])arrPositionId.get(idx);
        //return arrPositionId;
    }
    

    /**
     * @param arrPositionId the arrPositionId to set
     */
    public void addArrPositionId(String[] arrPositionId) {
         this.arrPositionId.add(arrPositionId);
        //this.arrPositionId = arrPositionId;
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
    
    /**
     * @return the arrSubSectionId
     */
    public String[] getArrSubSectionId(int idx) {
         if(idx>=arrSubSectionId.size()){
            return null;
        }
         return (String[])arrSubSectionId.get(idx);
        //return arrSubSectionId;
    }

    /**
     * @param arrSectionId the arrSectionId to set
     */
    public void addArrSubSectionId(String[] arrSubSectionId) {
         this.arrSubSectionId.add(arrSubSectionId);
        //this.arrSubSectionId = arrSubSectionId;
    }    
    

    /**
     * @return the arrLevelId
     */
    public String[] getArrLevelId(int idx) {
        if(idx>=arrLevelId.size()){
            return null;
        }
        return (String[])arrLevelId.get(idx);
        //return arrLevelId;
    }

    /**
     * @param arrLevelId the arrLevelId to set
     */
    public void addArrLevelId(String[] arrLevelId) {
         this.arrLevelId.add(arrLevelId);
        //this.arrLevelId = arrLevelId;
    }

    /**
     * @return the arrEducationId
     */
    public String[] getArrEducationId(int idx) {
        if(idx>=arrEducationId.size()){
            return null;
        }
        return (String[])arrEducationId.get(idx);
        //return arrEducationId;
    }

    /**
     * @param arrEducationId the arrEducationId to set
     */
    public void addArrEducationId(String[] arrEducationId) {
        this.arrEducationId.add(arrEducationId);
        //this.arrEducationId = arrEducationId;
    }

    /**
     * @return the arrReligionId
     */
    public String[] getArrReligionId(int idx) {
        //return arrReligionId;
        if(idx>=arrReligionId.size()){
            return null;
        }
        return (String[])arrReligionId.get(idx);
    }

    /**
     * @param arrReligionId the arrReligionId to set
     */
    public void addArrReligionId(String[] arrReligionId) {
        //this.arrReligionId = arrReligionId;
         this.arrReligionId.add(arrReligionId);
    }

    /**
     * @return the arrBlood
     */
    public String[] getArrBlood(int idx) {
        if(idx>=arrBlood.size()){
            return null;
        }
         return (String[])arrBlood.get(idx);
        //return arrBlood;
    }

    /**
     * @param arrBlood the arrBlood to set
     */
    public void addArrBlood(String[] arrBlood) {
        //this.arrBlood = arrBlood;
         this.arrBlood.add(arrBlood);
    }

    /**
     * @return the arrLanguageId
     */
    public String[] getArrLanguageId(int idx) {
        //return arrLanguageId;
         if(idx>=arrLanguageId.size()){
            return null;
        }
        return (String[])arrLanguageId.get(idx);
    }

    /**
     * @param arrLanguageId the arrLanguageId to set
     */
    public void addArrLanguageId(String[] arrLanguageId) {
        //this.arrLanguageId = arrLanguageId;
         this.arrLanguageId.add(arrLanguageId);
    }

    /**
     * @return the iSex
     */
    public int getiSex() {
        return iSex;
        
    }

    /**
     * @param iSex the iSex to set
     */
    public void setiSex(int iSex) {
        this.iSex = iSex;
         //this.iSex = iSex;
    }

    /**
     * @return the iResigned
     */
    public int getiResigned() {
        return iResigned;
        
    }

    /**
     * @param iResigned the iResigned to set
     */
    public void setiResigned(int iresign) {
        this.iResigned = iresign;
         
    }

    /**
     * @return the radiocommdate
     */
    public int getRadiocommdate() {
        return radiocommdate;
//        if(x>=radiocommdate.size()){
//            return 0;
//        }
//        return Integer.parseInt((String) radiocommdate.get(x));
    }

    /**
     * @param radiocommdate the radiocommdate to set
     */
    public void setRadiocommdate(int radiocommdate) {
        this.radiocommdate = radiocommdate;
         //this.radiocommdate.add(radiocommdate);
    }

    /**
     * @return the commdatefrom
     */
    public Date getCommdatefrom(int idx) {
        //return commdatefrom;
        if(idx>=commdatefrom.size()){
            return null;
        }
        return (Date)commdatefrom.get(idx);
    }

    /**
     * @param commdatefrom the commdatefrom to set
     */
    public void addCommdatefrom(Date commdatefrom) {
        this.commdatefrom.add(commdatefrom);
        
    }

    /**
     * @return the commdateto
     */
    public Date getCommdateto(int idx) {
       // return commdateto;
        if(idx>=commdateto.size()){
            return null;
        }
        return (Date)commdateto.get(idx);
    }

    /**
     * @param commdateto the commdateto to set
     */
    public void addCommdateto(Date commdateto) {
        //this.commdateto = commdateto;
        this.commdateto.add(commdateto);
    }

    /**
     * @return the workyearfrom
     */
    public String getWorkyearfrom(int idx) {
        //return workyearfrom;
         if(idx>=workyearfrom.size()){
            return null;
        }
        return (String)workyearfrom.get(idx);
    }

    /**
     * @param workyearfrom the workyearfrom to set
     */
    public void addWorkyearfrom(String workyearfrom) {
        //this.workyearfrom = workyearfrom;
         this.workyearfrom.add(workyearfrom);
    }

    /**
     * @return the workmonthfrom
     */
    public String getWorkmonthfrom(int idx) {
        //return workmonthfrom;
        if(idx>=workmonthfrom.size()){
            return null;
        }
        return (String)workmonthfrom.get(idx);
    }

    /**
     * @param workmonthfrom the workmonthfrom to set
     */
    public void addWorkmonthfrom(String workmonthfrom) {
        //this.workmonthfrom = workmonthfrom;
         this.workmonthfrom.add(workmonthfrom);
    }

    /**
     * @return the workyearto
     */
    public String getWorkyearto(int idx) {
        //return workyearto;
        if(idx>=workyearto.size()){
            return null;
        }
        return (String)workyearto.get(idx);
    }

    /**
     * @param workyearto the workyearto to set
     */
    public void addWorkyearto(String workyearto) {
       // this.workyearto = workyearto;
         this.workyearto.add(workyearto);
    }

    /**
     * @return the workmonthto
     */
    public String getWorkmonthto(int idx) {
        //return workmonthto;
        if(idx>=workmonthto.size()){
            return null;
        }
        return (String)workmonthto.get(idx);
    }

    /**
     * @param workmonthto the workmonthto to set
     */
    public void addWorkmonthto(String workmonthto) {
        //this.workmonthto = workmonthto;
        this.workmonthto.add(workmonthto);
    }

    /**
     * @return the ageyearfrom
     */
    public String getAgeyearfrom(int idx) {
        //return ageyearfrom;
        if(idx>=ageyearfrom.size()){
            return null;
        }
        return (String)ageyearfrom.get(idx);
    }

    /**
     * @param ageyearfrom the ageyearfrom to set
     */
    public void addAgeyearfrom(String ageyearfrom) {
        //this.ageyearfrom = ageyearfrom;
          this.ageyearfrom.add(ageyearfrom);
    }

    /**
     * @return the agemonthfrom
     */
    public String getAgemonthfrom(int idx) {
        //return agemonthfrom;
         if(idx>=agemonthfrom.size()){
            return null;
        }
        return (String)agemonthfrom.get(idx);
    }

    /**
     * @param agemonthfrom the agemonthfrom to set
     */
    public void addAgemonthfrom(String agemonthfrom) {
        //this.agemonthfrom = agemonthfrom;
         this.agemonthfrom.add(agemonthfrom);
    }

    /**
     * @return the ageyearto
     */
    public String getAgeyearto(int idx) {
        //return ageyearto;
        if(idx>=ageyearto.size()){
            return null;
        }
        return (String)ageyearto.get(idx);
    }

    /**
     * @param ageyearto the ageyearto to set
     */
    public void addAgeyearto(String ageyearto) {
       // this.ageyearto = ageyearto;
         this.ageyearto.add(ageyearto);
    }

    /**
     * @return the agemonthto
     */
    public String getAgemonthto(int idx) {
        //return agemonthto;
         if(idx>=agemonthto.size()){
            return null;
        }
        return (String)agemonthto.get(idx);
    }

    /**
     * @param agemonthto the agemonthto to set
     */
    public void addAgemonthto(String agemonthto) {
       // this.agemonthto = agemonthto;
         this.agemonthto.add(agemonthto);
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
     * @return the arrRaceId
     */
    public String[] getArrRaceId(int idx) {
        if(idx>=arrRaceId.size()){
            return null;
        }
         return (String[])arrRaceId.get(idx);
    }

    /**
     * @param arrRaceId the arrRaceId to set
     */
    public void addArrRaceId(String[] arrRaceId) {
        //this.arrRaceId = arrRaceId;
        this.arrRaceId.add(arrRaceId);
    }

    /**
     * @return the arrMaritalId
     */
    public String[] getArrMaritalId(int idx) {
        //return arrMaritalId;
         if(idx>=arrMaritalId.size()){
            return null;
        }
         return (String[])arrMaritalId.get(idx);
    }

    /**
     * @param arrMaritalId the arrMaritalId to set
     */
    public void addArrMaritalId(String[] arrMaritalId) {
        //this.arrMaritalId = arrMaritalId;
        this.arrMaritalId.add(arrMaritalId);
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
     * @return the addrsEmployee
     */
    public String getAddrsEmployee() {
         if(addrsEmployee==null || addrsEmployee.length()==0){
            return "";
        }
        return addrsEmployee;
    }

    /**
     * @param addrsEmployee the addrsEmployee to set
     */
    public void setAddrsEmployee(String addrsEmployee) {
        this.addrsEmployee = addrsEmployee;
    }

    /**
     * @return the birthdayChecked
     */
    public boolean isBirthdayChecked() {
        return birthdayChecked; 
    }

    /**
     * @param birthdayChecked the birthdayChecked to set
     */
    public void setBirthdayChecked(boolean birthdayChecked) {
        this.birthdayChecked = birthdayChecked;
    }

    /**
     * @return the birthMonth
     */
    public int getBirthMonth() {
        return birthMonth;
    }

    /**
     * @param birthMonth the birthMonth to set
     */
    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    /**
     * @return the salaryLevel
     */
    public String getSalaryLevel() {
        return salaryLevel;
    }

    /**
     * @param salaryLevel the salaryLevel to set
     */
    public void setSalaryLevel(String salaryLevel) {
        this.salaryLevel = salaryLevel;
    }

    /**
     * @return the startResign
     */
    public Date getStartResign() {
        return startResign;
    }

    /**
     * @param startResign the startResign to set
     */
    public void setStartResign(Date startResign) {
        this.startResign = startResign;
    }

    /**
     * @return the endResign
     */
    public Date getEndResign() {
        return endResign;
    }

    /**
     * @param endResign the endResign to set
     */
    public void setEndResign(Date endResign) {
        this.endResign = endResign;
    }

    /**
     * @return the arrEmpCategory
     */
    public String[] getArrEmpCategory(int idx) {
        //return arrEmpCategory;
         if(idx>=arrEmpCategory.size()){
            return null;
        }
         return (String[])arrEmpCategory.get(idx);
    }

    /**
     * @param arrEmpCategory the arrEmpCategory to set
     */
    public void addArrEmpCategory(String[] arrEmpCategory) {
       // this.arrEmpCategory = arrEmpCategory;
         this.arrEmpCategory.add(arrEmpCategory);
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
     * @return the dtBirthFrom
     */
    public Date getDtBirthFrom() {
        return dtBirthFrom;
    }

    /**
     * @param dtBirthFrom the dtBirthFrom to set
     */
    public void setDtBirthFrom(Date dtBirthFrom) {
        this.dtBirthFrom = dtBirthFrom;
    }

    /**
     * @return the dtBirthTo
     */
    public Date getDtBirthTo() {
        return dtBirthTo;
    }

    /**
     * @param dtBirthTo the dtBirthTo to set
     */
    public void setDtBirthTo(Date dtBirthTo) {
        this.dtBirthTo = dtBirthTo;
    }

  
   

    /**
     * @return the AddressPermanent
     */
    public String getAddressPermanent() {
        return AddressPermanent;
    }

    /**
     * @param AddressPermanent the AddressPermanent to set
     */
    public void setAddressPermanent(String AddressPermanent) {
        this.AddressPermanent = AddressPermanent;
    }

    /**
     * @return the birthYearFrom
     */
    public int getBirthYearFrom() {
        return birthYearFrom;
    }

    /**
     * @param birthYearFrom the birthYearFrom to set
     */
    public void setBirthYearFrom(int birthYearFrom) {
        this.birthYearFrom = birthYearFrom;
    }

    /**
     * @return the birthYearTo
     */
    public int getBirthYearTo() {
        return birthYearTo;
    }

    /**
     * @param birthYearTo the birthYearTo to set
     */
    public void setBirthYearTo(int birthYearTo) {
        this.birthYearTo = birthYearTo;
    }

    /**
     * @return the dtPeriodStart
     */
    public Date getDtPeriodStart() {
        return dtPeriodStart;
    }

    /**
     * @param dtPeriodStart the dtPeriodStart to set
     */
    public void setDtPeriodStart(Date dtPeriodStart) {
        this.dtPeriodStart = dtPeriodStart;
    }

    /**
     * @return the dtPeriodEnd
     */
    public Date getDtPeriodEnd() {
        return dtPeriodEnd;
    }

    /**
     * @param dtPeriodEnd the dtPeriodEnd to set
     */
    public void setDtPeriodEnd(Date dtPeriodEnd) {
        this.dtPeriodEnd = dtPeriodEnd;
    }

    /**
     * @return the dtCarrierWorkStart
     */
    public Date getDtCarrierWorkStart() {
        return dtCarrierWorkStart;
    }

    /**
     * @param dtCarrierWorkStart the dtCarrierWorkStart to set
     */
    public void setDtCarrierWorkStart(Date dtCarrierWorkStart) {
        this.dtCarrierWorkStart = dtCarrierWorkStart;
    }

    /**
     * @return the dtCarrierWorkEnd
     */
    public Date getDtCarrierWorkEnd() {
        return dtCarrierWorkEnd;
    }

    /**
     * @param dtCarrierWorkEnd the dtCarrierWorkEnd to set
     */
    public void setDtCarrierWorkEnd(Date dtCarrierWorkEnd) {
        this.dtCarrierWorkEnd = dtCarrierWorkEnd;
    }

    /**
     * @return the carrierCategoryEmp
     */
    public long getCarrierCategoryEmp() {
        return carrierCategoryEmp;
    }

    /**
     * @param carrierCategoryEmp the carrierCategoryEmp to set
     */
    public void setCarrierCategoryEmp(long carrierCategoryEmp) {
        this.carrierCategoryEmp = carrierCategoryEmp;
    }

    /**
     * @return the stylist
     */
    public int getStylist() {
        return stylist;
    }

    /**
     * @param stylist the stylist to set
     */
    public void setStylist(int stylist) {
        this.stylist = stylist;
    }
    
    /**
     * @return the sort
     */
    public String getStyle(int idx) {
       // return style;
        if(idx>=style.size()){
            return null;
        }
        return (String)style.get(idx);
    }

    /**
     * @param sort the sort to set
     */
    public void addStyle(String style) {
       // this.sort = sort;
         this.style.add(style);
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
    
     public String[] getArrGradeId(int idx) {
        if(idx>=arrGradeId.size()){
            return null;
        }
        return (String[])arrGradeId.get(idx);
        //return arrLocationId;
    }
    

    /**
     * @param arrGradeId the arrGradeId to set
     */
    public void addArrGradeId(String[] arrGradeId) {
         this.arrGradeId.add(arrGradeId);
        //this.arrLocationId = arrLocationId;
    }
    
    /**
     * @return the endcontract
     */
    public int getRadioendcontract() {
        return radioendcontract;
    }

    /**
     * @param radioendcontract the radioendcontract to set
     */
    public void setRadioendcontract(int radioendcontract) {
        this.radioendcontract = radioendcontract;
    }

    /**
     * @return the endcontractfrom
     */
    public Date getEndcontractfrom(int idx) {
        //return commdatefrom;
        if(idx>=endcontractfrom.size()){
            return null;
        }
        return (Date)endcontractfrom.get(idx);
    }

    /**
     * @param endcontractfrom the endcontractfrom to set
     */
    public void addEndcontractfrom(Date endcontractfrom) {
        this.endcontractfrom.add(endcontractfrom);
        
    }

    /**
     * @return the endcontractto
     */
    public Date getEndcontractto(int idx) {
       // return endcontractto;
        if(idx>=endcontractto.size()){
            return null;
        }
        return (Date)endcontractto.get(idx);
    }

    /**
     * @param endcontractto the endcontractto to set
     */
    public void addEndcontractto(Date endcontractto) {
        //this.commdateto = commdateto;
        this.endcontractto.add(endcontractto);
    }

    /**
     * @return the resignedReasonId
     */
    public long getResignedReasonId() {
        return resignedReasonId;
    }

    /**
     * @param resignedReasonId the resignedReasonId to set
     */
    public void setResignedReasonId(long resignedReasonId) {
        this.resignedReasonId = resignedReasonId;
    }

    
    
    
       public String[] getArrPayrollGroupId(int idx) {
        if(idx>=arrPayrollGroupId.size()){
            return null;
        }
        return (String[])arrPayrollGroupId.get(idx);
    }
    

    public void addArrPayrollGroupId(String[] arrPayrollGroupId) {
         this.arrPayrollGroupId.add(arrPayrollGroupId);
    }
    
    
    
}


