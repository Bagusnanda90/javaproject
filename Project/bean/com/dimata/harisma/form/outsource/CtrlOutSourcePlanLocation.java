/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

/**
 *
 * @author dimata005
 */
//public class CtrlOutSourcePlanLocation {
//}
import com.dimata.harisma.entity.outsource.OutSourcePlan;
import com.dimata.harisma.entity.outsource.OutSourcePlanLocation;
import com.dimata.harisma.entity.outsource.PstOutSourcePlanLocation;
import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import java.util.Vector;

/*
Description : Controll OutSourcePlanLocation
Date : Mon Sep 14 2015
Author : opie-eyek
*/

public class CtrlOutSourcePlanLocation extends Control implements I_Language {

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
    private OutSourcePlanLocation entOutSourcePlanLocation;
    private PstOutSourcePlanLocation pstOutSourcePlanLocation;
    private FrmOutSourcePlanLocation frmOutSourcePlanLocation;
    int language = LANGUAGE_DEFAULT;

    public CtrlOutSourcePlanLocation(HttpServletRequest request) {
        msgString = "";
        entOutSourcePlanLocation = new OutSourcePlanLocation();
        try {
            pstOutSourcePlanLocation = new PstOutSourcePlanLocation(0);
        } catch (Exception e) {;
        }
        frmOutSourcePlanLocation = new FrmOutSourcePlanLocation(request, entOutSourcePlanLocation);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmOutSourcePlanLocation.addError(frmOutSourcePlanLocation.FRM_FIELD_OUTSOURCEPLANLOCID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public OutSourcePlanLocation getOutSourcePlanLocation() {
        return entOutSourcePlanLocation;
    }

    public FrmOutSourcePlanLocation getForm() {
        return frmOutSourcePlanLocation;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int actionGenerate(int cmd, long oidOutSourcePlanLocation, Vector vProses, OutSourcePlan outSourcePlan,  HttpServletRequest request) {
        int rsCode = RSLT_OK;
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        switch (cmd) {
            case Command.ADD:
                break;
                
            case Command.SAVE:
                
                    frmOutSourcePlanLocation.requestEntityObjectMultiple();
                    
                    //proses insert
                    Vector listInputan = frmOutSourcePlanLocation.getGetValueInput();
                    if (listInputan != null && listInputan.size() > 0) {
                        for (int i = 0; i < listInputan.size(); i++) {
                                OutSourcePlanLocation outSourcePlanLocation =  (OutSourcePlanLocation) listInputan.get(i);
                                String where = PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANLOCID]+"="+outSourcePlanLocation.getOID();
                                
                                try {
                                   PstOutSourcePlanLocation.updateInputanPlanLocation(where, outSourcePlanLocation.getNameColomn(), outSourcePlanLocation.getValueInput());
                                } catch (Exception exc) {
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                                } 
                        }
                    }                    
                break;    
                
            case Command.SUBMIT:
                if(vProses.size()>1){
                    Vector vPosition = (Vector) vProses.get(0);
                    Vector vDivision = (Vector) vProses.get(1);
                    if(vPosition.size() > 0 && vDivision.size() > 0){
                        //delete semua nilai
                        String whereDelete = PstOutSourcePlanLocation.fieldNames[PstOutSourcePlanLocation.FLD_OUTSOURCEPLANID]+"='"+oidOutSourcePlanLocation+"'";
                        PstOutSourcePlanLocation.deleteAllPlanLocation(whereDelete);
                        
                        for(int i = 0;i < vPosition.size();i++){
                            //long oidPosition = (Long) vPosition.get(i);
                            long oidPosition= Long.parseLong(String.valueOf(vPosition.get(i)));
                               for(int k = 0; k < vDivision.size();k++ ){
                                  // long oidDivision= (Long) vDivision.get(k);
                                    long oidDivision= Long.parseLong(String.valueOf(vDivision.get(k)));
                                    OutSourcePlanLocation outSourcePlanLocation= new OutSourcePlanLocation();
                                    outSourcePlanLocation.setCompanyId(outSourcePlan.getCompanyId());
                                    outSourcePlanLocation.setDepartmentId(0);
                                    outSourcePlanLocation.setSectionId(0);
                                    outSourcePlanLocation.setDivisionId(oidDivision);
                                    outSourcePlanLocation.setPositionId(oidPosition);
                                    outSourcePlanLocation.setOutsourcePlanId(outSourcePlan.getOID());
                                    //buatkan query sesuai dengan generate existing yang dipilih periodenya
                                    outSourcePlanLocation.setPrevExisting(0);
                                    
                                    outSourcePlanLocation.setNumberTW1(0);
                                    outSourcePlanLocation.setNumberTW2(0);
                                    outSourcePlanLocation.setNumberTW3(0);
                                    outSourcePlanLocation.setNumberTW4(0);
                                    try {
                                            long oid = pstOutSourcePlanLocation.insertExc(outSourcePlanLocation);
                                    } catch (DBException dbexc) {
                                        excCode = dbexc.getErrorCode();
                                        msgString = getSystemMessage(excCode);
                                        return getControlMsgId(excCode);
                                    } catch (Exception exc) {
                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                                    }
                               }
                        }
                    }
                        
                }
                break;    
            default:
                break;
         }        
        return rsCode;
    }
    
    
    public int action(int cmd, long oidOutSourcePlanLocation) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidOutSourcePlanLocation != 0) {
                    try {
                        entOutSourcePlanLocation = PstOutSourcePlanLocation.fetchExc(oidOutSourcePlanLocation);
                    } catch (Exception exc) {
                    }
                }

                frmOutSourcePlanLocation.requestEntityObject(entOutSourcePlanLocation);

                if (frmOutSourcePlanLocation.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entOutSourcePlanLocation.getOID() == 0) {
                    try {
                        long oid = pstOutSourcePlanLocation.insertExc(this.entOutSourcePlanLocation);
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
                        long oid = pstOutSourcePlanLocation.updateExc(this.entOutSourcePlanLocation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidOutSourcePlanLocation != 0) {
                    try {
                        entOutSourcePlanLocation = PstOutSourcePlanLocation.fetchExc(oidOutSourcePlanLocation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidOutSourcePlanLocation != 0) {
                    try {
                        entOutSourcePlanLocation = PstOutSourcePlanLocation.fetchExc(oidOutSourcePlanLocation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidOutSourcePlanLocation != 0) {
                    try {
                        long oid = PstOutSourcePlanLocation.deleteExc(oidOutSourcePlanLocation);
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