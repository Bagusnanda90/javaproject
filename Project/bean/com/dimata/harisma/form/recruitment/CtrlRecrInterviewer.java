/* 
 * Ctrl Name  		:  CtrlRecrInterviewer.java 
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

public class CtrlRecrInterviewer extends Control implements I_Language 
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
	private RecrInterviewer recrInterviewer;
	private PstRecrInterviewer pstRecrInterviewer;
	private FrmRecrInterviewer frmRecrInterviewer;
	int language = LANGUAGE_DEFAULT;

	public CtrlRecrInterviewer(HttpServletRequest request){
		msgString = "";
		recrInterviewer = new RecrInterviewer();
		try{
			pstRecrInterviewer = new PstRecrInterviewer(0);
		}catch(Exception e){;}
		frmRecrInterviewer = new FrmRecrInterviewer(request, recrInterviewer);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmRecrInterviewer.addError(frmRecrInterviewer.FRM_FIELD_RECR_INTERVIEWER_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public RecrInterviewer getRecrInterviewer() { return recrInterviewer; } 

	public FrmRecrInterviewer getForm() { return frmRecrInterviewer; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidRecrInterviewer){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidRecrInterviewer != 0){
					try{
						recrInterviewer = PstRecrInterviewer.fetchExc(oidRecrInterviewer);
					}catch(Exception exc){
					}
				}

				frmRecrInterviewer.requestEntityObject(recrInterviewer);

				if(frmRecrInterviewer.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(recrInterviewer.getOID()==0){
					try{
						long oid = pstRecrInterviewer.insertExc(this.recrInterviewer);
                                                
                                                /****************************************
                                                 * insert juga ke hr_interviewer_factor *
                                                 ****************************************/
                                                Vector listfactor = PstRecrInterviewFactor.listAll();
                                                if (listfactor.size() > 0) {
                                                    for (int i=0; i<listfactor.size(); i++) {
                                                        RecrInterviewerFactor rierf = new RecrInterviewerFactor();
                                                        RecrInterviewFactor rif = (RecrInterviewFactor) listfactor.get(i);
                                                        rierf.setRecrInterviewFactorId(rif.getOID());
                                                        rierf.setRecrInterviewerId(oid);
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
						long oid = pstRecrInterviewer.updateExc(this.recrInterviewer);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidRecrInterviewer != 0) {
					try {
						recrInterviewer = PstRecrInterviewer.fetchExc(oidRecrInterviewer);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidRecrInterviewer != 0) {
					try {
						recrInterviewer = PstRecrInterviewer.fetchExc(oidRecrInterviewer);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidRecrInterviewer != 0){
					try{
						long oid = PstRecrInterviewer.deleteExc(oidRecrInterviewer);
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
