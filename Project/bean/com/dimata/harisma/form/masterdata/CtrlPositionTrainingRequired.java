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
 Description : Controll PositionTrainingRequired
 Date : Mon Feb 02 2015
 Author : Hendra Putu
 */
public class CtrlPositionTrainingRequired extends Control implements I_Language {

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
    private PositionTrainingRequired entPositionTrainingRequired;
    private PstPositionTrainingRequired pstPositionTrainingRequired;
    private FrmPositionTrainingRequired frmPositionTrainingRequired;
    int language = LANGUAGE_DEFAULT;

    public CtrlPositionTrainingRequired(HttpServletRequest request) {
        msgString = "";
        entPositionTrainingRequired = new PositionTrainingRequired();
        try {
            pstPositionTrainingRequired = new PstPositionTrainingRequired(0);
        } catch (Exception e) {;
        }
        frmPositionTrainingRequired = new FrmPositionTrainingRequired(request, entPositionTrainingRequired);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPositionTrainingRequired.addError(frmPositionTrainingRequired.FRM_FIELD_POS_TRAINING_REQ_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PositionTrainingRequired getPositionTrainingRequired() {
        return entPositionTrainingRequired;
    }

    public FrmPositionTrainingRequired getForm() {
        return frmPositionTrainingRequired;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPositionTrainingRequired) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidPositionTrainingRequired != 0) {
                    try {
                        entPositionTrainingRequired = PstPositionTrainingRequired.fetchExc(oidPositionTrainingRequired);
                    } catch (Exception exc) {
                    }
                }

                frmPositionTrainingRequired.requestEntityObject(entPositionTrainingRequired);

                if (frmPositionTrainingRequired.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entPositionTrainingRequired.getOID() == 0) {
                    try {
                        long oid = pstPositionTrainingRequired.insertExc(this.entPositionTrainingRequired);
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
                        long oid = pstPositionTrainingRequired.updateExc(this.entPositionTrainingRequired);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidPositionTrainingRequired != 0) {
                    try {
                        entPositionTrainingRequired = PstPositionTrainingRequired.fetchExc(oidPositionTrainingRequired);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidPositionTrainingRequired != 0) {
                    try {
                        entPositionTrainingRequired = PstPositionTrainingRequired.fetchExc(oidPositionTrainingRequired);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidPositionTrainingRequired != 0) {
                    try {
                        long oid = PstPositionTrainingRequired.deleteExc(oidPositionTrainingRequired);
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
