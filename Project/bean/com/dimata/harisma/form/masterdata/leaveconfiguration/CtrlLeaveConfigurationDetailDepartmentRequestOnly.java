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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartement;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartementRequestOnly;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartement;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartementRequestOnly;

public class CtrlLeaveConfigurationDetailDepartmentRequestOnly extends Control implements I_Language{
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
    private LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly;
    private PstLeaveConfigurationDetailDepartementRequestOnly pstLeaveConfigurationDetailDepartementRequestOnly;
    private FrmLeaveConfigurationDepartmentRequestOnly frmLeaveConfigurationDepartmentRequestOnly;
    int language = LANGUAGE_DEFAULT;

    public CtrlLeaveConfigurationDetailDepartmentRequestOnly(HttpServletRequest request) {
        msgString = "";
        leaveConfigurationDetailDepartementRequestOnly = new LeaveConfigurationDetailDepartementRequestOnly();
        try {
            pstLeaveConfigurationDetailDepartementRequestOnly = new PstLeaveConfigurationDetailDepartementRequestOnly(0);
        } catch (Exception e) {
            ;
        }
        frmLeaveConfigurationDepartmentRequestOnly = new FrmLeaveConfigurationDepartmentRequestOnly(request, leaveConfigurationDetailDepartementRequestOnly);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmLeaveConfigurationDepartmentRequestOnly.addError(frmLeaveConfigurationDepartmentRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public LeaveConfigurationDetailDepartementRequestOnly getConfigurationDetailDepartement() {
        return leaveConfigurationDetailDepartementRequestOnly;
    }

    public FrmLeaveConfigurationDepartmentRequestOnly getForm() {
        return frmLeaveConfigurationDepartmentRequestOnly;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidLeaveConfigMain) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;
            case Command.GOTO:
                frmLeaveConfigurationDepartmentRequestOnly.requestEntityObjectMultiple(leaveConfigurationDetailDepartementRequestOnly);
                break;
            case Command.SAVE:
                if (oidLeaveConfigMain != 0) {
                    try {
                        leaveConfigurationDetailDepartementRequestOnly = PstLeaveConfigurationDetailDepartementRequestOnly.fetchExc(oidLeaveConfigMain);
                    } catch (Exception exc) {
                    }
                }

                frmLeaveConfigurationDepartmentRequestOnly.requestEntityObject(leaveConfigurationDetailDepartementRequestOnly);

                if (frmLeaveConfigurationDepartmentRequestOnly.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (leaveConfigurationDetailDepartementRequestOnly.getOID() == 0) {
                    try {
                        long oid = pstLeaveConfigurationDetailDepartementRequestOnly.insertExc(this.leaveConfigurationDetailDepartementRequestOnly);

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
                        long oid = pstLeaveConfigurationDetailDepartementRequestOnly.updateExc(this.leaveConfigurationDetailDepartementRequestOnly);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidLeaveConfigMain != 0) {
                    try {
                        leaveConfigurationDetailDepartementRequestOnly = PstLeaveConfigurationDetailDepartementRequestOnly.fetchExc(oidLeaveConfigMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidLeaveConfigMain != 0) {
                    try {
                        leaveConfigurationDetailDepartementRequestOnly = PstLeaveConfigurationDetailDepartementRequestOnly.fetchExc(oidLeaveConfigMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidLeaveConfigMain != 0) {
                    try {
                        long oid = PstLeaveConfigurationDetailDepartement.deleteExc(oidLeaveConfigMain);
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
