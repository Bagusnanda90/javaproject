/*
 * CtrlCanteenVisitation.java
 *
 * Created on May 18, 1999, 11:42 AM
 */

package com.dimata.harisma.form.canteen;

/* java package */ 
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
import com.dimata.gui.jsp.*;

/* project package */
import com.dimata.harisma.entity.canteen.*;

/**
 *
 * @author  gedhy
 */
public class CtrlCanteenVisitation extends Control implements I_Language {
    
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
    private CanteenVisitation canteenVisitation;
    private PstCanteenVisitation pstCanteenVisitation;
    private FrmCanteenVisitation frmCanteenVisitation;
    int language = LANGUAGE_DEFAULT;
    
    public CtrlCanteenVisitation(HttpServletRequest request){
        msgString = "";
        canteenVisitation = new CanteenVisitation();
        try{
            pstCanteenVisitation = new PstCanteenVisitation(0);
        }catch(Exception e){;}
        frmCanteenVisitation = new FrmCanteenVisitation(request, canteenVisitation);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmCanteenVisitation.addError(frmCanteenVisitation.FRM_FIELD_VISITATION_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public int getLanguage(){ return language; }
    
    public void setLanguage(int language){ this.language = language; }
    
    public CanteenVisitation getCanteenVisitation() { return canteenVisitation; }
    
    public FrmCanteenVisitation getForm() { return frmCanteenVisitation; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidCanteenVisitation, HttpServletRequest request){
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch(cmd){
            case Command.ADD :
                break;
                
            case Command.SAVE :                
                if(oidCanteenVisitation != 0){
                    try{
                        canteenVisitation = PstCanteenVisitation.fetchExc(oidCanteenVisitation);
                    }catch(Exception exc){
                    }
                }
                
                frmCanteenVisitation.requestEntityObject(canteenVisitation);   
                Date visitationTime = ControlDate.getTime(FrmCanteenVisitation.fieldNames[FrmCanteenVisitation.FRM_FIELD_VISITATION_TIME], request);
                canteenVisitation.setVisitationTime(visitationTime);
                  
                if(frmCanteenVisitation.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                
                if(canteenVisitation.getOID()==0){
                    try{
                        long oid = pstCanteenVisitation.insertExc(this.canteenVisitation);                        
                    }catch(DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    }catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }else{
                    try {
                        long oid = pstCanteenVisitation.updateExc(this.canteenVisitation);                        
                    }catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }                
                break;
                
            case Command.EDIT :
                if (oidCanteenVisitation != 0) {
                    try {
                        canteenVisitation = PstCanteenVisitation.fetchExc(oidCanteenVisitation);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK :
                if (oidCanteenVisitation != 0) {
                    try {                        
                        canteenVisitation = PstCanteenVisitation.fetchExc(oidCanteenVisitation);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE :
                if (oidCanteenVisitation != 0){
                    try{
                        long oid = PstCanteenVisitation.deleteExc(oidCanteenVisitation);
                        if(oid!=0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        }else{
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    }catch(DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode); 
                    }catch(Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            default :
                
        }
        return rsCode;
    }
    
}
