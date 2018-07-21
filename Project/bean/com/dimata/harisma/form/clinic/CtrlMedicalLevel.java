
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

public class CtrlMedicalLevel extends Control implements I_Language {
    
        public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "Level sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Level exists", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private MedicalLevel medicalLevel;
	private PstMedicalLevel pstMedicalLevel;
	private FrmMedicalLevel frmMedicalLevel;
	int language = LANGUAGE_DEFAULT;

        
	public CtrlMedicalLevel(HttpServletRequest request){
		msgString = "";
		medicalLevel = new MedicalLevel();
		try{
			pstMedicalLevel = new PstMedicalLevel(0);
		}catch(Exception e){;}
		frmMedicalLevel = new FrmMedicalLevel(request, medicalLevel);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmMedicalLevel.addError(frmMedicalLevel.FRM_FIELD_MEDICAL_LEVEL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public MedicalLevel getMedicalLevel() { return medicalLevel; } 

	public FrmMedicalLevel getForm() { return frmMedicalLevel; }

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
						medicalLevel = PstMedicalLevel.fetchExc(oidMedicalRecord);
					}catch(Exception exc){
					}
				}
                              
				frmMedicalLevel.requestEntityObject(medicalLevel);
                                
                   		if(frmMedicalLevel.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(medicalLevel.getOID()==0){
					try{
						long oid = pstMedicalLevel.insertExc(this.medicalLevel);
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
						long oid = pstMedicalLevel.updateExc(this.medicalLevel);
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
						medicalLevel = PstMedicalLevel.fetchExc(oidMedicalRecord);
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
						medicalLevel = PstMedicalLevel.fetchExc(oidMedicalRecord);
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
						long oid = PstMedicalLevel.deleteExc(oidMedicalRecord);
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
