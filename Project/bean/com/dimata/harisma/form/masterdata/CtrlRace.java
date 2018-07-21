
package com.dimata.harisma.form.masterdata;

// import java
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

// import qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;

// import dimata
import com.dimata.util.*;
import com.dimata.util.lang.*;

// import harisma
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author bayu
 */

public class CtrlRace extends Control implements I_Language {
    
    public static final int RSLT_OK                 =   0;
    public static final int RSLT_UNKNOWN_ERROR      =   1;
    public static final int RSLT_EST_CODE_EXIST     =   2;
    public static final int RSLT_FORM_INCOMPLETE    =   3;
    
    public static String[][] resultText = 
    {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };

    private Race race;
    private PstRace pstRace;
    private FrmRace frmRace;
    private String msgString;
    private int language;
    
    
    public CtrlRace(HttpServletRequest request) throws DBException {
        msgString = "";
        language = LANGUAGE_DEFAULT;
        
        race = new Race();
        
        try {
            pstRace = new PstRace(0);
        }
        catch(DBException e) {
            throw e;
        }
        
        frmRace = new FrmRace(request, race);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmRace.addError(frmRace.FRM_FIELD_RACE_NAME, resultText[language][RSLT_EST_CODE_EXIST] );
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR]; 
        }
    }

    private int getControlMsgId(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }
    
    public Race getRace() {
        return race;
    }
    
    public FrmRace getForm() {
        return frmRace;
    }
    
    public String getMessage() {
        return msgString;
    }
    
    public int getLanguage() {
        return language;
    }
    
    public void setLanguage(int language) {
        this.language = language;
    }
    
    public int action(int command, long oid) {
        int rsCode = RSLT_OK;
        int excCode = FRMMessage.ERR_NONE;
        msgString = "";
        
        switch(command) {
            case Command.ADD :
                break;

            case Command.SAVE :
                if(oid != 0){
                    try {
                        race = PstRace.fetchExc(oid);
                    }
                    catch(Exception exc) { }
                }

                frmRace.requestEntityObject(race);

                if(frmRace.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }

                if(race.getOID()==0){
                    try{
                        long loid = pstRace.insertExc(this.race);
                    }
                    catch(DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    }
                    catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                else {
                    try {
                        long loid = pstRace.updateExc(this.race);
                    }
                    catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
                    }
                }
                break;

            case Command.EDIT :
                if (oid != 0) {
                    try {
                        race = PstRace.fetchExc(oid);
                    } 
                    catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } 
                    catch (Exception exc){ 
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK :
                if (oid != 0) {
                    try {
                        /*if(PstRace.checkMaster(oid))
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        else*/
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        race = PstRace.fetchExc(oid);
                    } 
                    catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } 
                    catch (Exception exc){ 
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE :
                if (oid != 0){
                    try{
                        long loid = PstRace.deleteExc(oid);
                        
                        if(loid!=0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        }
                        else {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    }
                    catch(DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch(Exception exc){	
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default :
        }
        
        return rsCode;
    }
    
}
