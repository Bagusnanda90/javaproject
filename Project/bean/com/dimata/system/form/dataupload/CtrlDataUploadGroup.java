/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.form.dataupload;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.system.entity.dataupload.DataUploadGroup;
import com.dimata.system.entity.dataupload.PstDataUploadGroup;
import com.dimata.system.form.dataupload.FrmDataUploadGroup;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class CtrlDataUploadGroup extends Control implements I_Language {

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
    private DataUploadGroup entDataUploadGroup;
    private PstDataUploadGroup pstDataUploadGroup;
    private FrmDataUploadGroup frmDataUploadGroup;
    int language = LANGUAGE_DEFAULT;

    public CtrlDataUploadGroup(HttpServletRequest request) {
        msgString = "";
        entDataUploadGroup = new DataUploadGroup();
        try {
            pstDataUploadGroup = new PstDataUploadGroup(0);
        } catch (Exception e) {;
        }
        frmDataUploadGroup = new FrmDataUploadGroup(request, entDataUploadGroup);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDataUploadGroup.addError(frmDataUploadGroup.FRM_FIELD_DATA_GROUP_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public DataUploadGroup getDataUploadGroup() {
        return entDataUploadGroup;
    }

    public FrmDataUploadGroup getForm() {
        return frmDataUploadGroup;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidDataUploadGroup) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidDataUploadGroup != 0) {
                    try {
                        entDataUploadGroup = PstDataUploadGroup.fetchExc(oidDataUploadGroup);
                    } catch (Exception exc) {
                    }
                }

                frmDataUploadGroup.requestEntityObject(entDataUploadGroup);

                if (frmDataUploadGroup.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entDataUploadGroup.getOID() == 0) {
                    try {
                        long oid = pstDataUploadGroup.insertExc(this.entDataUploadGroup);
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
                        long oid = pstDataUploadGroup.updateExc(this.entDataUploadGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidDataUploadGroup != 0) {
                    try {
                        entDataUploadGroup = PstDataUploadGroup.fetchExc(oidDataUploadGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidDataUploadGroup != 0) {
                    try {
                        entDataUploadGroup = PstDataUploadGroup.fetchExc(oidDataUploadGroup);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidDataUploadGroup != 0) {
                    try {
                        long oid = PstDataUploadGroup.deleteExc(oidDataUploadGroup);
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
