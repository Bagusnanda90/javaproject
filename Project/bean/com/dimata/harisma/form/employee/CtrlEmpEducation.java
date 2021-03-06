/* 
 * Ctrl Name  		:  CtrlEmpEducation.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee;
 
/* java package */
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppUser;
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
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.system.entity.system.PstSystemProperty;

public class CtrlEmpEducation extends Control implements I_Language {

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
    private EmpEducation empEducation;
    private PstEmpEducation pstEmpEducation;
    private FrmEmpEducation frmEmpEducation;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpEducation(HttpServletRequest request) {
        msgString = "";
        empEducation = new EmpEducation();
        try {
            pstEmpEducation = new PstEmpEducation(0);
        } catch (Exception e) {;
        }
        frmEmpEducation = new FrmEmpEducation(request, empEducation);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpEducation.addError(frmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpEducation getEmpEducation() {
        return empEducation;
    }

    public FrmEmpEducation getForm() {
        return frmEmpEducation;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpEducation, long oidEmployee, HttpServletRequest request, String loginName, long userId, String oidDeleteAll) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        /* Prepare data (configurasi log system) */
        int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
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
            System.out.println("Get AppUser: userId: "+e.toString());
        }
        /* End Prepare data (configurasi log system) */
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                EmpEducation prevEmpEducation = null;
                if (oidEmpEducation != 0) {
                    try {
                        empEducation = PstEmpEducation.fetchExc(oidEmpEducation);

                        if (sysLog == 1) {
                            prevEmpEducation = PstEmpEducation.fetchExc(oidEmpEducation);

                        }
                    } catch (Exception exc) {
                    }
                }
                //System.out.println("===> empEducation=" + empEducation);
                empEducation.setOID(oidEmpEducation);

                frmEmpEducation.requestEntityObject(empEducation);
                empEducation.setEmployeeId(oidEmployee);

                if (frmEmpEducation.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (empEducation.getOID() == 0) {
                    try {
                        long oid = pstEmpEducation.insertExc(this.empEducation);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        if (sysLog == 1) { /* kondisi jika sysLog == 1, maka proses di bawah ini dijalankan*/
                            String className = empEducation.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;
                            /* Lakukan set data ke entity logSysHistory */
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(PstEmpEducation.TBL_HR_EMP_EDUCATION); //entity
                            logSysHistory.setLogUserAction("ADD"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            /* Inisialisasi logField dengan menggambil field EmpEducation */
                            /* Tips: ambil data field dari persistent */
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_DESC]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_POINT]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_INSTITUTION_ID]+";";
                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr += ""+oid+";";
                            logCurr += ""+empEducation.getEducationId()+";";
                            logCurr += ""+empEducation.getEmployeeId()+";";
                            logCurr += ""+empEducation.getStartDate()+";";
                            logCurr += ""+empEducation.getEndDate()+";";
                            logCurr += ""+empEducation.getGraduation()+";";
                            logCurr += ""+empEducation.getEducationDesc()+";";
                            logCurr += ""+empEducation.getPoint()+";";
                            logCurr += ""+empEducation.getInstitutionId()+";";
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Education"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(empEducation.getEmployeeId());
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

                } else {
                    try {
                        long oid = pstEmpEducation.updateExc(this.empEducation);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        // logHistory
                        if (sysLog == 1) {
                            empEducation = PstEmpEducation.fetchExc(oid);
                            /* Inisialisasi logField dengan menggambil field EmpEducation */
                            /* Tips: ambil data field dari persistent */
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_DESC]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_POINT]+";";
                            logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_INSTITUTION_ID]+";";
                            
                            logPrev += ""+prevEmpEducation.getOID()+";";
                            logPrev += ""+prevEmpEducation.getEducationId()+";";
                            logPrev += ""+prevEmpEducation.getEmployeeId()+";";
                            logPrev += prevEmpEducation.getStartDate()+";";
                            logPrev += prevEmpEducation.getEndDate()+";";
                            logPrev += "'"+prevEmpEducation.getGraduation()+"';";
                            logPrev += "'"+prevEmpEducation.getEducationDesc()+"';";
                            logPrev += prevEmpEducation.getPoint()+";";
                            logPrev += ""+prevEmpEducation.getInstitutionId()+";";
                            
                            logCurr += ""+empEducation.getOID()+";";
                            logCurr += ""+empEducation.getEducationId()+";";
                            logCurr += ""+empEducation.getEmployeeId()+";";
                            logCurr += empEducation.getStartDate()+";";
                            logCurr += empEducation.getEndDate()+";";
                            logCurr += "'"+empEducation.getGraduation()+"';";
                            logCurr += "'"+empEducation.getEducationDesc()+"';";
                            logCurr += ""+empEducation.getPoint()+";";
                            logCurr += ""+empEducation.getInstitutionId()+";";

                            String className = empEducation.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;

                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(PstEmpEducation.TBL_HR_EMP_EDUCATION); //entity
                            logSysHistory.setLogUserAction("EDIT"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Education");
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(empEducation.getEmployeeId());

                            PstLogSysHistory.insertExc(logSysHistory);

                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidEmpEducation != 0) {
                    try {
                        empEducation = PstEmpEducation.fetchExc(oidEmpEducation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpEducation != 0) {
                    try {
                        empEducation = PstEmpEducation.fetchExc(oidEmpEducation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpEducation != 0) {
                    try {
                        EmpEducation empEdukasi = PstEmpEducation.fetchExc(oidEmpEducation);
                        logField  = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMP_EDUCATION_ID]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_DESC]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_GRADUATION]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_INSTITUTION_ID]+";";
                        logField += PstEmpEducation.fieldNames[PstEmpEducation.FLD_POINT]+";";
                        /* prev */
                        logPrev  = empEdukasi.getOID()+";";
                        logPrev += empEdukasi.getEmployeeId()+";";
                        logPrev += empEdukasi.getEducationId()+";";
                        logPrev += empEdukasi.getStartDate()+";";
                        logPrev += empEdukasi.getEndDate()+";";
                        logPrev += "'"+empEdukasi.getEducationDesc()+"';";
                        logPrev += "'"+empEdukasi.getGraduation()+"';";
                        logPrev += empEdukasi.getInstitutionId()+";";
                        logPrev += empEdukasi.getPoint()+";";

                    } catch(Exception e){
                        System.out.println(e.toString());
                    }
                    
                    try {

                        long oid = PstEmpEducation.deleteExc(oidEmpEducation);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                        // logHistory
                        if (sysLog == 1) {

                            if (empEducation.getEducationId() != 0) {
                                Education Edu = PstEducation.fetchExc(empEducation.getEducationId());
                                //logDetail = logDetail + " Education : " + Edu.getEducation() + " DELETED</br>";
                            }

                            String className = empEducation.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;

                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(PstEmpEducation.TBL_HR_EMP_EDUCATION); //entity
                            logSysHistory.setLogUserAction("DELETE"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logPrev);
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogModule("Employee Education");
                            logSysHistory.setLogEditedUserId(empEducation.getEmployeeId());
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());

                            PstLogSysHistory.insertExc(logSysHistory);

                        }

                        if (oid != 0) {
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
                break;
                case Command.DELETEALL:
                //splits => untuk mengurutkan beberapa data menjadi baris data
                String[] splits = oidDeleteAll.split(",");
                for (String asset : splits) {
                    if (asset != "") {
                        long oidEdu = Long.parseLong(asset);
                        if (oidEdu != 0) {
                            try {
                                long oid = PstEmpEducation.deleteExc(oidEdu);
                                if (oid != 0) {
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

            default:

        }
        return rsCode;
    }
}
