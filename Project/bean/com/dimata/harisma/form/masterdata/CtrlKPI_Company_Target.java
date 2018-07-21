/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Priska
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: CtrlCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Priska
 */
/* java package */
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.*;

public class CtrlKPI_Company_Target extends Control implements I_Language{
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private KPI_Company_Target kPI_Company_Target;
    private PstKPI_Company_Target pstKPI_Company_Target;
    private FrmKPI_Company_Target frmKPI_Company_Target;
 
    
    private KPI_Employee_Target kPI_Employee_Target;
    private PstKPI_Employee_Target pstKPI_Employee_Target;
    private FrmKPI_Employee_Target frmKPI_Employee_Target;
    
    
    private KPI_Section_Target kPI_Section_Target;
    private PstKPI_Section_Target pstKPI_Section_Target;
    
    private KPI_Department_Target kPI_Department_Target;
    private PstKPI_Department_Target pstKPI_Department_Target;
    
    private KPI_Division_Target kPI_Division_Target;
    private PstKPI_Division_Target pstKPI_Division_Target;
    
    int language = LANGUAGE_DEFAULT;

    public CtrlKPI_Company_Target(HttpServletRequest request) {
        msgString = "";
        kPI_Company_Target = new KPI_Company_Target();
        try {
            pstKPI_Company_Target = new PstKPI_Company_Target(0);
        } catch (Exception e) {
            ;
        }
        frmKPI_Company_Target = new FrmKPI_Company_Target(request, kPI_Company_Target);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmKPI_Company_Target.addError(frmKPI_Company_Target.FRM_FIELD_KPI_COMPANY_TARGET_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public KPI_Company_Target getdKPI_Company_Target() {
        return kPI_Company_Target;
    }

    public FrmKPI_Company_Target getForm() {
        return frmKPI_Company_Target;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidKPI_Company_Target, long kpiListId, int tahun, long companyId,long type,long divisiId,long departementId,long secId,long kpiAchiev) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidKPI_Company_Target != 0){
					try{
						kPI_Company_Target = PstKPI_Company_Target.fetchExc(oidKPI_Company_Target);
					}catch(Exception exc){
					}
				}
                                Vector listKpiEmployeeTarget = PstKPI_Company_Target.listAlldataEmployee(tahun, kpiListId, companyId);
                                //all by priska
				//frmKPI_Company_Target.requestEntityObject(kPI_Company_Target);
                               frmKPI_Company_Target.requestEntityMultipleObject(kpiListId, tahun,companyId);
                               Vector VListCompanyTarget = (Vector) frmKPI_Company_Target.getVlistKpiCompanyTarget();
                               
                               if(frmKPI_Company_Target.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                               //menghapus kpi company target
                               if (type == 1){
                                long del = PstKPI_Company_Target.deletewhereYear(tahun, kpiListId, companyId);
                               }
                                double totalCompanyTarget = 0 ;
                                for (int x = 0 ; x < VListCompanyTarget.size(); x++){
                                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) VListCompanyTarget.get(x);
                                    totalCompanyTarget = totalCompanyTarget+ kPI_Company_Target.getTarget();
                                    if (type ==1){
                                        try{
						long oid = pstKPI_Company_Target.insertExc(kPI_Company_Target);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                    }

                                }
                               
                                
                                frmKPI_Company_Target.requestEntityMultipleObjectDivision(kpiListId, tahun, companyId,VListCompanyTarget);
				Vector VListDivisionTarget = (Vector) frmKPI_Company_Target.getVlistKpiDivisionTarget();
                               
                                double totaltarget = 0;
                                for (int x = 0; x < VListDivisionTarget.size(); x++) {      
                                    KPI_Division_Target  kPI_Division_TargetLocal = (KPI_Division_Target) VListDivisionTarget.get(x);
                                    double target = (Double) (kPI_Division_TargetLocal.getTarget());
                                    totaltarget = totaltarget + target;
                                }
                                if (totaltarget != 0 && totaltarget > totalCompanyTarget){
                                    return RSLT_FORM_INCOMPLETE ;
                                }
                                //delete all kpi division target
                                if (type == 2){
                                long delDiv = pstKPI_Division_Target.deleteKpiDivisionTarget(tahun, kpiListId, companyId);
                                }
                                double totalDivisionTarget = 0 ;
                                double oneDivValueForDepart = 0;
                                for (int x = 0 ; x < VListDivisionTarget.size(); x++){
                                    KPI_Division_Target kPI_Division_TargetLocal = (KPI_Division_Target) VListDivisionTarget.get(x);
                                    totalDivisionTarget = totalDivisionTarget+ kPI_Division_TargetLocal.getTarget();
                                    if (divisiId >0 && (divisiId == kPI_Division_TargetLocal.getDivisionId())){
                                        oneDivValueForDepart = oneDivValueForDepart + kPI_Division_TargetLocal.getTarget();
                                    }
                                    
                                    if (type ==2){
                                        try{
						long oid = pstKPI_Division_Target.insertExc(kPI_Division_TargetLocal);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                        
                                    }
                                }
                                
                                
                                double oneDepValueForSection = 0 ;
                                if (divisiId > 0 && type > 2){
                                frmKPI_Company_Target.requestEntityMultipleObjectDepartment(kpiListId, tahun, companyId,VListCompanyTarget,divisiId);
				Vector VListDepartmentTarget = (Vector) frmKPI_Company_Target.getVlistKpiDepartmentTarget();
                               
                                double totaltargetDepartment = 0;
                                for (int x = 0; x < VListDepartmentTarget.size(); x++) {      
                                    KPI_Department_Target  kPI_Department_TargetLocal = (KPI_Department_Target) VListDepartmentTarget.get(x);
                                    double target = (Double) (kPI_Department_TargetLocal.getTarget());
                                    totaltargetDepartment = totaltargetDepartment + target;
                                }
                                
                                if (totaltargetDepartment != 0 && totaltargetDepartment > oneDivValueForDepart){
                                    return RSLT_FORM_INCOMPLETE ;
                                }
                                //delete all kpi departement target
                                if (type == 3){
                                long delDep = pstKPI_Department_Target.deleteKpiDepartmentTarget(tahun, kpiListId, companyId);
                                }
                                double totalDepartmentTarget = 0 ;
                                
                                for (int x = 0 ; x < VListDepartmentTarget.size(); x++){
                                    KPI_Department_Target kPI_DepartmentTargetLocal = (KPI_Department_Target) VListDepartmentTarget.get(x);
                                    totalDepartmentTarget = totalDepartmentTarget+ kPI_DepartmentTargetLocal.getTarget();
                                    if (departementId >0 && (departementId == kPI_DepartmentTargetLocal.getDepartmentId())){
                                        oneDepValueForSection = oneDepValueForSection + kPI_DepartmentTargetLocal.getTarget();
                                    }
                                    if (type ==3){
                                        try{
						long oid = pstKPI_Department_Target.insertExc(kPI_DepartmentTargetLocal);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                        
                                    }
                                }
                                }
                                
                                //save section target
                                double oneSecValueForEmployee = 0 ;
                                if (divisiId > 0 && departementId > 0 && type > 3){
                                    
                                frmKPI_Company_Target.requestEntityMultipleObjectSection(kpiListId, tahun, companyId,VListCompanyTarget,departementId);
				Vector VListSectionTarget = (Vector) frmKPI_Company_Target.getVlistKpiSectionTarget();
                               
                                frmKPI_Company_Target.requestEntityMultipleObjectEmployeeDepart(kpiListId, tahun, companyId,VListCompanyTarget,departementId);
				Vector VListEmployeeDepartTarget = (Vector) frmKPI_Company_Target.getVlistKpiEmployeeDepartTarget();
                                
                                double totaltargetSection = 0;
                                for (int x = 0; x < VListSectionTarget.size(); x++) {      
                                    KPI_Section_Target  kPI_Section_TargetLocal = (KPI_Section_Target) VListSectionTarget.get(x);
                                    double target = (Double) (kPI_Section_TargetLocal.getTarget());
                                    totaltargetSection = totaltargetSection + target;
                                }
                                
                                double totaltargetEmployee = 0;
                                String employeeId = "";
                                for (int x = 0; x < VListEmployeeDepartTarget.size(); x++) {      
                                    KPI_Employee_Target  kPI_Employee_TargetLocal = (KPI_Employee_Target) VListEmployeeDepartTarget.get(x);
                                    double target = (Double) (kPI_Employee_TargetLocal.getTarget());
                                    totaltargetEmployee = totaltargetEmployee + target;
                                    employeeId = employeeId+" "+kPI_Employee_TargetLocal.getEmployeeId()+",";
                                }
                                
                                if ((totaltargetSection+totaltargetEmployee) != 0 && (totaltargetSection+totaltargetEmployee) > oneDepValueForSection){
                                    return RSLT_FORM_INCOMPLETE ;
                                }
                                //delete all kpi employee target
                                if (type == 4 && kpiAchiev == 0){
                                long delSec = pstKPI_Section_Target.deleteKpiSectionTarget(tahun, kpiListId, companyId);
                                if (employeeId.length() >1){
                                employeeId = employeeId.substring(0, employeeId.length()-1);
                                long delEmpl = pstKPI_Employee_Target.deleteKpiEmployePerDepartTarget(tahun, kpiListId, companyId, employeeId);
                                }
                                }
                                double totalSectionTarget = 0 ;
                                
                                for (int x = 0 ; x < VListSectionTarget.size(); x++){
                                    KPI_Section_Target kPI_Section_TargetLocal = (KPI_Section_Target) VListSectionTarget.get(x);
                                    totalSectionTarget = totalSectionTarget+ kPI_Section_TargetLocal.getTarget();
                                    if (secId >0 && (secId == kPI_Section_TargetLocal.getSectionId())){
                                        oneSecValueForEmployee = oneSecValueForEmployee + kPI_Section_TargetLocal.getTarget();
                                    }
                                    if (type ==4 && kpiAchiev == 0){
                                        try{
						long oid = pstKPI_Section_Target.insertExc(kPI_Section_TargetLocal);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                        
                                    }
                                }
                                
                                
                                 for (int x = 0 ; x < VListEmployeeDepartTarget.size(); x++){
                                    KPI_Employee_Target kPI_Employee_TargetLocal = (KPI_Employee_Target) VListEmployeeDepartTarget.get(x);
                                    totalSectionTarget = totalSectionTarget+ kPI_Employee_TargetLocal.getTarget();
                                
                                    if (type ==4 && kpiAchiev == 0){
                                        try{
						long oid = pstKPI_Employee_Target.insertExc(kPI_Employee_TargetLocal);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                        
                                    }
                                }
                                 
                                frmKPI_Company_Target.requestEntityMultipleObjectEmployeeDepartAchiev(kpiListId, tahun, companyId,VListCompanyTarget,departementId);
				Vector VListEmployeeDepartAchiev = (Vector) frmKPI_Company_Target.getVlistKpiEmployeeDepartAchiev();
                                
                                 
                                 if (type == 4 && kpiAchiev == 1){
                                for (int x = 0 ; x < VListEmployeeDepartAchiev.size(); x++){
                                    KPI_Employee_Achiev kPI_Employee_Achiev = (KPI_Employee_Achiev) VListEmployeeDepartAchiev.get(x);
                                   
                                    if (type ==4 && kpiAchiev == 1){
                                        try{
						long oid = PstKPI_Employee_Achiev.insertExc(kPI_Employee_Achiev);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                        
                                    } 
                                }
                                }
                                 
                                 
                                }
                                
                                //save employee target
                                if (divisiId > 0 && departementId > 0 && secId > 0 && type > 4){
                                frmKPI_Company_Target.requestEntityMultipleObjectEmployee(kpiListId, tahun, companyId,VListCompanyTarget,secId);
				Vector VListEmployeeTarget = (Vector) frmKPI_Company_Target.getVlistKpiEmployeeTarget();
                               
                                
                                frmKPI_Company_Target.requestEntityMultipleObjectEmployeeAchiev(kpiListId, tahun, companyId,VListCompanyTarget,secId);
                                Vector VListEmployeeAchiev = (Vector) frmKPI_Company_Target.getVlistKpiEmployeeAchiev();
                                
                                String employeeId="";
                                double totaltargetEmployee = 0;
                                for (int x = 0; x < VListEmployeeTarget.size(); x++) {      
                                    KPI_Employee_Target  kPI_Employee_TargetLocal = (KPI_Employee_Target) VListEmployeeTarget.get(x);
                                    double target = (Double) (kPI_Employee_TargetLocal.getTarget());
                                    totaltargetEmployee = totaltargetEmployee + target;
                                    employeeId = employeeId+" "+kPI_Employee_TargetLocal.getEmployeeId()+",";
                                    
                                }
                                
                                if (totaltargetEmployee != 0 && totaltargetEmployee > oneSecValueForEmployee){
                                    return RSLT_FORM_INCOMPLETE ;
                                }
                                //delete all kpi employee target
                                if (type == 5 && kpiAchiev == 0){
                                    if (employeeId.length() >1){
                                    employeeId = employeeId.substring(0, employeeId.length()-1);
                                    long delEmpl = pstKPI_Employee_Target.deleteKpiEmployePerDepartTarget(tahun, kpiListId, companyId, employeeId);
                                    }    
                                }
                                double totalEmployeeTarget = 0 ;
                                //double oneSecValueForEmployee = 0 ;
                                for (int x = 0 ; x < VListEmployeeTarget.size(); x++){
                                    KPI_Employee_Target kPI_Employee_TargetLocal = (KPI_Employee_Target) VListEmployeeTarget.get(x);
                                    totalEmployeeTarget = totalEmployeeTarget+ kPI_Employee_TargetLocal.getTarget();
//                                    if (departementId >0 && (departementId == kPI_Section_TargetLocal.getSectionId())){
//                                        oneSecValueForEmployee = oneSecValueForEmployee + kPI_Section_TargetLocal.getTarget();
//                                    }
                                    if (type ==5 && kpiAchiev == 0){
                                        try{
						long oid = pstKPI_Employee_Target.insertExc(kPI_Employee_TargetLocal);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                        
                                    } 
                                }
                                 //delete all kpi employee target
                                if (type == 5 && kpiAchiev == 1){
                                long delEmpAchiev = PstKPI_Employee_Achiev.deleteKpiEmployeAchiev(tahun, kpiListId, companyId);
                                }
                                
                                //save kpi achiev
                                
                                if (type == 5 && kpiAchiev == 1){
                                for (int x = 0 ; x < VListEmployeeAchiev.size(); x++){
                                    KPI_Employee_Achiev kPI_Employee_Achiev = (KPI_Employee_Achiev) VListEmployeeAchiev.get(x);
                                   
                                    if (type ==5 && kpiAchiev == 1){
                                        try{
						long oid = PstKPI_Employee_Achiev.insertExc(kPI_Employee_Achiev);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                        
                                    } 
                                }
                                }
                                
                                
                                
                                }
                                
                                //frmKPI_Company_Target.requestEntityMultipleObjectEmployee(kpiListId, tahun, companyId, listKpiEmployeeTarget,VListCompanyTarget);
				
//                                //delete all kpi employee target
//                                long delemployee = PstKPI_Employee_Target.deleteKpiEmployeTarget(tahun, kpiListId, companyId);
//                                Vector VListEmployeeTarget = (Vector) frmKPI_Company_Target.getVlistKpiEmployeeTarget();
//                                for (int x = 0 ; x < VListEmployeeTarget.size(); x++){
//                                    KPI_Employee_Target kPI_Employee_Target = (KPI_Employee_Target) VListEmployeeTarget.get(x);
//                                    if (kPI_Employee_Target.getTarget() != 0){
//                                   try{
//						long oid = pstKPI_Employee_Target.insertExc(kPI_Employee_Target);
//					}catch(DBException dbexc){
//						excCode = dbexc.getErrorCode();
//						msgString = getSystemMessage(excCode);
//						return getControlMsgId(excCode);
//					}catch (Exception exc){
//						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//					}
// 
//                                }
//                                }
//                                
//                                
//                                Vector newlistKpiEmployeeTarget = pstKPI_Company_Target.listAlldataEmployee(tahun, kpiListId, companyId);
//                                
//                                long tempSect = 0;
//                                long tempDept = 0;
//                                long tempDivi = 0;
//                                
//                                double valueSect =  0;
//                                double valueDept =  0;
//                                double valueDivi =  0;
//                                
//                                double achievementSect =  0;
//                                double achievementDept =  0;
//                                double achievementDivi =  0;
//                                
//                                //variabel ini akan menyimpan total achiev
//                                double totalAchiev = 0;
//                                
//                                String dateStartString = "01-01-"+tahun;
//                                String dateEndString = "30-12-"+tahun;
//                                //delete kpi section,departement,division
//                                long delsection = PstKPI_Section_Target.deleteKpiSectionTarget(tahun, kpiListId, companyId);
//                                long deldepartment = PstKPI_Department_Target.deleteKpiDepartmentTarget(tahun, kpiListId, companyId);
//                                long deldivision = PstKPI_Division_Target.deleteKpiDivisionTarget(tahun, kpiListId, companyId);
//                                
//                                //insert database untuk section, department, division 
//                                for (int x = 0 ; x < newlistKpiEmployeeTarget.size(); x++){
//                                    KPI_ListAllDataEmp kPI_ListAllDataEmp = (KPI_ListAllDataEmp) newlistKpiEmployeeTarget.get(x);
//                                    totalAchiev = totalAchiev + kPI_ListAllDataEmp.getAchievement();
//                                    if (x == 0){
//                                        tempSect = kPI_ListAllDataEmp.getSectionId();
//                                        tempDept = kPI_ListAllDataEmp.getDepartmentId();
//                                        tempDivi = kPI_ListAllDataEmp.getDivisionId();
//                                    }
//                                                                           
//                                    if (kPI_ListAllDataEmp.getSectionId() == tempSect){
//                                        valueSect = valueSect + kPI_ListAllDataEmp.getTarget();
//                                        achievementSect = achievementSect + kPI_ListAllDataEmp.getAchievement();
//                                    } else {
//                                        if (tempSect > 0 ){
//                                                KPI_Section_Target kPI_Section_Target = new KPI_Section_Target();
//                                                kPI_Section_Target.setSectionId(tempSect);
//                                                kPI_Section_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
//                                                kPI_Section_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
//                                                kPI_Section_Target.setKpiListId(kpiListId);
//                                                kPI_Section_Target.setTarget(valueSect);
//                                                kPI_Section_Target.setAchievement(achievementSect);
//                                                 if (valueSect != 0) {
//                                                        try{
//                                                                long oid = pstKPI_Section_Target.insertExc(kPI_Section_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                 }
//                                        }
//                                            
//                                        tempSect= kPI_ListAllDataEmp.getSectionId();
//                                        valueSect = 0;
//                                        achievementSect = 0 ;
//                                        valueSect = valueSect + kPI_ListAllDataEmp.getTarget();
//                                        achievementSect = achievementSect + kPI_ListAllDataEmp.getAchievement();
//                                        }
//                                    
//                                    
//                                    
//                                    if (kPI_ListAllDataEmp.getDepartmentId() == tempDept){
//                                        valueDept = valueDept + kPI_ListAllDataEmp.getTarget();
//                                        achievementDept = achievementDept + kPI_ListAllDataEmp.getAchievement();
//                                    } else {
//                                        if (tempDept >0 ){
//                                                KPI_Department_Target kPI_Department_Target = new KPI_Department_Target();
//                                                kPI_Department_Target.setDepartmentId(tempDept);
//                                                kPI_Department_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
//                                                kPI_Department_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
//                                                kPI_Department_Target.setKpiListId(kpiListId);
//                                                kPI_Department_Target.setTarget(valueDept);
//                                                kPI_Department_Target.setAchievement(achievementDept);
//                                                if (valueDept != 0) {
//                                                    try{
//                                                                long oid = pstKPI_Department_Target.insertExc(kPI_Department_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                }
//                                                        
//   
//                                        }
//                                            
//                                        tempDept= kPI_ListAllDataEmp.getDepartmentId();
//                                        valueDept = 0;
//                                        achievementDept = 0 ;
//                                        
//                                        valueDept = valueDept + kPI_ListAllDataEmp.getTarget();
//                                        achievementDept = achievementDept + kPI_ListAllDataEmp.getAchievement();
//                                        
//                                        }
//                                    
//                                    
//                                    
//                                    
//                                    if (kPI_ListAllDataEmp.getDivisionId() == tempDivi ){
//                                        valueDivi = valueDivi + kPI_ListAllDataEmp.getTarget();
//                                        achievementDivi = achievementDivi + kPI_ListAllDataEmp.getAchievement();
//                                    } else {
//                                        if (tempDivi !=0 ){
//                                                KPI_Division_Target kPI_Division_Target = new KPI_Division_Target();
//                                                kPI_Division_Target.setDivisionId(tempDivi);
//                                                kPI_Division_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
//                                                kPI_Division_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
//                                                kPI_Division_Target.setKpiListId(kpiListId);
//                                                kPI_Division_Target.setTarget(valueDivi);
//                                                kPI_Division_Target.setAchievement(achievementDivi);
//                                                if (valueDivi > 0){
//                                                        try{
//                                                                long oid = pstKPI_Division_Target.insertExc(kPI_Division_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                }
//   
//                                        }
//                                            
//                                        tempDivi= kPI_ListAllDataEmp.getDivisionId();
//                                        valueDivi = 0;
//                                        achievementDivi = 0 ;
//                                        
//                                        valueDivi = valueDivi + kPI_ListAllDataEmp.getTarget();
//                                        achievementDivi = achievementDivi + kPI_ListAllDataEmp.getAchievement();
//                                        }
//                                    //untuk data terakir
//                                    if (x == (newlistKpiEmployeeTarget.size()-1)){
//                                        
//                                        
//                                        if ((tempSect >0) && (kPI_ListAllDataEmp.getSectionId() != 0) && (tempSect == kPI_ListAllDataEmp.getSectionId()) ){
//                                                KPI_Section_Target kPI_Section_Target = new KPI_Section_Target();
//                                                
//                                                kPI_Section_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
//                                                kPI_Section_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
//                                                kPI_Section_Target.setKpiListId(kpiListId);
//                                                if (kPI_ListAllDataEmp.getSectionId() == tempSect ){
//                                                    kPI_Section_Target.setSectionId(tempSect);
//                                                    kPI_Section_Target.setTarget(valueSect);
//                                                    kPI_Section_Target.setAchievement(achievementSect);
//                                                    if (valueSect > 0){
//                                                        try{
//                                                                long oid = pstKPI_Section_Target.insertExc(kPI_Section_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                    }
//                                                    
//                                                    
//                                                } else {
//                                                    kPI_Section_Target.setSectionId(kPI_ListAllDataEmp.getSectionId());
//                                                    kPI_Section_Target.setTarget(kPI_ListAllDataEmp.getTarget());
//                                                    kPI_Section_Target.setAchievement(kPI_ListAllDataEmp.getAchievement());
//                                                    if (kPI_ListAllDataEmp.getTarget() > 0){
//                                                        try{
//                                                                long oid = pstKPI_Section_Target.insertExc(kPI_Section_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                    }
//                                                }
//                                                
//                                                        
//                                                
//                                        }
//                                        
//                                        if ((tempDept !=0) && (tempDept == kPI_ListAllDataEmp.getDepartmentId()) ){
//                                                KPI_Department_Target kPI_Department_Target = new KPI_Department_Target();
//                                                
//                                                kPI_Department_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
//                                                kPI_Department_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
//                                                kPI_Department_Target.setKpiListId(kpiListId);
//                                                if (kPI_ListAllDataEmp.getDepartmentId() == tempDept ){
//                                                    kPI_Department_Target.setDepartmentId(tempDept);
//                                                    kPI_Department_Target.setTarget(valueDept);
//                                                    kPI_Department_Target.setAchievement(achievementDept);
//                                                    if (valueDept > 0){
//                                                        try{
//                                                                long oid = pstKPI_Department_Target.insertExc(kPI_Department_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                    }
//                                                } else {
//                                                    kPI_Department_Target.setDepartmentId(kPI_ListAllDataEmp.getDepartmentId());
//                                                    kPI_Department_Target.setTarget(kPI_ListAllDataEmp.getTarget());
//                                                    kPI_Department_Target.setAchievement(kPI_ListAllDataEmp.getAchievement());
//                                                    if (kPI_ListAllDataEmp.getTarget() > 0){
//                                                        try{
//                                                                long oid = pstKPI_Department_Target.insertExc(kPI_Department_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                    }
//                                                }
//                                                
//                                                        
//   
//                                        }
//                                        
//                                        
//                                        if ((tempDivi !=0) && (tempDivi == kPI_ListAllDataEmp.getDivisionId()) ){
//                                                KPI_Division_Target kPI_Division_Target = new KPI_Division_Target();
//                                                
//                                                kPI_Division_Target.setStartDate(Formater.formatDate(dateStartString, "dd-MM-yyyy"));
//                                                kPI_Division_Target.setEndDate(Formater.formatDate(dateEndString, "dd-MM-yyyy"));
//                                                kPI_Division_Target.setKpiListId(kpiListId);
//                                                if (kPI_ListAllDataEmp.getDivisionId() == tempDivi ){
//                                                    kPI_Division_Target.setDivisionId(tempDivi);
//                                                    kPI_Division_Target.setTarget(valueDivi);
//                                                    kPI_Division_Target.setAchievement(achievementDivi);
//                                                    if (valueDept > 0 ){
//                                                        try{
//                                                                long oid = pstKPI_Division_Target.insertExc(kPI_Division_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                    }
//                                                    
//                                                } else {
//                                                    kPI_Division_Target.setDivisionId(kPI_ListAllDataEmp.getDivisionId());
//                                                    kPI_Division_Target.setTarget(kPI_ListAllDataEmp.getTarget());
//                                                    kPI_Division_Target.setAchievement(kPI_ListAllDataEmp.getAchievement());
//                                                    if (kPI_ListAllDataEmp.getTarget() > 0 ){
//                                                        try{
//                                                                long oid = pstKPI_Division_Target.insertExc(kPI_Division_Target);
//                                                        }catch(DBException dbexc){
//                                                                excCode = dbexc.getErrorCode();
//                                                                msgString = getSystemMessage(excCode);
//                                                                return getControlMsgId(excCode);
//                                                        }catch (Exception exc){
//                                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                                                        }
//                                                    }
//                                                }
//                                                
//                                                        
//   
//                                        }
//                                    }
//                                       
//                                }
//                                double totaltargetcompany = 0;
//                                //cari total target
//                                for (int x = 0 ; x < VListCompanyTarget.size(); x++){
//                                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) VListCompanyTarget.get(x);
//                                    totaltargetcompany = totaltargetcompany + kPI_Company_Target.getTarget();
//                                }
//                                
//                                long delk = PstKPI_Company_Target.deletewhereYear(tahun, kpiListId, companyId);
//                                //save ulang untuk company sekaligus dengan achievement
//                                for (int x = 0 ; x < VListCompanyTarget.size(); x++){
//                                    KPI_Company_Target kPI_Company_Target = (KPI_Company_Target) VListCompanyTarget.get(x);
//                                    double percenttarget = (kPI_Company_Target.getTarget())/(totaltargetcompany/100); 
//                                    kPI_Company_Target.setAchievement(percenttarget*(totalAchiev/100));
//                                try{
//						long oid = pstKPI_Company_Target.insertExc(kPI_Company_Target);
//					}catch(DBException dbexc){
//						excCode = dbexc.getErrorCode();
//						msgString = getSystemMessage(excCode);
//						return getControlMsgId(excCode);
//					}catch (Exception exc){
//						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//					}
//
//                                }
                                
                                
				if(kPI_Company_Target.getOID()!=0){
					try {
						long oid = pstKPI_Company_Target.updateExc(this.kPI_Company_Target);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
                                
                                
                                
				break;

			case Command.EDIT :
				if (oidKPI_Company_Target != 0) {
					try {
						kPI_Company_Target = PstKPI_Company_Target.fetchExc(oidKPI_Company_Target);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidKPI_Company_Target != 0) {
					try {
						kPI_Company_Target = PstKPI_Company_Target.fetchExc(oidKPI_Company_Target);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidKPI_Company_Target != 0){
					try{
						long oid = PstKPI_Company_Target.deleteExc(oidKPI_Company_Target);
						if(oid!=0){
							msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
							excCode = RSLT_OK;
						}else{
							msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							excCode = RSLT_FORM_INCOMPLETE;
						}
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch(Exception exc){	
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			default :

		}
		return rsCode;
	}
}
