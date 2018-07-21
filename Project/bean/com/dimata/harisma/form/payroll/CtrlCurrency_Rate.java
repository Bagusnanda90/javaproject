/*
 * CtrlCurrency_Rate.java
 *
 * Created on April 5, 2007, 10:58 AM
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
public class CtrlCurrency_Rate extends Control implements I_Language {
    
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
	private Currency_Rate currency_Rate;
	private PstCurrency_Rate pstCurrency_Rate;
	private FrmCurrency_Rate frmCurrency_Rate;
        
        int language = LANGUAGE_DEFAULT;
        
    public CtrlCurrency_Rate(HttpServletRequest request){
		msgString = "";
		currency_Rate = new Currency_Rate();
		try{
			pstCurrency_Rate = new PstCurrency_Rate(0);
		}catch(Exception e){;}
		frmCurrency_Rate = new FrmCurrency_Rate(request, currency_Rate);
    }
    
     private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmCurrency_Rate.addError(frmCurrency_Rate.FRM_FIELD_CURR_RATE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
      
       public Currency_Rate getCurrency_Rate() { return currency_Rate; } 

	public FrmCurrency_Rate getForm() { return frmCurrency_Rate; }
        
        public String getMessage(){ return msgString; }

	public int getStart() { return start; }
        
          public int action(int cmd , long oidCurrency_Rate){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidCurrency_Rate != 0){
					try{
						currency_Rate = PstCurrency_Rate.fetchExc(oidCurrency_Rate);
					}catch(Exception exc){
					}
				}

				frmCurrency_Rate.requestEntityObject(currency_Rate);

				if(frmCurrency_Rate.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(currency_Rate.getOID()==0){
					try{
						long oid = pstCurrency_Rate.insertExc(this.currency_Rate);
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
						long oid = pstCurrency_Rate.updateExc(this.currency_Rate);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidCurrency_Rate != 0) {
					try {
						currency_Rate = PstCurrency_Rate.fetchExc(oidCurrency_Rate);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
                            if (oidCurrency_Rate != 0) {
                                try {
                                    currency_Rate = PstCurrency_Rate.fetchExc(oidCurrency_Rate);
                                } catch (DBException dbexc){
                                    excCode = dbexc.getErrorCode();
                                    msgString = getSystemMessage(excCode);
                                } catch (Exception exc){
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                }
                            }
                        break;
			case Command.DELETE :
                                System.out.println("oidCurrency_Rate   "+oidCurrency_Rate);
				if (oidCurrency_Rate != 0){
					try{
						long oid = PstCurrency_Rate.deleteExc(oidCurrency_Rate);
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
