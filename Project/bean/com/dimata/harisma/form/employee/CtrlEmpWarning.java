package com.dimata.harisma.form.employee;

// import java
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
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author bayu
 */
public class CtrlEmpWarning extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Kode sudah ada", "Data tidak lengkap"},
        {"Success", "Can not process", "Code exist", "Data incomplete"}
    };

    private int start;
    private String msgString;
    private int language;
    private EmpWarning warning;
    private PstEmpWarning pstWarning;
    private FrmEmpWarning frmWarning;

    public CtrlEmpWarning(HttpServletRequest request) {
        msgString = "";
        language = LANGUAGE_DEFAULT;
        warning = new EmpWarning();

        try {
            pstWarning = new PstEmpWarning(0);
        } catch (Exception e) {
        }

        frmWarning = new FrmEmpWarning(request, warning);
    }

    public int getStart() {
        return start;
    }

    public String getMessage() {
        return msgString;
    }

    public EmpWarning getEmpWarning() {
        return warning;
    }

    public FrmEmpWarning getForm() {
        return frmWarning;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                frmWarning.addError(frmWarning.FRM_FIELD_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public int action(int cmd, long oid, HttpServletRequest request, String loginName, long userId, String oidDeleteAll) {
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
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.EDIT:
                if (oid != 0) {
                    try {
                        warning = PstEmpWarning.fetchExc(oid);

                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.SAVE:
                EmpWarning prevEmpWarning = null;
                if (oid != 0) {
                    try {
                        warning = PstEmpWarning.fetchExc(oid);
                        if (sysLog == 1) {
                        
                        prevEmpWarning = PstEmpWarning.fetchExc(oid);

                        }  
                    } catch (Exception exc) {
                    }
                }

                frmWarning.requestEntityObject(warning);

                if (frmWarning.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (warning.getOID() == 0) {    // insert
                    try {
                        long result = pstWarning.insertExc(this.warning);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        if (sysLog == 1){
                        String className = warning.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + warning.getOID();
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
                            
                            logField = PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_FACT]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_BY]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_DATE]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_VALID_UNTIL]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_LEVEL_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_COMPANY_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_DIVISION_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_DEPARTMENT_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_SECTION_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_POSITION_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_LEVEL_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMP_CATEGORY_ID]+";";
                            
                            
                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr = ""+warning.getOID()+";";
                            logCurr += ""+warning.getEmployeeId()+";";
                            logCurr += ""+warning.getBreakFact()+";";
                            logCurr += ""+warning.getBreakDate()+";";
                            logCurr += ""+warning.getWarningBy()+";";
                            logCurr += ""+warning.getWarningDate()+";";
                            logCurr += ""+warning.getValidityDate()+";";
                            logCurr += ""+warning.getWarnLevelId()+";";
                            logCurr += ""+warning.getCompanyId()+";";
                            logCurr += ""+warning.getDivisionId()+";";
                            logCurr += ""+warning.getDepartmentId()+";";
                            logCurr += ""+warning.getSectionId()+";";
                            logCurr += ""+warning.getPositionId()+";";
                            logCurr += ""+warning.getLevelId()+";";
                            logCurr += ""+warning.getEmpCategoryId()+";";
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Warning"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(warning.getEmployeeId());
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
                        long result = pstWarning.updateExc(this.warning);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        // logHistory
                        if (sysLog == 1) {
                            warning = PstEmpWarning.fetchExc(oid);
                            
                            logField = PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_FACT]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_BY]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_DATE]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_VALID_UNTIL]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_LEVEL_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_COMPANY_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_DIVISION_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_DEPARTMENT_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_SECTION_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_POSITION_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_LEVEL_ID]+";";
                            logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMP_CATEGORY_ID]+";";
                            
                            logCurr = ""+warning.getOID()+";";
                            logCurr += ""+warning.getEmployeeId()+";";
                            logCurr += ""+warning.getBreakFact()+";";
                            logCurr += ""+warning.getBreakDate()+";";
                            logCurr += ""+warning.getWarningBy()+";";
                            logCurr += ""+warning.getWarningDate()+";";
                            logCurr += ""+warning.getValidityDate()+";";
                            logCurr += ""+warning.getWarnLevelId()+";";
                            logCurr += ""+warning.getCompanyId()+";";
                            logCurr += ""+warning.getDivisionId()+";";
                            logCurr += ""+warning.getDepartmentId()+";";
                            logCurr += ""+warning.getSectionId()+";";
                            logCurr += ""+warning.getPositionId()+";";
                            logCurr += ""+warning.getLevelId()+";";
                            logCurr += ""+warning.getEmpCategoryId()+";";
                            
                            logPrev = ""+prevEmpWarning.getOID()+";";
                            logPrev += ""+prevEmpWarning.getEmployeeId()+";";
                            logPrev += ""+prevEmpWarning.getBreakFact()+";";
                            logPrev += ""+prevEmpWarning.getBreakDate()+";";
                            logPrev += ""+prevEmpWarning.getWarningBy()+";";
                            logPrev += ""+prevEmpWarning.getWarningDate()+";";
                            logPrev += ""+prevEmpWarning.getValidityDate()+";";
                            logPrev += ""+prevEmpWarning.getWarnLevelId()+";";
                            logPrev += ""+prevEmpWarning.getCompanyId()+";";
                            logPrev += ""+prevEmpWarning.getDivisionId()+";";
                            logCurr += ""+prevEmpWarning.getDepartmentId()+";";
                            logPrev += ""+prevEmpWarning.getSectionId()+";";
                            logPrev += ""+prevEmpWarning.getPositionId()+";";
                            logPrev += ""+prevEmpWarning.getLevelId()+";";
                            logPrev += ""+prevEmpWarning.getEmpCategoryId()+";";
    
                            String className = warning.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + warning.getEmployeeId();

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
                            logSysHistory.setLogModule("Employee Warning"); /* nama sub module*/
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(warning.getEmployeeId());
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

            case Command.ASK:
                if (oid != 0) {
                    try {
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        warning = PstEmpWarning.fetchExc(oid);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oid != 0) {
                    try {

                        EmpWarning empWarning = PstEmpWarning.fetchExc(oid);
                        
                        logField = PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARNING_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_FACT]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_BY]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_DATE]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_VALID_UNTIL]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_WARN_LEVEL_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_COMPANY_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_DIVISION_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_DEPARTMENT_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_SECTION_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_POSITION_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_LEVEL_ID]+";";
                        logField += PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMP_CATEGORY_ID]+";";
                        
                        /*Prev*/
                        logPrev = ""+empWarning.getOID()+";";
                        logPrev += ""+empWarning.getEmployeeId()+";";
                        logPrev += ""+empWarning.getBreakFact()+";";
                        logPrev += ""+empWarning.getBreakDate()+";";
                        logPrev += ""+empWarning.getWarningBy()+";";
                        logPrev += ""+empWarning.getWarningDate()+";";
                        logPrev += ""+empWarning.getValidityDate()+";";
                        logPrev += ""+empWarning.getWarnLevelId()+";";
                        logPrev += ""+empWarning.getCompanyId()+";";
                        logPrev += ""+empWarning.getDivisionId()+";";
                        logPrev += ""+empWarning.getDepartmentId()+";";
                        logPrev += ""+empWarning.getSectionId()+";";
                        logPrev += ""+empWarning.getPositionId()+";";
                        logPrev += ""+empWarning.getLevelId()+";";
                        logPrev += ""+empWarning.getEmpCategoryId()+";";
                            
                        logCurr = "-;";
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
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        
                        String empName = PstEmployee.getEmployeeName(warning.getEmployeeId());

                        long result = PstEmpWarning.deleteExc(oid);

                        if (sysLog == 1) {

                            String className = warning.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empWarning.getEmployeeId();
                                                       
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
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]);
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogModule("Employee Warning"); /* nama sub module*/
                            
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            
                            logSysHistory.setLogEditedUserId(warning.getEmployeeId());
                           
                            PstLogSysHistory.insertExc(logSysHistory);

                        }

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
                break;
                
                case Command.DELETEALL:
                //splits => untuk mengurutkan beberapa data menjadi baris data
                String[] splits = oidDeleteAll.split(",");
                for (String asset : splits) {
                    if (asset != "") {
                        long oidEmpWarning = Long.parseLong(asset);
                        if (oidEmpWarning != 0) {
                            try {
                                oid = PstEmpWarning.deleteExc(oidEmpWarning);
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
