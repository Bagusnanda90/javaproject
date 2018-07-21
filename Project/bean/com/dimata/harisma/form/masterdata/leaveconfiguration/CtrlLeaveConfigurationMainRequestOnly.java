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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailDepartementRequestOnly;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPositionRequestOnly;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationMainRequestLeaveOnly;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartementRequestOnly;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPositionRequestOnly;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMainRequestOnly;
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

import java.util.Vector;

public class CtrlLeaveConfigurationMainRequestOnly extends Control implements I_Language{
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
    private LeaveConfigurationMainRequestLeaveOnly leaveConfigurationMainRequestLeaveOnly;
    private PstLeaveConfigurationMainRequestOnly pstLeaveConfigurationMainRequestOnly;
    private FrmLeaveConfigurationMainRequestOnly frmLeaveConfigurationMainRequestOnly;
    
    private LeaveConfigurationDetailDepartementRequestOnly leaveConfigurationDetailDepartementRequestOnly;
    private FrmLeaveConfigurationDepartmentRequestOnly frmLeaveConfigurationDepartmentRequestOnly;
    private LeaveConfigurationDetailPositionRequestOnly leaveConfigurationDetailPositionRequestOnly;
    private FrmLeaveConfigurationPositionRequestOnly frmLeaveConfigurationPositionRequestOnly;
    int language = LANGUAGE_DEFAULT;

    public CtrlLeaveConfigurationMainRequestOnly(HttpServletRequest request) {
        msgString = "";
        leaveConfigurationMainRequestLeaveOnly = new LeaveConfigurationMainRequestLeaveOnly();
        
        leaveConfigurationDetailDepartementRequestOnly = new LeaveConfigurationDetailDepartementRequestOnly();
        leaveConfigurationDetailPositionRequestOnly = new LeaveConfigurationDetailPositionRequestOnly();
        try {
            pstLeaveConfigurationMainRequestOnly = new PstLeaveConfigurationMainRequestOnly(0);
        } catch (Exception e) {
            ;
        }
        frmLeaveConfigurationMainRequestOnly = new FrmLeaveConfigurationMainRequestOnly(request, leaveConfigurationMainRequestLeaveOnly);
        
        frmLeaveConfigurationDepartmentRequestOnly = new FrmLeaveConfigurationDepartmentRequestOnly(request, leaveConfigurationDetailDepartementRequestOnly);
        
        frmLeaveConfigurationPositionRequestOnly = new FrmLeaveConfigurationPositionRequestOnly(request, leaveConfigurationDetailPositionRequestOnly);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmLeaveConfigurationMainRequestOnly.addError(frmLeaveConfigurationMainRequestOnly.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID_REQUEST_ONLY, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public LeaveConfigurationMainRequestLeaveOnly getLeaveConfigurationMain() {
        return leaveConfigurationMainRequestLeaveOnly;
    }
    
    public LeaveConfigurationDetailDepartementRequestOnly getLeaveConfigurationDetailDepartment() {
        return leaveConfigurationDetailDepartementRequestOnly;
    }
    
    public LeaveConfigurationDetailPositionRequestOnly getLeaveConfigurationDetailPosition() {
        return leaveConfigurationDetailPositionRequestOnly;
    }

    public FrmLeaveConfigurationMainRequestOnly getForm() {
        return frmLeaveConfigurationMainRequestOnly;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidLeaveConfigurationMain) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;
            case Command.GOTO:
                 frmLeaveConfigurationMainRequestOnly.requestEntityObject(leaveConfigurationMainRequestLeaveOnly);
                 leaveConfigurationMainRequestLeaveOnly.setOID(oidLeaveConfigurationMain);
                 frmLeaveConfigurationDepartmentRequestOnly.requestEntityObjectMultiple(leaveConfigurationDetailDepartementRequestOnly);
                 leaveConfigurationDetailDepartementRequestOnly.setLeaveConfigurationMainIdRequestOnly(oidLeaveConfigurationMain);
                 frmLeaveConfigurationPositionRequestOnly.requestEntityObject(leaveConfigurationDetailPositionRequestOnly); 
                 leaveConfigurationDetailPositionRequestOnly.setLeaveConfigurationMainIdRequestOnly(oidLeaveConfigurationMain);
                break;
            case Command.SAVE:
                if (oidLeaveConfigurationMain != 0) {
                    try {
                        leaveConfigurationMainRequestLeaveOnly = PstLeaveConfigurationMainRequestOnly.fetchExc(oidLeaveConfigurationMain);
                    } catch (Exception exc) {
                    }
                }

                frmLeaveConfigurationMainRequestOnly.requestEntityObject(leaveConfigurationMainRequestLeaveOnly);
                frmLeaveConfigurationDepartmentRequestOnly.requestEntityObjectMultiple(leaveConfigurationDetailDepartementRequestOnly);
                frmLeaveConfigurationPositionRequestOnly.requestEntityObject(leaveConfigurationDetailPositionRequestOnly); 
                 
                if (frmLeaveConfigurationMainRequestOnly.errorSize() > 0 || leaveConfigurationDetailDepartementRequestOnly.getDepartementIds()==null || leaveConfigurationDetailDepartementRequestOnly.getDepartementIds().length==0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (leaveConfigurationMainRequestLeaveOnly.getOID() == 0) {
                    try {
                        long oid = pstLeaveConfigurationMainRequestOnly.insertExc(this.leaveConfigurationMainRequestLeaveOnly);

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
                        long oid = pstLeaveConfigurationMainRequestOnly.updateExc(this.leaveConfigurationMainRequestLeaveOnly);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                FrmLeaveConfigurationDepartmentRequestOnly frmLeaveConfigurationDepartmentRequestOnly = new FrmLeaveConfigurationDepartmentRequestOnly();
                FrmLeaveConfigurationPositionRequestOnly frmLeaveConfigurationPositionRequestOnly = new FrmLeaveConfigurationPositionRequestOnly();
                if (rsCode == RSLT_OK && leaveConfigurationMainRequestLeaveOnly != null && leaveConfigurationMainRequestLeaveOnly.getOID() != 0) {
                    Vector vGroupDepartment = frmLeaveConfigurationDepartmentRequestOnly.getGroupDepartementId(leaveConfigurationMainRequestLeaveOnly.getOID(),leaveConfigurationDetailDepartementRequestOnly);
                    //Vector vGroupPosition = frmLeaveConfigurationPosition.getGroupPositionId(leaveConfigurationMain.getOID()); 

                    if(frmLeaveConfigurationDepartmentRequestOnly!=null && frmLeaveConfigurationPositionRequestOnly!=null && frmLeaveConfigurationDepartmentRequestOnly.getMessage()!=null && frmLeaveConfigurationDepartmentRequestOnly.getMessage().length()>0 || (frmLeaveConfigurationPositionRequestOnly.getMessage()!=null && frmLeaveConfigurationPositionRequestOnly.getMessage().length()>0 )){
                         msgString = FRMMessage.getMsg(FRMMessage.ERR_SAVED) + " Because " + (frmLeaveConfigurationPositionRequestOnly.getMessage()==null || frmLeaveConfigurationPositionRequestOnly.getMessage().length()==0?frmLeaveConfigurationDepartmentRequestOnly.getMessage():(frmLeaveConfigurationDepartmentRequestOnly.getMessage()+" AND "+ frmLeaveConfigurationPositionRequestOnly.getMessage()));
                        return RSLT_UNKNOWN_ERROR;
                    }
                    if (PstLeaveConfigurationDetailDepartementRequestOnly.setDetailConfigurationDepartment(this.leaveConfigurationMainRequestLeaveOnly.getOID(), vGroupDepartment) && PstLeaveConfigurationDetailPositionRequestOnly.setDetailConfigurationPosition(this.leaveConfigurationMainRequestLeaveOnly.getOID(), leaveConfigurationDetailPositionRequestOnly)) {
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                    } else {
                        msgString = FRMMessage.getErr(FRMMessage.ERR_UNKNOWN);
                        excCode = 0;
                    }
                }
                break;

            case Command.EDIT:
                if (oidLeaveConfigurationMain != 0) {
                    try {
                        leaveConfigurationMainRequestLeaveOnly = PstLeaveConfigurationMainRequestOnly.fetchExc(oidLeaveConfigurationMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidLeaveConfigurationMain != 0) {
                    try {
                        leaveConfigurationMainRequestLeaveOnly = PstLeaveConfigurationMainRequestOnly.fetchExc(oidLeaveConfigurationMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidLeaveConfigurationMain != 0) {
                    try {
                        //long oid = PstLeaveConfigurationMain.deleteExc(oidLeaveConfigurationMain);
                        long oid = PstLeaveConfigurationMainRequestOnly.deleteExc(oidLeaveConfigurationMain);
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
