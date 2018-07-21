/* 
 * Ctrl Name  		:  CtrlEmpEducation.java 
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
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.system.entity.system.PstSystemProperty;

public class CtrlEmpEducation extends Control implements I_Language 
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
	private EmpEducation empEducation;
	private PstEmpEducation pstEmpEducation;
	private FrmEmpEducation frmEmpEducation;
	int language = LANGUAGE_DEFAULT;

	public CtrlEmpEducation(HttpServletRequest request){
		msgString = "";
		empEducation = new EmpEducation();
		try{
			pstEmpEducation = new PstEmpEducation(0);
		}catch(Exception e){;}
		frmEmpEducation = new FrmEmpEducation(request, empEducation);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpEducation.addError(frmEmpEducation.FRM_FIELD_EMP_EDUCATION_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public EmpEducation getEmpEducation() { return empEducation; } 

	public FrmEmpEducation getForm() { return frmEmpEducation; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidEmpEducation, long oidEmployee, HttpServletRequest request, String loginName, long userId){
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
                            EmpEducation prevEmpEducation = null;
				if(oidEmpEducation != 0){
					try{
						empEducation = PstEmpEducation.fetchExc(oidEmpEducation);
                                                
                                                if(sysLog == 1){
                                                    prevEmpEducation = PstEmpEducation.fetchExc(oidEmpEducation);

                                                }
					}catch(Exception exc){
					}
				}
                                //System.out.println("===> empEducation=" + empEducation);
                empEducation.setOID(oidEmpEducation);

				frmEmpEducation.requestEntityObject(empEducation);
                empEducation.setEmployeeId(oidEmployee);

				if(frmEmpEducation.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(empEducation.getOID()==0){
					try{
						long oid = pstEmpEducation.insertExc(this.empEducation);
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
						long oid = pstEmpEducation.updateExc(this.empEducation);
                                                
                                                // logHistory
                                                if(sysLog == 1){
                                                    empEducation = PstEmpEducation.fetchExc(oid);

                                                    if(empEducation != null && prevEmpEducation != null){
                                                        if(empEducation.getEducationId() != prevEmpEducation.getEducationId()){
                                                            Education Edu = PstEducation.fetchExc(empEducation.getEducationId());
                                                            Education prevEdu = PstEducation.fetchExc(prevEmpEducation.getEducationId());
                                                            logDetail = logDetail+" Pendidikan : "+prevEdu.getEducation()+" >> "+Edu.getEducation()+" UPDATED</br>";
                                                        }
                                                        if(empEducation.getStartDate() != prevEmpEducation.getStartDate() && empEducation.getEndDate() != prevEmpEducation.getEndDate()){
                                                            logDetail = logDetail+" Year from "+prevEmpEducation.getStartDate()+" to "+prevEmpEducation.getEndDate()+" >> Year from "+empEducation.getStartDate()+" to "+empEducation.getEndDate()+" UPDATED</br>";
                                                        }
                                                        if(!empEducation.getGraduation().equals(prevEmpEducation.getGraduation())){
                                                            logDetail = logDetail+" Graduation : "+prevEmpEducation.getGraduation()+" >> "+empEducation.getGraduation()+" UPDATED</br>";
                                                        }
                                                        if(empEducation.getInstitutionId() != prevEmpEducation.getInstitutionId()){
                                                            ContactList contactList = PstContactList.fetchExc(empEducation.getInstitutionId());
                                                            ContactList prevContactList = PstContactList.fetchExc(prevEmpEducation.getInstitutionId());
                                                            logDetail = logDetail+" Universitas / Institusi : "+prevContactList.getCompName()+" >> "+contactList.getCompName()+" UPDATED</br>";
                                                        }
                                                        if(empEducation.getPoint() != prevEmpEducation.getPoint()){
                                                            logDetail = logDetail+" Point : "+prevEmpEducation.getPoint()+" >> "+empEducation.getPoint()+" UPDATED</br>";
                                                        }
                                                        if(!empEducation.getEducationDesc().equals(prevEmpEducation.getEducationDesc())){
                                                            logDetail = logDetail+" Desc : "+prevEmpEducation.getEducationDesc()+" >> "+empEducation.getEducationDesc()+" UPDATED</br>";
                                                        }
                                                        
                                                        String className = empEducation.getClass().getName();

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
				if (oidEmpEducation != 0) {
					try {
						empEducation = PstEmpEducation.fetchExc(oidEmpEducation);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpEducation != 0) {
					try {
						empEducation = PstEmpEducation.fetchExc(oidEmpEducation);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpEducation != 0){
					try{
						long oid = PstEmpEducation.deleteExc(oidEmpEducation);
                                                
                                                // logHistory
                                                if(sysLog == 1){

                                                    if(empEducation.getEducationId() != 0){
                                                        Education Edu = PstEducation.fetchExc(empEducation.getEducationId());
                                                        logDetail = logDetail+" Education : "+Edu.getEducation()+" DELETED</br>";
                                                    }

                                                    String className = empEducation.getClass().getName();

                                                    LogSysHistory logSysHistory = new LogSysHistory();

                                                    String reqUrl = request.getRequestURI().toString()+"?employee_oid="+oidEmployee;

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
