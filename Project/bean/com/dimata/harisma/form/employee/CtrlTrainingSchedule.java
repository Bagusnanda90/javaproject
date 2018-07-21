
package com.dimata.harisma.form.employee;

// import java
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// import dimata
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;

// import harisma
import com.dimata.harisma.entity.employee.*;

/**
 *
 * @author bayu
 */

public class CtrlTrainingSchedule extends Control implements I_Language {
    
        public static final int RSLT_OK               =   0;
	public static final int RSLT_UNKNOWN_ERROR    =   1;
	public static final int RSLT_CODE_EXIST       =   2;
	public static final int RSLT_FORM_INCOMPLETE  =   3;

	public static String[][] resultText = 
        {
            { "Berhasil", "Tidak dapat diproses", "No id sudah ada", "Data tidak lengkap" },
            { "Success", "Can not process", "Id code exist", "Data incomplete" }
	};
	
	private String msgString = "";
        private int start = 0;
        private int language = LANGUAGE_DEFAULT;
        
	private TrainingSchedule trainingSchedule;
	private PstTrainingSchedule pstTrainingSchedule;
	private FrmTrainingSchedule frmTrainingSchedule;
	

	public CtrlTrainingSchedule(HttpServletRequest request) {
            msgString = "";
            trainingSchedule = new TrainingSchedule();
            
            try {
                pstTrainingSchedule = new PstTrainingSchedule(0);
            }
            catch(Exception e) {}
            
            frmTrainingSchedule = new FrmTrainingSchedule(request, trainingSchedule);
	}

        
	private String getSystemMessage(int msgCode){
            switch (msgCode) {
                case I_DBExceptionInfo.MULTIPLE_ID :
                    this.frmTrainingSchedule.addError(FrmTrainingSchedule.FRM_FIELD_TRAIN_SCHEDULE_ID, resultText[language][RSLT_CODE_EXIST]);
                    return resultText[language][RSLT_CODE_EXIST];
                    
                default:
                    return resultText[language][RSLT_UNKNOWN_ERROR]; 
            }
	}

	private int getControlMsgId(int msgCode){
            switch (msgCode){
                case I_DBExceptionInfo.MULTIPLE_ID :
                    return RSLT_CODE_EXIST;
                    
                default:
                    return RSLT_UNKNOWN_ERROR;
            }
	}

        
	public int getLanguage() { 
            return language; 
        }

	public void setLanguage(int language){ 
            this.language = language; 
        }

	public TrainingSchedule getTrainSchedule() {
            return trainingSchedule; 
        } 

	public FrmTrainingSchedule getForm() {
            return frmTrainingSchedule; 
        }

	public String getMessage() {
            return msgString; 
        }

	public int getStart() { 
            return start; 
        }
        

	public int action(int command, long oidTrainSchedule){
            msgString = "";
            int excCode = I_DBExceptionInfo.NO_EXCEPTION;
            int rsCode = RSLT_OK;
            
            switch(command) {
                case Command.ADD :
                        break;

                case Command.SAVE :
                        if(oidTrainSchedule != 0) {
                            try {
                                trainingSchedule = PstTrainingSchedule.fetchExc(oidTrainSchedule);
                            }
                            catch(Exception e) {}
                        }
      
                        frmTrainingSchedule.requestEntityObject(trainingSchedule);

                        if(frmTrainingSchedule.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if(trainingSchedule.getOID()==0) {
                            try {
                                long oid = PstTrainingSchedule.insertExc(trainingSchedule);
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
                                long oid = PstTrainingSchedule.updateExc(trainingSchedule);
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
                        if(oidTrainSchedule != 0) {
                            try {
                                trainingSchedule = PstTrainingSchedule.fetchExc(oidTrainSchedule);
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
                        if(oidTrainSchedule != 0) {
                            try {
                                trainingSchedule = PstTrainingSchedule.fetchExc(oidTrainSchedule);
                                msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
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
                        if (oidTrainSchedule != 0) {
                            try {
                                long oid = PstTrainingSchedule.deleteExc(oidTrainSchedule);
                                
                                if(oid != 0) {
                                    excCode = RSLT_OK;
                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);                                    
                                }
                                else {
                                    excCode = RSLT_FORM_INCOMPLETE;
                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);                                    
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
        
        public int action(int command, long oidTrainSchedule, TrainingSchedule schedule){
            msgString = "";
            int excCode = I_DBExceptionInfo.NO_EXCEPTION;
            int rsCode = RSLT_OK;
            
            switch(command) {
                
                case Command.SAVE :
                        if(oidTrainSchedule == 0) {
                            try {
                                long oid = PstTrainingSchedule.insertExc(schedule);
                                schedule.setOID(oid);
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
                                schedule.setOID(oidTrainSchedule);
                                long oid = PstTrainingSchedule.updateExc(schedule);
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
                        if (oidTrainSchedule != 0) {
                            try {
                                long oid = PstTrainingSchedule.deleteExc(oidTrainSchedule);
                                
                                if(oid != 0) {
                                    excCode = RSLT_OK;
                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);                                    
                                }
                                else {
                                    excCode = RSLT_FORM_INCOMPLETE;
                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);                                    
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
