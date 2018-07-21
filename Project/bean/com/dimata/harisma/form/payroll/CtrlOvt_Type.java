/*
 * CtrlOvt_Type.java
 *
 * Created on April 12, 2007, 9:12 AM
 */

package com.dimata.harisma.form.payroll;

import com.dimata.gui.jsp.ControlDate;
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
public class CtrlOvt_Type extends Control implements I_Language  {
    
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
	private Ovt_Type ovt_Type;
	private PstOvt_Type pstOvt_Type;
	private FrmOvt_Type frmOvt_Type;
        
        int language = LANGUAGE_DEFAULT;
        
         public CtrlOvt_Type(HttpServletRequest request){
		msgString = "";
		ovt_Type = new Ovt_Type();
		try{
			pstOvt_Type = new PstOvt_Type(0);
		}catch(Exception e){;}
		frmOvt_Type = new FrmOvt_Type(request, ovt_Type);
	}
    
     private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmOvt_Type.addError(frmOvt_Type.FRM_FIELD_OVT_TYPE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
     
     public Ovt_Type getOvt_Type() { return ovt_Type; } 

     public FrmOvt_Type getForm() { return frmOvt_Type; }

     public String getMessage(){ return msgString; }

     public int getStart() { return start; }
     
      public int action(int cmd , long oidOvt_Type, HttpServletRequest request){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidOvt_Type != 0){
					try{
						ovt_Type = PstOvt_Type.fetchExc(oidOvt_Type);
					}catch(Exception exc){
					}
				}
                                
				frmOvt_Type.requestEntityObject(ovt_Type);
                                
                                //untuk date hour begin
                                //Date startTime = ControlDate.getTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], request);
                                Date hour_begin_Time = ControlDate.getTime(FrmOvt_Type.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_BEGIN], request);
                               
                                //untuk date hour end
                                Date hour_end_Time = ControlDate.getTime(FrmOvt_Type.fieldNames[FrmOvt_Type.FRM_FIELD_HOUR_END], request);
                               
                                if(frmOvt_Type.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
                                ovt_Type.setStd_work_hour_begin(hour_begin_Time);
                                ovt_Type.setStd_work_hour_end(hour_end_Time);

				if(ovt_Type.getOID()==0){
					try{
						long oid = pstOvt_Type.insertExc(this.ovt_Type);
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
						long oid = pstOvt_Type.updateExc(this.ovt_Type);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidOvt_Type!= 0) {
					try {
						ovt_Type = PstOvt_Type.fetchExc(oidOvt_Type);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidOvt_Type != 0) {
                                    try {
                                        ovt_Type = PstOvt_Type.fetchExc(oidOvt_Type);
                                    } catch (DBException dbexc){
                                        excCode = dbexc.getErrorCode();
                                        msgString = getSystemMessage(excCode);
                                    } catch (Exception exc){
                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    }
                                }
                        break;

			case Command.DELETE :
				if (oidOvt_Type != 0){
					try{
						long oid = PstOvt_Type.deleteExc(oidOvt_Type);
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
