/* 
 * Ctrl Name  		:  CtrlImageAssign.java 
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

public class CtrlImageAssign extends Control implements I_Language 
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
	private ImageAssign imageAssign;
	private PstImageAssign pstImageAssign;
	private FrmImageAssign frmImageAssign;
	int language = LANGUAGE_DEFAULT;

	public CtrlImageAssign(HttpServletRequest request){
		msgString = "";
		imageAssign = new ImageAssign();
		try{
			pstImageAssign = new PstImageAssign(0);
		}catch(Exception e){;}
		frmImageAssign = new FrmImageAssign(request, imageAssign);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmImageAssign.addError(frmImageAssign.FRM_FIELD_IMG_ASSIGN_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public ImageAssign getImageAssign() { return imageAssign; } 

	public FrmImageAssign getForm() { return frmImageAssign; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidImageAssign){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidImageAssign != 0){
					try{
						imageAssign = PstImageAssign.fetchExc(oidImageAssign);
					}catch(Exception exc){
					}
				}

				frmImageAssign.requestEntityObject(imageAssign);

				if(frmImageAssign.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(imageAssign.getOID()==0){
					try{
                                                /*
                                                Vector oidExist = new Vector();
                                                
                                                String where = PstImageAssign.fieldNames[PstImageAssign.FLD_EMPLOYEE_OID]+
                                                        " = "+this.imageAssign.getEmployeeOid();
                                                try{
                                                    oidExist = PstImageAssign.list(0, 0, where, null);
                                                }catch(Exception e){
                                                    System.out.println("Exception "+e.toString());
                                                }
                                                
                                                if(oidExist != null && oidExist.size()>0){
                                                    msgString = FRMMessage.getMsg(FRMMessage.MSG_IN_EXIST);
                                                    return RSLT_FORM_INCOMPLETE ;                                                    
                                                }
                                                */
						long oid = pstImageAssign.insertExc(this.imageAssign);
                                                
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
						long oid = pstImageAssign.updateExc(this.imageAssign);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidImageAssign != 0) {
					try {
						imageAssign = PstImageAssign.fetchExc(oidImageAssign);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidImageAssign != 0) {
					try {
                                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                                            imageAssign = PstImageAssign.fetchExc(oidImageAssign);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidImageAssign != 0){
					try{
						long oid = PstImageAssign.deleteExc(oidImageAssign);
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
