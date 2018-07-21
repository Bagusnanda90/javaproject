/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutSourceCostMaster;
import com.dimata.harisma.entity.outsource.PstOutSourceCostMaster;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dimata005
 */
public class CtrlOutSourceCostMaster extends Control implements I_Language {

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
    private OutSourceCostMaster entOutSourceCostMaster;
    private PstOutSourceCostMaster pstOutSourceCostMaster;
    private FrmOutSourceCostMaster frmOutSourceCostMaster;
    int language = LANGUAGE_DEFAULT;

    public CtrlOutSourceCostMaster(HttpServletRequest request) {
        msgString = "";
        entOutSourceCostMaster = new OutSourceCostMaster();
        try {
            pstOutSourceCostMaster = new PstOutSourceCostMaster(0);
        } catch (Exception e) {;
        }
        frmOutSourceCostMaster = new FrmOutSourceCostMaster(request, entOutSourceCostMaster);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmOutSourceCostMaster.addError(frmOutSourceCostMaster.FRM_FIELD_OUTSRC_COST_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public OutSourceCostMaster getOutSourceCostMaster() {
        return entOutSourceCostMaster;
    }

    public FrmOutSourceCostMaster getForm() {
        return frmOutSourceCostMaster;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidOutSourceCostMaster) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidOutSourceCostMaster != 0) {
                    try {
                        entOutSourceCostMaster = PstOutSourceCostMaster.fetchExc(oidOutSourceCostMaster);
                    } catch (Exception exc) {
                    }
                }

                frmOutSourceCostMaster.requestEntityObject(entOutSourceCostMaster);

                if (frmOutSourceCostMaster.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entOutSourceCostMaster.getOID() == 0) {
                    try {
                        long oid = pstOutSourceCostMaster.insertExc(this.entOutSourceCostMaster);
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
                        long oid = pstOutSourceCostMaster.updateExc(this.entOutSourceCostMaster);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidOutSourceCostMaster != 0) {
                    try {
                        entOutSourceCostMaster = PstOutSourceCostMaster.fetchExc(oidOutSourceCostMaster);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidOutSourceCostMaster != 0) {
                    try {
                        entOutSourceCostMaster = PstOutSourceCostMaster.fetchExc(oidOutSourceCostMaster);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidOutSourceCostMaster != 0) {
                    try {
                        long oid = PstOutSourceCostMaster.deleteExc(oidOutSourceCostMaster);
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
