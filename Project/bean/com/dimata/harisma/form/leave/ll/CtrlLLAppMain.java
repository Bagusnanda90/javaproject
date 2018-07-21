/*
 * CtrlDpApplication.java
 *
 * Created on October 21, 2004, 12:06 PM
 */

package com.dimata.harisma.form.leave.ll;

import com.dimata.harisma.form.leave.*;
import javax.servlet.http.HttpServletRequest;
//import java.util.Vector;
//import java.util.Date;
//import java.util.Calendar;
//import java.util.GregorianCalendar;

import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.system.I_DBExceptionInfo;

import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
//import com.dimata.util.Formater;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;
//import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.harisma.entity.leave.ll.LLAppMain;
import com.dimata.harisma.entity.leave.ll.PstLLAppMain;

/**
 *
 * @author  gedhy
 */
public class CtrlLLAppMain  extends Control implements I_Language {
    
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
    private LLAppMain objLLAppMain;
    private PstLLAppMain objPstLLAppMain;
    private FrmLLAppMain objFrmLLAppMain;
    int language = LANGUAGE_DEFAULT;

    public CtrlLLAppMain(HttpServletRequest request) {
        msgString = "";
        objLLAppMain = new LLAppMain();
        try {
            objPstLLAppMain = new PstLLAppMain(0);
        } catch (Exception e) {
            //System.out.printn();
        }
        objFrmLLAppMain = new FrmLLAppMain(request, objLLAppMain);
    }

    
    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.objFrmLLAppMain.addError(0, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public LLAppMain getEntity() {
        return objLLAppMain;
    }

    public FrmLLAppMain getForm() {
        return objFrmLLAppMain;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long alUploadId) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:                    
                if (alUploadId != 0)    
                {
                    try 
                    {                        
                        objLLAppMain = PstLLAppMain.fetchExc(alUploadId);
                    }
                    catch (Exception exc) 
                    {
                        System.out.println("Exc when fetch LLAppMain entity : " + exc.toString());    
                    }
                }

                objFrmLLAppMain.requestEntityObject(objLLAppMain);
                
                if (objFrmLLAppMain.errorSize() > 0) 
                {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (objLLAppMain.getOID() == 0) 
                {
                    try 
                    {
                        long oid = PstLLAppMain.insertExc(this.objLLAppMain);
                        objLLAppMain.setOID(oid);
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
                        long oid = PstLLAppMain.updateExc(this.objLLAppMain);                        
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
                        objLLAppMain = PstLLAppMain.fetchExc(alUploadId);
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
                        objLLAppMain = PstLLAppMain.fetchExc(alUploadId);
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
                        long oid = PstLLAppMain.deleteExc(alUploadId);
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
