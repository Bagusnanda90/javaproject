/* 
 * Ctrl Name  		:  CtrlPosition.java 
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

public class CtrlLeaveTarget extends Control implements I_Language 
{
	public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "OID sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "OID code exist", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private LeaveTarget leaveTarget;
	private PstLeaveTarget pstLeaveTarget;
	private FrmLeaveTarget frmLeaveTarget;
	int language = LANGUAGE_DEFAULT;

	public CtrlLeaveTarget(HttpServletRequest request){
		msgString = "";
		leaveTarget = new LeaveTarget();
		try{
			pstLeaveTarget = new PstLeaveTarget(0);
		}catch(Exception e){;}
		frmLeaveTarget = new FrmLeaveTarget(request, leaveTarget);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmLeaveTarget.addError(frmLeaveTarget.FRM_FIELD_LEAVE_TARGET_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public LeaveTarget getLeaveTarget() { return leaveTarget; }

	public FrmLeaveTarget getForm() { return frmLeaveTarget; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidLeaveTarget){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidLeaveTarget != 0){
					try{
						leaveTarget = PstLeaveTarget.fetchExc(oidLeaveTarget);
					}catch(Exception exc){
					}
				}

				frmLeaveTarget.requestEntityObject(leaveTarget);

				if(frmLeaveTarget.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(leaveTarget.getOID()==0){
					try{
                                            boolean isEnable = PstLeaveTarget.checkTargetDate(this.leaveTarget.getTargetDate());
                                            if(!isEnable){
						long oid = pstLeaveTarget.insertExc(this.leaveTarget);
                                            }else{
						msgString = "Target at this period is alredy exists";
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
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
						long oid = pstLeaveTarget.updateExc(this.leaveTarget);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidLeaveTarget != 0) {
					try {
						leaveTarget = PstLeaveTarget.fetchExc(oidLeaveTarget);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidLeaveTarget != 0) {
                                    try{
                                        msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);

						leaveTarget = PstLeaveTarget.fetchExc(oidLeaveTarget);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidLeaveTarget != 0){
					try{
						long oid = PstLeaveTarget.deleteExc(oidLeaveTarget);
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
