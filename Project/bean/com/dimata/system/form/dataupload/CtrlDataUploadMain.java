/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.form.dataupload;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.system.entity.dataupload.DataUploadDetail;
import com.dimata.system.entity.dataupload.DataUploadMain;
import com.dimata.system.entity.dataupload.PstDataUploadDetail;
import com.dimata.system.entity.dataupload.PstDataUploadMain;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class CtrlDataUploadMain extends Control implements I_Language {

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
    private DataUploadMain entDataUploadMain;
    private PstDataUploadMain pstDataUploadMain;
    private FrmDataUploadMain frmDataUploadMain;
    int language = LANGUAGE_DEFAULT;

    public CtrlDataUploadMain(HttpServletRequest request) {
        msgString = "";
        entDataUploadMain = new DataUploadMain();
        try {
            pstDataUploadMain = new PstDataUploadMain(0);
        } catch (Exception e) {;
        }
        frmDataUploadMain = new FrmDataUploadMain(request, entDataUploadMain);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDataUploadMain.addError(frmDataUploadMain.FRM_FIELD_DATA_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public DataUploadMain getDataUploadMain() {
        return entDataUploadMain;
    }

    public FrmDataUploadMain getForm() {
        return frmDataUploadMain;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidDataUploadMain) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidDataUploadMain != 0) {
                    try {
                        entDataUploadMain = PstDataUploadMain.fetchExc(oidDataUploadMain);
                    } catch (Exception exc) {
                    }
                }

                frmDataUploadMain.requestEntityObject(entDataUploadMain);

                if (frmDataUploadMain.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entDataUploadMain.getOID() == 0) {
                    try {
                        long oid = pstDataUploadMain.insertExc(this.entDataUploadMain);
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
                        long oid = pstDataUploadMain.updateExc(this.entDataUploadMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidDataUploadMain != 0) {
                    try {
                        entDataUploadMain = PstDataUploadMain.fetchExc(oidDataUploadMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidDataUploadMain != 0) {
                    try {
                        entDataUploadMain = PstDataUploadMain.fetchExc(oidDataUploadMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidDataUploadMain != 0) {
                    try {
                        long oid = PstDataUploadMain.deleteExc(oidDataUploadMain);
                        
                        Vector listUpDet = new Vector(1,1);
                        
                        listUpDet = PstDataUploadDetail.list(0, 0, "DATA_MAIN_ID='"+oidDataUploadMain+"'", "");
                        for(int i=0; i < listUpDet.size(); i++){
                            DataUploadDetail dUpDet = (DataUploadDetail)listUpDet.get(i);
                            
                            long oidDet = PstDataUploadDetail.deleteExc(dUpDet.getOID());
                        }
                        
                        
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
