/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitConfig;
import com.dimata.harisma.entity.payroll.PstBenefitConfig;
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import javax.servlet.http.*;

/*
 Description : Controll BenefitConfig
 Date : Sat Feb 21 2015
 Author : Hendra Putu
 */
public class CtrlBenefitConfig extends Control implements I_Language {

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
    private BenefitConfig entBenefitConfig;
    private PstBenefitConfig pstBenefitConfig;
    private FrmBenefitConfig frmBenefitConfig;
    int language = LANGUAGE_DEFAULT;

    public CtrlBenefitConfig(HttpServletRequest request) {
        msgString = "";
        entBenefitConfig = new BenefitConfig();
        try {
            pstBenefitConfig = new PstBenefitConfig(0);
        } catch (Exception e) {;
        }
        frmBenefitConfig = new FrmBenefitConfig(request, entBenefitConfig);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmBenefitConfig.addError(frmBenefitConfig.FRM_FIELD_BENEFIT_CONFIG_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public BenefitConfig getBenefitConfig() {
        return entBenefitConfig;
    }

    public FrmBenefitConfig getForm() {
        return frmBenefitConfig;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidBenefitConfig) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidBenefitConfig != 0) {
                    try {
                        entBenefitConfig = PstBenefitConfig.fetchExc(oidBenefitConfig);
                    } catch (Exception exc) {
                    }
                }

                frmBenefitConfig.requestEntityObject(entBenefitConfig);

                if (frmBenefitConfig.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entBenefitConfig.getOID() == 0) {
                    try {
                        long oid = pstBenefitConfig.insertExc(this.entBenefitConfig);
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
                        long oid = pstBenefitConfig.updateExc(this.entBenefitConfig);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidBenefitConfig != 0) {
                    try {
                        entBenefitConfig = PstBenefitConfig.fetchExc(oidBenefitConfig);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidBenefitConfig != 0) {
                    try {
                        entBenefitConfig = PstBenefitConfig.fetchExc(oidBenefitConfig);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidBenefitConfig != 0) {
                    try {
                        long oid = PstBenefitConfig.deleteExc(oidBenefitConfig);
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
