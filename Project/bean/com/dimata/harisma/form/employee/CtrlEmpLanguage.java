/* 
 * Ctrl Name  		:  CtrlEmpLanguage.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee;

/* java package */
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
import com.dimata.harisma.entity.masterdata.EmployeeCompetency;
import com.dimata.harisma.entity.masterdata.Language;
import com.dimata.harisma.entity.masterdata.PstCompetency;
import com.dimata.harisma.entity.masterdata.PstEmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstLanguage;
import static com.dimata.harisma.form.employee.CtrlExperience.RSLT_OK;
import com.dimata.system.entity.PstSystemProperty;

public class CtrlEmpLanguage extends Control implements I_Language {

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
    private EmpLanguage empLanguage;
    private PstEmpLanguage pstEmpLanguage;
    private FrmEmpLanguage frmEmpLanguage;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpLanguage(HttpServletRequest request) {
        msgString = "";
        empLanguage = new EmpLanguage();
        try {
            pstEmpLanguage = new PstEmpLanguage(0);
        } catch (Exception e) {;
        }
        frmEmpLanguage = new FrmEmpLanguage(request, empLanguage);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpLanguage.addError(frmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpLanguage getEmpLanguage() {
        return empLanguage;
    }

    public FrmEmpLanguage getForm() {
        return frmEmpLanguage;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpLanguage, long oidEmployee, HttpServletRequest request, String loginName, long userId, String oidDeleteAll) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        /* Prepare data (configurasi log system) */
        int sysLog = Integer.parseInt(String.valueOf(com.dimata.system.entity.system.PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
        String logField = "";
        String logPrev = "";
        String logCurr = "";
        Date nowDate = new Date();
        AppUser appUser = new AppUser();
        Employee emp = new Employee();
        try {
            appUser = PstAppUser.fetch(userId);
            emp = PstEmployee.fetchExc(appUser.getEmployeeId());
        } catch (Exception e) {
            System.out.println("Get AppUser: userId: " + e.toString());
        }
        /* End Prepare data (configurasi log system) */

        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                EmpLanguage prevEmployeeLang = null;
                if (oidEmpLanguage != 0) {
                    try {
                        empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);

                        if (sysLog == 1) {
                            prevEmployeeLang = PstEmpLanguage.fetchExc(oidEmpLanguage);

                        }

                    } catch (Exception exc) {
                    }
                }

                empLanguage.setOID(oidEmpLanguage);
                frmEmpLanguage.requestEntityObject(empLanguage);
                empLanguage.setEmployeeId(oidEmployee);

                if (frmEmpLanguage.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (empLanguage.getOID() == 0) {
                    try {
                        long oid = pstEmpLanguage.insertExc(this.empLanguage);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        if (sysLog == 1) { /* kondisi jika sysLog == 1, maka proses di bawah ini dijalankan*/
                            LogSysHistory logSysHistory = new LogSysHistory();   
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;
                            /* Lakukan set data ke entity logSysHistory */
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(PstEmpLanguage.TBL_HR_EMP_LANGUAGE); //entity
                            logSysHistory.setLogUserAction("ADD"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            /* Inisialisasi logField dengan menggambil field EmpEducation */
                            /* Tips: ambil data field dari persistent */

                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMP_LANGUAGE_ID]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_LANGUAGE_ID]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_DESCRIPTION]+";";

                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr += ""+oid+";";
                            logCurr += ""+empLanguage.getLanguageId()+";";
                            logCurr += ""+empLanguage.getEmployeeId()+";";
                            logCurr += ""+empLanguage.getOral()+";";
                            logCurr += ""+empLanguage.getWritten()+";";
                            logCurr += "'"+empLanguage.getDescription()+"';";
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Language"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(empLanguage.getEmployeeId());
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
                        long oid = pstEmpLanguage.updateExc(this.empLanguage);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        // logHistory
                        if (sysLog == 1) {
                            empLanguage = PstEmpLanguage.fetchExc(oid);
                            /* Inisialisasi logField dengan menggambil field EmpEducation */
                            /* Tips: ambil data field dari persistent */
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMP_LANGUAGE_ID]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_LANGUAGE_ID]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+";";
                            logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_DESCRIPTION]+";";

                            logPrev += ""+prevEmployeeLang.getOID()+";";
                            logPrev += ""+prevEmployeeLang.getLanguageId()+";";
                            logPrev += ""+prevEmployeeLang.getEmployeeId()+";";
                            logPrev += ""+prevEmployeeLang.getOral()+";";
                            logPrev += ""+prevEmployeeLang.getWritten()+";";
                            logPrev += "'"+prevEmployeeLang.getDescription()+"';";
                            
                            logCurr += ""+empLanguage.getOID()+";";
                            logCurr += ""+empLanguage.getLanguageId()+";";
                            logCurr += ""+empLanguage.getEmployeeId()+";";
                            logCurr += ""+empLanguage.getOral()+";";
                            logCurr += ""+empLanguage.getWritten()+";";
                            logCurr += "'"+empLanguage.getDescription()+"';";

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;

                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(PstEmpLanguage.TBL_HR_EMP_LANGUAGE); //entity
                            logSysHistory.setLogUserAction("EDIT"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Language");
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(empLanguage.getEmployeeId());

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
                if (oidEmpLanguage != 0) {
                    try {
                        empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpLanguage != 0) {
                    try {
                        empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpLanguage != 0) {
                    try {
                        empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);

                        logField = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMP_LANGUAGE_ID]+";";
                        logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_LANGUAGE_ID]+";";
                        logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+";";
                        logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_ORAL]+";";
                        logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_WRITTEN]+";";
                        logField += PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_DESCRIPTION]+";";
                        
                        logPrev = ""+empLanguage.getOID()+";";
                        logPrev += ""+empLanguage.getLanguageId()+";";
                        logPrev += ""+empLanguage.getEmployeeId()+";";
                        logPrev += ""+empLanguage.getOral()+";";
                        logPrev += ""+empLanguage.getWritten()+";";
                        logPrev += "'"+empLanguage.getDescription()+"';";

                        long oid = PstEmpLanguage.deleteExc(oidEmpLanguage);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                        if (sysLog == 1) {
                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;

                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(PstEmpLanguage.TBL_HR_EMP_LANGUAGE); //entity
                            logSysHistory.setLogUserAction("DELETE"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logPrev);
                            logSysHistory.setLogModule("Employee Language");
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(empLanguage.getEmployeeId());

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
                        long oidEmpLang = Long.parseLong(asset);
                        if (oidEmpLang != 0) {
                            try {
                                long oid = PstEmpLanguage.deleteExc(oidEmpLang);
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
