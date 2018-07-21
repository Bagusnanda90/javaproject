/* 
 * Ctrl Name  		:  CtrlExperience.java 
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

package com.dimata.harisma.form.employee;

/* java package */ 
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
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
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.PstLanguage;
import com.dimata.system.entity.PstSystemProperty;

public class CtrlExperience extends Control implements I_Language 
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
	private Experience experience;
	private PstExperience pstExperience;
	private FrmExperience frmExperience;
	int language = LANGUAGE_DEFAULT;

	public CtrlExperience(HttpServletRequest request){
		msgString = "";
		experience = new Experience();
		try{
			pstExperience = new PstExperience(0);
		}catch(Exception e){;}
		frmExperience = new FrmExperience(request, experience);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmExperience.addError(frmExperience.FRM_FIELD_WORK_HISTORY_PAST_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public Experience getExperience() { return experience; } 

	public FrmExperience getForm() { return frmExperience; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidExperience, long oidEmployee, HttpServletRequest request, String loginName, long userId){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
                //long sysLog = 1;
                String logDetail = "";
                Date nowDate = new Date();
                
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
                                Experience prevExperience = null;
				if(oidExperience != 0){
					try{
						experience = PstExperience.fetchExc(oidExperience);
                                                
                                                if(sysLog == 1){
                                                    prevExperience = PstExperience.fetchExc(oidExperience);

                                                }
                                                
					}catch(Exception exc){
					}
				}

            	experience.setOID(oidExperience);

				frmExperience.requestEntityObject(experience);

                experience.setEmployeeId(oidEmployee);

				if(frmExperience.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(experience.getOID()==0){
					try{
						long oid = pstExperience.insertExc(this.experience);
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
						long oid = pstExperience.updateExc(this.experience);
                                                
                                                // logHistory
                                                if(sysLog == 1){
                                                    experience = PstExperience.fetchExc(oid);

                                                    if(experience != null && prevExperience != null){
                                                        if(!experience.getCompanyName().equals(prevExperience.getCompanyName())){
                                                            logDetail = logDetail+" Company Name : "+prevExperience.getCompanyName()+" >> "+experience.getCompanyName()+" UPDATED</br>";
                                                        }
                                                        if(experience.getStartDate() != prevExperience.getStartDate()){
                                                            logDetail = logDetail+" Start Date : "+prevExperience.getStartDate()+" >> "+experience.getStartDate()+" UPDATED</br>";
                                                        }
                                                        if(experience.getEndDate() != prevExperience.getEndDate()){
                                                            logDetail = logDetail+" End Date : "+prevExperience.getEndDate()+" >> "+experience.getEndDate()+" UPDATED</br>";
                                                        }
                                                        if(!experience.getPosition().equals(prevExperience.getPosition())){
                                                            logDetail = logDetail+" Position : "+prevExperience.getPosition()+" >> "+experience.getPosition()+" UPDATED</br>";
                                                        }
                                                        if(!experience.getMoveReason().equals(prevExperience.getMoveReason())){
                                                            logDetail = logDetail+" Move Reason : "+prevExperience.getMoveReason()+" >> "+experience.getMoveReason()+" UPDATED</br>";
                                                        }
                                                        if(experience.getProviderID() != prevExperience.getProviderID()){
                                                            ContactList prevContList = PstContactList.fetchExc(prevExperience.getProviderID());
                                                            String prevProvider = prevContList.getCompName();
                                                            ContactList contList = PstContactList.fetchExc(experience.getProviderID());
                                                            String provider = contList.getCompName();
                                                            logDetail = logDetail+" Provider : "+prevProvider+" >> "+provider+" UPDATED</br>";
                                                        }
                                                        
                                                        String className = experience.getClass().getName();

                                                        LogSysHistory logSysHistory = new LogSysHistory();

                                                        String reqUrl = request.getRequestURI().toString()+"?employee_oid="+oidEmployee;

                                                        logSysHistory.setLogDocumentId(0);
                                                        logSysHistory.setLogUserId(userId);
                                                        logSysHistory.setLogLoginName(loginName);
                                                        logSysHistory.setLogDocumentNumber("");
                                                        logSysHistory.setLogDocumentType(className); //entity
                                                        logSysHistory.setLogUserAction("EDIT"); // command
                                                        logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                                                        logSysHistory.setLogUpdateDate(nowDate);
                                                        logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                                                        logSysHistory.setLogDetail(logDetail); // apa yang dirubah
                                                        logSysHistory.setStatus(0);

                                                        PstLogSysHistory.insertExc(logSysHistory);
                                                    }
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
				if (oidExperience != 0) {
					try {
						experience = PstExperience.fetchExc(oidExperience);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidExperience != 0) {
					try {
						experience = PstExperience.fetchExc(oidExperience);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidExperience != 0){
					try{
                                                experience = PstExperience.fetchExc(oidExperience);
                                                
                                                String empName = PstEmployee.getEmployeeName(experience.getEmployeeId());
                                                
						long oid = PstExperience.deleteExc(oidExperience);
                                                
                                                if(sysLog == 1){
                            
                                                    if(experience.getCompanyName() != null){

                                                        logDetail = logDetail+" Company Name : "+experience.getCompanyName()+" DELETED from Employee : "+empName+"</br>";
                                                    }

                                                    String className = experience.getClass().getName();

                                                    LogSysHistory logSysHistory = new LogSysHistory();

                                                    String reqUrl = request.getRequestURI().toString()+"?employee_oid="+experience.getEmployeeId();

                                                    logSysHistory.setLogDocumentId(0);
                                                    logSysHistory.setLogUserId(userId);
                                                    logSysHistory.setLogLoginName(loginName);
                                                    logSysHistory.setLogDocumentNumber("");
                                                    logSysHistory.setLogDocumentType(className); //entity
                                                    logSysHistory.setLogUserAction("DELETE"); // command
                                                    logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                                                    logSysHistory.setLogUpdateDate(nowDate);
                                                    logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                                                    logSysHistory.setLogDetail(logDetail); // apa yang dirubah
                                                    logSysHistory.setStatus(0);

                                                    PstLogSysHistory.insertExc(logSysHistory);

                                                }
                                                
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
