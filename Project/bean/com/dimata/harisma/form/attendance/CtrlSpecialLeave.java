
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
 * @author bayu
 */

public class CtrlSpecialLeave extends Control implements I_Language {
    
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "OID sudah ada", "Data tidak lengkap"},
        {"Success", "Can not process", "OID code exist", "Data incomplete"}
    };
    
    private int start;
    private String msgString;
    private SpecialLeave specialLeave;
    private PstSpecialLeave pstSpecialLeave;
    private FrmSpecialLeave frmSpecialLeave;
    int language = LANGUAGE_DEFAULT;
    
    public CtrlSpecialLeave(HttpServletRequest request){
        msgString = "";
        specialLeave = new SpecialLeave();
        try{
            pstSpecialLeave = new PstSpecialLeave(0);
        }catch(Exception e){;}
        frmSpecialLeave = new FrmSpecialLeave(request, specialLeave);
    }
     
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmSpecialLeave.addError(frmSpecialLeave.FRM_FIELD_SPECIAL_LEAVE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public SpecialLeave getSpecialLeave() { return specialLeave; }
    
    public FrmSpecialLeave getForm() { return frmSpecialLeave; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidLeave, int maxApproval) 
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
                        specialLeave = PstSpecialLeave.fetchExc(oidLeave);
                    }
                    catch(Exception exc)
                    {
                    }
                }
                
                frmSpecialLeave.requestEntityObject(specialLeave);
                
                switch(maxApproval){
                    case 1: 
                        if(specialLeave.getApprovalId()>0){
                            specialLeave.setApproval2Id(specialLeave.getApprovalId());
                            specialLeave.setApproval3Id(specialLeave.getApprovalId());
                            specialLeave.setApproval2Date(specialLeave.getApprovalDate());
                            specialLeave.setApproval3Date(specialLeave.getApprovalDate());
                        }
                        break;
                    case 2: 
                        if(specialLeave.getApproval2Id()>0){
                            specialLeave.setApproval3Id(specialLeave.getApproval2Id());
                            specialLeave.setApproval3Date(specialLeave.getApproval2Date());
                        }
                        break;
                }
                
                if(frmSpecialLeave.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                
                if(specialLeave.getOID()==0)
                {
                    try
                    {
                        long oid = PstSpecialLeave.insertExc(this.specialLeave);
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
                        long oid = PstSpecialLeave.updateExc(this.specialLeave);
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
                        specialLeave = PstSpecialLeave.fetchExc(oidLeave);
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
                        specialLeave = PstSpecialLeave.fetchExc(oidLeave);
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
                        long oid = PstSpecialLeave.deleteExc(oidLeave);
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
