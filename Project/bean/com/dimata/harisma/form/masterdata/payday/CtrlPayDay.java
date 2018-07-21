
package com.dimata.harisma.form.masterdata.payday;

// import java
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.payday.PayDay;
import com.dimata.harisma.entity.masterdata.payday.PstPayDay;
import com.dimata.harisma.form.masterdata.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import javax.servlet.http.*;

/**
 *
 * @author bayu
 */

public class CtrlPayDay extends Control implements I_Language {
    
    public static final int RSLT_OK                 =   0;
    public static final int RSLT_UNKNOWN_ERROR      =   1;
    public static final int RSLT_EST_CODE_EXIST     =   2;
    public static final int RSLT_FORM_INCOMPLETE    =   3;
    
    public static String[][] resultText = 
    {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };

    private PayDay payDay;
    private PstPayDay pstPayDay;
    private FrmPayDay frmPayDay;
    private String msgString;
    private int language;
    
    
    public CtrlPayDay(HttpServletRequest request) throws DBException {
        msgString = "";
        language = LANGUAGE_DEFAULT;
        
        payDay = new PayDay();
        
        try {
            pstPayDay = new PstPayDay(0);
        }
        catch(DBException e) {
            throw e;
        }
        
        frmPayDay = new FrmPayDay(request, payDay);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmPayDay.addError(frmPayDay.FRM_PAY_DAY_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public PayDay getPayDay() {
        return payDay;
    }
    
    public FrmPayDay getForm() {
        return frmPayDay;
    }
    
    public String getMessage() {
        return msgString;
    }
    
    public int getLanguage() {
        return language;
    }
    
    public void setLanguage(int language) {
        this.language = language;
    }
    
    public int action(int command, long oid) {
        int rsCode = RSLT_OK;
        int excCode = FRMMessage.ERR_NONE;
        msgString = "";
        
        switch(command) {
            case Command.ADD :
                break;
            case Command.GOTO :
                if(frmPayDay!=null && payDay!=null){
                    frmPayDay.requestEntityObject(payDay);
                }
                
                break;
            case Command.SAVE :
                if(oid != 0){
                    try {
                        payDay = PstPayDay.fetchExc(oid);
                    }
                    catch(Exception exc) { }
                }

                frmPayDay.requestEntityObject(payDay);

                if(frmPayDay.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }

                if(payDay.getOID()==0){
                    try{
                        long loid = pstPayDay.insertExc(this.payDay);
                        if(loid!=0){
                             msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                        }
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
                        long loid = pstPayDay.updateExc(this.payDay);
                        if(loid!=0){
                             msgString = FRMMessage.getMsg(FRMMessage.MSG_UPDATED);
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

            case Command.EDIT :
                if (oid != 0) {
                    try {
                        payDay = PstPayDay.fetchExc(oid);
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
                        /*if(PstPayDay.checkMaster(oid))
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        else*/
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        payDay = PstPayDay.fetchExc(oid);
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
                        long loid = PstPayDay.deleteExc(oid);
                        
                        if(loid!=0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        }
                        else {
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
                break;

            default :
        }
        
        return rsCode;
    }
    
}
