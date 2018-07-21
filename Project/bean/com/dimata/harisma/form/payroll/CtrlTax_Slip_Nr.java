/*
 * CtrlTax_Slip_Nr.java
 *
 * Created on April 5, 2007, 3:49 PM
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

public class CtrlTax_Slip_Nr extends Control implements I_Language{
    
    /** Creates a new instance of CtrlTax_Slip_Nr */
    public CtrlTax_Slip_Nr() {
    }
    
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
     private Tax_Slip_Nr tax_Slip_Nr;
     private PstTax_Slip_Nr pstTax_Slip_Nr;
     private FrmTax_Slip_Nr frmTax_Slip_Nr;

     int language = LANGUAGE_DEFAULT;
     
      public CtrlTax_Slip_Nr(HttpServletRequest request){
		msgString = "";
		tax_Slip_Nr = new Tax_Slip_Nr();
		try{
			pstTax_Slip_Nr = new PstTax_Slip_Nr(0);
		}catch(Exception e){;}
		frmTax_Slip_Nr = new FrmTax_Slip_Nr(request, tax_Slip_Nr);
	}
      
       private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmTax_Slip_Nr.addError(frmTax_Slip_Nr.FRM_FIELD_TAX_SLIP_NR_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
        
        public Tax_Slip_Nr getTax_Slip_Nr() { return tax_Slip_Nr; } 

	public FrmTax_Slip_Nr getForm() { return frmTax_Slip_Nr; }
        
        public String getMessage(){ return msgString; }

	public int getStart() { return start; }
        
        public int action(int cmd , long oidTax_Slip_Nr){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidTax_Slip_Nr != 0){
					try{
						tax_Slip_Nr = PstTax_Slip_Nr.fetchExc(oidTax_Slip_Nr);
					}catch(Exception exc){
					}
				}

				frmTax_Slip_Nr.requestEntityObject(tax_Slip_Nr);

				if(frmTax_Slip_Nr.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(tax_Slip_Nr.getOID()==0){
					try{
						long oid = pstTax_Slip_Nr.insertExc(this.tax_Slip_Nr);
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
						long oid = pstTax_Slip_Nr.updateExc(this.tax_Slip_Nr);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidTax_Slip_Nr != 0) {
					try {
						tax_Slip_Nr = PstTax_Slip_Nr.fetchExc(oidTax_Slip_Nr);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
                            if (oidTax_Slip_Nr != 0) {
                                try {
                                    tax_Slip_Nr = PstTax_Slip_Nr.fetchExc(oidTax_Slip_Nr);
                                } catch (DBException dbexc){
                                    excCode = dbexc.getErrorCode();
                                    msgString = getSystemMessage(excCode);
                                } catch (Exception exc){
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                }
                            }
                        break;
			case Command.DELETE :
				if (oidTax_Slip_Nr != 0){
					try{
						long oid = PstTax_Slip_Nr.deleteExc(oidTax_Slip_Nr);
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
