/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.PstPublicLeave;
import com.dimata.harisma.entity.masterdata.PublicLeave;
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
public class CtrlPublicLeave extends Control implements I_Language{
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
    private PublicLeave objPublicLeave;
    private PstPublicLeave objPstPublicLeave;
    private FrmPublicLeave objFrmPublicLeave;
    int language = LANGUAGE_FOREIGN;

    public CtrlPublicLeave(HttpServletRequest request) {
        msgString = "";
        objPublicLeave = new PublicLeave();
        try {
            objPstPublicLeave = new PstPublicLeave(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objFrmPublicLeave = new FrmPublicLeave(request, objPublicLeave);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.objFrmPublicLeave.addError(FrmPublicLeave.FRM_FIELD_PUBLIC_LEAVE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PublicLeave getPublicLeave() {
        return objPublicLeave;
    }

    public FrmPublicLeave getForm() {
        return objFrmPublicLeave;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int iCmd, long lOidPublicLeaveDetail) {
        int iRsCode = RSLT_OK;
        switch (iCmd) {
            case Command.ADD:
                break;
            case Command.SAVE:
                iRsCode = actionSave(lOidPublicLeaveDetail);
                break;
            case Command.EDIT:
                iRsCode = actionEditOrAsk(lOidPublicLeaveDetail);
                break;
            case Command.ASK:
                iRsCode = actionEditOrAsk(lOidPublicLeaveDetail);
                break;
            case Command.DELETE:
                iRsCode = actionDelete(lOidPublicLeaveDetail);
                break;
            default:
                break;
        }

        return iRsCode;
    }

    private int actionSave(long lOidPublicLeave) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidPublicLeave != 0) {
            try {
                objPublicLeave = PstPublicLeave.fetchExc(lOidPublicLeave);
            } catch (DBException dbe) {
                dbe.printStackTrace();
            }
        }
        objFrmPublicLeave.requestEntityObject(objPublicLeave);

        if (objPublicLeave.getFlagSch()!=0 && objFrmPublicLeave.errorSize() > 0) {
            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
            return RSLT_FORM_INCOMPLETE;
        }

        if (objPublicLeave.getOID() == 0) {
            try {
                long insc = PstPublicLeave.insertExc(objPublicLeave);
                if(insc!=0){
                 msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                }
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        } else {
            try {
               long upd = PstPublicLeave.updateExc(objPublicLeave);
                if(upd!=0){
                 msgString = FRMMessage.getMsg(FRMMessage.MSG_UPDATED);
                }
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }

        return excCode;
    }

    public int actionEditOrAsk(long lOidPublicLeave) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidPublicLeave != 0) {
            try {
                objPublicLeave = PstPublicLeave.fetchExc(lOidPublicLeave);
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }
        return excCode;
    }

    public int actionDelete(long lOidPublicLeave) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidPublicLeave != 0) {
            try {
                long oid = PstPublicLeave.deleteExc(lOidPublicLeave);
                if (oid != 0) {
                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                    excCode = RSLT_OK;
                } else {
                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                    excCode = RSLT_FORM_INCOMPLETE;
                }
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }
        return excCode;
    }
}
