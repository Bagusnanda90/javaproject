/*
 * CtrlOvt_Idx.java
 *
 * Created on April 12, 2007, 11:12 AM
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
public class CtrlOvt_Idx extends Control implements I_Language {
    
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
	private Ovt_Idx ovt_Idx;
	private PstOvt_Idx pstOvt_Idx;
	private FrmOvt_Idx frmOvt_Idx;
        
        int language = LANGUAGE_DEFAULT;
        
         public CtrlOvt_Idx(HttpServletRequest request){
		msgString = "";
		ovt_Idx = new Ovt_Idx();
		try{
			pstOvt_Idx = new PstOvt_Idx(0);
		}catch(Exception e){;}
		frmOvt_Idx = new FrmOvt_Idx(request, ovt_Idx);
	}
         
         private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmOvt_Idx.addError(frmOvt_Idx.FRM_FIELD_OVT_IDX_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

         public Ovt_Idx getOvt_Idx() { return ovt_Idx; } 

         public FrmOvt_Idx getForm() { return frmOvt_Idx; }

         public String getMessage(){ return msgString; }

         public int getStart() { return start; }
         
         
         public int action(int cmd , long oidOvt_Idx, String overtime_Code){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidOvt_Idx != 0){
					try{
						ovt_Idx = PstOvt_Idx.fetchExc(oidOvt_Idx);
					}catch(Exception exc){
					}
				}

				frmOvt_Idx.requestEntityObject(ovt_Idx);
                                
                                ovt_Idx.setOvt_type_code(overtime_Code);

				if(frmOvt_Idx.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(ovt_Idx.getOID()==0){
					try{
						long oid = pstOvt_Idx.insertExc(this.ovt_Idx);
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
						long oid = pstOvt_Idx.updateExc(this.ovt_Idx);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidOvt_Idx!= 0) {
					try {
						ovt_Idx = PstOvt_Idx.fetchExc(oidOvt_Idx);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidOvt_Idx != 0) {
                                    try {
                                        ovt_Idx = PstOvt_Idx.fetchExc(oidOvt_Idx);
                                    } catch (DBException dbexc){
                                        excCode = dbexc.getErrorCode();
                                        msgString = getSystemMessage(excCode);
                                    } catch (Exception exc){
                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    }
                                }
                        break;

			case Command.DELETE :
				if (oidOvt_Idx != 0){
					try{
						long oid = PstOvt_Idx.deleteExc(oidOvt_Idx);
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
