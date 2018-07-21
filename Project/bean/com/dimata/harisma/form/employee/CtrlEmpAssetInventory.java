/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import javax.servlet.http.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.form.employee.FrmEmpAssetInventory;
import java.util.Vector;

/*
 Description : Controll EmpAssetInventory
 Date : Fri Feb 03 2017
 Author : Gunadi
 */
public class CtrlEmpAssetInventory extends Control implements I_Language {

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
    private EmpAssetInventory entEmpAssetInventory;
    private PstEmpAssetInventory pstEmpAssetInventory;
    private FrmEmpAssetInventory frmEmpAssetInventory;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpAssetInventory(HttpServletRequest request) {
        msgString = "";
        entEmpAssetInventory = new EmpAssetInventory();
        try {
            pstEmpAssetInventory = new PstEmpAssetInventory(0);
        } catch (Exception e) {;
        }
        frmEmpAssetInventory = new FrmEmpAssetInventory(request, entEmpAssetInventory);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpAssetInventory.addError(frmEmpAssetInventory.FRM_FIELD_EMP_ASSET_INVENTORY_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpAssetInventory getEmpAssetInventory() {
        return entEmpAssetInventory;
    }

    public FrmEmpAssetInventory getForm() {
        return frmEmpAssetInventory;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpAssetInventory, String oidDeleteAll) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidEmpAssetInventory != 0) {
                    try {
                        entEmpAssetInventory = PstEmpAssetInventory.fetchExc(oidEmpAssetInventory);
                    } catch (Exception exc) {
                    }
                }

                frmEmpAssetInventory.requestEntityObject(entEmpAssetInventory);
                
                if (entEmpAssetInventory.getCheckBy() > 0 ){
                    entEmpAssetInventory.setStatus(pstEmpAssetInventory.CHECKED);
                } 
                if (entEmpAssetInventory.getApprovedBy() > 0){
                    entEmpAssetInventory.setStatus(pstEmpAssetInventory.APPROVED);
                }
                if (entEmpAssetInventory.getReceivedBy() > 0 && entEmpAssetInventory.getDocType() == pstEmpAssetInventory.HAND_OVER){
                    entEmpAssetInventory.setStatus(pstEmpAssetInventory.RECEIVED);
                }
                if (entEmpAssetInventory.getReceivedBy() > 0 && entEmpAssetInventory.getDocType() == pstEmpAssetInventory.RETURN){
                    entEmpAssetInventory.setStatus(pstEmpAssetInventory.RETURNED);
                }

                if (frmEmpAssetInventory.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entEmpAssetInventory.getOID() == 0) {
                    try {
                        long oid = pstEmpAssetInventory.insertExc(this.entEmpAssetInventory);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        
                        Vector listItem = new Vector();
                        String where = PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID] + " = 0";
                        listItem = PstEmpAssetInventoryItem.list(0, 0, where, "");
                        if (listItem.size() > 0){
                            for (int i = 0; i < listItem.size(); i++){
                                EmpAssetInventoryItem item = (EmpAssetInventoryItem) listItem.get(i);
                                String sqlUpdate = "UPDATE "+PstEmpAssetInventoryItem.TBL_EMP_ASSET_INVENTORY_ITEM+" SET ";
                                sqlUpdate += PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ID]+"="+oid+"";
                                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                sqlUpdate += " WHERE "+PstEmpAssetInventoryItem.fieldNames[PstEmpAssetInventoryItem.FLD_EMP_ASSET_INVENTORY_ITEM_ID]+"="+item.getOID();
                                DBHandler.execUpdate(sqlUpdate);
                            }
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
                        long oid = pstEmpAssetInventory.updateExc(this.entEmpAssetInventory);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidEmpAssetInventory != 0) {
                    try {
                        entEmpAssetInventory = PstEmpAssetInventory.fetchExc(oidEmpAssetInventory);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpAssetInventory != 0) {
                    try {
                        entEmpAssetInventory = PstEmpAssetInventory.fetchExc(oidEmpAssetInventory);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpAssetInventory != 0) {
                    try {
                        long oid = PstEmpAssetInventory.deleteExc(oidEmpAssetInventory);
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
            case Command.DELETEALL:
                //splits => untuk mengurutkan beberapa data menjadi baris data
                String[] splits = oidDeleteAll.split(",");
                for (String asset : splits) {
                    if (asset != "") {
                        long oidDoc = Long.parseLong(asset);
                        if (oidDoc != 0) {
                            try {
                                long oid = PstEmpAssetInventory.deleteExc(oidDoc);
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
                    }
                }
                break;    

            default:

        }
        return rsCode;
    }
}