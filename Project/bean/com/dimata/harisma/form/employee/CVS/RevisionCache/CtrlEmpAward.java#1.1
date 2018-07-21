
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

/**
 *
 * @author bayu
 */

public class CtrlEmpAward extends Control implements I_Language {
    
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
    private EmpAward award;
    private PstEmpAward pstAward;
    private FrmEmpAward frmAward;    
    
    
    public CtrlEmpAward(HttpServletRequest request) {
        msgString = "";    
        language = LANGUAGE_DEFAULT;
        award = new EmpAward();
        
        try {
            pstAward = new PstEmpAward(0);
        }
        catch(Exception e) {}
        
        frmAward = new FrmEmpAward(request, award);
    }
    
    
    public int getStart() {
        return start;
    }
    
    public String getMessage() {
        return msgString;
    }
    
    public EmpAward getEmpAward() {
        return award;
    }
    
    public FrmEmpAward getForm() {
        return frmAward;
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
                frmAward.addError(frmAward.FRM_FIELD_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public int action(int cmd, long oid) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        switch(cmd){
                case Command.ADD :
                        break;
                        
                case Command.EDIT :
                        if(oid != 0) {
                            try {
                                    award = PstEmpAward.fetchExc(oid);
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
                        if(oid != 0){
                            try{
                                    award = PstEmpAward.fetchExc(oid);
                            }
                            catch(Exception exc){}
                        }

                        frmAward.requestEntityObject(award);

                        if(frmAward.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if(award.getOID()==0){    // insert
                            try{
                                long result = pstAward.insertExc(this.award);
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
                                long result = pstAward.updateExc(this.award);
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
                                award = PstEmpAward.fetchExc(oid);
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
                                long result = PstEmpAward.deleteExc(oid);
                                
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
