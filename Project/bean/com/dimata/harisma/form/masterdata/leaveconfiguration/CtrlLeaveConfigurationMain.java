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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationDetailPosition;
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
import com.dimata.harisma.entity.masterdata.leaveconfiguration.LeaveConfigurationMain;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailDepartement;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationDetailPosition;
import com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain;
import java.util.Vector;

public class CtrlLeaveConfigurationMain extends Control implements I_Language{
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
    private LeaveConfigurationMain leaveConfigurationMain;
    private PstLeaveConfigurationMain pstLeaveConfigurationMain;
    private FrmLeaveConfigurationMain frmLeaveConfigurationMain;
    
    private LeaveConfigurationDetailDepartement leaveConfigurationDetailDepartement;
    private FrmLeaveConfigurationDepartment frmLeaveConfigurationDepartment;
    private LeaveConfigurationDetailPosition leaveConfigurationDetailPosition;
    private FrmLeaveConfigurationPosition frmLeaveConfigurationPosition;
    int language = LANGUAGE_DEFAULT;

    public CtrlLeaveConfigurationMain(HttpServletRequest request) {
        msgString = "";
        leaveConfigurationMain = new LeaveConfigurationMain();
        
        leaveConfigurationDetailDepartement = new LeaveConfigurationDetailDepartement();
        leaveConfigurationDetailPosition = new LeaveConfigurationDetailPosition();
        try {
            pstLeaveConfigurationMain = new PstLeaveConfigurationMain(0);
        } catch (Exception e) {
            ;
        }
        frmLeaveConfigurationMain = new FrmLeaveConfigurationMain(request, leaveConfigurationMain);
        
        frmLeaveConfigurationDepartment = new FrmLeaveConfigurationDepartment(request, leaveConfigurationDetailDepartement);
        
        frmLeaveConfigurationPosition = new FrmLeaveConfigurationPosition(request, leaveConfigurationDetailPosition);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmLeaveConfigurationMain.addError(frmLeaveConfigurationMain.FRM_FIELD_LEAVE_CONFIGURATION_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public LeaveConfigurationMain getLeaveConfigurationMain() {
        return leaveConfigurationMain;
    }
    
    public LeaveConfigurationDetailDepartement getLeaveConfigurationDetailDepartment() {
        return leaveConfigurationDetailDepartement;
    }
    
    public LeaveConfigurationDetailPosition getLeaveConfigurationDetailPosition() {
        return leaveConfigurationDetailPosition;
    }

    public FrmLeaveConfigurationMain getForm() {
        return frmLeaveConfigurationMain;
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
                 frmLeaveConfigurationMain.requestEntityObject(leaveConfigurationMain);
                 leaveConfigurationMain.setOID(oidLeaveConfigurationMain);
                 frmLeaveConfigurationDepartment.requestEntityObjectMultiple(leaveConfigurationDetailDepartement);
                 leaveConfigurationDetailDepartement.setLeaveConfigurationMainId(oidLeaveConfigurationMain);
                 frmLeaveConfigurationPosition.requestEntityObject(leaveConfigurationDetailPosition); 
                 leaveConfigurationDetailPosition.setLeaveConfigurationMainId(oidLeaveConfigurationMain);
                break;
            case Command.SAVE:
                if (oidLeaveConfigurationMain != 0) {
                    try {
                        leaveConfigurationMain = PstLeaveConfigurationMain.fetchExc(oidLeaveConfigurationMain);
                    } catch (Exception exc) {
                    }
                }

                frmLeaveConfigurationMain.requestEntityObject(leaveConfigurationMain);
                frmLeaveConfigurationDepartment.requestEntityObjectMultiple(leaveConfigurationDetailDepartement);
                 frmLeaveConfigurationPosition.requestEntityObject(leaveConfigurationDetailPosition); 
                 
                if (frmLeaveConfigurationMain.errorSize() > 0 || leaveConfigurationDetailDepartement.getDepartementIds()==null || leaveConfigurationDetailDepartement.getDepartementIds().length==0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (leaveConfigurationMain.getOID() == 0) {
                    try {
                        long oid = pstLeaveConfigurationMain.insertExc(this.leaveConfigurationMain);

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
                        long oid = pstLeaveConfigurationMain.updateExc(this.leaveConfigurationMain);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                FrmLeaveConfigurationDepartment frmLeaveConfigurationDepartment = new FrmLeaveConfigurationDepartment();
                FrmLeaveConfigurationPosition frmLeaveConfigurationPosition = new FrmLeaveConfigurationPosition();
                if (rsCode == RSLT_OK && leaveConfigurationMain != null && leaveConfigurationMain.getOID() != 0) {
                    Vector vGroupDepartment = frmLeaveConfigurationDepartment.getGroupDepartementId(leaveConfigurationMain.getOID(),leaveConfigurationDetailDepartement);
                    //Vector vGroupPosition = frmLeaveConfigurationPosition.getGroupPositionId(leaveConfigurationMain.getOID()); 

                    if(frmLeaveConfigurationDepartment!=null && frmLeaveConfigurationPosition!=null && frmLeaveConfigurationDepartment.getMessage()!=null && frmLeaveConfigurationDepartment.getMessage().length()>0 || (frmLeaveConfigurationPosition.getMessage()!=null && frmLeaveConfigurationPosition.getMessage().length()>0 )){
                         msgString = FRMMessage.getMsg(FRMMessage.ERR_SAVED) + " Because " + (frmLeaveConfigurationPosition.getMessage()==null || frmLeaveConfigurationPosition.getMessage().length()==0?frmLeaveConfigurationDepartment.getMessage():(frmLeaveConfigurationDepartment.getMessage()+" AND "+ frmLeaveConfigurationPosition.getMessage()));
                        return RSLT_UNKNOWN_ERROR;
                    }
                    if (PstLeaveConfigurationDetailDepartement.setDetailConfigurationDepartment(this.leaveConfigurationMain.getOID(), vGroupDepartment) && PstLeaveConfigurationDetailPosition.setDetailConfigurationPosition(this.leaveConfigurationMain.getOID(), leaveConfigurationDetailPosition)) {
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
                        leaveConfigurationMain = PstLeaveConfigurationMain.fetchExc(oidLeaveConfigurationMain);
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
                        leaveConfigurationMain = PstLeaveConfigurationMain.fetchExc(oidLeaveConfigurationMain);
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
                        long oid = PstLeaveConfigurationMain.deleteExc(oidLeaveConfigurationMain);
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
