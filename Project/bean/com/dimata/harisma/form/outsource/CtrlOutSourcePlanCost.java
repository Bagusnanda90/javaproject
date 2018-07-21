/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

/**
 *
 * @author dimata005
 */
import com.dimata.harisma.entity.outsource.OutSourcePlanCost;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanCost;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation;
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import java.util.Vector;
/*
 Description : Controll OutSourcePlanCost
 Date : Mon Sep 14 2015
 Author : opie-eyek
 */

public class CtrlOutSourcePlanCost extends Control implements I_Language {

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
    private OutSourcePlanCost entOutSourcePlanCost;
    private PstOutSourcePlanCost pstOutSourcePlanCost;
    private FrmOutSourcePlanCost frmOutSourcePlanCost;
    int language = LANGUAGE_DEFAULT;

    public CtrlOutSourcePlanCost(HttpServletRequest request) {
        msgString = "";
        entOutSourcePlanCost = new OutSourcePlanCost();
        try {
            pstOutSourcePlanCost = new PstOutSourcePlanCost(0);
        } catch (Exception e) {;
        }
        frmOutSourcePlanCost = new FrmOutSourcePlanCost(request, entOutSourcePlanCost);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmOutSourcePlanCost.addError(FrmOutSourcePlanCost.FRM_FIELD_OUTSOURCECOSTID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public OutSourcePlanCost getOutSourcePlanCost() {
        return entOutSourcePlanCost;
    }

    public FrmOutSourcePlanCost getForm() {
        return frmOutSourcePlanCost;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int actionSubmit(long oidOutSourcePlan, long oidOutMasterCost) {
        Vector<OutSourcePlanCost> vCost = this.frmOutSourcePlanCost.requestEntityObjectMultiple(oidOutSourcePlan, oidOutMasterCost);
        int iResult = RSLT_OK;
        if (vCost != null && vCost.size() > 0) {
            try { // delete previuos data
                String sql = "delete from " + PstOutSourcePlanCost.TBL_OUTSOURCEPLANCOST
                        + " where " + PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCEPLANLOCID] + " IN ( SELECT "+ PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID]+" FROM "+PstOutSourcePlanLocation.TBL_OUTSOURCEPLANLOCATION +" l WHERE l."+PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"="+ oidOutSourcePlan +")"  
                        + " AND " + PstOutSourcePlanCost.fieldNames[PstOutSourcePlanCost.FLD_OUTSOURCECOSTID] + "=" + oidOutMasterCost;
                PstOutSourcePlanCost.delete(sql);
            } catch (Exception exc) {
                System.out.println(exc);
            }

            for (int idx = 0; idx < vCost.size(); idx++) {
                try {
                    OutSourcePlanCost cost = vCost.get(idx);
                    PstOutSourcePlanCost.insertExc(cost);
                } catch (Exception exc) {
                    System.out.println(exc);
                    iResult = RSLT_UNKNOWN_ERROR;
                }
            }

        }
        return iResult;
    }

    public int action(int cmd, long oidOutSourcePlanCost) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidOutSourcePlanCost != 0) {
                    try {
                        entOutSourcePlanCost = PstOutSourcePlanCost.fetchExc(oidOutSourcePlanCost);
                    } catch (Exception exc) {
                    }
                }

                frmOutSourcePlanCost.requestEntityObject(entOutSourcePlanCost);

                if (frmOutSourcePlanCost.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entOutSourcePlanCost.getOID() == 0) {
                    try {
                        long oid = pstOutSourcePlanCost.insertExc(this.entOutSourcePlanCost);
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
                        long oid = pstOutSourcePlanCost.updateExc(this.entOutSourcePlanCost);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidOutSourcePlanCost != 0) {
                    try {
                        entOutSourcePlanCost = PstOutSourcePlanCost.fetchExc(oidOutSourcePlanCost);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidOutSourcePlanCost != 0) {
                    try {
                        entOutSourcePlanCost = PstOutSourcePlanCost.fetchExc(oidOutSourcePlanCost);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidOutSourcePlanCost != 0) {
                    try {
                        long oid = PstOutSourcePlanCost.deleteExc(oidOutSourcePlanCost);
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
