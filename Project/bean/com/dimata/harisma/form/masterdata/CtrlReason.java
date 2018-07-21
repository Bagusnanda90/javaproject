/*
 * CtrlReason.java
 *
 * Created on June 20, 2007, 5:09 PM
 */

package com.dimata.harisma.form.masterdata;


/* java package */ 
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
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
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author  yunny
 */
public class CtrlReason extends Control implements I_Language {
    
        public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;
        public static int RSLT_DATA_ALREADY_USE = 4;
        public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap","Data sudah digunakan,tidak boleh di hapus"},
		{"Succes", "Can not process", "Estimation code exist", "Data incomplete", "Data is already used, cannot delete data"}
	};
        
        private int start;
	private String msgString;
	private Reason reason;
	private PstReason pstReason;
	private FrmReason frmReason;
	int language = LANGUAGE_DEFAULT;
        
        public CtrlReason(HttpServletRequest request){
		msgString = "";
		reason = new Reason();
		try{
			pstReason = new PstReason(0);
		}catch(Exception e){;}
		frmReason = new FrmReason(request, reason);
	}
        
        private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmReason.addError(frmReason.FRM_FIELD_REASON_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
        
        public int getLanguage(){ return language; }

	public void setLanguage(int language){ this.language = language; }

	public Reason getReason() { return reason; }

	public FrmReason getForm() { return frmReason; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }
        
        public int action(int cmd , long oidReason){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidReason != 0){
					try{
						reason = PstReason.fetchExc(oidReason);
					}catch(Exception exc){
					}
				}

				frmReason.requestEntityObject(reason);

				if(frmReason.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

                                if(PstReason.isNoUsed(reason.getNo(), reason.getOID())){
                                    msgString = "Number has been used";
                                    return RSLT_FORM_INCOMPLETE ;
                                }
                                
				if(reason.getOID()==0){
					try{
						long oid = pstReason.insertExc(this.reason);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}

				}else{
					try {
						long oid = pstReason.updateExc(this.reason);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidReason != 0) {
					try {
						reason = PstReason.fetchExc(oidReason);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidReason != 0) {
                	try{
					    if(PstReason.checkMaster(oidReason))
                         	msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        else
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);

						reason = PstReason.fetchExc(oidReason);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
                            int intStart=1;
                            int intFin = 31;
				if (oidReason != 0){
					try{
                                               //jikayang di delete terpakai
                                                reason = PstReason.fetchExc(oidReason);
                                                for (int i = intStart; i <= intFin; i++) {
                                                   String whereClause = PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_REASON + (i - 1)]+"="+reason.getNo();
                                                   int checkReasonNo= PstEmpSchedule.getCount(whereClause);
                                                   if(checkReasonNo !=0){
                                                       msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							excCode = RSLT_DATA_ALREADY_USE;
                                                        break;
                                                   }
                                                }
                                                     //update by satrya 2012-10-29
                                                long oid =0;
                                               if(excCode==0){
							oid = PstReason.deleteExc(oidReason);
                                               }        
						
                                              
						if(oid!=0){
                                                     if(excCode==0){
							msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
							rsCode = RSLT_OK;
                                                        }
						}else if(excCode == RSLT_DATA_ALREADY_USE){
                                                     msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							rsCode = RSLT_DATA_ALREADY_USE;
                                                }
                                                else{
							msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							rsCode = RSLT_FORM_INCOMPLETE;
						}
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch(Exception exc){	
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			default :

		}
		return rsCode;
	}
    
  
    
}
