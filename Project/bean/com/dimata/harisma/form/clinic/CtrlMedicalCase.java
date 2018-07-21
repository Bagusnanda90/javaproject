
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

public class CtrlMedicalCase extends Control implements I_Language {
    
        public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "Level sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Case exists", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private MedicalCase MedicalCase;
	private PstMedicalCase pstMedicalCase;
	private FrmMedicalCase frmMedicalCase;
	int language = LANGUAGE_DEFAULT;

        
	public CtrlMedicalCase(HttpServletRequest request){
		msgString = "";
		MedicalCase = new MedicalCase();
		try{
			pstMedicalCase = new PstMedicalCase(0);
		}catch(Exception e){System.out.println();}
		frmMedicalCase = new FrmMedicalCase(request, MedicalCase);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmMedicalCase.addError(FrmMedicalCase.FRM_FIELD_MEDICAL_CASE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public MedicalCase getMedicalCase() { return MedicalCase; } 

	public FrmMedicalCase getForm() { return frmMedicalCase; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

        
	public int action(int cmd , long oidMedicalRecord){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidMedicalRecord != 0){
					try{
						MedicalCase = PstMedicalCase.fetchExc(oidMedicalRecord);
					}catch(Exception exc){
					}
				}
                              
				frmMedicalCase.requestEntityObject(MedicalCase);
                                
                   		if(frmMedicalCase.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(MedicalCase.getOID()==0){
					try{
						long oid = PstMedicalCase.insertExc(this.MedicalCase);
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
						long oid = PstMedicalCase.updateExc(this.MedicalCase);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidMedicalRecord != 0) {
					try {
						MedicalCase = PstMedicalCase.fetchExc(oidMedicalRecord);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidMedicalRecord != 0) {
					try {
						MedicalCase = PstMedicalCase.fetchExc(oidMedicalRecord);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidMedicalRecord != 0){
					try{
						long oid = PstMedicalCase.deleteExc(oidMedicalRecord);
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
