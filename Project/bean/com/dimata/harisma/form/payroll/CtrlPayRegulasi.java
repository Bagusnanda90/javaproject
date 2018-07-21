/*
 * CtrlPayRegulasi.java
 *
 * Created on August 29, 2007, 5:24 PM
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
public class CtrlPayRegulasi extends Control implements I_Language{
    
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Konfigurasi tipe ini sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "This configuration type exist", "Data incomplete"}
    };
    
    private int start;
    private String msgString;
    private PayRegulasi payRegulasi;
    private PstPayRegulasi pstPayRegulasi;
    private FrmPayRegulasi frmPayRegulasi;
    int language = LANGUAGE_DEFAULT;
    
    public CtrlPayRegulasi(HttpServletRequest request){
		msgString = "";
		payRegulasi = new PayRegulasi();
		try{
			pstPayRegulasi = new PstPayRegulasi(0);
		}catch(Exception e){;}
		frmPayRegulasi = new FrmPayRegulasi(request, payRegulasi);
	}
    
     private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPayRegulasi.addError(frmPayRegulasi.FRM_FIELD_REGULASI_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
        
        public PayRegulasi  getPayRegulasi() { return payRegulasi ; } 

	public FrmPayRegulasi getForm() { return frmPayRegulasi; }
        
        public String getMessage(){ return msgString; }

	public int getStart() { return start; }
        
         public int action(int cmd , long oidPayRegulasi){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidPayRegulasi != 0){
					try{
						payRegulasi = PstPayRegulasi.fetchExc(oidPayRegulasi);
					}catch(Exception exc){
					}
				}

				frmPayRegulasi.requestEntityObject(payRegulasi);

				if(frmPayRegulasi.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(payRegulasi.getOID()==0){
					try{
						long oid = pstPayRegulasi.insertExc(this.payRegulasi);
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
						long oid = pstPayRegulasi.updateExc(this.payRegulasi);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidPayRegulasi != 0) {
					try {
						payRegulasi = PstPayRegulasi.fetchExc(oidPayRegulasi);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
                            if (oidPayRegulasi != 0) {
                                try {
                                    payRegulasi = PstPayRegulasi.fetchExc(oidPayRegulasi);
                                } catch (DBException dbexc){
                                    excCode = dbexc.getErrorCode();
                                    msgString = getSystemMessage(excCode);
                                } catch (Exception exc){
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                }
                            }
                        break;
			case Command.DELETE :
				if (oidPayRegulasi != 0){
					try{
						long oid = PstPayRegulasi.deleteExc(oidPayRegulasi);
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
