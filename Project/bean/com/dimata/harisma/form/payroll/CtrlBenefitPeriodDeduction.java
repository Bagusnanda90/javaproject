/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitPeriodDeduction;
import com.dimata.harisma.entity.payroll.PstBenefitPeriodDeduction;
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import javax.servlet.http.*;

/*
 Description : Controll BenefitPeriodDeduction
 Date : Sun Feb 22 2015
 Author : Hendra Putu
 */
public class CtrlBenefitPeriodDeduction extends Control implements I_Language {

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
    private BenefitPeriodDeduction entBenefitPeriodDeduction;
    private PstBenefitPeriodDeduction pstBenefitPeriodDeduction;
    private FrmBenefitPeriodDeduction frmBenefitPeriodDeduction;
    int language = LANGUAGE_DEFAULT;

    public CtrlBenefitPeriodDeduction(HttpServletRequest request) {
        msgString = "";
        entBenefitPeriodDeduction = new BenefitPeriodDeduction();
        try {
            pstBenefitPeriodDeduction = new PstBenefitPeriodDeduction(0);
        } catch (Exception e) {;
        }
        frmBenefitPeriodDeduction = new FrmBenefitPeriodDeduction(request, entBenefitPeriodDeduction);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmBenefitPeriodDeduction.addError(frmBenefitPeriodDeduction.FRM_FIELD_BENEFIT_PERIOD_DEDUCTION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public BenefitPeriodDeduction getBenefitPeriodDeduction() {
        return entBenefitPeriodDeduction;
    }

    public FrmBenefitPeriodDeduction getForm() {
        return frmBenefitPeriodDeduction;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidBenefitPeriodDeduction) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidBenefitPeriodDeduction != 0) {
                    try {
                        entBenefitPeriodDeduction = PstBenefitPeriodDeduction.fetchExc(oidBenefitPeriodDeduction);
                    } catch (Exception exc) {
                    }
                }

                frmBenefitPeriodDeduction.requestEntityObject(entBenefitPeriodDeduction);

                if (frmBenefitPeriodDeduction.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entBenefitPeriodDeduction.getOID() == 0) {
                    try {
                        long oid = pstBenefitPeriodDeduction.insertExc(this.entBenefitPeriodDeduction);
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
                        long oid = pstBenefitPeriodDeduction.updateExc(this.entBenefitPeriodDeduction);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidBenefitPeriodDeduction != 0) {
                    try {
                        entBenefitPeriodDeduction = PstBenefitPeriodDeduction.fetchExc(oidBenefitPeriodDeduction);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidBenefitPeriodDeduction != 0) {
                    try {
                        entBenefitPeriodDeduction = PstBenefitPeriodDeduction.fetchExc(oidBenefitPeriodDeduction);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidBenefitPeriodDeduction != 0) {
                    try {
                        long oid = PstBenefitPeriodDeduction.deleteExc(oidBenefitPeriodDeduction);
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