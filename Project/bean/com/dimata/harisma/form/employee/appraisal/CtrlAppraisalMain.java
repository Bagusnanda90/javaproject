/* 
 * Ctrl Name  		:  CtrlAppraisalMain.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.employee.appraisal;

/* java package */ 

import com.dimata.harisma.entity.employee.appraisal.AppraisalMain;
import com.dimata.harisma.entity.employee.appraisal.PstAppraisalMain;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;


public class CtrlAppraisalMain extends Control implements I_Language 
{
	public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "Data sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private AppraisalMain appraisalMain;
	private PstAppraisalMain pstAppraisalMain;
	private FrmAppraisalMain frmAppraisalMain;


	int language = LANGUAGE_DEFAULT;

	public CtrlAppraisalMain(HttpServletRequest request){
            msgString = "";
            appraisalMain = new AppraisalMain();
            try{
                    pstAppraisalMain = new PstAppraisalMain(0);
            }catch(Exception e){;}

            frmAppraisalMain = new FrmAppraisalMain(request,appraisalMain);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
                    case I_DBExceptionInfo.MULTIPLE_ID :
                            this.frmAppraisalMain.addError(frmAppraisalMain.FRM_FIELD_APP_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public AppraisalMain getAppraisalMain() { return appraisalMain; } 

	public FrmAppraisalMain getForm() { return frmAppraisalMain; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidAppraisalMain){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidAppraisalMain != 0){
					try{
						appraisalMain = PstAppraisalMain.fetchExc(oidAppraisalMain);
					}catch(Exception exc){
					}
				}

				frmAppraisalMain.requestEntityObject(appraisalMain); 

                                //System.out.println("frmAppraisalMain.errorSize() : "+frmAppraisalMain.errorSize());
                                
				if(frmAppraisalMain.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
				if(appraisalMain.getOID()==0){
					try{
						long oid = pstAppraisalMain.insertExc(this.appraisalMain);
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
						long oid = pstAppraisalMain.updateExc(this.appraisalMain);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidAppraisalMain != 0) {
					try {
						appraisalMain = PstAppraisalMain.fetchExc(oidAppraisalMain);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidAppraisalMain != 0) {
					try {
                                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                                            appraisalMain = PstAppraisalMain.fetchExc(oidAppraisalMain);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidAppraisalMain != 0){
					try{
						long oid = PstAppraisalMain.deleteExc(oidAppraisalMain);
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
