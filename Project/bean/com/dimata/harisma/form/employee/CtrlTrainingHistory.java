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
import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.*;
import static com.dimata.harisma.form.masterdata.CtrlDocMaster.RSLT_FORM_INCOMPLETE;
import static com.dimata.harisma.form.masterdata.CtrlDocMaster.RSLT_OK;
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;
import java.util.Vector;

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

        public int action(int cmd, long oidTrainingHistory, HttpServletRequest request, String loginName, long userId, String oidDeleteAll) {
            return action(cmd, oidTrainingHistory, request, loginName, userId, oidDeleteAll, "");
        }
    
    public int action(int cmd, long oidTrainingHistory, HttpServletRequest request, String loginName, long userId, String oidDeleteAll, String object) {
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
                //Date timeStart = ControlDate.getTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_START_TIME], request);
                //Date timeEnd = ControlDate.getTime(FrmTrainingHistory.fieldNames[FrmTrainingHistory.FRM_FIELD_END_TIME], request);
                //entTrainingHistory.setStartTime(timeStart);
                //entTrainingHistory.setEndTime(timeEnd);

                if (frmTrainingHistory.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entTrainingHistory.getOID() == 0) {
                    try {
                        long oid = pstTrainingHistory.insertExc(this.entTrainingHistory);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        EmpDocList empDocList = new EmpDocList();
                        empDocList.setEmp_doc_id(this.entTrainingHistory.getEmpDocId());
                        empDocList.setEmployee_id(this.entTrainingHistory.getEmployeeId());
                        empDocList.setEmp_reprimand(0);
                        empDocList.setObject_name(object);
                        empDocList.setEmp_career(0);
                        empDocList.setEmp_award_id(0);
                        empDocList.setEmp_training(oid);
                        long oidEmpDocList = PstEmpDocList.insertExc(empDocList);
                        if (sysLog == 1) {
                            String className = entTrainingHistory.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + entTrainingHistory.getEmployeeId();

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
                            
                            logField = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_PROGRAM]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_REMARK]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_PRESENCE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_TIME]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_PLAN_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_POINT]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_NOMOR_SK]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMP_DOC_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE]+";";
                            
                            
                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr = ""+entTrainingHistory.getOID()+";";
                            logCurr += ""+entTrainingHistory.getEmployeeId()+";";
                            logCurr += ""+entTrainingHistory.getTrainingProgram()+";";
                            logCurr += ""+entTrainingHistory.getStartDate()+";";
                            logCurr += ""+entTrainingHistory.getEndDate()+";";
                            logCurr += ""+entTrainingHistory.getTrainer()+";";
                            logCurr += ""+entTrainingHistory.getRemark()+";";
                            logCurr += ""+entTrainingHistory.getTrainingId()+";";
                            logCurr += ""+entTrainingHistory.getDuration()+";";
                            logCurr += ""+entTrainingHistory.getPresence()+";";
                            logCurr += ""+entTrainingHistory.getStartTime()+";";
                            logCurr += ""+entTrainingHistory.getEndDate()+";";
                            logCurr += ""+entTrainingHistory.getTrainingActivityPlanId()+";";
                            logCurr += ""+entTrainingHistory.getTrainingActivityActualId()+";";
                            logCurr += ""+entTrainingHistory.getPoint()+";";
                            logCurr += ""+entTrainingHistory.getNomorSk()+";";
                            logCurr += ""+entTrainingHistory.getEmpDocId()+";";
                            logCurr += ""+entTrainingHistory.getTrainingTitle()+";";
                            
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Training"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(entTrainingHistory.getEmployeeId());
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
                        long oid = pstTrainingHistory.updateExc(this.entTrainingHistory);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        String whereList = PstEmpDocList.fieldNames[PstEmpDocList.FLD_REPRIMAND_ID]+ " = " + oid; 
                        Vector listEmpDocList = PstEmpDocList.list(0, 0, whereList , "");
                        if (listEmpDocList.size() > 0){
                            for (int i=0; i< listEmpDocList.size(); i++){
                                EmpDocList empDocListGet = (EmpDocList) listEmpDocList.get(i);
                                EmpDocList empDocList = new EmpDocList();
                                empDocList.setOID(empDocListGet.getOID());
                                empDocList.setEmp_doc_id(this.entTrainingHistory.getEmpDocId());
                                empDocList.setEmployee_id(this.entTrainingHistory.getEmployeeId());
                                empDocList.setEmp_reprimand(0);
                                empDocList.setObject_name(object);
                                empDocList.setEmp_career(0);
                                empDocList.setEmp_award_id(0);
                                empDocList.setEmp_reprimand(oid);
                                long oidEmpDocList = PstEmpDocList.updateExc(empDocList);
                            }
                        }
                        // logHistory
                        if(sysLog == 1){
                            entTrainingHistory = PstTrainingHistory.fetchExc(oid);

                            logField = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_PROGRAM]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_REMARK]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_PRESENCE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_TIME]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_PLAN_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_POINT]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_NOMOR_SK]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMP_DOC_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE]+";";
                            
                            logCurr = ""+entTrainingHistory.getOID()+";";
                            logCurr += ""+entTrainingHistory.getEmployeeId()+";";
                            logCurr += ""+entTrainingHistory.getTrainingProgram()+";";
                            logCurr += ""+entTrainingHistory.getStartDate()+";";
                            logCurr += ""+entTrainingHistory.getEndDate()+";";
                            logCurr += ""+entTrainingHistory.getTrainer()+";";
                            logCurr += ""+entTrainingHistory.getRemark()+";";
                            logCurr += ""+entTrainingHistory.getTrainingId()+";";
                            logCurr += ""+entTrainingHistory.getDuration()+";";
                            logCurr += ""+entTrainingHistory.getPresence()+";";
                            logCurr += ""+entTrainingHistory.getStartTime()+";";
                            logCurr += ""+entTrainingHistory.getEndDate()+";";
                            logCurr += ""+entTrainingHistory.getTrainingActivityPlanId()+";";
                            logCurr += ""+entTrainingHistory.getTrainingActivityActualId()+";";
                            logCurr += ""+entTrainingHistory.getPoint()+";";
                            logCurr += ""+entTrainingHistory.getNomorSk()+";";
                            logCurr += ""+entTrainingHistory.getEmpDocId()+";";
                            logCurr += ""+entTrainingHistory.getTrainingTitle()+";";
                            
                            logPrev = ""+prevTrainHistory.getOID()+";";
                            logPrev += ""+prevTrainHistory.getEmployeeId()+";";
                            logPrev += ""+prevTrainHistory.getTrainingProgram()+";";
                            logPrev += ""+prevTrainHistory.getStartDate()+";";
                            logPrev += ""+prevTrainHistory.getEndDate()+";";
                            logPrev += ""+prevTrainHistory.getTrainer()+";";
                            logPrev += ""+prevTrainHistory.getRemark()+";";
                            logPrev += ""+prevTrainHistory.getTrainingId()+";";
                            logPrev += ""+prevTrainHistory.getDuration()+";";
                            logPrev += ""+prevTrainHistory.getPresence()+";";
                            logPrev += ""+prevTrainHistory.getStartTime()+";";
                            logPrev += ""+prevTrainHistory.getEndDate()+";";
                            logPrev += ""+prevTrainHistory.getTrainingActivityPlanId()+";";
                            logPrev += ""+prevTrainHistory.getTrainingActivityActualId()+";";
                            logPrev += ""+prevTrainHistory.getPoint()+";";
                            logPrev += ""+prevTrainHistory.getNomorSk()+";";
                            logPrev += ""+prevTrainHistory.getEmpDocId()+";";
                            logPrev += ""+prevTrainHistory.getTrainingTitle()+";";

                            String className = entTrainingHistory.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString()+"?employee_oid="+entTrainingHistory.getEmployeeId();

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
                            logSysHistory.setLogModule("Employee Training"); /* nama sub module*/
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(entTrainingHistory.getEmployeeId());
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
                        
                            logField = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_PROGRAM]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINER]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_REMARK]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_DURATION]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_PRESENCE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_START_TIME]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_END_DATE]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_PLAN_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_POINT]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_NOMOR_SK]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMP_DOC_ID]+";";
                            logField += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_TITLE]+";";
                            
                            logPrev = ""+entTrainingHistory.getOID()+";";
                            logPrev += ""+entTrainingHistory.getEmployeeId()+";";
                            logPrev += ""+entTrainingHistory.getTrainingProgram()+";";
                            logPrev += ""+entTrainingHistory.getStartDate()+";";
                            logPrev += ""+entTrainingHistory.getEndDate()+";";
                            logPrev += ""+entTrainingHistory.getTrainer()+";";
                            logPrev += ""+entTrainingHistory.getRemark()+";";
                            logPrev += ""+entTrainingHistory.getTrainingId()+";";
                            logPrev += ""+entTrainingHistory.getDuration()+";";
                            logPrev += ""+entTrainingHistory.getPresence()+";";
                            logPrev += ""+entTrainingHistory.getStartTime()+";";
                            logPrev += ""+entTrainingHistory.getEndDate()+";";
                            logPrev += ""+entTrainingHistory.getTrainingActivityPlanId()+";";
                            logPrev += ""+entTrainingHistory.getTrainingActivityActualId()+";";
                            logPrev += ""+entTrainingHistory.getPoint()+";";
                            logPrev += ""+entTrainingHistory.getNomorSk()+";";
                            logPrev += ""+entTrainingHistory.getEmpDocId()+";";
                            logPrev += ""+entTrainingHistory.getTrainingTitle()+";";                     
                        
                        /*curr*/
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
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        
                        
                        String empName = PstEmployee.getEmployeeName(entTrainingHistory.getEmployeeId());
                        
                        long oid = PstTrainingHistory.deleteExc(oidTrainingHistory);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                        if(sysLog == 1){
                           
                            String className = entTrainingHistory.getClass().getName();

                            LogSysHistory logSysHistory = new LogSysHistory();

                            String reqUrl = request.getRequestURI().toString()+"?employee_oid="+entTrainingHistory.getEmployeeId();
                            
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
                            logSysHistory.setLogModule("Employee Training");
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(entTrainingHistory.getEmployeeId());

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
                        long oidTraining = Long.parseLong(asset);
                        if (oidTraining != 0) {
                            try {
                                long oid = PstTrainingHistory.deleteExc(oidTraining);
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
