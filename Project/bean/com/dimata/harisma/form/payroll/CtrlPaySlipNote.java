/*
 * CtrlPaySlip.java
 *
 * Created on April 25, 2007, 9:56 AM
 */

package com.dimata.harisma.form.payroll;

/* java package */ 
import com.dimata.harisma.entity.payroll.*;
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import java.util.Vector;
import javax.servlet.http.*;


/**
 *
 * @author  yunny
 */
public class CtrlPaySlipNote  extends Control implements I_Language {
        
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
	private PaySlipNote paySlipNote;
	private PstPaySlipNote pstPaySlipNote;
	private FrmPaySlipNote frmPaySlipNote;
        
        int language = LANGUAGE_DEFAULT;
    
    /** Creates a new instance of CtrlPaySlip */
        public CtrlPaySlipNote(HttpServletRequest request) {
        msgString = "";
		paySlipNote = new PaySlipNote();
        //locker = new Locker();
		try{
			pstPaySlipNote = new PstPaySlipNote(0);
            //pstLocker = new PstLocker(0);
		}catch(Exception e){;}

        frmPaySlipNote = new FrmPaySlipNote(request,paySlipNote);
    }
        
     private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPaySlipNote.addError(frmPaySlipNote.FRM_PAY_SLIP_NOTE_ID , resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public PaySlipNote getPaySlipNote() { return paySlipNote; } 
    
    public FrmPaySlipNote getForm() { return frmPaySlipNote; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    
    public int action(int cmd , String whereClause){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(whereClause != null && whereClause.length()>0){
					try{
                                            Vector listPaySlipNote = PstPaySlipNote.list(0, 0, whereClause, "");
                                            if(listPaySlipNote!=null && listPaySlipNote.size()==1){
                                                paySlipNote = (PaySlipNote)listPaySlipNote.get(0); 
                                            }
						
					}catch(Exception exc){
					}
				}

				frmPaySlipNote.requestEntityObject(paySlipNote); 

                                //System.out.println("frmEmployee.errorSize() : "+frmEmployee.errorSize());
                                
				if(frmPaySlipNote.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
                                
				if(paySlipNote.getOID()==0){
					try{
						long oid = pstPaySlipNote.insertExc(this.paySlipNote);

                        


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
						long oid = pstPaySlipNote.updateExc(this.paySlipNote);
                        

					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			
			case Command.DELETE :
                        //System.out.println("oidPaySlip "+oidPaySlip);
				if (whereClause != null && whereClause.length()>0){
					try{
						
                                                Vector listPaySlipNote = PstPaySlipNote.list(0, 0, whereClause, "");
                                                if(listPaySlipNote!=null && listPaySlipNote.size()==1){
                                                    paySlipNote = (PaySlipNote)listPaySlipNote.get(0); 
                                                }
                                                long oid = PstPaySlipNote.deleteExc(paySlipNote.getOID());
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
