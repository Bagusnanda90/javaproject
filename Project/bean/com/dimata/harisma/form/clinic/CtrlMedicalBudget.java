
package com.dimata.harisma.form.clinic;

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
import com.dimata.harisma.entity.clinic.*;

/**
 *
 * @author bayu
 */

public class CtrlMedicalBudget extends Control implements I_Language {
    
        public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "Level sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Budget exists", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private MedicalBudget medicalBudget;
	private PstMedicalBudget pstMedicalBudget;
	private FrmMedicalBudget frmMedicalBudget;
	int language = LANGUAGE_DEFAULT;

        
	public CtrlMedicalBudget(HttpServletRequest request){
		msgString = "";
		medicalBudget = new MedicalBudget();
		try{
			pstMedicalBudget = new PstMedicalBudget(0);
		}catch(Exception e){System.out.println();}
		frmMedicalBudget = new FrmMedicalBudget(request, medicalBudget);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmMedicalBudget.addError(FrmMedicalBudget.FRM_FIELD_MEDICAL_CASE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public MedicalBudget getMedicalCase() { return medicalBudget; } 

	public FrmMedicalBudget getForm() { return frmMedicalBudget; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

        
	public int action(int cmd , long oidMedicalLevel, long oidMedicalCase ){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidMedicalLevel != 0 && oidMedicalCase!=0){
					try{
						medicalBudget = PstMedicalBudget.fetchExc(oidMedicalLevel, oidMedicalCase);
					}catch(Exception exc){
					}
				}
                              
				frmMedicalBudget.requestEntityObject(medicalBudget);
                                
                   		if(frmMedicalBudget.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(medicalBudget.getOID()==0){
					try{
						long oid = PstMedicalBudget.insertExc(this.medicalBudget);
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
						long oid = PstMedicalBudget.updateExc(this.medicalBudget);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidMedicalLevel != 0 && oidMedicalCase!=0) {
					try {
						medicalBudget = PstMedicalBudget.fetchExc(oidMedicalLevel, oidMedicalCase);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidMedicalLevel != 0 && oidMedicalCase!=0) {
					try {
						medicalBudget = PstMedicalBudget.fetchExc(oidMedicalLevel, oidMedicalCase);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidMedicalLevel != 0 && oidMedicalCase!=0) {
					try{
						long oid = PstMedicalBudget.deleteExc(oidMedicalLevel, oidMedicalCase);
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
