/* 
 * Ctrl Name  		:  CtrlLevel.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.masterdata;

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
import com.dimata.harisma.entity.masterdata.*;
import java.util.Vector;

public class CtrlInformationHrd extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Name Information sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Name Information alredy exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private InformationHrd informationHrd;
    private PstInformationHrd pstInformationHrd;
    private FrmInformationHrd frmInformationHrd;
    int language = LANGUAGE_FOREIGN;

    public CtrlInformationHrd(HttpServletRequest request) {
        msgString = "";
        informationHrd = new InformationHrd();
        try {
            pstInformationHrd = new PstInformationHrd(0);
        } catch (Exception e) {;
        }
        frmInformationHrd = new FrmInformationHrd(request, informationHrd);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmInformationHrd.addError(frmInformationHrd.FRM_FIELD_INFORMATION_HRD_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public InformationHrd getInformationHrd() {
        return informationHrd;
    }

    public FrmInformationHrd getForm() {
        return frmInformationHrd;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidInfoHrd) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidInfoHrd != 0) {
                    try {
                        informationHrd = PstInformationHrd.fetchExc(oidInfoHrd);
                    } catch (Exception exc) {
                    }
                }

                informationHrd.setOID(oidInfoHrd);
                frmInformationHrd.requestEntityObject(informationHrd);

                if (informationHrd != null && informationHrd.getDtStartInfo() != null && informationHrd.getDtEndInfo() != null) {
                        Vector ovLst = PstInformationHrd.listInformationOverlap(informationHrd.getOID(),informationHrd.getDtStartInfo(), informationHrd.getDtEndInfo(),"");
                        if (ovLst != null && ovLst.size() > 0) {
                            msgString = resultText[language][RSLT_EST_CODE_EXIST] + " " + informationHrd.getNamaInformation() + " please check other Information  on the same range:";
                            for (int idx = 0; idx < ovLst.size(); idx++) {
                                InformationHrd objInformationHrd = (InformationHrd) ovLst.get(idx);
                                msgString = msgString + " <a href=\"javascript:openInformationOverlap(\'" + objInformationHrd.getOID() + "\');\">" + objInformationHrd.getNamaInformation() + "</a> ; ";
                            }
                            return RSLT_EST_CODE_EXIST;
                        }
                    
                }
                if (frmInformationHrd.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (informationHrd.getOID() == 0) {
                    try {
                        long oid = pstInformationHrd.insertExc(this.informationHrd);
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
                        long oid = pstInformationHrd.updateExc(this.informationHrd);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidInfoHrd != 0) {
                    try {
                        informationHrd = PstInformationHrd.fetchExc(oidInfoHrd);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidInfoHrd != 0) {
                    try {
                        if (PstInformationHrd.checkMaster(oidInfoHrd)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        informationHrd = PstInformationHrd.fetchExc(oidInfoHrd);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidInfoHrd != 0) {
                    try {
                        long oid = PstInformationHrd.deleteExc(oidInfoHrd);
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
