/* 
 * Ctrl Name  		:  CtrlRecrWorkHistory.java 
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

public class CtrlRecrWorkHistory extends Control implements I_Language 
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
	private RecrWorkHistory recrWorkHistory;
	private PstRecrWorkHistory pstRecrWorkHistory;
	private FrmRecrWorkHistory frmRecrWorkHistory;
	int language = LANGUAGE_DEFAULT;

	public CtrlRecrWorkHistory(HttpServletRequest request){
		msgString = "";
		recrWorkHistory = new RecrWorkHistory();
		try{
			pstRecrWorkHistory = new PstRecrWorkHistory(0);
		}catch(Exception e){;}
		frmRecrWorkHistory = new FrmRecrWorkHistory(request, recrWorkHistory);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmRecrWorkHistory.addError(frmRecrWorkHistory.FRM_FIELD_RECR_WORK_HISTORY_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public RecrWorkHistory getRecrWorkHistory() { return recrWorkHistory; } 

	public FrmRecrWorkHistory getForm() { return frmRecrWorkHistory; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd, long oidRecrWorkHistory, long oidRecrApplication){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidRecrWorkHistory != 0){
					try{
						recrWorkHistory = PstRecrWorkHistory.fetchExc(oidRecrWorkHistory);
					}catch(Exception exc){
					}
				}
                                //======= tambahan
                                recrWorkHistory.setOID(oidRecrWorkHistory);

				frmRecrWorkHistory.requestEntityObject(recrWorkHistory);

                                //======= tambahan
                                recrWorkHistory.setRecrApplicationId(oidRecrApplication);

				if(frmRecrWorkHistory.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(recrWorkHistory.getOID()==0){
					try{
						long oid = pstRecrWorkHistory.insertExc(this.recrWorkHistory);
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
						long oid = pstRecrWorkHistory.updateExc(this.recrWorkHistory);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidRecrWorkHistory != 0) {
					try {
						recrWorkHistory = PstRecrWorkHistory.fetchExc(oidRecrWorkHistory);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidRecrWorkHistory != 0) {
					try {
						recrWorkHistory = PstRecrWorkHistory.fetchExc(oidRecrWorkHistory);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidRecrWorkHistory != 0){
					try{
						long oid = PstRecrWorkHistory.deleteExc(oidRecrWorkHistory);
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
