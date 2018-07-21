/* 
 * Ctrl Name  		:  CtrlAssessmentFormItem.java 
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

package com.dimata.harisma.form.employee.assessment;

/* java package */ 

import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormItem;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;


public class CtrlAssessmentFormItem extends Control implements I_Language 
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
	private AssessmentFormItem assFormItem;
	private PstAssessmentFormItem pstAssFormItem;
	private FrmAssessmentFormItem frmAssFormItem;


	int language = LANGUAGE_DEFAULT;

	public CtrlAssessmentFormItem(HttpServletRequest request){
            msgString = "";
            assFormItem = new AssessmentFormItem();
            try{
                    pstAssFormItem = new PstAssessmentFormItem(0);
            }catch(Exception e){;}

            frmAssFormItem = new FrmAssessmentFormItem(request,assFormItem);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
                    case I_DBExceptionInfo.MULTIPLE_ID :
                            this.frmAssFormItem.addError(frmAssFormItem.FRM_FIELD_ASS_FORM_ITEM_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public AssessmentFormItem getAssessmentFormItem() { return assFormItem; } 

	public FrmAssessmentFormItem getForm() { return frmAssFormItem; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidAssessmentFormItem){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidAssessmentFormItem != 0){
					try{
						assFormItem = PstAssessmentFormItem.fetchExc(oidAssessmentFormItem);
					}catch(Exception exc){
					}
				}

				frmAssFormItem.requestEntityObject(assFormItem); 

                                //System.out.println("frmAssFormItem.errorSize() : "+frmAssFormItem.errorSize());
                                
				if(frmAssFormItem.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
				if(assFormItem.getOID()==0){
					try{
						long oid = pstAssFormItem.insertExc(this.assFormItem);
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
						long oid = pstAssFormItem.updateExc(this.assFormItem);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidAssessmentFormItem != 0) {
					try {
						assFormItem = PstAssessmentFormItem.fetchExc(oidAssessmentFormItem);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN) + "  Exception : " + exc ;
					}
				}
				break;

			case Command.ASK :
				if (oidAssessmentFormItem != 0) {
					try {
                                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                                            assFormItem = PstAssessmentFormItem.fetchExc(oidAssessmentFormItem);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidAssessmentFormItem != 0){
					try{
						long oid = PstAssessmentFormItem.deleteExc(oidAssessmentFormItem);
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
