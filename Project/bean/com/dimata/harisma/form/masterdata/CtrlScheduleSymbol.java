/* 
 * Ctrl Name  		:  CtrlScheduleSymbol.java 
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
import com.dimata.gui.jsp.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;

public class CtrlScheduleSymbol extends Control implements I_Language {

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
    private ScheduleSymbol scheduleSymbol;
    private PstScheduleSymbol pstScheduleSymbol;
    private FrmScheduleSymbol frmScheduleSymbol;
    int language = LANGUAGE_DEFAULT;

    public CtrlScheduleSymbol(HttpServletRequest request) {
        msgString = "";
        scheduleSymbol = new ScheduleSymbol();
        try {
            pstScheduleSymbol = new PstScheduleSymbol(0);
        } catch (Exception e) {;
        }
        frmScheduleSymbol = new FrmScheduleSymbol(request, scheduleSymbol);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmScheduleSymbol.addError(frmScheduleSymbol.FRM_FIELD_SCHEDULE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public ScheduleSymbol getScheduleSymbol() {
        return scheduleSymbol;
    }

    public FrmScheduleSymbol getForm() {
        return frmScheduleSymbol;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidScheduleSymbol, HttpServletRequest request, String oidDeleteAll) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidScheduleSymbol != 0) {
                    try {
                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidScheduleSymbol);
                    } catch (Exception exc) {
                        System.out.println("Exception fetchExc oidScheduleSymbol" + exc);
                    }
                }

                frmScheduleSymbol.requestEntityObject(scheduleSymbol);
                //update by satrya 2012-09-27
                //Date TimeIn = ControlDate.getTime(FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TIME_IN], request);
                //scheduleSymbol.setTimeIn(TimeIn);
                scheduleSymbol.setTimeIn(scheduleSymbol.getTimeIn());
                //Date TimeOut = ControlDate.getTime(FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TIME_OUT], request);
                //scheduleSymbol.setTimeOut(TimeOut);
                scheduleSymbol.setTimeOut(scheduleSymbol.getTimeOut());
                //Date breakOut = ControlDate.getTime(FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_BREAK_OUT], request);
                //scheduleSymbol.setBreakOut(breakOut);
                scheduleSymbol.setBreakOut(scheduleSymbol.getBreakOut());
                //Date breakIn = ControlDate.getTime(FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_BREAK_IN], request);
                //scheduleSymbol.setBreakIn(breakIn);
                scheduleSymbol.setBreakIn(scheduleSymbol.getBreakIn());

                if (frmScheduleSymbol.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (scheduleSymbol.getOID() == 0) {
                    try {
                        long oid = PstScheduleSymbol.insertExc(scheduleSymbol);
                        if (oid>0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
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
                        long oid = PstScheduleSymbol.updateExc(scheduleSymbol);
                        if (oid>0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
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
                if (oidScheduleSymbol != 0) {
                    try {
                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidScheduleSymbol);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidScheduleSymbol != 0) {
                    try {
                        if (PstScheduleSymbol.checkMaster(oidScheduleSymbol)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidScheduleSymbol);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidScheduleSymbol != 0) {
                    try {
                        long oid = PstScheduleSymbol.deleteExc(oidScheduleSymbol);
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
                        long oidPer = Long.parseLong(asset);
                        if (oidPer != 0) {
                            try {
                                long oid = PstScheduleSymbol.deleteExc(oidPer);
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
