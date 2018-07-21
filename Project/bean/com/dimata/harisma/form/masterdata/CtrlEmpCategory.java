/* 
 * Ctrl Name  		:  CtrlEmpCategory.java 
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
import com.dimata.harisma.session.payroll.I_PayrollCalculator;
import com.dimata.system.entity.PstSystemProperty;

public class CtrlEmpCategory extends Control implements I_Language 
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
	private EmpCategory empCategory;
	private PstEmpCategory pstEmpCategory;
	private FrmEmpCategory frmEmpCategory;
	int language = LANGUAGE_DEFAULT;
        //update by satrya 2014-02-13
        I_PayrollCalculator  payrollCalculator = null;
        String parollCalculatorClassName ="";  
	public CtrlEmpCategory(HttpServletRequest request){
		msgString = "";
		empCategory = new EmpCategory();
		try{
			pstEmpCategory = new PstEmpCategory(0);
		}catch(Exception e){;}
		frmEmpCategory = new FrmEmpCategory(request, empCategory);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpCategory.addError(frmEmpCategory.FRM_FIELD_EMP_CATEGORY_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public EmpCategory getEmpCategory() { return empCategory; } 

	public FrmEmpCategory getForm() { return frmEmpCategory; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidEmpCategory){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidEmpCategory != 0){
					try{
						empCategory = PstEmpCategory.fetchExc(oidEmpCategory);
					}catch(Exception exc){
					}
				}

				frmEmpCategory.requestEntityObject(empCategory);

				if(frmEmpCategory.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                //update by satrya 2014-02-13
                                try{
                                    parollCalculatorClassName = PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME");
                                    if(parollCalculatorClassName==null || parollCalculatorClassName.length()< 1 ){
                                        parollCalculatorClassName ="com.dimata.harisma.session.payroll.PayrollCalculator";    
                                    }
                                    payrollCalculator = (I_PayrollCalculator) (Class.forName(parollCalculatorClassName).newInstance());
                                    } catch(Exception exc){
                                        System.out.println(exc);
                                  }
                                
				if(empCategory.getOID()==0){
					try{
						long oid = pstEmpCategory.insertExc(this.empCategory);
                                                //update by satrya 2014-03-06
                                                if(payrollCalculator!=null){
                                                    System.out.println("load config");
                                                    payrollCalculator.loadEmpCategoryInsentif();
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
						long oid = pstEmpCategory.updateExc(this.empCategory);
                                                //update by satrya 2014-03-06
                                               if(payrollCalculator!=null){
                                                   System.out.println("load config");
                                                    payrollCalculator.loadEmpCategoryInsentif();
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
				if (oidEmpCategory != 0) {
					try {
						empCategory = PstEmpCategory.fetchExc(oidEmpCategory);
                                                //payrollCalculator.getLoadDataEmpCat();
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpCategory != 0) {
					try {
                        if(PstEmpCategory.checkMaster(oidEmpCategory))
                        	msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        else
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);

						empCategory = PstEmpCategory.fetchExc(oidEmpCategory);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpCategory != 0){
					try{
						long oid = PstEmpCategory.deleteExc(oidEmpCategory);
                                                //payrollCalculator.getLoadDataEmpCat();
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
