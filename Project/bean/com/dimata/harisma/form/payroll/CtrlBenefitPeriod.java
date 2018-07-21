/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitPeriod;
import com.dimata.harisma.entity.payroll.PstBenefitPeriod;
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import javax.servlet.http.*;

/*
 Description : Controll BenefitPeriod
 Date : Sun Feb 22 2015
 Author : Hendra Putu
 */
public class CtrlBenefitPeriod extends Control implements I_Language {

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
    private BenefitPeriod entBenefitPeriod;
    private PstBenefitPeriod pstBenefitPeriod;
    private FrmBenefitPeriod frmBenefitPeriod;
    int language = LANGUAGE_DEFAULT;

    public CtrlBenefitPeriod(HttpServletRequest request) {
        msgString = "";
        entBenefitPeriod = new BenefitPeriod();
        try {
            pstBenefitPeriod = new PstBenefitPeriod(0);
        } catch (Exception e) {;
        }
        frmBenefitPeriod = new FrmBenefitPeriod(request, entBenefitPeriod);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmBenefitPeriod.addError(frmBenefitPeriod.FRM_FIELD_BENEFIT_PERIOD_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public BenefitPeriod getBenefitPeriod() {
        return entBenefitPeriod;
    }

    public FrmBenefitPeriod getForm() {
        return frmBenefitPeriod;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidBenefitPeriod) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidBenefitPeriod != 0) {
                    try {
                        entBenefitPeriod = PstBenefitPeriod.fetchExc(oidBenefitPeriod);
                    } catch (Exception exc) {
                    }
                }

                frmBenefitPeriod.requestEntityObject(entBenefitPeriod);

                if (frmBenefitPeriod.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entBenefitPeriod.getOID() == 0) {
                    try {
                        long oid = pstBenefitPeriod.insertExc(this.entBenefitPeriod);
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
                        long oid = pstBenefitPeriod.updateExc(this.entBenefitPeriod);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidBenefitPeriod != 0) {
                    try {
                        entBenefitPeriod = PstBenefitPeriod.fetchExc(oidBenefitPeriod);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidBenefitPeriod != 0) {
                    try {
                        entBenefitPeriod = PstBenefitPeriod.fetchExc(oidBenefitPeriod);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidBenefitPeriod != 0) {
                    try {
                        long oid = PstBenefitPeriod.deleteExc(oidBenefitPeriod);
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
