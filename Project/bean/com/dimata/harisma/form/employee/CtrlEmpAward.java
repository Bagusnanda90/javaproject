
package com.dimata.harisma.form.employee;

// import java
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppUser;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import dimata
import com.dimata.util.*;
import com.dimata.util.lang.*;

// import qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;

// import project
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.*;
import static com.dimata.harisma.form.employee.CtrlEmpLanguage.RSLT_FORM_INCOMPLETE;
import static com.dimata.harisma.form.employee.CtrlEmpLanguage.RSLT_OK;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author bayu
 */

public class CtrlEmpAward extends Control implements I_Language {
    
    public static int RSLT_OK               = 0;
    public static int RSLT_UNKNOWN_ERROR    = 1;
    public static int RSLT_EST_CODE_EXIST   = 2;
    public static int RSLT_FORM_INCOMPLETE  = 3;
    
    public static String[][] resultText = {
            {"Berhasil", "Tidak dapat diproses", "Kode sudah ada", "Data tidak lengkap"},
            {"Success", "Can not process", "Code exist", "Data incomplete"}
    };

    private int start;
    private String msgString;
    private int language;
    private EmpAward award;
    private PstEmpAward pstAward;
    private FrmEmpAward frmAward;    
    
    
    public CtrlEmpAward(HttpServletRequest request) {
        msgString = "";    
        language = LANGUAGE_DEFAULT;
        award = new EmpAward();
        
        try {
            pstAward = new PstEmpAward(0);
        }
        catch(Exception e) {}
        
        frmAward = new FrmEmpAward(request, award);
    }
    
    
    public int getStart() {
        return start;
    }
    
    public String getMessage() {
        return msgString;
    }
    
    public EmpAward getEmpAward() {
        return award;
    }
    
    public FrmEmpAward getForm() {
        return frmAward;
    }
    
    public int getLanguage() {
        return language;
    }
    
    public void setLanguage(int language) {
        this.language = language;
    }
    
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                frmAward.addError(frmAward.FRM_FIELD_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR]; 
        }
    }
    
    private int getControlMsgId(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }
    
    public int action (int cmd, long oid, HttpServletRequest request, String loginName, long userId, String oidDeleteAll) {
        return action(cmd, oid, request, loginName, userId, oidDeleteAll, "");
    }

    public int action(int cmd, long oid, HttpServletRequest request, String loginName, long userId, String oidDeleteAll, String object) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
        //long sysLog = 1;
        String logField = "";
        String logPrev = "";
        String logCurr = "";
        Date nowDate = new Date();
        AppUser appUser = new AppUser();
        Employee emp = new Employee();
        try {
            appUser = PstAppUser.fetch(userId);
            emp = PstEmployee.fetchExc(appUser.getEmployeeId());
        } catch(Exception e){
            System.out.println("Get AppUser: userId");
        }
        /* End Prepare data (configurasi log system) */

        switch(cmd){
                case Command.ADD :
                        break;
                        
                case Command.EDIT :
                        if(oid != 0) {
                            try {
                                    award = PstEmpAward.fetchExc(oid);
                            } 
                            catch (DBException dbexc){
                                    excCode = dbexc.getErrorCode();
                                    msgString = getSystemMessage(excCode);
                            } 
                            catch (Exception exc){ 
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }
                        }
                        break;

                case Command.SAVE :
                    EmpAward prevEmpAward = null;
                        if(oid != 0){
                            try{
                            award = PstEmpAward.fetchExc(oid);
                            if(sysLog == 1){
                            
                            prevEmpAward = PstEmpAward.fetchExc(oid);
                        }
                            }
                            catch(Exception exc){}
                        }

                        frmAward.requestEntityObject(award);

                        if(frmAward.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if (award.getOID() == 0) {
                        try {
                        long result = pstAward.insertExc(this.award);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        
                        EmpDocList empDocList = new EmpDocList();
                        empDocList.setEmp_doc_id(award.getDocId());
                        empDocList.setEmployee_id(award.getEmployeeId());
                        empDocList.setEmp_award_id(result);
                        empDocList.setObject_name(object);
                        empDocList.setEmp_career(0);
                        empDocList.setEmp_reprimand(0);
                        long oidEmpDocList = PstEmpDocList.insertExc(empDocList);
                        
                        
                        if (sysLog == 1) {
                            
                            String className = award.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                        
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + award.getEmployeeId();
                            /* Lakukan set data ke entity logSysHistory */
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("ADD"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            /* Inisialisasi logField dengan menggambil field EmpEducation */
                            /* Tips: ambil data field dari persistent */
                            logField = PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_DEPARTMENT_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_SECTION_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DESC]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_PROVIDER_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_TITLE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_DIVISION_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_COMPANY_ID]+";";
                            
                            
                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr = ""+award.getOID()+";";
                            logCurr += ""+award.getEmployeeId()+";";
                            logCurr += ""+award.getDepartmentId()+";";
                            logCurr += ""+award.getSectionId()+";";
                            logCurr += ""+award.getAwardDate()+";";
                            logCurr += ""+award.getAwardType()+";";
                            logCurr += ""+award.getAwardDescription()+";";
                            logCurr += ""+award.getProviderId()+";";
                            logCurr += ""+award.getTitle()+";";
                            logCurr += ""+award.getDivisionId()+";";
                            logCurr += ""+award.getCompanyId()+";";
  
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Award"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(award.getEmployeeId());
                            /* setelah di set maka lakukan proses insert ke table logSysHistory */
                            PstLogSysHistory.insertExc(logSysHistory);
                        }
                       
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } 
                        else {                      // update
                            try {
                                long result = pstAward.updateExc(this.award);
                                msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                                // logHistory
                                String whereList = PstEmpDocList.fieldNames[PstEmpDocList.FLD_AWARD_ID]+ " = " + result; 
                                Vector listEmpDocList = PstEmpDocList.list(0, 0, whereList , "");
                                if (listEmpDocList.size() > 0){
                                    for (int i=0; i< listEmpDocList.size(); i++){
                                        EmpDocList empDocListGet = (EmpDocList) listEmpDocList.get(i);
                                        EmpDocList empDocList = new EmpDocList();
                                        empDocList.setOID(empDocListGet.getOID());
                                        empDocList.setEmp_doc_id(award.getDocId());
                                        empDocList.setEmployee_id(award.getEmployeeId());
                                        empDocList.setEmp_award_id(result);
                                        empDocList.setObject_name(object);
                                        empDocList.setEmp_career(0);
                                        empDocList.setEmp_reprimand(0);
                                        long oidEmpDocList = PstEmpDocList.updateExc(empDocList);
                                    }
                                }

                                
                        if(sysLog == 1){
                            award = pstAward.fetchExc(oid);
                            
                            logField = PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_DEPARTMENT_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_SECTION_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DESC]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_PROVIDER_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_TITLE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_DIVISION_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_COMPANY_ID]+";";
                            
                            logCurr = ""+award.getOID()+";";
                            logCurr += ""+award.getEmployeeId()+";";
                            logCurr += ""+award.getDepartmentId()+";";
                            logCurr += ""+award.getSectionId()+";";
                            logCurr += ""+award.getAwardDate()+";";
                            logCurr += ""+award.getAwardType()+";";
                            logCurr += ""+award.getAwardDescription()+";";
                            logCurr += ""+award.getProviderId()+";";
                            logCurr += ""+award.getTitle()+";";
                            logCurr += ""+award.getDivisionId()+";";
                            logCurr += ""+award.getCompanyId()+";";
                            
                            logPrev = ""+prevEmpAward.getOID()+";";
                            logPrev += ""+prevEmpAward.getEmployeeId()+";";
                            logPrev += ""+prevEmpAward.getDepartmentId()+";";
                            logPrev += ""+prevEmpAward.getSectionId()+";";
                            logPrev += ""+prevEmpAward.getAwardDate()+";";
                            logPrev += ""+prevEmpAward.getAwardType()+";";
                            logPrev += ""+prevEmpAward.getAwardDescription()+";";
                            logPrev += ""+prevEmpAward.getProviderId()+";";
                            logPrev += ""+prevEmpAward.getTitle()+";";
                            logPrev += ""+prevEmpAward.getDivisionId()+";";
                            logPrev += ""+prevEmpAward.getCompanyId()+";";    
                                
                                
                                
                            String className = award.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + award.getOID();
                            
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("EDIT"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Award"); /* nama sub module*/
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(award.getEmployeeId());
                            PstLogSysHistory.insertExc(logSysHistory);
                                   
                                
                        }
                            }
                            catch (DBException dbexc){
                                excCode = dbexc.getErrorCode();
                                msgString = getSystemMessage(excCode);
                            }
                            catch (Exception exc){
                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
                            }

                        }
                        break;
                

                case Command.ASK :
                        if (oid != 0) {
                            try {                                    
                                msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                                award = PstEmpAward.fetchExc(oid);
                            } 
                            catch (DBException dbexc){
                                excCode = dbexc.getErrorCode();
                                msgString = getSystemMessage(excCode);
                            } 
                            catch (Exception exc){ 
                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }
                        }
                        break;

                case Command.DELETE :
                        if (oid != 0){
                            try{
                                
                            EmpAward empAward = PstEmpAward.fetchExc(oid);
                            logField = PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_DEPARTMENT_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_SECTION_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_TYPE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DESC]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_PROVIDER_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_TITLE]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_DIVISION_ID]+";";
                            logField += PstEmpAward.fieldNames[PstEmpAward.FLD_COMPANY_ID]+";";
                            
                            logPrev = ""+empAward.getOID()+";";
                            logPrev += ""+empAward.getEmployeeId()+";";
                            logPrev += ""+empAward.getDepartmentId()+";";
                            logPrev += ""+empAward.getSectionId()+";";
                            logPrev += ""+empAward.getAwardDate()+";";
                            logPrev += ""+empAward.getAwardType()+";";
                            logPrev += ""+empAward.getAwardDescription()+";";
                            logPrev += ""+empAward.getProviderId()+";";
                            logPrev += ""+empAward.getTitle()+";";
                            logPrev += ""+empAward.getDivisionId()+";";
                            logPrev += ""+empAward.getCompanyId()+";";
                           
                            /*Curr*/
                            logCurr  = "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            logCurr += "-;";
                            
                                
                            long result = PstEmpAward.deleteExc(oid);
                            if (sysLog == 1) {
                                   
                                    
                            String className = award.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empAward.getEmployeeId();

                            
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("DELETE"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Award"); /* nama sub module*/
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(award.getEmployeeId());
                            PstLogSysHistory.insertExc(logSysHistory);
                            }
                                
                                if(result != 0){
                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                    excCode = RSLT_OK;
                                }
                                else{
                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                    excCode = RSLT_FORM_INCOMPLETE;
                                }
                            }
                            catch(DBException dbexc){
                                excCode = dbexc.getErrorCode();
                                msgString = getSystemMessage(excCode);
                            }
                            catch(Exception exc){	
                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }
                        }
                        break;
                        
                        case Command.DELETEALL:
                        //splits => untuk mengurutkan beberapa data menjadi baris data
                        String[] splits = oidDeleteAll.split(",");
                        for (String asset : splits) {
                            if (asset != "") {
                                long oidAward = Long.parseLong(asset);
                                if (oidAward != 0) {
                                    try {
                                        long result = PstEmpAward.deleteExc(oidAward);
                                        if (result != 0) {
                                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                            excCode = RSLT_OK;
                                        } else {
                                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                            excCode = RSLT_FORM_INCOMPLETE;
                                        }
                                    } catch (DBException dbexc) {
                                        excCode = dbexc.getErrorCode();
                                        msgString = getSystemMessage(excCode);
                                    } catch (Exception exc) {
                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    }
                                }
                            }
                        }
                        break;
        }
        
        return rsCode;
    }
}
