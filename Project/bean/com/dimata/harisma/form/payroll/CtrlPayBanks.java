/*
 * CtrlPayBanks.java
 *
 * Created on March 31, 2007, 11:50 AM
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
 * @author  autami
 */
public class CtrlPayBanks extends Control implements I_Language {
    
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
    private PayBanks payBanks;
    private PstPayBanks pstPayBanks;
    private FrmPayBanks frmPayBanks;

    int language = LANGUAGE_DEFAULT;
    
    
    /** Creates a new instance of CtrlPayBanks */
    public CtrlPayBanks(HttpServletRequest request) {
        msgString = "";
	payBanks = new PayBanks();
        try{
            pstPayBanks = new PstPayBanks(0);            
	}catch(Exception e){
            ;
        }
            frmPayBanks = new FrmPayBanks(request,payBanks);	
    }
    
     private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                    this.frmPayBanks.addError(frmPayBanks.FRM_FIELD_BANK_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
     
     public int getLanguage(){
         return language; 
     }
     
     public void setLanguage(int language){ this.language = language; }
    
     public PayBanks getPayBanks() { return payBanks; } 

     public FrmPayBanks getForm() { return frmPayBanks; }

     public String getMessage(){ return msgString; }

     public int getStart() { return start; }
     
     public int action(int cmd , long oidPayBanks){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidPayBanks != 0){
                                    try{
						payBanks = PstPayBanks.fetchExc(oidPayBanks);
                                    }catch(Exception exc){
				    }
				}

				frmPayBanks.requestEntityObject(payBanks); 
                                
				if(frmPayBanks.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}                               
                                
				if(payBanks.getOID()==0){
					try{
						long oid = pstPayBanks.insertExc(this.payBanks);                    

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
						long oid = pstPayBanks.updateExc(this.payBanks);
                        		}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidPayBanks != 0) {
					try {
						payBanks = PstPayBanks.fetchExc(oidPayBanks);                                               
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidPayBanks != 0) {
					try {
						payBanks = PstPayBanks.fetchExc(oidPayBanks);
                                                
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :                       
				if (oidPayBanks != 0){
					try{
						long oid = PstPayBanks.deleteExc(oidPayBanks);                                                
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
     

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
