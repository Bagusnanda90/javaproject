/* 
 * Ctrl Name  		:  CtrlRecrApplication.java 
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
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPosition;
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

public class CtrlRecrApplication extends Control implements I_Language 
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
	private RecrApplication recrApplication;
	private PstRecrApplication pstRecrApplication;
	private FrmRecrApplication frmRecrApplication;
	int language = LANGUAGE_DEFAULT;

	public CtrlRecrApplication(HttpServletRequest request){
		msgString = "";
		recrApplication = new RecrApplication();
		try{
			pstRecrApplication = new PstRecrApplication(0);
		}catch(Exception e){;}
		frmRecrApplication = new FrmRecrApplication(request, recrApplication);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmRecrApplication.addError(frmRecrApplication.FRM_FIELD_RECR_APPLICATION_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public RecrApplication getRecrApplication() { return recrApplication; } 

	public FrmRecrApplication getForm() { return frmRecrApplication; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidRecrApplication, int from){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidRecrApplication != 0){
					try{
						recrApplication = PstRecrApplication.fetchExc(oidRecrApplication);
					}catch(Exception exc){
					}
				}

				frmRecrApplication.requestEntityObject(recrApplication);

				if(frmRecrApplication.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(recrApplication.getOID()==0){
					try{
						long oid = pstRecrApplication.insertExc(this.recrApplication);
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
                                            if (from==1){
                                                
                                                long oid = pstRecrApplication.updateExcfamilly(this.recrApplication);
                                            } else if (from==2){
                                                long oid = pstRecrApplication.updateExcSkill(this.recrApplication);
                                            } else{
						long oid = pstRecrApplication.updateExc(this.recrApplication);
                                            
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
				if (oidRecrApplication != 0) {
					try {
						recrApplication = PstRecrApplication.fetchExc(oidRecrApplication);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
                        case Command.ACTIVATE:
				if (oidRecrApplication != 0) {
                                    Employee employee = new Employee();
                                    
					try{
                                                recrApplication = PstRecrApplication.fetchExc(oidRecrApplication);
                                                employee.setFullName(recrApplication.getFullName());
                                                
                                                long positionId = PstPosition.getPositionId(recrApplication.getPosition());
                                                
                                                employee.setPositionId(positionId);
                                                employee.setBirthDate(recrApplication.getBirthDate());
                                                employee.setBirthPlace(recrApplication.getBirthPlace());
                                                employee.setAddressPermanent(recrApplication.getAddress());
                                                employee.setIndentCardNr(recrApplication.getIdCardNum());
                                                employee.setPostalCode(recrApplication.getPostalCode());
                                                employee.setAstekNum(recrApplication.getAstekNum());
                                                employee.setBloodType(recrApplication.getBloodType());
                                                employee.setSex(recrApplication.getSex());
                                                employee.setReligionId(recrApplication.getReligionId());
                                                employee.setPhone(recrApplication.getPhone());
                                                
                                                employee.setMaritalId(recrApplication.getMaritalId());
                                                long oid = PstEmployee.insertExc(employee);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}

				}
				break;    

			case Command.ASK :
				if (oidRecrApplication != 0) {
					try {
						recrApplication = PstRecrApplication.fetchExc(oidRecrApplication);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidRecrApplication != 0){
					try{
						long oid = PstRecrApplication.deleteExc(oidRecrApplication);
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
