/* 
 * Ctrl Name  		:  CtrlTrainingHistory.java 
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

import com.dimata.gui.jsp.ControlDate;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.PstTrainingHistory;
import com.dimata.harisma.entity.employee.TrainingHistory;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.EmpDoc;
import com.dimata.harisma.entity.masterdata.PstEmpDoc;
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;

/*
 Description : Controll TrainingHistory
 Date : Thu Oct 08 2015
 Author : Hendra Putu
 */
public class CtrlTrainingHistory extends Control implements I_Language {

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
    private TrainingHistory entTrainingHistory;
    private PstTrainingHistory pstTrainingHistory;
    private FrmTrainingHistory frmTrainingHistory;
    int language = LANGUAGE_DEFAULT;

    public CtrlTrainingHistory(HttpServletRequest request) {
        msgString = "";
        entTrainingHistory = new TrainingHistory();
        try {
            pstTrainingHistory = new PstTrainingHistory(0);
        } catch (Exception e) {;
        }
        frmTrainingHistory = new FrmTrainingHistory(request, entTrainingHistory);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmTrainingHistory.addError(frmTrainingHistory.FRM_FIELD_TRAINING_HISTORY_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public TrainingHistory getTrainingHistory() {
        return entTrainingHistory;
    }

    public FrmTrainingHistory getForm() {
        return frmTrainingHistory;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidTrainingHistory, HttpServletRequest request, String loginName, long userId) {
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
                TrainingHistory prevTrainHistory = null;
                if (oidTrainingHistory != 0) {
                    try {
                        entTrainingHistory = PstTrainingHistory.fetchExc(oidTrainingHistory);
                        
                        if(sysLog == 1){
                            prevTrainHistory = PstTrainingHistory.fetchExc(oidTrainingHistory);

                        }
                    } catch (Exception exc) {
                    }
                }
                
                frmTrainingHistory.requestEntityObject(entTrainingHistory);
                Date timeStart = ControlDate.getTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_TIME], request);
                Date timeEnd = ControlDate.getTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_TIME], request);
                entTrainingHistory.setStartTime(timeStart);
                entTrainingHistory.setEndTime(timeEnd);

                if (frmTrainingHistory.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entTrainingHistory.getOID() == 0) {
                    try {
                        long oid = pstTrainingHistory.insertExc(this.entTrainingHistory);
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
                        long oid = pstTrainingHistory.updateExc(this.entTrainingHistory);
                        
                        // logHistory
                        if(sysLog == 1){
                            entTrainingHistory = PstTrainingHistory.fetchExc(oid);

                            if(entTrainingHistory != null && prevTrainHistory != null){
                                if(!entTrainingHistory.getTrainingProgram().equals(prevTrainHistory.getTrainingProgram())){
                                    logDetail = logDetail+" Training Program : "+prevTrainHistory.getTrainingProgram()+" >> "+entTrainingHistory.getTrainingProgram()+" UPDATED</br>";
                                }
                                if(!entTrainingHistory.getTrainer().equals(prevTrainHistory.getTrainer())){
                                    logDetail = logDetail+" Trainer : "+prevTrainHistory.getTrainer()+" >> "+entTrainingHistory.getTrainer()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getStartDate() != prevTrainHistory.getStartDate()){
                                    logDetail = logDetail+" Start Date : "+prevTrainHistory.getStartDate()+" >> "+entTrainingHistory.getStartDate()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getEndDate() != prevTrainHistory.getEndDate()){
                                    logDetail = logDetail+" End Date : "+prevTrainHistory.getEndDate()+" >> "+entTrainingHistory.getEndDate()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getStartTime() != prevTrainHistory.getStartTime()){
                                    logDetail = logDetail+" Start Time : "+prevTrainHistory.getStartTime()+" >> "+entTrainingHistory.getStartTime()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getEndTime() != prevTrainHistory.getEndTime()){
                                    logDetail = logDetail+" End Time : "+prevTrainHistory.getEndTime()+" >> "+entTrainingHistory.getEndTime()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getDuration() != prevTrainHistory.getDuration()){
                                    logDetail = logDetail+" Duration : "+prevTrainHistory.getDuration()+" >> "+entTrainingHistory.getDuration()+" UPDATED</br>";
                                }
                                if(!entTrainingHistory.getRemark().equals(prevTrainHistory.getRemark())){
                                    logDetail = logDetail+" Remarks : "+prevTrainHistory.getRemark()+" >> "+entTrainingHistory.getRemark()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getPoint() != prevTrainHistory.getPoint()){
                                    logDetail = logDetail+" Point : "+prevTrainHistory.getPoint()+" >> "+entTrainingHistory.getPoint()+" UPDATED</br>";
                                }
                                if(!entTrainingHistory.getNomorSk().equals(prevTrainHistory.getNomorSk())){
                                    logDetail = logDetail+" Nomor SK : "+prevTrainHistory.getNomorSk()+" >> "+entTrainingHistory.getNomorSk()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getTanggalSk() != prevTrainHistory.getTanggalSk()){
                                    logDetail = logDetail+" Tanggal SK : "+prevTrainHistory.getTanggalSk()+" >> "+entTrainingHistory.getTanggalSk()+" UPDATED</br>";
                                }
                                if(entTrainingHistory.getEmpDocId() != prevTrainHistory.getEmpDocId()){
                                    EmpDoc prevEmpDoc = PstEmpDoc.fetchExc(prevTrainHistory.getEmpDocId());
                                    String sPrevEmpDoc = prevEmpDoc.getDoc_title();
                                    EmpDoc empDoc = PstEmpDoc.fetchExc(entTrainingHistory.getEmpDocId());
                                    String sEmpDoc = empDoc.getDoc_title();
                                    logDetail = logDetail+" Emp Doc : "+sPrevEmpDoc+" >> "+sEmpDoc+" UPDATED</br>";
                                }

                                String className = entTrainingHistory.getClass().getName();

                                LogSysHistory logSysHistory = new LogSysHistory();

                                String reqUrl = request.getRequestURI().toString()+"?employee_oid="+entTrainingHistory.getEmployeeId();

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
                if (oidTrainingHistory != 0) {
                    try {
                        entTrainingHistory = PstTrainingHistory.fetchExc(oidTrainingHistory);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidTrainingHistory != 0) {
                    try {
                        entTrainingHistory = PstTrainingHistory.fetchExc(oidTrainingHistory);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidTrainingHistory != 0) {
                    try {
                        
                        entTrainingHistory = PstTrainingHistory.fetchExc(oidTrainingHistory);
                                                
                        String empName = PstEmployee.getEmployeeName(entTrainingHistory.getEmployeeId());
                        
                        long oid = PstTrainingHistory.deleteExc(oidTrainingHistory);
                        
                        if(sysLog == 1){
                            
                            if(entTrainingHistory.getTrainingProgram() != null){

                                logDetail = logDetail+" Training Program : "+entTrainingHistory.getTrainingProgram()+" DELETED from Employee : "+empName+"</br>";
                            }

                            String className = entTrainingHistory.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString()+"?employee_oid="+entTrainingHistory.getEmployeeId();

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
