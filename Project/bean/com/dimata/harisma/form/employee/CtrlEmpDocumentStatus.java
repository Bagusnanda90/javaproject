/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.employee.*;

/*
Description : Controll EmpDocumentStatus
Date : Wed Oct 12 2016
Author : Gunadi
 */
public class CtrlEmpDocumentStatus extends Control implements I_Language {

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
    private EmpDocumentStatus entEmpDocumentStatus;
    private PstEmpDocumentStatus pstEmpDocumentStatus;
    private FrmEmpDocumentStatus frmEmpDocumentStatus;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpDocumentStatus(HttpServletRequest request) {
        msgString = "";
        entEmpDocumentStatus = new EmpDocumentStatus();
        try {
            pstEmpDocumentStatus = new PstEmpDocumentStatus(0);
        } catch (Exception e) {;
        }
        frmEmpDocumentStatus = new FrmEmpDocumentStatus(request, entEmpDocumentStatus);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpDocumentStatus.addError(frmEmpDocumentStatus.FRM_FIELD_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpDocumentStatus getEmpDocumentStatus() {
        return entEmpDocumentStatus;
    }

    public FrmEmpDocumentStatus getForm() {
        return frmEmpDocumentStatus;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpDocumentStatus) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidEmpDocumentStatus != 0) {
                    try {
                        entEmpDocumentStatus = PstEmpDocumentStatus.fetchExc(oidEmpDocumentStatus);
                    } catch (Exception exc) {
                    }
                }

                frmEmpDocumentStatus.requestEntityObject(entEmpDocumentStatus);

                if (frmEmpDocumentStatus.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entEmpDocumentStatus.getOID() == 0) {
                    try {
                        long oid = pstEmpDocumentStatus.insertExc(this.entEmpDocumentStatus);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        long oid = pstEmpDocumentStatus.updateExc(this.entEmpDocumentStatus);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidEmpDocumentStatus != 0) {
                    try {
                        entEmpDocumentStatus = PstEmpDocumentStatus.fetchExc(oidEmpDocumentStatus);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpDocumentStatus != 0) {
                    try {
                        entEmpDocumentStatus = PstEmpDocumentStatus.fetchExc(oidEmpDocumentStatus);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpDocumentStatus != 0) {
                    try {
                        long oid = PstEmpDocumentStatus.deleteExc(oidEmpDocumentStatus);
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default:

        }
        return rsCode;
    }
}
