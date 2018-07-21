/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.EmpRelevantDocGroup;
import com.dimata.harisma.entity.masterdata.PstEmpRelevantDocGroup;
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
public class CtrlEmpRelevantDocGroup extends Control implements I_Language {

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
    private EmpRelevantDocGroup entEmpRelevantDocGroup;
    private PstEmpRelevantDocGroup pstEmpRelevantDocGroup;
    private FrmEmpRelevantDocGroup frmEmpRelevantDocGroup;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpRelevantDocGroup(HttpServletRequest request) {
        msgString = "";
        entEmpRelevantDocGroup = new EmpRelevantDocGroup();
        try {
            pstEmpRelevantDocGroup = new PstEmpRelevantDocGroup(0);
        } catch (Exception e) {;
        }
        frmEmpRelevantDocGroup = new FrmEmpRelevantDocGroup(request, entEmpRelevantDocGroup);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpRelevantDocGroup.addError(frmEmpRelevantDocGroup.FRM_FIELD_EMP_RELVT_DOC_GRP_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpRelevantDocGroup getEmpRelevantDocGroup() {
        return entEmpRelevantDocGroup;
    }

    public FrmEmpRelevantDocGroup getForm() {
        return frmEmpRelevantDocGroup;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpRelevantDocGroup) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidEmpRelevantDocGroup != 0) {
                    try {
                        entEmpRelevantDocGroup = PstEmpRelevantDocGroup.fetchExc(oidEmpRelevantDocGroup);
                    } catch (Exception exc) {
                    }
                }

                frmEmpRelevantDocGroup.requestEntityObject(entEmpRelevantDocGroup);

                if (frmEmpRelevantDocGroup.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entEmpRelevantDocGroup.getOID() == 0) {
                    try {
                        long oid = pstEmpRelevantDocGroup.insertExc(this.entEmpRelevantDocGroup);
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
                        long oid = pstEmpRelevantDocGroup.updateExc(this.entEmpRelevantDocGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidEmpRelevantDocGroup != 0) {
                    try {
                        entEmpRelevantDocGroup = PstEmpRelevantDocGroup.fetchExc(oidEmpRelevantDocGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpRelevantDocGroup != 0) {
                    try {
                        entEmpRelevantDocGroup = PstEmpRelevantDocGroup.fetchExc(oidEmpRelevantDocGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpRelevantDocGroup != 0) {
                    try {
                        long oid = PstEmpRelevantDocGroup.deleteExc(oidEmpRelevantDocGroup);
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
