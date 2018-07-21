/*
 * CtrlDpApplication.java
 *
 * Created on October 21, 2004, 12:06 PM
 */

package com.dimata.harisma.form.leave.dp;

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
import com.dimata.harisma.entity.leave.dp.DpAppMain;
import com.dimata.harisma.entity.leave.dp.PstDpAppMain;

/**
 *
 * @author  gedhy
 */
public class CtrlDpAppMain  extends Control implements I_Language {
    
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
    private DpAppMain objDpAppMain;
    private PstDpAppMain objPstDpAppMain;
    private FrmDpAppMain objFrmDpAppMain;
    int language = LANGUAGE_DEFAULT;

    public CtrlDpAppMain(HttpServletRequest request) {
        msgString = "";
        objDpAppMain = new DpAppMain();
        try {
            objPstDpAppMain = new PstDpAppMain(0);
        } catch (Exception e) {
            //System.out.printn();
        }
        objFrmDpAppMain = new FrmDpAppMain(request, objDpAppMain);
    }

    
    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.objFrmDpAppMain.addError(0, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public DpAppMain getDpApplication() {
        return objDpAppMain;
    }

    public FrmDpAppMain getForm() {
        return objFrmDpAppMain;
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
                        objDpAppMain = PstDpAppMain.fetchExc(alUploadId);
                    }
                    catch (Exception exc) 
                    {
                        System.out.println("Exc when fetch DpAppMain entity : " + exc.toString());    
                    }
                }

                objFrmDpAppMain.requestEntityObject(objDpAppMain);
                
                if (objFrmDpAppMain.errorSize() > 0) 
                {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (objDpAppMain.getOID() == 0) 
                {
                    try 
                    {
                        long oid = PstDpAppMain.insertExc(this.objDpAppMain);
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
                        long oid = PstDpAppMain.updateExc(this.objDpAppMain);                        
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
                        objDpAppMain = PstDpAppMain.fetchExc(alUploadId);
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
                        objDpAppMain = PstDpAppMain.fetchExc(alUploadId);
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
                        long oid = PstDpAppMain.deleteExc(alUploadId);
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
