/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author	 : Ari_20110930
 * @version	 : -
 */
/*******************************************************************
 * Class Description 	: CtrlFamRelation
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Wiweka
 */
/* java package */
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.employee.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;

public class CtrlFamRelation extends Control implements I_Language {

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
    private FamRelation famRelation;
    private PstFamRelation pstFamRelation;
    private FrmFamRelation frmFamRelation;
    int language = LANGUAGE_DEFAULT;

    public CtrlFamRelation(HttpServletRequest request) {
        msgString = "";
        famRelation = new FamRelation();
        try {
            pstFamRelation = new PstFamRelation(0);
        } catch (Exception e) {
            ;
        }
        frmFamRelation = new FrmFamRelation(request, famRelation);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmFamRelation.addError(frmFamRelation.FRM_FIELD_FAMILY_RELATION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public FamRelation getFamRelation() {
        return famRelation;
    }

    public FrmFamRelation getForm() {
        return frmFamRelation;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidFamRelation) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                String prevFamRelation = "";
                if (oidFamRelation != 0) {
                    try {
                        famRelation = PstFamRelation.fetchExc(oidFamRelation);
                        prevFamRelation = famRelation.getFamRelation();
                    } catch (Exception exc) {
                    }
                }

                frmFamRelation.requestEntityObject(famRelation);

                if (frmFamRelation.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (famRelation.getOID() == 0) {
                    try {
                        long oid = pstFamRelation.insertExc(this.famRelation);
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
                        long oid = pstFamRelation.updateExc(this.famRelation);

                        PstFamilyMember.updateFamRelation(prevFamRelation, this.famRelation.getFamRelation());
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidFamRelation != 0) {
                    try {
                        famRelation = PstFamRelation.fetchExc(oidFamRelation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidFamRelation != 0) {
                    try {
                        if (PstFamRelation.checkMaster(oidFamRelation)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }
                        famRelation = PstFamRelation.fetchExc(oidFamRelation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidFamRelation != 0) {

                    try {
                        famRelation = PstFamRelation.fetchExc(oidFamRelation);
                        prevFamRelation = famRelation.getFamRelation();
                    } catch (Exception exc) {
                    }


                    try {
                        boolean inUseFamRelation = PstFamilyMember.existFamRelation(famRelation.getFamRelation());
                        //long oid = PstFamRelation.deleteExc(oidFamRelation);
                        if (!inUseFamRelation) {
                            long oid = PstFamRelation.deleteExc(oidFamRelation);
                            if (oid != 0) {
                                msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                excCode = RSLT_OK;
                            } else {
                                msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                excCode = RSLT_FORM_INCOMPLETE;
                            }
                        } else {
                            msgString = "Family Relation in use in Family Member Data";
                            excCode = RSLT_EST_CODE_EXIST;
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
