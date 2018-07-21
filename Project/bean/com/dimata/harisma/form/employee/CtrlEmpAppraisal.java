/* 
 * Ctrl Name  		:  CtrlEmpAppraisal.java 
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
import com.dimata.gui.jsp.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class CtrlEmpAppraisal extends Control implements I_Language 
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
	private EmpAppraisal empAppraisal;
	private PstEmpAppraisal pstEmpAppraisal;
	private FrmEmpAppraisal frmEmpAppraisal;
 /*   private int totScore = 0;
    private int totCriteria = 0;
    private double average = 0;  */
	int language = LANGUAGE_DEFAULT;

	public CtrlEmpAppraisal(HttpServletRequest request){
		msgString = "";
		empAppraisal = new EmpAppraisal();
		try{
			pstEmpAppraisal = new PstEmpAppraisal(0);
		}catch(Exception e){;}
		frmEmpAppraisal = new FrmEmpAppraisal(request, empAppraisal);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpAppraisal.addError(frmEmpAppraisal.FRM_FIELD_EMPLOYEE_APPRAISAL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    /*
    public int getTotScore(){ return totScore; }

	public void setTotScore(int totScore){ this.totScore = totScore; }

    public int getTotCriteria(){ return totCriteria; }

	public void setTotCriteria(int totCriteria){ this.totCriteria = totCriteria; }

    public double getAverage(){ return average; }

	public void setAverage(double average){ this.average = average; }    */

	public EmpAppraisal getEmpAppraisal() { return empAppraisal; } 

	public FrmEmpAppraisal getForm() { return frmEmpAppraisal; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidEmpAppraisal, HttpServletRequest request){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidEmpAppraisal != 0){
					try{
						empAppraisal = PstEmpAppraisal.fetchExc(oidEmpAppraisal);
					}catch(Exception exc){
					}
				}

 
            	empAppraisal.setOID(oidEmpAppraisal);

				frmEmpAppraisal.requestEntityObject(empAppraisal);

                Date startTime = ControlDate.getTime(FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_TIME_PERFORMANCE], request);
            	empAppraisal.setTimePerformance(startTime);

                if(oidEmpAppraisal != 0){
	                Vector evaluate = PstPerformanceEvaluation.list(0,0,PstPerformanceEvaluation.fieldNames[PstPerformanceEvaluation.FLD_EMPLOYEE_APPRAISAL]+" = "+oidEmpAppraisal,"");
	                int totScore = PstPerformanceEvaluation.countScore(oidEmpAppraisal);
                    double average = 0.0;
                    if( evaluate!=null && evaluate.size()>0){
                        average=(new Integer(totScore)).doubleValue()/(new Integer(evaluate.size())).doubleValue();
                    } 
	                empAppraisal.setTotalCriteria(evaluate.size());
	                empAppraisal.setTotalScore(totScore);
	                empAppraisal.setScoreAverage(average);
                        if(empAppraisal.getGroupRankId()==0){
                            // getg group rank according to current employee level
                            try{
                                Employee empl = PstEmployee.fetchExc(empAppraisal.getEmployeeId());
                                Level lvl = PstLevel.fetchExc(empl.getLevelId());
                                
                                empAppraisal.setGroupRankId(lvl.getGroupRankId());
                            }catch(Exception exc){
                                System.out.println("EXC get EmpAppraisal get Group Rank"+exc);
                            }
                        }
                }

				if(frmEmpAppraisal.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(empAppraisal.getOID()==0){
					try{
						long oid = pstEmpAppraisal.insertExc(this.empAppraisal);
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
						long oid = pstEmpAppraisal.updateExc(this.empAppraisal);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
                System.out.println("oidEmpAppraisal "+oidEmpAppraisal);
				if (oidEmpAppraisal != 0) {
					try {
						this.empAppraisal = PstEmpAppraisal.fetchExc(oidEmpAppraisal);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){
                        System.out.println("........................."+exc.toString());
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpAppraisal != 0) {
					try {
						empAppraisal = PstEmpAppraisal.fetchExc(oidEmpAppraisal);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpAppraisal != 0){
					try{
						long oid = PstEmpAppraisal.deleteExc(oidEmpAppraisal);
                        oid = PstPerformanceEvaluation.deleteByAppraisal(oidEmpAppraisal);
                        oid = PstDevImprovement.deleteByAppraisal(oidEmpAppraisal);
                        oid = PstDevImprovementPlan.deleteByAppraisal(oidEmpAppraisal);
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
