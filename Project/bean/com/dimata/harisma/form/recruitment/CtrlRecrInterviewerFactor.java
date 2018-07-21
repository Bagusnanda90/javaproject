/* 
 * Ctrl Name  		:  CtrlRecrInterviewerFactor.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.recruitment;

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
import com.dimata.harisma.entity.recruitment.*;

public class CtrlRecrInterviewerFactor extends Control implements I_Language 
{
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
	private RecrInterviewerFactor recrInterviewerFactor;
	private PstRecrInterviewerFactor pstRecrInterviewerFactor;
	private FrmRecrInterviewerFactor frmRecrInterviewerFactor;
	int language = LANGUAGE_DEFAULT;

	public CtrlRecrInterviewerFactor(HttpServletRequest request){
		msgString = "";
		recrInterviewerFactor = new RecrInterviewerFactor();
		try{
			pstRecrInterviewerFactor = new PstRecrInterviewerFactor(0);
		}catch(Exception e){;}
		frmRecrInterviewerFactor = new FrmRecrInterviewerFactor(request, recrInterviewerFactor);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmRecrInterviewerFactor.addError(frmRecrInterviewerFactor.FRM_FIELD_RECR_INTERVIEWER_FACTOR_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public RecrInterviewerFactor getRecrInterviewerFactor() { return recrInterviewerFactor; } 

	public FrmRecrInterviewerFactor getForm() { return frmRecrInterviewerFactor; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidRecrInterviewerFactor){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidRecrInterviewerFactor != 0){
					try{
						recrInterviewerFactor = PstRecrInterviewerFactor.fetchExc(oidRecrInterviewerFactor);
					}catch(Exception exc){
					}
				}

				frmRecrInterviewerFactor.requestEntityObject(recrInterviewerFactor);

				if(frmRecrInterviewerFactor.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(recrInterviewerFactor.getOID()==0){
					try{
						long oid = pstRecrInterviewerFactor.insertExc(this.recrInterviewerFactor);
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
						long oid = pstRecrInterviewerFactor.updateExc(this.recrInterviewerFactor);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidRecrInterviewerFactor != 0) {
					try {
						recrInterviewerFactor = PstRecrInterviewerFactor.fetchExc(oidRecrInterviewerFactor);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidRecrInterviewerFactor != 0) {
					try {
						recrInterviewerFactor = PstRecrInterviewerFactor.fetchExc(oidRecrInterviewerFactor);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidRecrInterviewerFactor != 0){
					try{
						long oid = PstRecrInterviewerFactor.deleteExc(oidRecrInterviewerFactor);
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
