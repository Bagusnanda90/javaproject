/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import com.dimata.harisma.entity.employee.PositionCandidateEmp;
import com.dimata.harisma.entity.employee.PstPositionCandidateEmp;
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.masterdata.*;

/*
 Description : Controll PositionCandidateEmp
 Date : Wed Feb 11 2015
 Author : Hendra Putu
 */
public class CtrlPositionCandidateEmp extends Control implements I_Language {

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
    private PositionCandidateEmp entPositionCandidateEmp;
    private PstPositionCandidateEmp pstPositionCandidateEmp;
    private FrmPositionCandidateEmp frmPositionCandidateEmp;
    int language = LANGUAGE_DEFAULT;

    public CtrlPositionCandidateEmp(HttpServletRequest request) {
        msgString = "";
        entPositionCandidateEmp = new PositionCandidateEmp();
        try {
            pstPositionCandidateEmp = new PstPositionCandidateEmp(0);
        } catch (Exception e) {;
        }
        frmPositionCandidateEmp = new FrmPositionCandidateEmp(request, entPositionCandidateEmp);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPositionCandidateEmp.addError(frmPositionCandidateEmp.FRM_FIELD_POS_CANDIDATE_EMP_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PositionCandidateEmp getPositionCandidateEmp() {
        return entPositionCandidateEmp;
    }

    public FrmPositionCandidateEmp getForm() {
        return frmPositionCandidateEmp;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPositionCandidateEmp) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidPositionCandidateEmp != 0) {
                    try {
                        entPositionCandidateEmp = PstPositionCandidateEmp.fetchExc(oidPositionCandidateEmp);
                    } catch (Exception exc) {
                    }
                }

                frmPositionCandidateEmp.requestEntityObject(entPositionCandidateEmp);

                if (frmPositionCandidateEmp.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entPositionCandidateEmp.getOID() == 0) {
                    try {
                        long oid = pstPositionCandidateEmp.insertExc(this.entPositionCandidateEmp);
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
                        long oid = pstPositionCandidateEmp.updateExc(this.entPositionCandidateEmp);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidPositionCandidateEmp != 0) {
                    try {
                        entPositionCandidateEmp = PstPositionCandidateEmp.fetchExc(oidPositionCandidateEmp);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidPositionCandidateEmp != 0) {
                    try {
                        entPositionCandidateEmp = PstPositionCandidateEmp.fetchExc(oidPositionCandidateEmp);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidPositionCandidateEmp != 0) {
                    try {
                        long oid = PstPositionCandidateEmp.deleteExc(oidPositionCandidateEmp);
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
