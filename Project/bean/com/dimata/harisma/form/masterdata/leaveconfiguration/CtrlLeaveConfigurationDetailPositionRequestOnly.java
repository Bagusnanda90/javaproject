/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Satrya Ramayu 2014-06-12
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: CtrlCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata.leaveconfiguration;

/**
 *
 * @author Wiweka
 */
/* java package */
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPositionRequestOnly;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPositionRequestOnly;

public class CtrlLeaveConfigurationDetailPositionRequestOnly extends Control implements I_Language{
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
    private LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly;
    private PstLeaveConfigurationDetailPositionRequestOnly pstLeaveConfigurationDetailPositionRequestOnly;
    private FrmLeaveConfigurationPositionRequestOnly frmLeaveConfigurationPositionRequestOnly;
    int language = LANGUAGE_DEFAULT;

    public CtrlLeaveConfigurationDetailPositionRequestOnly(HttpServletRequest request) {
        msgString = "";
        leaveConfigurationDetailPositionRequestOnly = new LeaveConfigurationDetailPositionRequestOnly();
        try {
            pstLeaveConfigurationDetailPositionRequestOnly = new PstLeaveConfigurationDetailPositionRequestOnly(0);
        } catch (Exception e) {
            ;
        }
        frmLeaveConfigurationPositionRequestOnly = new FrmLeaveConfigurationPositionRequestOnly(request, leaveConfigurationDetailPositionRequestOnly);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmLeaveConfigurationPositionRequestOnly.addError(frmLeaveConfigurationPositionRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public LeaveConfigurationDetailPositionRequestOnly getLeaveConfigurationDetailPosition() {
        return leaveConfigurationDetailPositionRequestOnly;
    }

    public FrmLeaveConfigurationPositionRequestOnly getForm() {
        return frmLeaveConfigurationPositionRequestOnly;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCompany) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidCompany != 0) {
                    try {
                        leaveConfigurationDetailPositionRequestOnly = PstLeaveConfigurationDetailPositionRequestOnly.fetchExc(oidCompany);
                    } catch (Exception exc) {
                    }
                }

                frmLeaveConfigurationPositionRequestOnly.requestEntityObject(leaveConfigurationDetailPositionRequestOnly);

                if (frmLeaveConfigurationPositionRequestOnly.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (leaveConfigurationDetailPositionRequestOnly.getOID() == 0) {
                    try {
                        long oid = pstLeaveConfigurationDetailPositionRequestOnly.insertExc(this.leaveConfigurationDetailPositionRequestOnly);

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
                        long oid = pstLeaveConfigurationDetailPositionRequestOnly.updateExc(this.leaveConfigurationDetailPositionRequestOnly);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidCompany != 0) {
                    try {
                        leaveConfigurationDetailPositionRequestOnly = PstLeaveConfigurationDetailPositionRequestOnly.fetchExc(oidCompany);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCompany != 0) {
                    try {
                        leaveConfigurationDetailPositionRequestOnly = PstLeaveConfigurationDetailPositionRequestOnly.fetchExc(oidCompany);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCompany != 0) {
                    try {
                        long oid = PstLeaveConfigurationDetailPosition.deleteExc(oidCompany);
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
