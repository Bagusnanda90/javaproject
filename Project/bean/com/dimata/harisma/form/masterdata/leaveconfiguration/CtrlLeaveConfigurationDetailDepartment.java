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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartement;
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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartement;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartement;

public class CtrlLeaveConfigurationDetailDepartment extends Control implements I_Language{
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
    private LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement;
    private PstLeaveConfigurationDetailDepartement pstLeaveConfigurationDetailDepartement;
    private FrmLeaveConfigurationDepartment frmLeaveConfigurationDepartment;
    int language = LANGUAGE_DEFAULT;

    public CtrlLeaveConfigurationDetailDepartment(HttpServletRequest request) {
        msgString = "";
        leaveConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
        try {
            pstLeaveConfigurationDetailDepartement = new PstLeaveConfigurationDetailDepartement(0);
        } catch (Exception e) {
            ;
        }
        frmLeaveConfigurationDepartment = new FrmLeaveConfigurationDepartment(request, leaveConfigurationDetailDepartement);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmLeaveConfigurationDepartment.addError(frmLeaveConfigurationDepartment.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public LeaveConfigurationDetailDepartement getConfigurationDetailDepartement() {
        return leaveConfigurationDetailDepartement;
    }

    public FrmLeaveConfigurationDepartment getForm() {
        return frmLeaveConfigurationDepartment;
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
                frmLeaveConfigurationDepartment.requestEntityObjectMultiple(leaveConfigurationDetailDepartement);
                break;
            case Command.SAVE:
                if (oidLeaveConfigMain != 0) {
                    try {
                        leaveConfigurationDetailDepartement = PstLeaveConfigurationDetailDepartement.fetchExc(oidLeaveConfigMain);
                    } catch (Exception exc) {
                    }
                }

                frmLeaveConfigurationDepartment.requestEntityObject(leaveConfigurationDetailDepartement);

                if (frmLeaveConfigurationDepartment.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (leaveConfigurationDetailDepartement.getOID() == 0) {
                    try {
                        long oid = pstLeaveConfigurationDetailDepartement.insertExc(this.leaveConfigurationDetailDepartement);

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
                        long oid = pstLeaveConfigurationDetailDepartement.updateExc(this.leaveConfigurationDetailDepartement);
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
                        leaveConfigurationDetailDepartement = PstLeaveConfigurationDetailDepartement.fetchExc(oidLeaveConfigMain);
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
                        leaveConfigurationDetailDepartement = PstLeaveConfigurationDetailDepartement.fetchExc(oidLeaveConfigMain);
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
