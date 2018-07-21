/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.form.overtime;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import dimata
import com.dimata.util.*;
import com.dimata.util.lang.*;

// import qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;

// import project
import com.dimata.harisma.entity.overtime.*;
import com.dimata.harisma.form.overtime.*;
/**
 *
 * @author Wiweka
 */
public class CtrlOvertimeold extends Control implements I_Language{
    public static int RSLT_OK               = 0;
    public static int RSLT_UNKNOWN_ERROR    = 1;
    public static int RSLT_EST_CODE_EXIST   = 2;
    public static int RSLT_FORM_INCOMPLETE  = 3;

    public static String[][] resultText = {
            {"Berhasil", "Tidak dapat diproses", "Kode sudah ada", "Data tidak lengkap"},
            {"Success", "Can not process", "Code exist", "Data incomplete"}
    };

    private int start;
    private String msgString;
    private int language;
    private Overtime overtime;
    private PstOvertime pstOvertime;
    private FrmOvertime frmOvertime;


    public CtrlOvertimeold(HttpServletRequest request) {
        msgString = "";
        language = LANGUAGE_DEFAULT;
        overtime = new Overtime();

        try {
            pstOvertime = new PstOvertime(0);
        }
        catch(Exception e) {}

        frmOvertime = new FrmOvertime(request, overtime);
    }


    public int getStart() {
        return start;
    }

    public String getMessage() {
        return msgString;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public FrmOvertime getForm() {
        return frmOvertime;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }


   /* private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                frmOvertime.addError(frmOvertime.FRM_FIELD_OVERTIME_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }*/

    private int getControlMsgId(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int action(int cmd, long oidOvertime,long oidEmployee,HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        switch(cmd){
                case Command.ADD :
                        break;

                case Command.EDIT :
                        if(oidOvertime != 0) {
                            try {
                                    overtime = PstOvertime.fetchExc(oidOvertime);
                            }
                            catch (DBException dbexc){
                                    excCode = dbexc.getErrorCode();
                                    //msgString = getSystemMessage(excCode);
                            }
                            catch (Exception exc){
                                    //msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }
                        }
                        break;

                case Command.SAVE :
                        if(oidOvertime != 0){
                            try{
                                    overtime = PstOvertime.fetchExc(oidOvertime);
                            }
                            catch(Exception exc){}
                        }
                        overtime.setOID(oidOvertime);
                        frmOvertime.requestEntityObject(overtime);

                        if(frmOvertime.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if(overtime.getOID()==0){    // insert
                            try{
                                long result = pstOvertime.insertExc(this.overtime);
                            }
                            catch(DBException dbexc){
                                excCode = dbexc.getErrorCode();
                                //msgString = getSystemMessage(excCode);
                                return getControlMsgId(excCode);
                            }
                            catch (Exception exc){
                                //msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                            }

                        }
                        else {                      // update
                            try {
                                long result = pstOvertime.updateExc(this.overtime);
                            }
                            catch (DBException dbexc){
                                excCode = dbexc.getErrorCode();
                               // msgString = getSystemMessage(excCode);
                            }
                            catch (Exception exc){
                                //msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }

                        }
                        break;


                case Command.ASK :
                        if (oidOvertime != 0) {
                            try {
                                msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                                overtime = PstOvertime.fetchExc(oidOvertime);
                            }
                            catch (DBException dbexc){
                                excCode = dbexc.getErrorCode();
                               // msgString = getSystemMessage(excCode);
                            }
                            catch (Exception exc){
                               // msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }
                        }
                        break;

                case Command.DELETE :
                        if (oidOvertime != 0){
                            try{
                                long result = PstOvertime.deleteExc(oidOvertime);

                                if(result != 0){
                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                    excCode = RSLT_OK;
                                }
                                else{
                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                    excCode = RSLT_FORM_INCOMPLETE;
                                }
                            }
                            catch(DBException dbexc){
                                excCode = dbexc.getErrorCode();
//                                msgString = getSystemMessage(excCode);
                            }
                            catch(Exception exc){
                         //       msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }
                        }
        }

        return rsCode;
    }

}
