/* 
 * Ctrl Name  		:  CtrlFingerPrint.java 
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

public class CtrlFingerPrint extends Control implements I_Language 
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
	private FingerPrint fingerPrint;
	private PstFingerPrint pstFingerPrint;
	private FrmFingerPrint frmFingerPrint;
	int language = LANGUAGE_DEFAULT;

	public CtrlFingerPrint(HttpServletRequest request){
		msgString = "";
		fingerPrint = new FingerPrint();
		try{
			pstFingerPrint = new PstFingerPrint(0);
		}catch(Exception e){;}
		frmFingerPrint = new FrmFingerPrint(request, fingerPrint);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmFingerPrint.addError(frmFingerPrint.FRM_FIELD_FINGER_PRINT_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public FingerPrint getFingerPrint() { return fingerPrint; } 

	public FrmFingerPrint getForm() { return frmFingerPrint; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidFingerPrint){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidFingerPrint != 0){
					try{
						fingerPrint = PstFingerPrint.fetchExc(oidFingerPrint);
					}catch(Exception exc){
					}
				}

				frmFingerPrint.requestEntityObject(fingerPrint);

				if(frmFingerPrint.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(fingerPrint.getOID()==0){
					try{
                                            if(PstFingerPrint.checkEmpNum(this.fingerPrint.getEmployeeNum())){
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                                                excCode = RSLT_EST_CODE_EXIST;
                                            }else{
						long oid = pstFingerPrint.insertExc(this.fingerPrint);
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
						long oid = pstFingerPrint.updateExc(this.fingerPrint);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidFingerPrint != 0) {
					try {
						fingerPrint = PstFingerPrint.fetchExc(oidFingerPrint);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidFingerPrint != 0) {
					try {
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);

						fingerPrint = PstFingerPrint.fetchExc(oidFingerPrint);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidFingerPrint != 0){
					try{
						long oid = PstFingerPrint.deleteExc(oidFingerPrint);
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
