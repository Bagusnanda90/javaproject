
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
import com.dimata.harisma.entity.masterdata.PstReprimand;
import com.dimata.harisma.entity.masterdata.Reprimand;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author bayu
 */

public class CtrlEmpReprimand extends Control implements I_Language {
    
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
    private EmpReprimand reprimand;
    private PstEmpReprimand pstReprimand;
    private FrmEmpReprimand frmReprimand;    
    
    
    public CtrlEmpReprimand(HttpServletRequest request) {
        msgString = "";    
        language = LANGUAGE_DEFAULT;
        reprimand = new EmpReprimand();
        
        try {
            pstReprimand = new PstEmpReprimand(0);
        }
        catch(Exception e) {}
        
        frmReprimand = new FrmEmpReprimand(request, reprimand);
    }
    
    
    public int getStart() {
        return start;
    }
    
    public String getMessage() {
        return msgString;
    }
    
    public EmpReprimand getEmpReprimand() {
        return reprimand;
    }
    
    public FrmEmpReprimand getForm() {
        return frmReprimand;
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
                frmReprimand.addError(frmReprimand.FRM_FIELD_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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
                                    reprimand = PstEmpReprimand.fetchExc(oid);
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
                        EmpReprimand prevEmpReprimand = null;
                        if(oid != 0){
                            try{
                                    reprimand = PstEmpReprimand.fetchExc(oid);
                                    
                                    if(sysLog == 1){
                                        prevEmpReprimand = PstEmpReprimand.fetchExc(oid);
                                    }
                                    
                            }
                            catch(Exception exc){}
                        }

                        frmReprimand.requestEntityObject(reprimand);

                        if(frmReprimand.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if(reprimand.getOID()==0){    // insert
                            try{
                                long result = pstReprimand.insertExc(this.reprimand);
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
                        else {                      // update
                            try {
                                long result = pstReprimand.updateExc(this.reprimand);
                                
                                // logHistory
                                if(sysLog == 1){
                                    reprimand = PstEmpReprimand.fetchExc(oid);

                                    if(reprimand != null && prevEmpReprimand != null){
                                        if(reprimand.getReprimandDate() != prevEmpReprimand.getReprimandDate()){
                                            logDetail = logDetail+" Reprimand Date : "+prevEmpReprimand.getReprimandDate()+" >> "+reprimand.getReprimandDate()+" UPDATED</br>";
                                        }
                                        if(reprimand.getReprimandLevelId() != prevEmpReprimand.getReprimandLevelId()){
                                            Reprimand reprim = PstReprimand.fetchExc(reprimand.getReprimandLevelId());
                                            Reprimand prevReprim = PstReprimand.fetchExc(prevEmpReprimand.getReprimandLevelId());
                                            logDetail = logDetail+" Reprimand Level : "+prevReprim+" >> "+reprim+" UPDATED</br>";
                                        }
                                        if(!reprimand.getChapter().equals(prevEmpReprimand.getChapter())){
                                            logDetail = logDetail+" Chapter/Bab : "+prevEmpReprimand.getChapter()+" >> "+reprimand.getChapter()+" UPDATED</br>";
                                        }
                                        if(!reprimand.getArticle().equals(prevEmpReprimand.getArticle())){
                                            logDetail = logDetail+" Article/Pasal : "+prevEmpReprimand.getArticle()+" >> "+reprimand.getArticle()+" UPDATED</br>";
                                        }
                                        if(!reprimand.getVerse().equals(prevEmpReprimand.getVerse())){
                                            logDetail = logDetail+" Verse/Ayat : "+prevEmpReprimand.getVerse()+" >> "+reprimand.getVerse()+" UPDATED</br>";
                                        }
                                        if(!reprimand.getPage().equals(prevEmpReprimand.getPage())){
                                            logDetail = logDetail+" Page/Halaman : "+prevEmpReprimand.getPage()+" >> "+reprimand.getPage()+" UPDATED</br>";
                                        }
                                        if(!reprimand.getDescription().equals(prevEmpReprimand.getDescription())){
                                            logDetail = logDetail+" Description/Uraian : "+prevEmpReprimand.getDescription()+" >> "+reprimand.getDescription()+" UPDATED</br>";
                                        }
                                        if(reprimand.getValidityDate() != prevEmpReprimand.getValidityDate()){
                                            logDetail = logDetail+" Valid Until : "+prevEmpReprimand.getValidityDate()+" >> "+reprimand.getValidityDate()+" UPDATED</br>";
                                        }

                                        String className = reprimand.getClass().getName();

                                        LogSysHistory logSysHistory = new LogSysHistory();

                                        String reqUrl = request.getRequestURI().toString()+"?employee_oid="+reprimand.getEmployeeId();

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
                                reprimand = PstEmpReprimand.fetchExc(oid);
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
                                reprimand = PstEmpReprimand.fetchExc(oid);
                                                
                                String empName = PstEmployee.getEmployeeName(reprimand.getEmployeeId());
                                
                                long result = PstEmpReprimand.deleteExc(oid);
                                
                                if(sysLog == 1){
                            
                                    if(reprimand.getDescription() != null){

                                        logDetail = logDetail+" Description : "+reprimand.getDescription()+" DELETED from Employee : "+empName+"</br>";
                                    }

                                    String className = reprimand.getClass().getName();

                                    LogSysHistory logSysHistory = new LogSysHistory();

                                    String reqUrl = request.getRequestURI().toString()+"?employee_oid="+reprimand.getEmployeeId();

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
