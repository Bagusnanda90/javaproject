
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tu Roy
 */
/*
package com.dimata.harisma.form.leave;
import javax.servlet.http.HttpServletRequest;

import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.system.I_DBExceptionInfo;

import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;


public class CtrlSpecialUpload extends Control implements I_Language{
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
    private SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken;
    private PstSpecialUnpaidLeaveTaken objPstSpecialUnpaidLeaveTaken;
    private FrmSpecialUnpaidLeaveTaken objFrmSpecialUnpaidLeaveTaken;
    int language = LANGUAGE_DEFAULT;

    public CtrlSpecialUpload(HttpServletRequest request) {
        msgString = "";
        objSpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
        try {
            objPstSpecialUnpaidLeaveTaken = new PstSpecialUnpaidLeaveTaken(0);
        } catch (Exception e) {
            //System.out.printn();
        }
        objFrmSpecialUnpaidLeaveTaken = new FrmSpecialUnpaidLeaveTaken(request, objSpecialUnpaidLeaveTaken);
    }

    
    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.objFrmSpecialUnpaidLeaveTaken.addError(0, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public SpecialUnpaidLeaveTaken getDpApplication() {
        return objSpecialUnpaidLeaveTaken;
    }

    public FrmSpecialUnpaidLeaveTaken getForm() {
        return objFrmSpecialUnpaidLeaveTaken;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long specialUploadId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:                    
                if (specialUploadId != 0)    
                {
                    try 
                    {                        
                        objSpecialUnpaidLeaveTaken = PstSpecialUnpaidLeaveTaken.fetchExc(specialUploadId);
                    }
                    catch (Exception exc) 
                    {
                        System.out.println("Exc when fetch Special Upload entity : " + exc.toString());    
                    }
                }

                objFrmAlUpload.requestEntityObject(objAlUpload);
                
                if (objFrmAlUpload.errorSize() > 0) 
                {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (objAlUpload.getOID() == 0) 
                {
                    try 
                    {
                        long oid = PstAlUpload.insertExc(this.objAlUpload);
                    }
                    catch (DBException dbexc)   
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
                        long oid = PstAlUpload.updateExc(this.objAlUpload);                        
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

            case Command.EDIT:
                if (alUploadId != 0) 
                {
                    try 
                    {
                        objAlUpload = PstAlUpload.fetchExc(alUploadId);
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

            case Command.ASK:
                if (alUploadId != 0) 
                {
                    try 
                    {
                        objAlUpload = PstAlUpload.fetchExc(alUploadId);
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

            case Command.DELETE:
                if (alUploadId != 0) 
                {
                    try 
                    {
                        long oid = PstAlUpload.deleteExc(alUploadId);
                        if (oid != 0) 
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

            default :

        }
        return rsCode;
    }
}
 */
