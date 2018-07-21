/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutsrcCostProv;
import com.dimata.harisma.entity.outsource.OutsrcCostProvDetail;
import com.dimata.harisma.entity.outsource.PstOutsrcCostProv;
import com.dimata.harisma.entity.outsource.PstOutsrcCostProvDetail;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class CtrlOutsrcCostProv extends Control implements I_Language {

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
    private OutsrcCostProv entOutsrcCostProv;
    private PstOutsrcCostProv pstOutsrcCostProv;
    private FrmOutsrcCostProv frmOutsrcCostProv;
    int language = LANGUAGE_DEFAULT;
    HttpServletRequest request = null;

    public CtrlOutsrcCostProv(HttpServletRequest request) {
        msgString = "";
        entOutsrcCostProv = new OutsrcCostProv();
        this.request = request;
        try {
            pstOutsrcCostProv = new PstOutsrcCostProv(0);
        } catch (Exception e) {;
        }
        frmOutsrcCostProv = new FrmOutsrcCostProv(request, entOutsrcCostProv);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmOutsrcCostProv.addError(frmOutsrcCostProv.FRM_FIELD_OUTSRC_COST_PROVIDER_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public OutsrcCostProv getOutsrcCostProv() {
        return entOutsrcCostProv;
    }

    public FrmOutsrcCostProv getForm() {
        return frmOutsrcCostProv;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidOutsrcCostProv, Vector vMaster ) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        
     
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidOutsrcCostProv != 0) {
                    try {
                        entOutsrcCostProv = PstOutsrcCostProv.fetchExc(oidOutsrcCostProv);
                    } catch (Exception exc) {
                    }
                }
                
               

                frmOutsrcCostProv.requestEntityObject(entOutsrcCostProv);

                if (frmOutsrcCostProv.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entOutsrcCostProv.getOID() == 0) {
                    try {
                        long oid = pstOutsrcCostProv.insertExc(this.entOutsrcCostProv); 
                         CtrlOutsrcCostProvDetail ctrlOutsrcCostProvDetail = new CtrlOutsrcCostProvDetail(this.request);
                         FrmOutsrcCostProvDetail frmOutsrcCostProvDetail = ctrlOutsrcCostProvDetail.getForm();
        
                         Vector vCostProvDetail  = frmOutsrcCostProvDetail.requestEntityObject(oid, vMaster);
                         for(int i=0; i<vCostProvDetail.size(); i++){
                             OutsrcCostProvDetail outsrcCostProvDetail = (OutsrcCostProvDetail)vCostProvDetail.get(i);
                             long oidDetail = PstOutsrcCostProvDetail.insertExc(outsrcCostProvDetail);
                         }
                             
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
                        long oid = pstOutsrcCostProv.updateExc(this.entOutsrcCostProv);
                        
                        CtrlOutsrcCostProvDetail ctrlOutsrcCostProvDetail = new CtrlOutsrcCostProvDetail(this.request);
                         FrmOutsrcCostProvDetail frmOutsrcCostProvDetail = ctrlOutsrcCostProvDetail.getForm();
                         
                         //long oidDetail = PstOutsrcCostProvDetail.getOid("")
        
                         Vector vCostProvDetail  = frmOutsrcCostProvDetail.requestEntityObject(oid, vMaster);
                         for(int i=0; i<vCostProvDetail.size(); i++){
                             OutsrcCostProvDetail outsrcCostProvDetail = (OutsrcCostProvDetail)vCostProvDetail.get(i);
                             
                             long oidD = PstOutsrcCostProvDetail.updateExc(outsrcCostProvDetail);
                         }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidOutsrcCostProv != 0) {
                    try {
                        entOutsrcCostProv = PstOutsrcCostProv.fetchExc(oidOutsrcCostProv);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidOutsrcCostProv != 0) {
                    try {
                        entOutsrcCostProv = PstOutsrcCostProv.fetchExc(oidOutsrcCostProv);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidOutsrcCostProv != 0) {
                    try {
                        //delete cost prov detail
                        Vector listCostProvDetail = PstOutsrcCostProvDetail.list(0, 0, "OUTSRC_COST_PROVIDER_ID="+oidOutsrcCostProv, "");
                        for(int i=0;i < listCostProvDetail.size(); i++){
                            OutsrcCostProvDetail outsrcCostProvDetail = (OutsrcCostProvDetail)listCostProvDetail.get(i);
                            long oidDet = PstOutsrcCostProv.deleteExc(outsrcCostProvDetail.getOID());
                        }
                        
                        long oid = PstOutsrcCostProv.deleteExc(oidOutsrcCostProv);
                        
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
