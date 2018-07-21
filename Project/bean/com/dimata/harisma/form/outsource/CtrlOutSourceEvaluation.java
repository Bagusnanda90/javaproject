/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.form.outsource;

/**
 *
 * @author Wiweka
 */
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.harisma.entity.outsource.OutSourceEvaluation;
import com.dimata.harisma.entity.outsource.PstOutSourceEvaluation;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;
public class CtrlOutSourceEvaluation extends Control implements I_Language
{
	public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "Kode status pegawai sudah ada ...", "Data tidak lengkap"},
		{"Succes", "Can not process", "Code already exist ...", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private OutSourceEvaluation outSourceEvaluation;
	private PstOutSourceEvaluation pstOutSourceEvaluation;
	private FrmOutSourceEvaluation frmOutSourceEvaluation;
	int language = LANGUAGE_DEFAULT;

	public CtrlOutSourceEvaluation(HttpServletRequest request){
		msgString = "";
		outSourceEvaluation = new OutSourceEvaluation();
		try{
			pstOutSourceEvaluation = new PstOutSourceEvaluation(0);
		}catch(Exception e){;}
		frmOutSourceEvaluation = new FrmOutSourceEvaluation(request, outSourceEvaluation);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmOutSourceEvaluation.addError(frmOutSourceEvaluation.FRM_FIELD_OUTSOURCE_EVAL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public OutSourceEvaluation getOutSourceEvaluation() { return outSourceEvaluation; }

	public FrmOutSourceEvaluation getForm() { return frmOutSourceEvaluation; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidOutSourceEvaluation){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidOutSourceEvaluation != 0){
					try{
						outSourceEvaluation = PstOutSourceEvaluation.fetchExc(oidOutSourceEvaluation);
					}catch(Exception exc){
					}
				}

				frmOutSourceEvaluation.requestEntityObject(outSourceEvaluation);

				if(frmOutSourceEvaluation.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(outSourceEvaluation.getOID()==0){
					try{
						long oid = pstOutSourceEvaluation.insertExc(this.outSourceEvaluation);
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
						long oid = pstOutSourceEvaluation.updateExc(this.outSourceEvaluation);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}

				}
				break;

			case Command.EDIT :
				if (oidOutSourceEvaluation != 0) {
					try {
						outSourceEvaluation = PstOutSourceEvaluation.fetchExc(oidOutSourceEvaluation);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidOutSourceEvaluation != 0) {
					try {
						outSourceEvaluation = PstOutSourceEvaluation.fetchExc(oidOutSourceEvaluation);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidOutSourceEvaluation != 0){
					try{
						long oid = PstOutSourceEvaluation.deleteExc(oidOutSourceEvaluation);
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

