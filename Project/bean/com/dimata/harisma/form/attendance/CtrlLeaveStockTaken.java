/*
 * CtrlLeaveStockTakenTaken.java
 *
 * Created on July 23, 2004, 4:19 PM
 */

package com.dimata.harisma.form.attendance;

// import core java package
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import dimata package
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;

// import harisma package
import com.dimata.harisma.entity.attendance.*;

/**
 *
 * @author  gedhy
 */
public class CtrlLeaveStockTaken extends Control implements I_Language {
    
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
    private LeaveStockTaken leaveStockTaken;
    private PstLeaveStockTaken pstLeaveStockTaken;
    private FrmLeaveStockTaken frmLeaveStockTaken;
    int language = LANGUAGE_DEFAULT;
    
    public CtrlLeaveStockTaken(HttpServletRequest request){
        msgString = "";
        leaveStockTaken = new LeaveStockTaken();
        try{
            pstLeaveStockTaken = new PstLeaveStockTaken(0);
        }catch(Exception e){;}
        frmLeaveStockTaken = new FrmLeaveStockTaken(request, leaveStockTaken);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmLeaveStockTaken.addError(frmLeaveStockTaken.FRM_FIELD_LEAVE_STOCK_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public LeaveStockTaken getLeaveStockTaken() { return leaveStockTaken; }
    
    public FrmLeaveStockTaken getForm() { return frmLeaveStockTaken; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidLeave) 
    {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch(cmd)
        {
            case Command.ADD :
                break;
                
            case Command.SAVE :
                if(oidLeave != 0)
                {
                    try
                    {
                        leaveStockTaken = PstLeaveStockTaken.fetchExc(oidLeave);
                    }
                    catch(Exception exc)
                    {
                    }
                }
                
                frmLeaveStockTaken.requestEntityObject(leaveStockTaken);
                
                if(frmLeaveStockTaken.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                
                if(leaveStockTaken.getOID()==0)
                {
                    try
                    {
                        long oid = PstLeaveStockTaken.insertExc(this.leaveStockTaken);
                    }
                    catch(DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                else
                {
                    try 
                    {
                        long oid = PstLeaveStockTaken.updateExc(this.leaveStockTaken);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                break;
                
            case Command.EDIT :
                if (oidLeave != 0) 
                {
                    try 
                    {
                        leaveStockTaken = PstLeaveStockTaken.fetchExc(oidLeave);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK :
                if (oidLeave != 0) 
                {
                    try 
                    {
                        leaveStockTaken = PstLeaveStockTaken.fetchExc(oidLeave);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE :
                if (oidLeave != 0)
                {
                    try
                    {
                        long oid = PstLeaveStockTaken.deleteExc(oidLeave);
                        if(oid!=0)
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        }
                        else
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    }
                    catch(DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch(Exception exc)                    
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            default :
                
        }
        return rsCode;
    }
    
    
}
