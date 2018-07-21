/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.PstWarningReprimandAyat;
import com.dimata.harisma.entity.masterdata.WarningReprimandAyat;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import static com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Hendra McHen
 */
public class CtrlWarningReprimandAyat extends Control implements I_Language {

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
    
    private WarningReprimandAyat ayat;
    private PstWarningReprimandAyat pstWarningReprimandAyat;
    private FrmWarningReprimandAyat frmWarningReprimandAyat;
    int language = LANGUAGE_DEFAULT;

    public CtrlWarningReprimandAyat(HttpServletRequest request) {
        msgString = "";
        ayat = new WarningReprimandAyat();
        try {
            pstWarningReprimandAyat = new PstWarningReprimandAyat(0);
        } catch (Exception e) {
            ;
        }
        frmWarningReprimandAyat = new FrmWarningReprimandAyat(request, ayat);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmWarningReprimandAyat.addError(frmWarningReprimandAyat.FRM_FIELD_AYAT_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public WarningReprimandAyat getWarningReprimandAyat() {
        return ayat;
    }

    public FrmWarningReprimandAyat getForm() {
        return frmWarningReprimandAyat;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidAyat) {       
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;
            case Command.SAVE:
                if (oidAyat != 0) {
                    try {
                        ayat = PstWarningReprimandAyat.fetchExc(oidAyat);
                    } catch (Exception exc) {
                    }
                }
                frmWarningReprimandAyat.requestEntityObject(ayat);
                if (frmWarningReprimandAyat.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                if (ayat.getOID() == 0) {
                    try {
                        long oid = pstWarningReprimandAyat.insertExc(this.ayat);
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
                        long oid = pstWarningReprimandAyat.updateExc(this.ayat);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
            case Command.EDIT:

                if (oidAyat != 0) {

                    try {

                        ayat = PstWarningReprimandAyat.fetchExc(oidAyat);

                    } catch (DBException dbexc) {

                        excCode = dbexc.getErrorCode();

                        msgString = getSystemMessage(excCode);

                    } catch (Exception exc) {

                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

                    }

                }

                break;



            case Command.ASK:

                if (oidAyat != 0) {

                    try {

                        ayat = PstWarningReprimandAyat.fetchExc(oidAyat);

                    } catch (DBException dbexc) {

                        excCode = dbexc.getErrorCode();

                        msgString = getSystemMessage(excCode);

                    } catch (Exception exc) {

                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

                    }

                }

                break;
            case Command.DELETE:
                if (oidAyat != 0) {

                    try {
                        long oid = PstWarningReprimandAyat.deleteExc(oidAyat);
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