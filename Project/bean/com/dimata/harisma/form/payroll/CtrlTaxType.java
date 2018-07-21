/*
 * CtrlTaxType.java
 *
 * Created on March 31, 2007, 9:51 AM
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
public class CtrlTaxType extends Control implements I_Language{
    
    /** Creates a new instance of CtrlTaxType */
    public CtrlTaxType() {
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
	private TaxType taxType;
	private PstTaxType pstTaxType;
	private FrmTaxType frmTaxType;
        
        int language = LANGUAGE_DEFAULT;
        
        
        public CtrlTaxType(HttpServletRequest request){
		msgString = "";
		taxType = new TaxType();
		try{
			pstTaxType = new PstTaxType(0);
		}catch(Exception e){;}
		frmTaxType = new FrmTaxType(request, taxType);
	}
        
        private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmTaxType.addError(frmTaxType.FRM_FIELD_TAX_TYPE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
        
        public TaxType getTaxType() { return taxType; } 

	public FrmTaxType getForm() { return frmTaxType; }
        
        public String getMessage(){ return msgString; }

	public int getStart() { return start; }
        
        public int action(int cmd , long oidTaxType){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidTaxType != 0){
					try{
						taxType = PstTaxType.fetchExc(oidTaxType);
					}catch(Exception exc){
					}
				}

				frmTaxType.requestEntityObject(taxType);

				if(frmTaxType.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(taxType.getOID()==0){
					try{
						long oid = pstTaxType.insertExc(this.taxType);
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
						long oid = pstTaxType.updateExc(this.taxType);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidTaxType != 0) {
					try {
						taxType = PstTaxType.fetchExc(oidTaxType);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
                            if (oidTaxType != 0) {
                                try {
                                    taxType = PstTaxType.fetchExc(oidTaxType);
                                } catch (DBException dbexc){
                                    excCode = dbexc.getErrorCode();
                                    msgString = getSystemMessage(excCode);
                                } catch (Exception exc){
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                }
                            }
                        break;
			case Command.DELETE :
				if (oidTaxType != 0){
					try{
						long oid = PstTaxType.deleteExc(oidTaxType);
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
