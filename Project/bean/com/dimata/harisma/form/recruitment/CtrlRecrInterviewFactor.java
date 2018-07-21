/* 
 * Ctrl Name  		:  CtrlRecrInterviewFactor.java 
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

public class CtrlRecrInterviewFactor extends Control implements I_Language 
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
	private RecrInterviewFactor recrInterviewFactor;
	private PstRecrInterviewFactor pstRecrInterviewFactor;
	private FrmRecrInterviewFactor frmRecrInterviewFactor;
	int language = LANGUAGE_DEFAULT;

	public CtrlRecrInterviewFactor(HttpServletRequest request){
		msgString = "";
		recrInterviewFactor = new RecrInterviewFactor();
		try{
			pstRecrInterviewFactor = new PstRecrInterviewFactor(0);
		}catch(Exception e){;}
		frmRecrInterviewFactor = new FrmRecrInterviewFactor(request, recrInterviewFactor);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmRecrInterviewFactor.addError(frmRecrInterviewFactor.FRM_FIELD_RECR_INTERVIEW_FACTOR_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public RecrInterviewFactor getRecrInterviewFactor() { return recrInterviewFactor; } 

	public FrmRecrInterviewFactor getForm() { return frmRecrInterviewFactor; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidRecrInterviewFactor){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidRecrInterviewFactor != 0){
					try{
						recrInterviewFactor = PstRecrInterviewFactor.fetchExc(oidRecrInterviewFactor);
					}catch(Exception exc){
					}
				}

				frmRecrInterviewFactor.requestEntityObject(recrInterviewFactor);

				if(frmRecrInterviewFactor.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(recrInterviewFactor.getOID()==0){
					try{
						long oid = pstRecrInterviewFactor.insertExc(this.recrInterviewFactor);
                                                
                                                /****************************************
                                                 * insert juga ke hr_interviewer_factor *
                                                 ****************************************/
                                                Vector listinterviewer = PstRecrInterviewer.listAll();
                                                if (listinterviewer.size() > 0) {
                                                    for (int i=0; i<listinterviewer.size(); i++) {
                                                        RecrInterviewerFactor rierf = new RecrInterviewerFactor();
                                                        RecrInterviewer ri = (RecrInterviewer) listinterviewer.get(i);
                                                        rierf.setRecrInterviewFactorId(oid);
                                                        rierf.setRecrInterviewerId(ri.getOID());
                                                        PstRecrInterviewerFactor.insertExc(rierf);
                                                    }
                                                }
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
						long oid = pstRecrInterviewFactor.updateExc(this.recrInterviewFactor);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidRecrInterviewFactor != 0) {
					try {
						recrInterviewFactor = PstRecrInterviewFactor.fetchExc(oidRecrInterviewFactor);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidRecrInterviewFactor != 0) {
					try {
						recrInterviewFactor = PstRecrInterviewFactor.fetchExc(oidRecrInterviewFactor);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidRecrInterviewFactor != 0){
					try{
						long oid = PstRecrInterviewFactor.deleteExc(oidRecrInterviewFactor);
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
