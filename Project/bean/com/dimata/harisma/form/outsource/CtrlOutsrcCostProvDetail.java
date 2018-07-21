/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutsrcCostProvDetail;
import com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class CtrlOutsrcCostProvDetail extends Control implements I_Language {

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
    private OutsrcCostProvDetail entOutsrcCostProvDetail;
    private PstOutsrcCostProvDetail pstOutsrcCostProvDetail;
    private FrmOutsrcCostProvDetail frmOutsrcCostProvDetail;
    int language = LANGUAGE_DEFAULT;

    public CtrlOutsrcCostProvDetail(HttpServletRequest request) {
        msgString = "";
        entOutsrcCostProvDetail = new OutsrcCostProvDetail();
        try {
            pstOutsrcCostProvDetail = new PstOutsrcCostProvDetail(0);
        } catch (Exception e) {;
        }
        frmOutsrcCostProvDetail = new FrmOutsrcCostProvDetail(request, entOutsrcCostProvDetail);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmOutsrcCostProvDetail.addError(frmOutsrcCostProvDetail.FRM_FIELD_OUTSRC_COST_PROV_DETLD_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public OutsrcCostProvDetail getOutsrcCostProvDetail() {
        return entOutsrcCostProvDetail;
    }

    public FrmOutsrcCostProvDetail getForm() {
        return frmOutsrcCostProvDetail;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidOutsrcCostProvDetail) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidOutsrcCostProvDetail != 0) {
                    try {
                        entOutsrcCostProvDetail = PstOutsrcCostProvDetail.fetchExc(oidOutsrcCostProvDetail);
                    } catch (Exception exc) {
                    }
                }

                frmOutsrcCostProvDetail.requestEntityObject(entOutsrcCostProvDetail);

                if (frmOutsrcCostProvDetail.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entOutsrcCostProvDetail.getOID() == 0) {
                    try {
                        long oid = pstOutsrcCostProvDetail.insertExc(this.entOutsrcCostProvDetail);
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
                        long oid = pstOutsrcCostProvDetail.updateExc(this.entOutsrcCostProvDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidOutsrcCostProvDetail != 0) {
                    try {
                        entOutsrcCostProvDetail = PstOutsrcCostProvDetail.fetchExc(oidOutsrcCostProvDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidOutsrcCostProvDetail != 0) {
                    try {
                        entOutsrcCostProvDetail = PstOutsrcCostProvDetail.fetchExc(oidOutsrcCostProvDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidOutsrcCostProvDetail != 0) {
                    try {
                        long oid = PstOutsrcCostProvDetail.deleteExc(oidOutsrcCostProvDetail);
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
