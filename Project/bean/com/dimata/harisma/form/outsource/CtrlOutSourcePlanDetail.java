/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

/**
 *
 * @author dimata005
 */
import com.dimata.harisma.entity.outsource.OutSourcePlanDetail;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanDetail;
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;

/*
 Description : Controll OutSourcePlanDetail
 Date : Mon Sep 14 2015
 Author : opie-eyek
 */
public class CtrlOutSourcePlanDetail extends Control implements I_Language {

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
    private OutSourcePlanDetail entOutSourcePlanDetail;
    private PstOutSourcePlanDetail pstOutSourcePlanDetail;
    private FrmOutSourcePlanDetail frmOutSourcePlanDetail;
    int language = LANGUAGE_DEFAULT;

    public CtrlOutSourcePlanDetail(HttpServletRequest request) {
        msgString = "";
        entOutSourcePlanDetail = new OutSourcePlanDetail();
        try {
            pstOutSourcePlanDetail = new PstOutSourcePlanDetail(0);
        } catch (Exception e) {;
        }
        frmOutSourcePlanDetail = new FrmOutSourcePlanDetail(request, entOutSourcePlanDetail);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmOutSourcePlanDetail.addError(frmOutSourcePlanDetail.FRM_FIELD_OUTSOURCEPLANDETAILID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public OutSourcePlanDetail getOutSourcePlanDetail() {
        return entOutSourcePlanDetail;
    }

    public FrmOutSourcePlanDetail getForm() {
        return frmOutSourcePlanDetail;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidOutSourcePlanDetail) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidOutSourcePlanDetail != 0) {
                    try {
                        entOutSourcePlanDetail = PstOutSourcePlanDetail.fetchExc(oidOutSourcePlanDetail);
                    } catch (Exception exc) {
                    }
                }

                frmOutSourcePlanDetail.requestEntityObject(entOutSourcePlanDetail);

                if (frmOutSourcePlanDetail.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entOutSourcePlanDetail.getOID() == 0) {
                    try {
                        long oid = pstOutSourcePlanDetail.insertExc(this.entOutSourcePlanDetail);
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
                        long oid = pstOutSourcePlanDetail.updateExc(this.entOutSourcePlanDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidOutSourcePlanDetail != 0) {
                    try {
                        entOutSourcePlanDetail = PstOutSourcePlanDetail.fetchExc(oidOutSourcePlanDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidOutSourcePlanDetail != 0) {
                    try {
                        entOutSourcePlanDetail = PstOutSourcePlanDetail.fetchExc(oidOutSourcePlanDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidOutSourcePlanDetail != 0) {
                    try {
                        long oid = PstOutSourcePlanDetail.deleteExc(oidOutSourcePlanDetail);
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