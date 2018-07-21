
package com.dimata.harisma.form.employee;

// import java
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
import com.dimata.harisma.entity.masterdata.PstWarning;
import com.dimata.harisma.entity.masterdata.Warning;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author bayu
 */

public class CtrlEmpWarning extends Control implements I_Language {
    
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
    private EmpWarning warning;
    private PstEmpWarning pstWarning;
    private FrmEmpWarning frmWarning;    
    
    
    public CtrlEmpWarning(HttpServletRequest request) {
        msgString = "";    
        language = LANGUAGE_DEFAULT;
        warning = new EmpWarning();
        
        try {
            pstWarning = new PstEmpWarning(0);
        }
        catch(Exception e) {}
        
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
    
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                frmWarning.addError(frmWarning.FRM_FIELD_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public int action(int cmd, long oid, HttpServletRequest request, String loginName, long userId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
        //long sysLog = 1;
        String logDetail = "";
        Date nowDate = new Date();

        switch(cmd){
                case Command.ADD :
                        break;
                        
                case Command.EDIT :
                        if(oid != 0) {
                            try {
                                    warning = PstEmpWarning.fetchExc(oid);
                                    
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
                        EmpWarning prevEmpWarning = null;
                        if(oid != 0){
                            try{
                                    warning = PstEmpWarning.fetchExc(oid);
                                    
                                    if(sysLog == 1){
                                        prevEmpWarning = PstEmpWarning.fetchExc(oid);

                                    }
                                    
                            }
                            catch(Exception exc){}
                        }

                        frmWarning.requestEntityObject(warning);

                        if(frmWarning.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if(warning.getOID()==0){    // insert
                            try{
                                long result = pstWarning.insertExc(this.warning);
                            }
                            catch(DBException dbexc){
                                excCode = dbexc.getErrorCode();
                                msgString = getSystemMessage(excCode);
                                return getControlMsgId(excCode);
                            }
                            catch (Exception exc){
                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                            }

                        }
                        else {
                            try {
                                long result = pstWarning.updateExc(this.warning);
                                
                                // logHistory
                                if(sysLog == 1){
                                    warning = PstEmpWarning.fetchExc(oid);

                                    if(warning != null && prevEmpWarning != null){
                                        if(warning.getBreakDate() != prevEmpWarning.getBreakDate()){
                                            logDetail = logDetail+" Break Date : "+prevEmpWarning.getBreakDate()+" >> "+warning.getBreakDate()+" UPDATED</br>";
                                        }
                                        if(!warning.getBreakFact().equals(prevEmpWarning.getBreakFact())){
                                            logDetail = logDetail+" Break Fact : "+prevEmpWarning.getBreakFact()+" >> "+warning.getBreakFact()+" UPDATED</br>";
                                        }
                                        if(warning.getWarningDate() != prevEmpWarning.getWarningDate()){
                                            logDetail = logDetail+" Warning Date : "+prevEmpWarning.getWarningDate()+" >> "+warning.getWarningDate()+" UPDATED</br>";
                                        }
                                        if(warning.getWarnLevelId() != prevEmpWarning.getWarnLevelId()){
                                            Warning warningLvl = PstWarning.fetchExc(warning.getWarnLevelId());
                                            Warning prevWarningLvl = PstWarning.fetchExc(prevEmpWarning.getWarnLevelId());
                                            logDetail = logDetail+" Warning Level : "+prevWarningLvl.getWarnDesc()+" >> "+warningLvl.getWarnDesc()+" UPDATED</br>";
                                        }
                                        if(!warning.getWarningBy().equals(prevEmpWarning.getWarningBy())){
                                            logDetail = logDetail+" Warning By : "+prevEmpWarning.getWarningBy()+" >> "+warning.getWarningBy()+" UPDATED</br>";
                                        }
                                        if(warning.getValidityDate() != prevEmpWarning.getValidityDate()){
                                            logDetail = logDetail+" Valid until : "+prevEmpWarning.getValidityDate()+" >> "+warning.getValidityDate()+" UPDATED</br>";
                                        }

                                        String className = warning.getClass().getName();

                                        LogSysHistory logSysHistory = new LogSysHistory();

                                        String reqUrl = request.getRequestURI().toString()+"?employee_oid="+warning.getEmployeeId();

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
                                warning = PstEmpWarning.fetchExc(oid);
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
                                
                                warning = PstEmpWarning.fetchExc(oid);
                                                
                                String empName = PstEmployee.getEmployeeName(warning.getEmployeeId());
                                
                                long result = PstEmpWarning.deleteExc(oid);
                                
                                if(sysLog == 1){
                            
                                    if(warning.getBreakFact() != null){

                                        logDetail = logDetail+" Break Fact : "+warning.getBreakFact()+" DELETED from Employee : "+empName+"</br>";
                                    }

                                    String className = warning.getClass().getName();

                                    LogSysHistory logSysHistory = new LogSysHistory();

                                    String reqUrl = request.getRequestURI().toString()+"?employee_oid="+warning.getEmployeeId();

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
        }
        
        return rsCode;
    }
}
