/*
 * CtrlSpecialLeaveStock.java
 *
 * Created on September 10, 2007, 5:13 PM
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
 * @author  yunny
 */
public class CtrlSpecialLeaveStock  extends Control implements I_Language{
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "OID sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "OID code exist", "Data incomplete"}
    };
    
    private int start;
    private String msgString;
    private SpecialLeaveStock specialLeaveStock;
    private PstSpecialLeaveStock pstSpecialLeaveStock;
    private FrmSpecialLeaveStock frmSpecialLeaveStock;
    int language = LANGUAGE_DEFAULT;
    
     public CtrlSpecialLeaveStock(HttpServletRequest request){
        msgString = "";
        specialLeaveStock = new SpecialLeaveStock();
        try{
            pstSpecialLeaveStock = new PstSpecialLeaveStock(0);
        }catch(Exception e){;}
        frmSpecialLeaveStock = new FrmSpecialLeaveStock(request, specialLeaveStock);
    }
     
     public CtrlSpecialLeaveStock(){
        msgString = "";
        specialLeaveStock = new SpecialLeaveStock();
        try{
            pstSpecialLeaveStock = new PstSpecialLeaveStock(0);
        }catch(Exception e){;}
        frmSpecialLeaveStock = new FrmSpecialLeaveStock();
    }
     
      private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmSpecialLeaveStock.addError(frmSpecialLeaveStock.FRM_FIELD_SPECIAL_LEAVE_STOCK_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public SpecialLeaveStock getSpecialLeaveStock() { return specialLeaveStock; }
    
    public FrmSpecialLeaveStock getForm() { return frmSpecialLeaveStock; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidLeaveStock) 
    {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch(cmd)
        {
            case Command.ADD :
                break;
                
            case Command.SAVE :
                if(oidLeaveStock != 0)
                {
                    try
                    {
                        specialLeaveStock = PstSpecialLeaveStock.fetchExc(oidLeaveStock);
                    }
                    catch(Exception exc)
                    {
                    }
                }
                
                frmSpecialLeaveStock.requestEntityObject(specialLeaveStock);
                
                if(frmSpecialLeaveStock.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                
                if(specialLeaveStock.getOID()==0)
                {
                    try
                    {
                        long oid = PstSpecialLeaveStock.insertExc(this.specialLeaveStock);
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
                        long oid = PstSpecialLeaveStock.updateExc(this.specialLeaveStock);
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
                if (oidLeaveStock != 0) 
                {
                    try 
                    {
                        specialLeaveStock = PstSpecialLeaveStock.fetchExc(oidLeaveStock);
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
                if (oidLeaveStock != 0) 
                {
                    try 
                    {
                        specialLeaveStock = PstSpecialLeaveStock.fetchExc(oidLeaveStock);
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
                if (oidLeaveStock != 0)
                {
                    try
                    {
                        long oid = PstSpecialLeaveStock.deleteExc(oidLeaveStock);
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
    
    public int action(int cmd , long oidLeaveStock, SpecialLeaveStock leaveStock) 
    {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        
        switch(cmd)
        {
            case Command.ADD :
                break;
                
            case Command.SAVE :
                if(oidLeaveStock != 0)
                {
                    try
                    {
                        specialLeaveStock = PstSpecialLeaveStock.fetchExc(oidLeaveStock);
                    }
                    catch(Exception exc)
                    {
                    }
                }
                
                specialLeaveStock.setOID(leaveStock.getOID());
                specialLeaveStock.setSpecialLeaveId(leaveStock.getSpecialLeaveId());
                specialLeaveStock.setSymbolId(leaveStock.getSymbolId());
                specialLeaveStock.setEmployeeId(leaveStock.getEmployeeId());
                specialLeaveStock.setTakenDate(leaveStock.getTakenDate());
                specialLeaveStock.setTakenQty(leaveStock.getTakenQty());
                                               
                if(specialLeaveStock.getOID()==0)
                {
                    try
                    {
                        long oid = PstSpecialLeaveStock.insertExc(this.specialLeaveStock);
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
                        long oid = PstSpecialLeaveStock.updateExc(this.specialLeaveStock);
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
                if (oidLeaveStock != 0) 
                {
                    try 
                    {
                        specialLeaveStock = PstSpecialLeaveStock.fetchExc(oidLeaveStock);
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
                if (oidLeaveStock != 0) 
                {
                    try 
                    {
                        specialLeaveStock = PstSpecialLeaveStock.fetchExc(oidLeaveStock);
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
                if (oidLeaveStock != 0)
                {
                    try
                    {
                        long oid = PstSpecialLeaveStock.deleteExc(oidLeaveStock);
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