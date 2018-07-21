/*
 * CtrlOvt_Employee_Idx.java
 *
 * Created on April 7, 2007, 1:03 PM
 */

package com.dimata.harisma.form.payroll;

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
import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author  emerliana
 */
public class CtrlOvt_Employee_Idx extends Control implements I_Language {
    
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
	private Ovt_Employee_Idx ovt_Employee_Idx;
	private PstOvt_Employee_Idx pstOvt_Employee_Idx;
	private FrmOvt_Employee_Idx frmOvt_Employee_Idx;
        
        int language = LANGUAGE_DEFAULT;
    
    public CtrlOvt_Employee_Idx(HttpServletRequest request){
		msgString = "";
		ovt_Employee_Idx = new Ovt_Employee_Idx();
		try{
			pstOvt_Employee_Idx = new PstOvt_Employee_Idx(0);
		}catch(Exception e){;}
		frmOvt_Employee_Idx = new FrmOvt_Employee_Idx(request, ovt_Employee_Idx);
	}
    
     private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmOvt_Employee_Idx.addError(frmOvt_Employee_Idx.FRM_FIELD_OVT_EMPLY_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
     
     public Ovt_Employee_Idx getOvt_Employee_Idx() { return ovt_Employee_Idx; } 

     public FrmOvt_Employee_Idx getForm() { return frmOvt_Employee_Idx; }

     public String getMessage(){ return msgString; }

     public int getStart() { return start; }
     
     public int action(int cmd , long oidOvt_Employee_Idx){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidOvt_Employee_Idx != 0){
					try{
						ovt_Employee_Idx = PstOvt_Employee_Idx.fetchExc(oidOvt_Employee_Idx);
					}catch(Exception exc){
					}
				}

				frmOvt_Employee_Idx.requestEntityObject(ovt_Employee_Idx);

				if(frmOvt_Employee_Idx.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(ovt_Employee_Idx.getOID()==0){
					try{
						long oid = pstOvt_Employee_Idx.insertExc(this.ovt_Employee_Idx);
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
						long oid = pstOvt_Employee_Idx.updateExc(this.ovt_Employee_Idx);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidOvt_Employee_Idx != 0) {
					try {
						ovt_Employee_Idx = PstOvt_Employee_Idx.fetchExc(oidOvt_Employee_Idx);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidOvt_Employee_Idx != 0) {
                                    try {
                                        ovt_Employee_Idx = PstOvt_Employee_Idx.fetchExc(oidOvt_Employee_Idx);
                                    } catch (DBException dbexc){
                                        excCode = dbexc.getErrorCode();
                                        msgString = getSystemMessage(excCode);
                                    } catch (Exception exc){
                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    }
                                }
                        break;

			case Command.DELETE :
				if (oidOvt_Employee_Idx != 0){
					try{
						long oid = PstOvt_Employee_Idx.deleteExc(oidOvt_Employee_Idx);
						if(oid!=0){
							msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
							excCode = RSLT_OK;
						}else{
							msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							excCode = RSLT_FORM_INCOMPLETE;
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
