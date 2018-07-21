
package com.dimata.harisma.form.masterdata;

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
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author bayu
 */

public class CtrlTrainType extends Control implements I_Language {
    
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
        
	private TrainType trainType;
	private PstTrainType pstTrainType;
	private FrmTrainType frmTrainType;
	

	public CtrlTrainType(HttpServletRequest request) {
            msgString = "";
            trainType = new TrainType();
            
            try {
                pstTrainType = new PstTrainType(0);
            }
            catch(Exception e) {}
            
            frmTrainType = new FrmTrainType(request, trainType);
	}

        
	private String getSystemMessage(int msgCode){
            switch (msgCode) {
                case I_DBExceptionInfo.MULTIPLE_ID :
                    this.frmTrainType.addError(FrmTrainType.FRM_FIELD_TRAIN_TYPE_ID, resultText[language][RSLT_CODE_EXIST]);
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

	public TrainType getTrainType() {
            return trainType; 
        } 

	public FrmTrainType getForm() {
            return frmTrainType; 
        }

	public String getMessage() {
            return msgString; 
        }

	public int getStart() { 
            return start; 
        }
        

	public int action(int command, long oidTrainType){
            msgString = "";
            int excCode = I_DBExceptionInfo.NO_EXCEPTION;
            int rsCode = RSLT_OK;
            
            switch(command) {
                case Command.ADD :
                        break;

                case Command.SAVE :
                        if(oidTrainType != 0) {
                            try {
                                trainType = PstTrainType.fetchExc(oidTrainType);
                            }
                            catch(Exception e) {}
                        }
      
                        frmTrainType.requestEntityObject(trainType);

                        if(frmTrainType.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if(trainType.getOID()==0) {
                            try {
                                long oid = PstTrainType.insertExc(trainType);
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
                                long oid = PstTrainType.updateExc(trainType);
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
                        if(oidTrainType != 0) {
                            try {
                                trainType = PstTrainType.fetchExc(oidTrainType);
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
                        if(oidTrainType != 0) {
                            try {
                                trainType = PstTrainType.fetchExc(oidTrainType);
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
                        if (oidTrainType != 0) {
                            try {
                                long oid = PstTrainType.deleteExc(oidTrainType);
                                
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
        
       
