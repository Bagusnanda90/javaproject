/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form;

import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.employee.EmpRelevantDoc;
import com.dimata.harisma.entity.employee.EmpRelvtDocPage;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmpRelevantDoc;
import com.dimata.harisma.entity.employee.PstEmpRelvtDocPage;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.form.employee.FrmEmpRelvtDocPage;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class CtrlEmpRelvtDocPage extends Control implements I_Language {

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
    private EmpRelvtDocPage entEmpRelvtDocPage;
    private PstEmpRelvtDocPage pstEmpRelvtDocPage;
    private FrmEmpRelvtDocPage frmEmpRelvtDocPage;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpRelvtDocPage(HttpServletRequest request) {
        msgString = "";
        entEmpRelvtDocPage = new EmpRelvtDocPage();
        try {
            pstEmpRelvtDocPage = new PstEmpRelvtDocPage(0);
        } catch (Exception e) {;
        }
        frmEmpRelvtDocPage = new FrmEmpRelvtDocPage(request, entEmpRelvtDocPage);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpRelvtDocPage.addError(frmEmpRelvtDocPage.FRM_FIELD_EMP_RELVT_DOC_PAGE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpRelvtDocPage getEmpRelvtDocPage() {
        return entEmpRelvtDocPage;
    }

    public FrmEmpRelvtDocPage getForm() {
        return frmEmpRelvtDocPage;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpRelvtDocPage, HttpServletRequest request, String loginName, long userId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
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
            System.out.println("Get AppUser: userId");
        }
        EmpRelevantDoc empRelvtDoc = new EmpRelevantDoc();
        try {
            empRelvtDoc = PstEmpRelevantDoc.fetchExc(entEmpRelvtDocPage.getOID());
        } catch (Exception exc) {
            System.out.println("");
        }
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                EmpRelvtDocPage prevEmpRelvtDocPage = null;
                
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        entEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);
                        if (sysLog == 1) {
                            prevEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);

                        }
                    } catch (Exception exc) {
                    }
                }

                frmEmpRelvtDocPage.requestEntityObject(entEmpRelvtDocPage);

                if (frmEmpRelvtDocPage.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entEmpRelvtDocPage.getOID() == 0) {
                    try {
                        long oid = pstEmpRelvtDocPage.insertExc(this.entEmpRelvtDocPage);
                        if (sysLog == 1) { /* kondisi jika sysLog == 1, maka proses di bawah ini dijalankan*/
                            
                            String className = entEmpRelvtDocPage.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empRelvtDoc.getEmployeeId();
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
                            logField = PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_EMP_RELVT_DOC_PAGE_ID]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_PAGE_TITLE]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_PAGE_DESC]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_DOC_RELEVANT_ID]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_FILE_NAME]+";";
                            
                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr += ""+entEmpRelvtDocPage.getOID()+";";
                            logCurr += ""+entEmpRelvtDocPage.getPageTitle()+";";
                            logCurr += ""+entEmpRelvtDocPage.getPageDesc()+";";
                            logCurr += ""+entEmpRelvtDocPage.getDocRelevantId()+";";
                            logCurr += ""+entEmpRelvtDocPage.getFileName()+";";
                            
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Relevant Doc Page"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(empRelvtDoc.getEmployeeId());
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
                        long oid = pstEmpRelvtDocPage.updateExc(this.entEmpRelvtDocPage);
                        if(sysLog == 1){
                            logField = PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_EMP_RELVT_DOC_PAGE_ID]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_PAGE_TITLE]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_PAGE_DESC]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_DOC_RELEVANT_ID]+";";
                            logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_FILE_NAME]+";";
                            
                            logCurr = ""+entEmpRelvtDocPage.getOID()+";";
                            logCurr += ""+entEmpRelvtDocPage.getPageTitle()+";";
                            logCurr += ""+entEmpRelvtDocPage.getPageDesc()+";";
                            logCurr += ""+entEmpRelvtDocPage.getDocRelevantId()+";";
                            if(entEmpRelvtDocPage.getFileName() != null){
                            logCurr += ""+entEmpRelvtDocPage.getFileName()+";";    
                            }
                            
                            
                            logPrev = ""+prevEmpRelvtDocPage.getOID()+";";
                            logPrev += ""+prevEmpRelvtDocPage.getPageTitle()+";";
                            logPrev += ""+prevEmpRelvtDocPage.getPageDesc()+";";
                            logPrev += ""+prevEmpRelvtDocPage.getDocRelevantId()+";";
                            logPrev += ""+prevEmpRelvtDocPage.getFileName()+";";
                            
                            String className = entEmpRelvtDocPage.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empRelvtDoc.getEmployeeId();
                            /* Lakukan set data ke entity logSysHistory */
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
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogModule("Employee Relevant Doc Page"); /* nama sub module*/
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(empRelvtDoc.getEmployeeId());
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
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        entEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);
                        
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        entEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpRelvtDocPage != 0) {
                    try {
                        entEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelvtDocPage);
                        logField = PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_EMP_RELVT_DOC_PAGE_ID]+";";
                        logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_PAGE_TITLE]+";";
                        logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_PAGE_DESC]+";";
                        logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_DOC_RELEVANT_ID]+";";
                        logField += PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_FILE_NAME]+";";
                            
                        logPrev = ""+entEmpRelvtDocPage.getOID()+";";
                        logPrev += ""+entEmpRelvtDocPage.getPageTitle()+";";
                        logPrev += ""+entEmpRelvtDocPage.getPageDesc()+";";
                        logPrev += ""+entEmpRelvtDocPage.getDocRelevantId()+";";
                        logPrev += ""+entEmpRelvtDocPage.getFileName()+";";
                            
                        logCurr = "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        long oid = PstEmpRelvtDocPage.deleteExc(oidEmpRelvtDocPage);
                        if (sysLog == 1) {
                            String className = entEmpRelvtDocPage.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empRelvtDoc.getEmployeeId();

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
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Relevant Doc Page");
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(empRelvtDoc.getEmployeeId());

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

            default:

        }
        return rsCode;
    }
}
