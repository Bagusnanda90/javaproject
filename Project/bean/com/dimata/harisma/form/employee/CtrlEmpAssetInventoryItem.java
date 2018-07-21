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
import com.dimata.system.entity.system.PstSystemProperty;

/*
 Description : Controll EmpAssetInventoryItem
 Date : Mon Feb 06 2017
 Author : Gunadi
 */
public class CtrlEmpAssetInventoryItem extends Control implements I_Language {

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
    private EmpAssetInventoryItem entEmpAssetInventoryItem;
    private PstEmpAssetInventoryItem pstEmpAssetInventoryItem;
    private FrmEmpAssetInventoryItem frmEmpAssetInventoryItem;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpAssetInventoryItem(HttpServletRequest request) {
        msgString = "";
        entEmpAssetInventoryItem = new EmpAssetInventoryItem();
        try {
            pstEmpAssetInventoryItem = new PstEmpAssetInventoryItem(0);
        } catch (Exception e) {;
        }
        frmEmpAssetInventoryItem = new FrmEmpAssetInventoryItem(request, entEmpAssetInventoryItem);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpAssetInventoryItem.addError(frmEmpAssetInventoryItem.FRM_FIELD_EMP_ASSET_INVENTORY_ITEM_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpAssetInventoryItem getEmpAssetInventoryItem() {
        return entEmpAssetInventoryItem;
    }

    public FrmEmpAssetInventoryItem getForm() {
        return frmEmpAssetInventoryItem;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpAssetInventoryItem, String oidDeleteAll, int stok) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidEmpAssetInventoryItem != 0) {
                    try {
                        entEmpAssetInventoryItem = PstEmpAssetInventoryItem.fetchExc(oidEmpAssetInventoryItem);
                    } catch (Exception exc) {
                    }
                }

                frmEmpAssetInventoryItem.requestEntityObject(entEmpAssetInventoryItem);

                if (frmEmpAssetInventoryItem.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entEmpAssetInventoryItem.getOID() == 0) {
                    try {
                        long oid = pstEmpAssetInventoryItem.insertExc(this.entEmpAssetInventoryItem);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        
                        String dbPos = String.valueOf(PstSystemProperty.getValueByNameWithStringNull("PROCHAIN_DB_NAME"));
                        EmpAssetInventoryItem asset = PstEmpAssetInventoryItem.fetchExc(oid);
                        int qty = stok - asset.getQty();
                        String sql = "UPDATE "+dbPos+".pos_material_stock SET QTY="+qty;
                        sql += " WHERE LOCATION_ID="+asset.getLocationId()+" AND MATERIAL_UNIT_ID="+asset.getMaterialId();
                        DBHandler.updateParsial(sql);
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
                        long oid = pstEmpAssetInventoryItem.updateExc(this.entEmpAssetInventoryItem);
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
                if (oidEmpAssetInventoryItem != 0) {
                    try {
                        entEmpAssetInventoryItem = PstEmpAssetInventoryItem.fetchExc(oidEmpAssetInventoryItem);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpAssetInventoryItem != 0) {
                    try {
                        entEmpAssetInventoryItem = PstEmpAssetInventoryItem.fetchExc(oidEmpAssetInventoryItem);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpAssetInventoryItem != 0) {
                    try {
                        long oid = PstEmpAssetInventoryItem.deleteExc(oidEmpAssetInventoryItem);
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
                                long oid = PstEmpAssetInventoryItem.deleteExc(oidDoc);
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