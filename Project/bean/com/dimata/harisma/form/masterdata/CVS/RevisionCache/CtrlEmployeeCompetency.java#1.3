/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;

/*
 Description : Controll EmployeeCompetency
 Date : Tue Oct 06 2015
 Author : Hendra Putu
 */
public class CtrlEmployeeCompetency extends Control implements I_Language {

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
    private EmployeeCompetency entEmployeeCompetency;
    private PstEmployeeCompetency pstEmployeeCompetency;
    private FrmEmployeeCompetency frmEmployeeCompetency;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmployeeCompetency(HttpServletRequest request) {
        msgString = "";
        entEmployeeCompetency = new EmployeeCompetency();
        try {
            pstEmployeeCompetency = new PstEmployeeCompetency(0);
        } catch (Exception e) {;
        }
        frmEmployeeCompetency = new FrmEmployeeCompetency(request, entEmployeeCompetency);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmployeeCompetency.addError(frmEmployeeCompetency.FRM_FIELD_EMPLOYEE_COMP_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmployeeCompetency getEmployeeCompetency() {
        return entEmployeeCompetency;
    }

    public FrmEmployeeCompetency getForm() {
        return frmEmployeeCompetency;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmployeeCompetency, HttpServletRequest request, String loginName, long userId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
        //long sysLog = 1;
        String logDetail = "";
        Date nowDate = new Date();
        
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                EmployeeCompetency prevEmployeeComp = null;
                if (oidEmployeeCompetency != 0) {
                    try {
                        entEmployeeCompetency = PstEmployeeCompetency.fetchExc(oidEmployeeCompetency);
                        
                        if(sysLog == 1){
                            prevEmployeeComp = PstEmployeeCompetency.fetchExc(oidEmployeeCompetency);
                            
                        }
                    } catch (Exception exc) {
                    }
                }

                frmEmployeeCompetency.requestEntityObject(entEmployeeCompetency);

                if (frmEmployeeCompetency.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entEmployeeCompetency.getOID() == 0) {
                    try {
                        long oid = pstEmployeeCompetency.insertExc(this.entEmployeeCompetency);
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
                        long oid = pstEmployeeCompetency.updateExc(this.entEmployeeCompetency);
                        
                        // logHistory
                        if(sysLog == 1){
                            entEmployeeCompetency = PstEmployeeCompetency.fetchExc(oid);
                            
                            if(entEmployeeCompetency != null && prevEmployeeComp != null){
                                if(entEmployeeCompetency.getCompetencyId() != prevEmployeeComp.getCompetencyId()){
                                    String nameComp = PstCompetency.getCompetencyName(entEmployeeCompetency.getCompetencyId());
                                    String prevNameComp = PstCompetency.getCompetencyName(prevEmployeeComp.getCompetencyId());
                                    logDetail = logDetail+" Competency : "+prevNameComp+" >> "+nameComp+" UPDATED</br>";
                                }
                                if(entEmployeeCompetency.getLevelValue() != prevEmployeeComp.getLevelValue()){
                                    logDetail = logDetail+" Level Value : "+prevEmployeeComp.getLevelValue()+" >> "+entEmployeeCompetency.getLevelValue()+" UPDATED</br>";
                                }
                                if(entEmployeeCompetency.getDateOfAchvmt() != prevEmployeeComp.getDateOfAchvmt()){
                                    logDetail = logDetail+" Date of Achievement : "+prevEmployeeComp.getDateOfAchvmt()+" >> "+entEmployeeCompetency.getDateOfAchvmt()+" UPDATED</br>";
                                }
                                if(!entEmployeeCompetency.getSpecialAchievement().equals(prevEmployeeComp.getSpecialAchievement())){
                                    logDetail = logDetail+" Special Achievement : "+prevEmployeeComp.getSpecialAchievement()+" >> "+entEmployeeCompetency.getSpecialAchievement()+" UPDATED</br>";
                                }
                                String className = entEmployeeCompetency.getClass().getName();
                                
                                LogSysHistory logSysHistory = new LogSysHistory();
                                
                                String reqUrl = request.getRequestURI().toString()+"?employee_oid="+entEmployeeCompetency.getEmployeeId();
                                                                
                                logSysHistory.setLogDocumentId(0);
                                logSysHistory.setLogUserId(userId);
                                logSysHistory.setLogLoginName(loginName);
                                logSysHistory.setLogDocumentNumber("");
                                logSysHistory.setLogDocumentType(className); //entity
                                logSysHistory.setLogUserAction("EDIT"); // command
                                logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                                logSysHistory.setLogUpdateDate(nowDate);
                                logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                                logSysHistory.setLogDetail(logDetail); // apa yang dirubah
                                logSysHistory.setStatus(0);
                                
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
                if (oidEmployeeCompetency != 0) {
                    try {
                        entEmployeeCompetency = PstEmployeeCompetency.fetchExc(oidEmployeeCompetency);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmployeeCompetency != 0) {
                    try {
                        entEmployeeCompetency = PstEmployeeCompetency.fetchExc(oidEmployeeCompetency);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmployeeCompetency != 0) {
                    try {
                        
                        entEmployeeCompetency = PstEmployeeCompetency.fetchExc(oidEmployeeCompetency);
                        
                        String empName = PstEmployee.getEmployeeName(entEmployeeCompetency.getEmployeeId());
                        
                        long oid = PstEmployeeCompetency.deleteExc(oidEmployeeCompetency);
                        
                        if(sysLog == 1){
                            
                            if(entEmployeeCompetency.getCompetencyId() != 0){
                                
                                String nameComp = PstCompetency.getCompetencyName(oidEmployeeCompetency);
                                
                                logDetail = logDetail+" Competency : "+nameComp+" DELETED from Employee : "+empName+"</br>";
                            }
                            
                            String className = entEmployeeCompetency.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString()+"?employee_oid="+entEmployeeCompetency.getEmployeeId();

                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("DELETE"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logDetail); // apa yang dirubah
                            logSysHistory.setStatus(0);

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