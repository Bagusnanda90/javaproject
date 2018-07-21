/*
 * CtrlCanteenSchedule.java
 *
 * Created on April 23, 2005, 11:55 AM
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
public class CtrlCanteenSchedule  extends Control implements I_Language 
{
    
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
    private CanteenSchedule canteenSchedule;
    private PstCanteenSchedule pstCanteenSchedule;
    private FrmCanteenSchedule frmCanteenSchedule;
    int language = LANGUAGE_DEFAULT;
    
    public CtrlCanteenSchedule(HttpServletRequest request){
        msgString = "";
        canteenSchedule = new CanteenSchedule();
        try{
            pstCanteenSchedule = new PstCanteenSchedule(0);
        }catch(Exception e){;}
        frmCanteenSchedule = new FrmCanteenSchedule(request, canteenSchedule);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmCanteenSchedule.addError(frmCanteenSchedule.FRM_FIELD_CANTEEN_SCHEDULE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public CanteenSchedule getCanteenSchedule() { return canteenSchedule; }
    
    public FrmCanteenSchedule getForm() { return frmCanteenSchedule; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidCanteenSchedule, HttpServletRequest request){
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch(cmd){
            case Command.ADD :
                break;
                
            case Command.SAVE :
                if(oidCanteenSchedule != 0){
                    try{
                        canteenSchedule = PstCanteenSchedule.fetchExc(oidCanteenSchedule);
                    }catch(Exception exc){
                    }
                }
                
                frmCanteenSchedule.requestEntityObject(canteenSchedule);   
                Date TimeOpen = ControlDate.getTime(FrmCanteenSchedule.fieldNames[FrmCanteenSchedule.FRM_FIELD_TIME_OPEN], request);
                canteenSchedule.setTTimeOpen(TimeOpen);
                Date TimeClose = ControlDate.getTime(FrmCanteenSchedule.fieldNames[FrmCanteenSchedule.FRM_FIELD_TIME_CLOSE], request);
                canteenSchedule.setTTimeClose(TimeClose);
                  
                if(frmCanteenSchedule.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                
                if(canteenSchedule.getOID()==0){
                    try{
                        long oid = pstCanteenSchedule.insertExc(this.canteenSchedule);
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
                        long oid = pstCanteenSchedule.updateExc(this.canteenSchedule);
                    }catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                break;
                
            case Command.EDIT :
                if (oidCanteenSchedule != 0) {
                    try {
                        canteenSchedule = PstCanteenSchedule.fetchExc(oidCanteenSchedule);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK :
                if (oidCanteenSchedule != 0) {
                    try {                        
                        canteenSchedule = PstCanteenSchedule.fetchExc(oidCanteenSchedule);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE :
                if (oidCanteenSchedule != 0){
                    try{
                        long oid = PstCanteenSchedule.deleteExc(oidCanteenSchedule);
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
