/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.masterdata.*;

/*
 Description : Controll PositionEducationRequired
 Date : Mon Feb 02 2015
 Author : Hendra Putu
 */
public class CtrlPositionEducationRequired extends Control implements I_Language {

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
    private PositionEducationRequired entPositionEducationRequired;
    private PstPositionEducationRequired pstPositionEducationRequired;
    private FrmPositionEducationRequired frmPositionEducationRequired;
    int language = LANGUAGE_DEFAULT;

    public CtrlPositionEducationRequired(HttpServletRequest request) {
        msgString = "";
        entPositionEducationRequired = new PositionEducationRequired();
        try {
            pstPositionEducationRequired = new PstPositionEducationRequired(0);
        } catch (Exception e) {;
        }
        frmPositionEducationRequired = new FrmPositionEducationRequired(request, entPositionEducationRequired);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPositionEducationRequired.addError(frmPositionEducationRequired.FRM_FIELD_POS_EDUCATION_REQ_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PositionEducationRequired getPositionEducationRequired() {
        return entPositionEducationRequired;
    }

    public FrmPositionEducationRequired getForm() {
        return frmPositionEducationRequired;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPositionEducationRequired) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidPositionEducationRequired != 0) {
                    try {
                        entPositionEducationRequired = PstPositionEducationRequired.fetchExc(oidPositionEducationRequired);
                    } catch (Exception exc) {
                    }
                }

                frmPositionEducationRequired.requestEntityObject(entPositionEducationRequired);

                if (frmPositionEducationRequired.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entPositionEducationRequired.getOID() == 0) {
                    try {
                        long oid = pstPositionEducationRequired.insertExc(this.entPositionEducationRequired);
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
                        long oid = pstPositionEducationRequired.updateExc(this.entPositionEducationRequired);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidPositionEducationRequired != 0) {
                    try {
                        entPositionEducationRequired = PstPositionEducationRequired.fetchExc(oidPositionEducationRequired);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidPositionEducationRequired != 0) {
                    try {
                        entPositionEducationRequired = PstPositionEducationRequired.fetchExc(oidPositionEducationRequired);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidPositionEducationRequired != 0) {
                    try {
                        long oid = PstPositionEducationRequired.deleteExc(oidPositionEducationRequired);
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
