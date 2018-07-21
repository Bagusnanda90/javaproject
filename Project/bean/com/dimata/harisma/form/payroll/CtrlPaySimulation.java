/* 
 * Ctrl Name  		:  CtrlPaySimulation.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...]
 * *****************************************************************
 */
package com.dimata.harisma.form.payroll;

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
import com.dimata.harisma.entity.payroll.*;

public class CtrlPaySimulation extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Data sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Data exists", "Data incomplete"}
    };

    private int start;
    private String msgString;
    private PaySimulation paySimulation;
    private PstPaySimulation pstPaySimulation;
    private FrmPaySimulation frmPaySimulation;
    int language = LANGUAGE_DEFAULT;

    public CtrlPaySimulation(HttpServletRequest request) {
        msgString = "";
        paySimulation = new PaySimulation();
        try {
            pstPaySimulation = new PstPaySimulation(0);
        } catch (Exception e) {;
        }
        frmPaySimulation = new FrmPaySimulation(request, paySimulation);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPaySimulation.addError(frmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PaySimulation getPaySimulation() {
        return paySimulation;
    }

    public FrmPaySimulation getForm() {
        return frmPaySimulation;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPaySimulation) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        long oid = 0;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidPaySimulation != 0) {
                    try {
                        paySimulation = PstPaySimulation.fetchExc(oidPaySimulation);
                    } catch (Exception exc) {
                    }
                }

                frmPaySimulation.requestEntityObject(paySimulation);

                if (frmPaySimulation.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (paySimulation.getOID() == 0) {
                    try {
                        oid = pstPaySimulation.insertExc(this.paySimulation);
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
                        oid = pstPaySimulation.updateExc(this.paySimulation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }

                break;
                
            case Command.LOAD: // generate  PaySimulationStructure from payslip
                if (oidPaySimulation != 0) {
                    try {
                        paySimulation = PstPaySimulation.fetchExc(oidPaySimulation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                    try {
                        PstPaySimulationStructure.generatePaySimStruct(paySimulation);
                    } catch (Exception exc) {
                        msgString = msgString + " Exception in get Simulation Structure " + exc;
                    }
                }
                break;
                
            case Command.SUBMIT:
                if (oidPaySimulation != 0) {
                    try {
                        paySimulation = PstPaySimulation.fetchExc(oidPaySimulation);
                    } catch (Exception exc) {
                    }
                }

                frmPaySimulation.requestEntityObject(paySimulation);

                if (frmPaySimulation.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (paySimulation.getOID() == 0) {
                    try {
                        oid = pstPaySimulation.insertExc(this.paySimulation);
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
                        oid = pstPaySimulation.updateExc(this.paySimulation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                
                if(oid>0){
                     frmPaySimulation.requestPaySimulationStructure(this.paySimulation);
                     PstPaySimulationStructure.updateExc(this.paySimulation.getPaySimulationStruct());
                }
                
                break;

            case Command.EDIT:
                if (oidPaySimulation != 0) {
                    try {
                        paySimulation = PstPaySimulation.fetchExc(oidPaySimulation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidPaySimulation != 0) {
                    try {
                        paySimulation = PstPaySimulation.fetchExc(oidPaySimulation);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidPaySimulation != 0) {
                    try {
                        oid = PstPaySimulation.deleteExc(oidPaySimulation);
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
                
            case Command.RESET: // delete the structure
                if (oidPaySimulation != 0) {
                    try {
                        paySimulation = PstPaySimulation.fetchExc(oidPaySimulation);
                        oid = PstPaySimulationStructure.deleteExcByPaySimOid(oidPaySimulation);
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
