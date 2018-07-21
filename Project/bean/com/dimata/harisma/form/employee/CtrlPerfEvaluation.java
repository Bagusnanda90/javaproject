/* 
 * Ctrl Name  		:  CtrlPerfEvaluation.java 
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
import com.dimata.harisma.entity.masterdata.*;

public class CtrlPerfEvaluation extends Control implements I_Language 
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
	private PerformanceEvaluation performanceEvaluation;
	private PstPerformanceEvaluation pstPerformanceEvaluation;
	private FrmPerfEvaluation frmPerfEvaluation;
	int language = LANGUAGE_DEFAULT;

	public CtrlPerfEvaluation(HttpServletRequest request){
		msgString = "";
		performanceEvaluation = new PerformanceEvaluation();
		try{
			pstPerformanceEvaluation = new PstPerformanceEvaluation(0);
		}catch(Exception e){;}
		frmPerfEvaluation = new FrmPerfEvaluation(request, performanceEvaluation);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPerfEvaluation.addError(frmPerfEvaluation.FRM_FIELD_PERFORMANCE_APPRAISAL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public PerformanceEvaluation getPerformanceEvaluation() { return performanceEvaluation; } 

	public FrmPerfEvaluation getForm() { return frmPerfEvaluation; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidPerformanceEvaluation, long oidEmpAppraisal, Vector vectPerfEva){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidPerformanceEvaluation != 0){
					try{
						performanceEvaluation = PstPerformanceEvaluation.fetchExc(oidPerformanceEvaluation);
					}catch(Exception exc){
					}
				}

            	performanceEvaluation.setOID(oidPerformanceEvaluation);

                if(oidPerformanceEvaluation != 0){
					frmPerfEvaluation.requestEntityObject(performanceEvaluation);
	
	                performanceEvaluation.setEmployeeAppraisal(oidEmpAppraisal);
	
					if(frmPerfEvaluation.errorSize()>0) {
						msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
						return RSLT_FORM_INCOMPLETE ;
					}

                    try {
						long oid = pstPerformanceEvaluation.updateExc(performanceEvaluation);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
                }else{
	                System.out.println("vectPerfEva"+vectPerfEva.size());
	                for(int i=0;i < vectPerfEva.size();i++){
	                    PerformanceEvaluation performanceEvaluation = (PerformanceEvaluation)vectPerfEva.get(i);
	
						if(performanceEvaluation.getOID()==0){
							try{
								long oid = pstPerformanceEvaluation.insertExc(performanceEvaluation);
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
								long oid = pstPerformanceEvaluation.updateExc(performanceEvaluation);
							}catch (DBException dbexc){
								excCode = dbexc.getErrorCode();
								msgString = getSystemMessage(excCode);
							}catch (Exception exc){
								msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
							}
		
						}
                    }
                }

                System.out.println("oidEmpAppraisal "+oidEmpAppraisal);
                if(oidEmpAppraisal != 0){
                    try{
                        PstEmpAppraisal.updateScore(oidEmpAppraisal);
                    }catch(Exception exc){
                        System.out.println("exc"+exc.toString());
                    }

                }

				break;

			case Command.EDIT :
				if (oidPerformanceEvaluation != 0) {
					try {
						performanceEvaluation = PstPerformanceEvaluation.fetchExc(oidPerformanceEvaluation);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidPerformanceEvaluation != 0) {
					try {
						performanceEvaluation = PstPerformanceEvaluation.fetchExc(oidPerformanceEvaluation);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidPerformanceEvaluation != 0){
					try{
						long oid = PstPerformanceEvaluation.deleteExc(oidPerformanceEvaluation);
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
