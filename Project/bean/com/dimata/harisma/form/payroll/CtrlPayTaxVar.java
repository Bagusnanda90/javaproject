/*
 * CtrlPayTaxVar.java
 *
 * Created on August 10, 2007, 1:58 PM
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
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author  emerliana
 */
public class CtrlPayTaxVar extends Control implements I_Language {
    
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
	private PayTaxVariabel payTaxVariabel;
	private PstPayTaxVariabel pstPayTaxVariabel;
	private FrmPayTaxVariabel frmPayTaxVariabel;
        
        int language = LANGUAGE_DEFAULT;
        
    public CtrlPayTaxVar(HttpServletRequest request){
		msgString = "";
		payTaxVariabel = new PayTaxVariabel();
        //locker = new Locker();
		try{
			pstPayTaxVariabel = new PstPayTaxVariabel(0);
            //pstLocker = new PstLocker(0);
		}catch(Exception e){;}

        frmPayTaxVariabel = new FrmPayTaxVariabel(request,payTaxVariabel);
		//frmLocker = new FrmLocker(request, locker);
	}
    
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPayTaxVariabel.addError(frmPayTaxVariabel.FRM_FIELD_TAX_VARIABEL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
     public PayTaxVariabel getPayTaxVariabel() { return payTaxVariabel; } 
     public FrmPayTaxVariabel getForm() { return frmPayTaxVariabel; }
     public String getMessage(){ return msgString; }
     public int getStart() { return start; }
    
  public int action(int cmd , long oidPayTaxVariabel, String codeSalary){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidPayTaxVariabel != 0){
					try{
						payTaxVariabel = PstPayTaxVariabel.fetchExc(oidPayTaxVariabel);
					}catch(Exception exc){
					}
				}
                                
                                
				frmPayTaxVariabel.requestEntityObject(payTaxVariabel);
                                
                                payTaxVariabel.setLevelCode(codeSalary);
                                
				if(frmPayTaxVariabel.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
				if(payTaxVariabel.getOID()==0){
					try{
						long oid = pstPayTaxVariabel.insertExc(this.payTaxVariabel);
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
						long oid = pstPayTaxVariabel.updateExc(this.payTaxVariabel);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidPayTaxVariabel != 0) {
					try {
						payTaxVariabel = PstPayTaxVariabel.fetchExc(oidPayTaxVariabel);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidPayTaxVariabel != 0) {
					try {
                                            if(PstPayTaxVariabel.checkMaster(oidPayTaxVariabel))
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                                            else
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);

                                            payTaxVariabel = PstPayTaxVariabel.fetchExc(oidPayTaxVariabel);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidPayTaxVariabel != 0){
					try{
						long oid = PstPayTaxVariabel.deleteExc(oidPayTaxVariabel);
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
