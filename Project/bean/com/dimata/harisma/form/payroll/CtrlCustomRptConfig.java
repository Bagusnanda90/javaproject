/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

/**
 * Description : Date :
 *
 * @author Hendra Putu
 */
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.CustomRptConfig;
import com.dimata.harisma.entity.payroll.PstCustomRptConfig;

/*
 Description : Controll CustomRptConfig
 Date : Thu Mar 26 2015
 Author : Hendra Putu
 */
public class CtrlCustomRptConfig extends Control implements I_Language {

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
    private CustomRptConfig entCustomRptConfig;
    private PstCustomRptConfig pstCustomRptConfig;
    private FrmCustomRptConfig frmCustomRptConfig;
    int language = LANGUAGE_DEFAULT;

    public CtrlCustomRptConfig(HttpServletRequest request) {
        msgString = "";
        entCustomRptConfig = new CustomRptConfig();
        try {
            pstCustomRptConfig = new PstCustomRptConfig(0);
        } catch (Exception e) {;
        }
        frmCustomRptConfig = new FrmCustomRptConfig(request, entCustomRptConfig);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmCustomRptConfig.addError(frmCustomRptConfig.FRM_FIELD_RPT_CONFIG_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public CustomRptConfig getCustomRptConfig() {
        return entCustomRptConfig;
    }

    public FrmCustomRptConfig getForm() {
        return frmCustomRptConfig;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCustomRptConfig) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidCustomRptConfig != 0) {
                    try {
                        entCustomRptConfig = PstCustomRptConfig.fetchExc(oidCustomRptConfig);
                    } catch (Exception exc) {
                    }
                }

                frmCustomRptConfig.requestEntityObject(entCustomRptConfig);

                if (frmCustomRptConfig.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entCustomRptConfig.getOID() == 0) {
                    try {
                        long oid = pstCustomRptConfig.insertExc(this.entCustomRptConfig);
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
                        long oid = pstCustomRptConfig.updateExc(this.entCustomRptConfig);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidCustomRptConfig != 0) {
                    try {
                        entCustomRptConfig = PstCustomRptConfig.fetchExc(oidCustomRptConfig);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCustomRptConfig != 0) {
                    try {
                        entCustomRptConfig = PstCustomRptConfig.fetchExc(oidCustomRptConfig);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCustomRptConfig != 0) {
                    try {
                        long oid = PstCustomRptConfig.deleteExc(oidCustomRptConfig);
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