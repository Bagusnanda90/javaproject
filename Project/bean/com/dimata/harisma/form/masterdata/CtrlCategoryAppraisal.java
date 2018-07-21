/* 
 * Ctrl Name  		:  CtrlCategoryAppraisal.java 
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

package com.dimata.harisma.form.masterdata;

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
import com.dimata.harisma.entity.masterdata.*;

public class CtrlCategoryAppraisal extends Control implements I_Language 
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
	private CategoryAppraisal categoryAppraisal;
	private PstCategoryAppraisal pstCategoryAppraisal;
	private FrmCategoryAppraisal frmCategoryAppraisal;
	int language = LANGUAGE_DEFAULT;

	public CtrlCategoryAppraisal(HttpServletRequest request){
		msgString = "";
		categoryAppraisal = new CategoryAppraisal();
		try{
			pstCategoryAppraisal = new PstCategoryAppraisal(0);
		}catch(Exception e){;}
		frmCategoryAppraisal = new FrmCategoryAppraisal(request, categoryAppraisal);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmCategoryAppraisal.addError(frmCategoryAppraisal.FRM_FIELD_CATEGORY_APPRAISAL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public CategoryAppraisal getCategoryAppraisal() { return categoryAppraisal; } 

	public FrmCategoryAppraisal getForm() { return frmCategoryAppraisal; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidCategoryAppraisal, long oidGroupCategory){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidCategoryAppraisal != 0){
					try{
						categoryAppraisal = PstCategoryAppraisal.fetchExc(oidCategoryAppraisal);
					}catch(Exception exc){
					}
				}

            	categoryAppraisal.setOID(oidCategoryAppraisal);
				frmCategoryAppraisal.requestEntityObject(categoryAppraisal);
                categoryAppraisal.setGroupCategoryId(oidGroupCategory);

				if(frmCategoryAppraisal.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(categoryAppraisal.getOID()==0){
					try{
						long oid = pstCategoryAppraisal.insertExc(this.categoryAppraisal);
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
						long oid = pstCategoryAppraisal.updateExc(this.categoryAppraisal);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidCategoryAppraisal != 0) {
					try {
						categoryAppraisal = PstCategoryAppraisal.fetchExc(oidCategoryAppraisal);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidCategoryAppraisal != 0) {
					try {
                        if(PstCategoryAppraisal.checkMaster(oidCategoryAppraisal))
                        	msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        else
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);

						categoryAppraisal = PstCategoryAppraisal.fetchExc(oidCategoryAppraisal);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidCategoryAppraisal != 0){
					try{
						long oid = PstCategoryAppraisal.deleteExc(oidCategoryAppraisal);
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
