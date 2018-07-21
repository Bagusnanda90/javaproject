/* 
 * Ctrl Name  		:  CtrlEmpLanguage.java 
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
import com.dimata.harisma.entity.masterdata.EmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstCompetency;
import com.dimata.harisma.entity.masterdata.PstEmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstLanguage;
import com.dimata.system.entity.PstSystemProperty;

public class CtrlEmpLanguage extends Control implements I_Language 
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
	private EmpLanguage empLanguage;
	private PstEmpLanguage pstEmpLanguage;
	private FrmEmpLanguage frmEmpLanguage;
	int language = LANGUAGE_DEFAULT;

	public CtrlEmpLanguage(HttpServletRequest request){
		msgString = "";
		empLanguage = new EmpLanguage();
		try{
			pstEmpLanguage = new PstEmpLanguage(0);
		}catch(Exception e){;}
		frmEmpLanguage = new FrmEmpLanguage(request, empLanguage);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpLanguage.addError(frmEmpLanguage.FRM_FIELD_EMP_LANGUAGE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public EmpLanguage getEmpLanguage() { return empLanguage; } 

	public FrmEmpLanguage getForm() { return frmEmpLanguage; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

        public int action(int cmd , long oidEmpLanguage, long oidEmployee, HttpServletRequest request, String loginName, long userId){
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
                                EmpLanguage prevEmployeeLang = null;
				if(oidEmpLanguage != 0){
					try{
						empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);
                                                
                                                if(sysLog == 1){
                                                    prevEmployeeLang = PstEmpLanguage.fetchExc(oidEmpLanguage);

                                                }
                                                
					}catch(Exception exc){
					}
				}
                                //System.out.println("===> empEducation=" + empEducation);
                                empLanguage.setOID(oidEmpLanguage);
                                frmEmpLanguage.requestEntityObject(empLanguage);
                                empLanguage.setEmployeeId(oidEmployee);

				if(frmEmpLanguage.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(empLanguage.getOID()==0){
					try{
						long oid = pstEmpLanguage.insertExc(this.empLanguage);
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
						long oid = pstEmpLanguage.updateExc(this.empLanguage);
                                                
                                                // logHistory
                                                if(sysLog == 1){
                                                    empLanguage = PstEmpLanguage.fetchExc(oid);

                                                    if(empLanguage != null && prevEmployeeLang != null){
                                                        if(empLanguage.getLanguageId() != prevEmployeeLang.getLanguageId()){
                                                            String nameLang = PstLanguage.getLangName(empLanguage.getLanguageId());
                                                            String prevNameLang = PstLanguage.getLangName(prevEmployeeLang.getLanguageId());
                                                            logDetail = logDetail+" Language : "+prevNameLang+" >> "+nameLang+" UPDATED</br>";
                                                        }
                                                        if(empLanguage.getOral() != prevEmployeeLang.getOral()){
                                                            String oral = PstEmpLanguage.gradeKey[empLanguage.getOral()];
                                                            String prevOral = PstEmpLanguage.gradeKey[prevEmployeeLang.getOral()];
                                                            logDetail = logDetail+" Oral : "+prevOral+" >> "+oral+" UPDATED</br>";
                                                        }
                                                        if(empLanguage.getWritten() != prevEmployeeLang.getWritten()){
                                                            String written = PstEmpLanguage.gradeKey[empLanguage.getWritten()];
                                                            String prevWritten = PstEmpLanguage.gradeKey[prevEmployeeLang.getWritten()];
                                                            logDetail = logDetail+" Written : "+prevWritten+" >> "+written+" UPDATED</br>";
                                                        }
                                                        if(!empLanguage.getDescription().equals(prevEmployeeLang.getDescription())){
                                                            logDetail = logDetail+" Desc : "+prevEmployeeLang.getDescription()+" >> "+empLanguage.getDescription()+" UPDATED</br>";
                                                        }
                                                        String className = empLanguage.getClass().getName();

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
				if (oidEmpLanguage != 0) {
					try {
						empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpLanguage != 0) {
					try {
						empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpLanguage != 0){
					try{
                                                empLanguage = PstEmpLanguage.fetchExc(oidEmpLanguage);
                                                
                                                String empName = PstEmployee.getEmployeeName(empLanguage.getEmployeeId());
                                                                                            
						long oid = PstEmpLanguage.deleteExc(oidEmpLanguage);
                                                
                                                if(sysLog == 1){
                            
                                                    if(empLanguage.getLanguageId() != 0){

                                                        String nameLang = PstEmpLanguage.getLang(String.valueOf(empLanguage.getLanguageId()));

                                                        logDetail = logDetail+" Language : "+nameLang+" DELETED from Employee : "+empName+"</br>";
                                                    }

                                                    String className = empLanguage.getClass().getName();

                                                    LogSysHistory logSysHistory = new LogSysHistory();

                                                    String reqUrl = request.getRequestURI().toString()+"?employee_oid="+empLanguage.getEmployeeId();

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
