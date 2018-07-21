/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Ari_20110930
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: FrmCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Priska
 */

/* java package */

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.util.Formater;

public class FrmKPI_Company_Target extends FRMHandler implements I_FRMInterface, I_FRMType{
     private KPI_Company_Target kPI_Company_Target;

    private Vector vlistKpiCompanyTarget = new Vector();
    
   private Vector vlistKpiDivisionTarget = new Vector();
   private Vector vlistKpiDepartmentTarget = new Vector();
   private Vector vlistKpiSectionTarget = new Vector();
   private Vector vlistKpiEmployeeDepartTarget = new Vector();
   private Vector vlistKpiEmployeeDepartAchiev = new Vector();
   private Vector vlistKpiEmployeeTarget = new Vector();
   private Vector vlistKpiEmployeeAchiev = new Vector();
     
    public static final String FRM_NAME_KPI_COMPANY_TARGET = "FRM_NAME_KPI_COMPANY_TARGET";

    public static final int FRM_FIELD_KPI_COMPANY_TARGET_ID = 0;
    public static final int FRM_FIELD_KPI_LIST_ID = 1;
    public static final int FRM_FIELD_STARTDATE = 2;
    public static final int FRM_FIELD_ENDDATE = 3;
    public static final int FRM_FIELD_COMPANY_ID = 4;
    public static final int FRM_FIELD_TARGET = 5;
    public static final int FRM_FIELD_ACHIEVEMENT = 6;
    public static final int FRM_FIELD_YEAR = 7;
    
    public static final int FRM_FIELD_PERCENT_E = 8;
    public static final int FRM_FIELD_ACHIEVEMENT_E = 9;
    
    public static final int FRM_FIELD_COMPETITOR_ID = 10;
    public static final int FRM_FIELD_COMPETITOR_VALUE = 11;
    
    public static final int FRM_FIELD_PERCENT_DIVISION = 12;
    public static final int FRM_FIELD_ACHIEVEMENT_DIVISION = 13;

    public static final int FRM_FIELD_PERCENT_DEPARTMENT = 14;
    public static final int FRM_FIELD_ACHIEVEMENT_DEPARTMENT = 15;
    
    public static final int FRM_FIELD_PERCENT_SECTION = 16;
    public static final int FRM_FIELD_ACHIEVEMENT_SECTION = 17;
    
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_KPI_COMPANY_TARGET_ID",
        "FRM_FIELD_KPI_LIST_ID",
        "FRM_FIELD_STARTDATE",
        "FRM_FIELD_ENDDATE",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_TARGET",
        "FRM_FIELD_ACHIEVEMENT",
        "FRM_FIELD_YEAR",
        "FRM_FIELD_TARGET_E",
        "FRM_FIELD_ACHIEVEMENT_E",
        "FRM_FIELD_COMPETITOR_ID",
        "FRM_FIELD_COMPETITOR_VALUE",
        "FRM_FIELD_TARGET_DIVISION",
        "FRM_FIELD_ACHIEVEMENT_DIVISION",
        "FRM_FIELD_TARGET_DEPARTMENT",
        "FRM_FIELD_ACHIEVEMENT_DEPARTMENT",
        "FRM_FIELD_TARGET_SECTION",
        "FRM_FIELD_ACHIEVEMENT_SECTION"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT
    };

    //update by priska
    public static final int[] yearValue = {2013,2014,2015,2016,2017,2018,2019,2020};    
    public static final String[] yearKey = {"2013","2014","2015","2016","2017","2018","2019","2020"};
    
    public static Vector getYearValue(){
        Vector year = new Vector();
        for (int i= 0; i< yearValue.length; i++){
            year.add(String.valueOf(yearValue[i]));
        }
        return year;
    }
    
    public static Vector getYearKey(){
        Vector year = new Vector();
        for (int i = 0; i < yearKey.length; i++ ){
            year.add(yearKey[i]);
        }
        return year;
    }    
    
    public FrmKPI_Company_Target() {
    }

    public FrmKPI_Company_Target(KPI_Company_Target kPI_Company_Target) {
        this.kPI_Company_Target = kPI_Company_Target;
    }

    public FrmKPI_Company_Target(HttpServletRequest request, KPI_Company_Target kPI_Company_Target) {
        super(new FrmKPI_Company_Target(kPI_Company_Target), request);
        this.kPI_Company_Target = kPI_Company_Target;
    }

    public String getFormName() {
        return FRM_NAME_KPI_COMPANY_TARGET;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public KPI_Company_Target getEntityObject() {
        return kPI_Company_Target;
    }

       public void requestEntityMultipleObject(long kpiListId, int tahun,long companyId) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
           // String[] selectedUser = this.getParamsStringValues("userSelect");
           // long kpilistid = Long.valueOf("504404582014324552") ;
            
             KPI_List kPI_List = new KPI_List();
        
        try {
            kPI_List = PstKPI_List.fetchExc(kpiListId);
        }catch (Exception e ){
            
        }
        Date startDateList  = kPI_List.getValid_from() ;
        Date endDateList    = kPI_List.getValid_to() ;
            
            
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
        int month1 =  startDateList.getMonth()+1;
        int month2 =  endDateList.getMonth()+1;
        int jumlahBulan = (month2-month1)+1;
        for (int x = month1; x < (month2+1); x++ ){            
                            double target= this.getParamDouble(fieldNames[FRM_FIELD_TARGET]+x);
                            KPI_Company_Target  kPI_Company_Target = new KPI_Company_Target();
                            kPI_Company_Target.setKpiListId(kpiListId);
                            
                           // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateStartString = "";
                            String dateEndString = "";
                            if (x == 1){
                                dateStartString = "01-01-"+tahun;
                                dateEndString = "31-01-"+tahun;
                             }
                            if (x == 2){
                                dateStartString = "01-02-"+tahun;
                                dateEndString = "28-02-"+tahun;
                            }
                             if (x == 3){
                                dateStartString = "01-03-"+tahun;
                                dateEndString = "31-03-"+tahun;
                            }
                             if (x == 4){
                                dateStartString = "01-04-"+tahun;
                                dateEndString = "30-04-"+tahun;
                            }
                             if (x == 5){
                                dateStartString = "01-05-"+tahun;
                                dateEndString = "31-05-"+tahun;
                            }
                             if (x == 6){
                                dateStartString = "01-06-"+tahun;
                                dateEndString = "30-06-"+tahun;
                            }
                             if (x == 7){
                                dateStartString = "01-07-"+tahun;
                                dateEndString = "31-07-"+tahun;
                            }
                             if (x == 8){
                                dateStartString = "01-08-"+tahun;
                                dateEndString = "30-08-"+tahun;
                            }
                             if (x == 9){
                                dateStartString = "01-09-"+tahun;
                                dateEndString = "30-09-"+tahun;
                            }
                             if (x == 10){
                                dateStartString = "01-10-"+tahun;
                                dateEndString = "30-10-"+tahun;
                            }
                             if (x == 11){
                                dateStartString = "01-11-"+tahun;
                                dateEndString = "30-11-"+tahun;
                            }
                             if (x == 12){
                                dateStartString = "01-12-"+tahun;
                                dateEndString = "30-12-"+tahun;
                            }
                             
                            
                            kPI_Company_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Company_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Company_Target.setCompanyId(companyId);
                            kPI_Company_Target.setTarget(target);
                            kPI_Company_Target.setAchievement(0);
                            
                            double competitorValue= this.getParamDouble(fieldNames[FRM_FIELD_COMPETITOR_VALUE]);
                            long competitorId= this.getParamLong(fieldNames[FRM_FIELD_COMPETITOR_ID]);
                            
                            kPI_Company_Target.setCompetitorId(competitorId);
                            kPI_Company_Target.setCompetitorValue(competitorValue);
                           
                            vlistKpiCompanyTarget.add(kPI_Company_Target);
                    }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
 
//    public void requestEntityMultipleObjectEmployee(long kpiListId, int tahun,long companyId, Vector listKpiEmployeeTarget, Vector VListCompanyTarget ) { //melakukan 
//        ///pemanggilan terhadap Employee
//        try {
//            this.requestParam();
//            
//            double total = 0;
//                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
//                            
//                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
//                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
//                            total = total + (kPI_Company_Target1.getTarget());
//                            }
//            
//                            Date startdate = new  Date();
//                            Date enddate = new  Date();
//                            for (int x = 0; x < listKpiEmployeeTarget.size(); x++) {       
//                            KPI_ListAllDataEmp kPI_ListAllDataEmp = (KPI_ListAllDataEmp)listKpiEmployeeTarget.get(x);
//                           // double target= this.getParamDouble(fieldNames[FRM_FIELD_TARGET]+x);
//                            double percent= this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_E]+x);
//                            double Achievement= this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_E]+x);
//                            KPI_Employee_Target  kPI_Employee_Target = new KPI_Employee_Target();
//                            
//                            String dateStartString = "01-01-"+tahun;
//                            String dateEndString = "30-12-"+tahun;
//                            
//                            
//                             
//                            double target = total/100*percent;
//                            
//                            kPI_Employee_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
//                            kPI_Employee_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
//                            kPI_Employee_Target.setEmployeeId(kPI_ListAllDataEmp.getEmployeeId());
//                            kPI_Employee_Target.setTarget(target);
//                            kPI_Employee_Target.setAchievement(Achievement);
//                            kPI_Employee_Target.setKpiListId(kpiListId);
//                            vlistKpiEmployeeTarget.add(kPI_Employee_Target);
//                            }
//            
//            
//        } catch (Exception e) {
//            System.out.println("Error on requestEntityObject : " + e.toString());
//        }
//    }
    
    
     public void requestEntityMultipleObjectDivision(long kpiListId, int tahun,long companyId, Vector VListCompanyTarget ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            double total = 0;
                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            
                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
                            total = total + (kPI_Company_Target1.getTarget());
                            }
            
                            Vector VDivision = PstDivision.list(0, 0, "","");
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
                            
                            for (int x = 0; x < VDivision.size(); x++) {       
                            Division division = (Division)VDivision.get(x);
                            double percent = this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_DIVISION]+division.getOID());
                            double Achievement = this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_DIVISION]+division.getOID());
                            
                            KPI_Division_Target  kPI_Division_Target = new KPI_Division_Target();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            
                            
                             
                            double target =  total/100 * percent;
                            
                            kPI_Division_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Division_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Division_Target.setDivisionId(division.getOID());
                            kPI_Division_Target.setTarget(target);
                            kPI_Division_Target.setAchievement(Achievement);
                            kPI_Division_Target.setKpiListId(kpiListId);
                            if ( target > 0 ){
                                  vlistKpiDivisionTarget.add(kPI_Division_Target);
                            }
                            }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
     public void requestEntityMultipleObjectDepartment(long kpiListId, int tahun,long companyId, Vector VListCompanyTarget,long divisiId ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            double total = 0;
                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            
                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
                            total = total + (kPI_Company_Target1.getTarget());
                            }
            
                            Vector VDepartment = PstDepartment.list(0, 0, " hr_department.DIVISION_ID="+divisiId,"");
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
                            
                            for (int x = 0; x < VDepartment.size(); x++) {       
                            Department department = (Department)VDepartment.get(x);
                            double percent = this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_DEPARTMENT]+department.getOID());
                            double Achievement = this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_DEPARTMENT]+department.getOID());
                            
                            KPI_Department_Target  kPI_Department_Target = new KPI_Department_Target();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            
                            
                            double target =  total/100 * percent;
                            
                            kPI_Department_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Department_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Department_Target.setDepartmentId(department.getOID());
                            kPI_Department_Target.setTarget(target);
                            kPI_Department_Target.setAchievement(Achievement);
                            kPI_Department_Target.setKpiListId(kpiListId);
                            if ( target > 0 ){
                                  vlistKpiDepartmentTarget.add(kPI_Department_Target);
                            }
                            }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
     
         
     public void requestEntityMultipleObjectSection(long kpiListId, int tahun,long companyId, Vector VListCompanyTarget, long departmentId ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            double total = 0;
                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            
                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
                            total = total + (kPI_Company_Target1.getTarget());
                            }
            
                            Vector VSection = PstSection.list(0, 0, " DEPARTMENT_ID = "+departmentId,"");
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
                            
                            for (int x = 0; x < VSection.size(); x++) {       
                            Section section = (Section)VSection.get(x);
                            double percent = this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_SECTION]+section.getOID());
                            double Achievement = this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_SECTION]+section.getOID());
                            
                            KPI_Section_Target  kPI_Section_Target = new KPI_Section_Target();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            
                            
                            double target =  total/100 * percent;
                            
                            kPI_Section_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Section_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Section_Target.setSectionId(section.getOID());
                            kPI_Section_Target.setTarget(target);
                            kPI_Section_Target.setAchievement(Achievement);
                            kPI_Section_Target.setKpiListId(kpiListId);
                            if ( target > 0 ){
                                  vlistKpiSectionTarget.add(kPI_Section_Target);
                            }
                            }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
     
       public void requestEntityMultipleObjectEmployee(long kpiListId, int tahun,long companyId, Vector VListCompanyTarget, long sectionId ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            double total = 0;
                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            
                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
                            total = total + (kPI_Company_Target1.getTarget());
                            }
            
                            Vector VEmployee = PstEmployee.list(0, 0, " SECTION_ID = "+sectionId,"");
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
                            
                            for (int x = 0; x < VEmployee.size(); x++) {       
                            Employee employee = (Employee)VEmployee.get(x);
                            double percent = this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_E]+employee.getOID());
                            double Achievement = this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_E]+employee.getOID());
                            
                            KPI_Employee_Target  kPI_Employee_Target = new KPI_Employee_Target();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            
                            
                            double target =  total/100 * percent;
                            
                            kPI_Employee_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Employee_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Employee_Target.setEmployeeId(employee.getOID());
                            kPI_Employee_Target.setTarget(target);
                            kPI_Employee_Target.setAchievement(Achievement);
                            kPI_Employee_Target.setKpiListId(kpiListId);
                            if ( target > 0 ){
                                  vlistKpiEmployeeTarget.add(kPI_Employee_Target);
                            }
                            }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
        public void requestEntityMultipleObjectEmployeeAchiev(long kpiListId, int tahun,long companyId, Vector VListCompanyTarget, long sectionId ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            double total = 0;
                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            
                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
                            total = total + (kPI_Company_Target1.getTarget());
                            }
            
                            Vector VEmployee = PstEmployee.list(0, 0, " SECTION_ID = "+sectionId,"");
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
                            
                            for (int x = 0; x < VEmployee.size(); x++) {       
                            Employee employee = (Employee)VEmployee.get(x);
                            double percent = this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_E]+employee.getOID());
                            double Achievement = this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_E]+employee.getOID());
                            
                            KPI_Employee_Achiev  kPI_Employee_Achiev = new KPI_Employee_Achiev();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            Date date = new Date();
                            
                            double target =  total/100 * percent;
                            
                            kPI_Employee_Achiev.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Employee_Achiev.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Employee_Achiev.setEmployeeId(employee.getOID());
                            kPI_Employee_Achiev.setEntryDate(date);
                            kPI_Employee_Achiev.setAchievement(Achievement);
                            kPI_Employee_Achiev.setKpiListId(kpiListId);
                            if ( target > 0 ){
                                  vlistKpiEmployeeAchiev.add(kPI_Employee_Achiev);
                            }
                            }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }  
       
        
       
        public void requestEntityMultipleObjectEmployeeDepartAchiev(long kpiListId, int tahun,long companyId, Vector VListCompanyTarget, long departId ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            double total = 0;
                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            
                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
                            total = total + (kPI_Company_Target1.getTarget());
                            }
            
                            Vector VEmployee = PstEmployee.list(0, 0, " DEPARTMENT_ID = "+departId+" AND SECTION_ID = "+0,"");
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
                            
                            for (int x = 0; x < VEmployee.size(); x++) {       
                            Employee employee = (Employee)VEmployee.get(x);
                            double percent = this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_E]+employee.getOID());
                            double Achievement = this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_E]+employee.getOID());
                            
                            KPI_Employee_Achiev  kPI_Employee_Achiev = new KPI_Employee_Achiev();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            Date date = new Date();
                            
                            double target =  total/100 * percent;
                            
                            kPI_Employee_Achiev.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Employee_Achiev.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Employee_Achiev.setEmployeeId(employee.getOID());
                            kPI_Employee_Achiev.setEntryDate(date);
                            kPI_Employee_Achiev.setAchievement(Achievement);
                            kPI_Employee_Achiev.setKpiListId(kpiListId);
                            if ( target > 0 ){
                                  vlistKpiEmployeeDepartAchiev.add(kPI_Employee_Achiev);
                            }
                            }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }  
             public void requestEntityMultipleObjectEmployeeDepart(long kpiListId, int tahun,long companyId, Vector VListCompanyTarget, long departId ) { //melakukan 
        ///pemanggilan terhadap Employee
        try {
            this.requestParam();
            
            double total = 0;
                            //Vector vtotal = PstKPI_Company_Target.listAlldata(tahun, kpiListId, companyId);
                            
                            for (int ik=0; ik<VListCompanyTarget.size();ik++){
                                KPI_Company_Target kPI_Company_Target1 = (KPI_Company_Target) VListCompanyTarget.get(ik);
                            total = total + (kPI_Company_Target1.getTarget());
                            }
            
                            Vector VEmployee = PstEmployee.list(0, 0, " DEPARTMENT_ID = "+departId+" AND SECTION_ID = "+0,"");
                            Date startdate = new  Date();
                            Date enddate = new  Date();
                            
                            
                            for (int x = 0; x < VEmployee.size(); x++) {       
                            Employee employee = (Employee)VEmployee.get(x);
                            double percent = this.getParamDouble(fieldNames[FRM_FIELD_PERCENT_E]+employee.getOID());
                            double Achievement = this.getParamDouble(fieldNames[FRM_FIELD_ACHIEVEMENT_E]+employee.getOID());
                            
                            KPI_Employee_Target  kPI_Employee_Target = new KPI_Employee_Target();
                            
                            String dateStartString = "01-01-"+tahun;
                            String dateEndString = "30-12-"+tahun;
                            
                            
                            double target =  total/100 * percent;
                            
                            kPI_Employee_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
                            kPI_Employee_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
                            kPI_Employee_Target.setEmployeeId(employee.getOID());
                            kPI_Employee_Target.setTarget(target);
                            kPI_Employee_Target.setAchievement(Achievement);
                            kPI_Employee_Target.setKpiListId(kpiListId);
                            if ( target > 0 ){
                                  vlistKpiEmployeeDepartTarget.add(kPI_Employee_Target);
                            }
                            }
            
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
     
     
    public void requestEntityObject(KPI_Company_Target kPI_Company_Target) {
        try {
            this.requestParam();
            kPI_Company_Target.setKpiListId(getLong(FRM_FIELD_KPI_LIST_ID));
            kPI_Company_Target.setStartDate(getDate(FRM_FIELD_STARTDATE));
            kPI_Company_Target.setEndDate(getDate(FRM_FIELD_ENDDATE));
            kPI_Company_Target.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            kPI_Company_Target.setTarget(getDouble(FRM_FIELD_TARGET));
            kPI_Company_Target.setAchievement(getDouble(FRM_FIELD_ACHIEVEMENT));
         
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
 /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiCompanyTarget() {
        return vlistKpiCompanyTarget;
    }
    
     
     /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiDivisionTarget() {
        return vlistKpiDivisionTarget;
    }
     /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiDepartmentTarget() {
        return vlistKpiDepartmentTarget;
    }
      /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiSectionTarget() {
        return vlistKpiSectionTarget;
    }
     /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiEmployeeDepartTarget() {
        return vlistKpiEmployeeDepartTarget;
    }
    /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiEmployeeTarget() {
        return vlistKpiEmployeeTarget;
    }
     /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiEmployeeAchiev() {
        return vlistKpiEmployeeAchiev;
    }
    /**
     * @return the vlistEntriOpname
     */
    public Vector getVlistKpiEmployeeDepartAchiev() {
        return vlistKpiEmployeeDepartAchiev;
    }
}
