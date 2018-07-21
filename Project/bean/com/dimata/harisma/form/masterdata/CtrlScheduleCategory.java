/* 
 * Ctrl Name  		:  CtrlScheduleCategory.java 
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

public class CtrlScheduleCategory extends Control implements I_Language 
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
	private ScheduleCategory scheduleCategory;
	private PstScheduleCategory pstScheduleCategory;
	private FrmScheduleCategory frmScheduleCategory;
	int language = LANGUAGE_DEFAULT;

	public CtrlScheduleCategory(HttpServletRequest request){
		msgString = "";
		scheduleCategory = new ScheduleCategory();
		try{
			pstScheduleCategory = new PstScheduleCategory(0);
		}catch(Exception e){;}
		frmScheduleCategory = new FrmScheduleCategory(request, scheduleCategory);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmScheduleCategory.addError(frmScheduleCategory.FRM_FIELD_SCHEDULE_CATEGORY_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public ScheduleCategory getScheduleCategory() { return scheduleCategory; } 

	public FrmScheduleCategory getForm() { return frmScheduleCategory; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidScheduleCategory, String oidDeleteAll){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidScheduleCategory != 0){
					try{
						scheduleCategory = PstScheduleCategory.fetchExc(oidScheduleCategory);
					}catch(Exception exc){
					}
				}

				frmScheduleCategory.requestEntityObject(scheduleCategory);

				if(frmScheduleCategory.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(scheduleCategory.getOID()==0){
					try{
						long oid = pstScheduleCategory.insertExc(this.scheduleCategory);
                                                if (oid>0){
                                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
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
						long oid = pstScheduleCategory.updateExc(this.scheduleCategory);
                                                if (oid>0){
                                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                                                }
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidScheduleCategory != 0) {
					try {
						scheduleCategory = PstScheduleCategory.fetchExc(oidScheduleCategory);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidScheduleCategory != 0) {
					try {
                        if(PstScheduleCategory.checkMaster(oidScheduleCategory))
                        	msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        else
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);

						scheduleCategory = PstScheduleCategory.fetchExc(oidScheduleCategory);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidScheduleCategory != 0){
					try{
						long oid = PstScheduleCategory.deleteExc(oidScheduleCategory);
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
                            
                            case Command.DELETEALL:
                            //splits => untuk mengurutkan beberapa data menjadi baris data
                            String[] splits = oidDeleteAll.split(",");
                            for (String asset : splits) {
                                if (asset != "") {
                                    long oidPer = Long.parseLong(asset);
                                    if (oidPer != 0) {
                                        try {
                                            long oid = PstScheduleCategory.deleteExc(oidPer);
                                            if (oid != 0) {
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                                excCode = RSLT_OK;
                                            } else {
                                                msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                                excCode = RSLT_FORM_INCOMPLETE;
                                            }
                                        } catch (DBException dbexc) {
                                            excCode = dbexc.getErrorCode();
                                            msgString = getSystemMessage(excCode);
                                        } catch (Exception exc) {
                                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                        }
                                    }
                                }
                            }
                            break;

			default :

		}
		return rsCode;
	}
}
