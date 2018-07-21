/* 
 * Ctrl Name  		:  CtrlEmpSalary.java 
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

public class CtrlEmpSalary extends Control implements I_Language 
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
	private EmpSalary empSalary;
	private PstEmpSalary pstEmpSalary;
	private FrmEmpSalary frmEmpSalary;
	int language = LANGUAGE_DEFAULT;

	public CtrlEmpSalary(HttpServletRequest request){
		msgString = "";
		empSalary = new EmpSalary();
		try{
			pstEmpSalary = new PstEmpSalary(0);
		}catch(Exception e){;}
		frmEmpSalary = new FrmEmpSalary(request, empSalary);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpSalary.addError(frmEmpSalary.FRM_FIELD_EMP_SALARY_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public EmpSalary getEmpSalary() { return empSalary; } 

	public FrmEmpSalary getForm() { return frmEmpSalary; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidEmpSalary){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidEmpSalary != 0){
					try{
						empSalary = PstEmpSalary.fetchExc(oidEmpSalary);
					}catch(Exception exc){
					}
				}

				frmEmpSalary.requestEntityObject(empSalary);

				if(frmEmpSalary.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

                double incBasic = empSalary.getNewBasic()-empSalary.getCurrBasic();
                double incTransport = empSalary.getNewTransport()-empSalary.getCurrTransport();
                double incTotal = incBasic + incTransport + empSalary.getAdditional();
                empSalary.setCurrDate(new Date());
                empSalary.setIncSalary(incBasic);
                empSalary.setIncTransport(incTransport);
                empSalary.setIncTotal(incTotal);
                empSalary.setPercentageBasic((incBasic/empSalary.getCurrBasic())*100);
                empSalary.setPercentTransport((incTransport/empSalary.getCurrTransport())*100);
                empSalary.setPercentageTotal((incTotal/empSalary.getCurrTotal())*100);

				if(empSalary.getOID()==0){
					try{
						long oid = pstEmpSalary.insertExc(this.empSalary);
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
						long oid = pstEmpSalary.updateExc(this.empSalary);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidEmpSalary != 0) {
					try {
						empSalary = PstEmpSalary.fetchExc(oidEmpSalary);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpSalary != 0) {
					try {
						empSalary = PstEmpSalary.fetchExc(oidEmpSalary);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpSalary != 0){
					try{
						long oid = PstEmpSalary.deleteExc(oidEmpSalary);
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
