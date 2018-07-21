/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.FrmPaySlipGroup;
import com.dimata.harisma.entity.payroll.PaySlipGroup;
import com.dimata.harisma.entity.payroll.PstPaySlipGroup;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class CtrlPaySlipGroup extends Control implements I_Language{
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Data sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private PaySlipGroup paySlipGroup;
    private PstPaySlipGroup pstPaySlipGroup;
    private FrmPaySlipGroup frmPaySlipGroup;
    int language = LANGUAGE_FOREIGN;

    public CtrlPaySlipGroup(HttpServletRequest request) {
        msgString = "";
        paySlipGroup = new PaySlipGroup();
        try {
            pstPaySlipGroup = new PstPaySlipGroup(0);
        } catch (Exception e) {
            ;
        }
        frmPaySlipGroup= new FrmPaySlipGroup(request, paySlipGroup);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPaySlipGroup.addError(frmPaySlipGroup.FRM_FIELD_PAYSLIP_GROUP_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PaySlipGroup getCompany() {
        return paySlipGroup;
    }

    public FrmPaySlipGroup getForm() {
        return frmPaySlipGroup;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPaySlipGroup) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidPaySlipGroup != 0) {
                    try {
                        paySlipGroup = PstPaySlipGroup.fetchExc(oidPaySlipGroup);
                    } catch (Exception exc) {
                    }
                }

                frmPaySlipGroup.requestEntityObject(paySlipGroup);

                if (frmPaySlipGroup.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (paySlipGroup.getOID() == 0) {
                    try {
                        long oid = pstPaySlipGroup.insertExc(this.paySlipGroup);

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
                        long oid = pstPaySlipGroup.updateExc(this.paySlipGroup);

                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidPaySlipGroup != 0) {
                    try {
                        paySlipGroup = PstPaySlipGroup.fetchExc(oidPaySlipGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidPaySlipGroup != 0) {
                    try {
                        //cek apakah oidPaySlipGroup ini sudah di gunakan 
                        if (PstPaySlipGroup.checkUsePaySlipGroup(oidPaySlipGroup)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        paySlipGroup = PstPaySlipGroup.fetchExc(oidPaySlipGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidPaySlipGroup != 0) {
                    try {
                        long oid = PstPaySlipGroup.deleteExc(oidPaySlipGroup);

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