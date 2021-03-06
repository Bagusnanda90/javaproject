/* 
 * Ctrl Name  		:  CtrlExperience.java 
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
import com.dimata.harisma.entity.masterdata.PstLanguage;
import com.dimata.system.entity.PstSystemProperty;

public class CtrlExperience extends Control implements I_Language {

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
    private Experience experience;
    private PstExperience pstExperience;
    private FrmExperience frmExperience;
    int language = LANGUAGE_DEFAULT;

    public CtrlExperience(HttpServletRequest request) {
        msgString = "";
        experience = new Experience();
        try {
            pstExperience = new PstExperience(0);
        } catch (Exception e) {;
        }
        frmExperience = new FrmExperience(request, experience);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmExperience.addError(frmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Experience getExperience() {
        return experience;
    }

    public FrmExperience getForm() {
        return frmExperience;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidExperience, long oidEmployee, HttpServletRequest request, String loginName, long userId, String oidDeleteAll) {
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
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                Experience prevExperience = null;
                if (oidExperience != 0) {
                    try {
                        experience = PstExperience.fetchExc(oidExperience);

                        if (sysLog == 1) {
                            prevExperience = PstExperience.fetchExc(oidExperience);

                        }

                    } catch (Exception exc) {
                    }
                }

                experience.setOID(oidExperience);

                frmExperience.requestEntityObject(experience);

                experience.setEmployeeId(oidEmployee);

                if (frmExperience.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (experience.getOID() == 0) {
                    try {
                        long oid = pstExperience.insertExc(this.experience);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        if (sysLog == 1) {
                            
                            String className = experience.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                        
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + oidEmployee;
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
                            logField = PstExperience.fieldNames[PstExperience.FLD_WORK_HISTORY_PAST_ID]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_COMPANY_NAME]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_START_DATE]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_END_DATE]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_POSITION]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_MOVE_REASON]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_PROVIDER_ID]+";";
                            
                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr = ""+experience.getOID()+";";
                            logCurr += ""+experience.getEmployeeId()+";";
                            logCurr += ""+experience.getCompanyName()+";";
                            logCurr += ""+experience.getStartDate()+";";
                            logCurr += ""+experience.getEndDate()+";";
                            logCurr += ""+experience.getPosition()+";";
                            logCurr += ""+experience.getMoveReason()+";";
                            logCurr += ""+experience.getProviderID()+";";
                            
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Experience"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(experience.getEmployeeId());
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
                        long oid = pstExperience.updateExc(this.experience);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        if(sysLog == 1){
                            experience = PstExperience.fetchExc(oid);
                            
                            if(experience != null && prevExperience != null){
                            
                            logField = PstExperience.fieldNames[PstExperience.FLD_WORK_HISTORY_PAST_ID]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_COMPANY_NAME]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_START_DATE]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_END_DATE]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_POSITION]+";";
                            logField += PstExperience.fieldNames[PstExperience.FLD_MOVE_REASON]+";";  
                            logField += PstExperience.fieldNames[PstExperience.FLD_PROVIDER_ID]+";";
                                
                            logCurr = ""+experience.getOID()+";";
                            logCurr += ""+experience.getEmployeeId()+";";
                            logCurr += ""+experience.getCompanyName()+";";
                            logCurr += ""+experience.getStartDate()+";";
                            logCurr += ""+experience.getEndDate()+";";
                            logCurr += ""+experience.getPosition()+";";
                            logCurr += ""+experience.getMoveReason()+";";
                            logCurr += ""+experience.getProviderID()+";";
                            
                            logPrev = ""+prevExperience.getOID()+";";
                            logPrev += ""+prevExperience.getEmployeeId()+";";
                            logPrev += ""+prevExperience.getCompanyName()+";";
                            logPrev += ""+prevExperience.getStartDate()+";";
                            logPrev += ""+prevExperience.getEndDate()+";";
                            logPrev += ""+prevExperience.getPosition()+";";
                            logPrev += ""+prevExperience.getMoveReason()+";";
                            logPrev += ""+prevExperience.getProviderID()+";";
                            
                            String className = experience.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + experience.getOID();
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
                            logSysHistory.setLogModule("Employee Experience"); /* nama sub module*/
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(experience.getEmployeeId());
                            PstLogSysHistory.insertExc(logSysHistory);
                            }
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
                if (oidExperience != 0) {
                    try {
                        experience = PstExperience.fetchExc(oidExperience);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidExperience != 0) {
                    try {
                        experience = PstExperience.fetchExc(oidExperience);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidExperience != 0) {
                    try {
                        Experience experience = PstExperience.fetchExc(oidExperience);
                        logField = PstExperience.fieldNames[PstExperience.FLD_WORK_HISTORY_PAST_ID]+";";
                        logField += PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID]+";";
                        logField += PstExperience.fieldNames[PstExperience.FLD_COMPANY_NAME]+";";
                        logField += PstExperience.fieldNames[PstExperience.FLD_START_DATE]+";";
                        logField += PstExperience.fieldNames[PstExperience.FLD_END_DATE]+";";
                        logField += PstExperience.fieldNames[PstExperience.FLD_POSITION]+";";
                        logField += PstExperience.fieldNames[PstExperience.FLD_MOVE_REASON]+";";
                        logField += PstExperience.fieldNames[PstExperience.FLD_PROVIDER_ID]+";";

                        logPrev = ""+experience.getOID()+";";
                        logPrev += ""+experience.getEmployeeId()+";";
                        logPrev += ""+experience.getCompanyName()+";";
                        logPrev += ""+experience.getStartDate()+";";
                        logPrev += ""+experience.getEndDate()+";";
                        logPrev += ""+experience.getPosition()+";";
                        logPrev += ""+experience.getMoveReason()+";";
                        logPrev += ""+experience.getProviderID()+";";
                        
                        /*Curr*/
                        logCurr  = "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        
                        String empName = PstEmployee.getEmployeeName(experience.getEmployeeId());

                        long oid = PstExperience.deleteExc(oidExperience);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                        if (sysLog == 1) {


                            String className = experience.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + experience.getEmployeeId();

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
                            logSysHistory.setLogModule("Employee Experience");
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(experience.getEmployeeId());

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
                        long oidExp = Long.parseLong(asset);
                        if (oidExp != 0) {
                            try {
                                long oid = PstExperience.deleteExc(oidExp);
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
