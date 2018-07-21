/*
 * CtrlSalaryLevel.java
 *
 * Created on April 3, 2007, 3:15 AM
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
 * @author  Ana
 */
public class CtrlSalaryLevel extends Control implements I_Language{
    
    /** Creates a new instance of CtrlSalaryLevel */
    public CtrlSalaryLevel() {
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
	private SalaryLevel salaryLevel;
	private PstSalaryLevel pstSalaryLevel;
	private FrmSalaryLevel frmSalaryLevel;
        
        int language = LANGUAGE_DEFAULT;
        
         public CtrlSalaryLevel(HttpServletRequest request){
		msgString = "";
		salaryLevel = new SalaryLevel();
		try{
			pstSalaryLevel = new PstSalaryLevel(0);
		}catch(Exception e){;}
		frmSalaryLevel = new FrmSalaryLevel(request, salaryLevel);
	}
         
          private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmSalaryLevel.addError(frmSalaryLevel.FRM_FIELD_SAL_LEVEL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
        
        public SalaryLevel getSalaryLevel() { return salaryLevel; } 

	public FrmSalaryLevel getForm() { return frmSalaryLevel; }
        
        public String getMessage(){ return msgString; }

	public int getStart() { return start; }
        
          public int action(int cmd , long oidSalaryLevel){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidSalaryLevel != 0){
					try{
						salaryLevel = PstSalaryLevel.fetchExc(oidSalaryLevel);
					}catch(Exception exc){
					}
				}

				frmSalaryLevel.requestEntityObject(salaryLevel);

				if(frmSalaryLevel.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(salaryLevel.getOID()==0){
					try{
						long oid = pstSalaryLevel.insertExc(this.salaryLevel);
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
						long oid = pstSalaryLevel.updateExc(this.salaryLevel);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidSalaryLevel != 0) {
					try {
						salaryLevel = PstSalaryLevel.fetchExc(oidSalaryLevel);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
                            if (oidSalaryLevel != 0) {
                                try {
                                    salaryLevel = PstSalaryLevel.fetchExc(oidSalaryLevel);
                                } catch (DBException dbexc){
                                    excCode = dbexc.getErrorCode();
                                    msgString = getSystemMessage(excCode);
                                } catch (Exception exc){
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                }
                            }
                        break;
			case Command.DELETE :
				if (oidSalaryLevel != 0){
					try{
						long oid = PstSalaryLevel.deleteExc(oidSalaryLevel);
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
