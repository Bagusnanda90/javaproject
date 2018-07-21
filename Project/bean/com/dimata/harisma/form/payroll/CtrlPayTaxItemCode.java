/*
 * CtrlPayTaxItemCode.java
 *
 * Created on April 4, 2007, 3:26 PM
 */

package com.dimata.harisma.form.payroll;
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
import com.dimata.harisma.entity.payroll.*;
//import com.dimata.harisma.entity.locker.*;
//import com.dimata.harisma.form.locker.*;
//import com.dimata.harisma.entity.attendance.*;

/**
 *
 * @author  yunny
 */
public class CtrlPayTaxItemCode extends Control implements I_Language{
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
	private PayTaxItemCode payTaxItemCode;
	private PstPayTaxItemCode pstPayTaxItemCode;
	private FrmPayTaxItemCode frmPayTaxItemCode;
        
        int language = LANGUAGE_DEFAULT;
    
    /** Creates a new instance of CtrlPayTaxItemCode */
   public CtrlPayTaxItemCode(HttpServletRequest request){
		msgString = "";
		payTaxItemCode = new PayTaxItemCode();
        //locker = new Locker();
		try{
			pstPayTaxItemCode = new PstPayTaxItemCode(0);
            //pstLocker = new PstLocker(0);
		}catch(Exception e){;}

        frmPayTaxItemCode = new FrmPayTaxItemCode(request,payTaxItemCode);
		//frmLocker = new FrmLocker(request, locker);
	}
   
   private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPayTaxItemCode.addError(frmPayTaxItemCode.FRM_FIELD_TAX_ITEM_CD_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
     public PayTaxItemCode getPayTaxItemCode() { return payTaxItemCode; } 
     public FrmPayTaxItemCode getForm() { return frmPayTaxItemCode; }
     public String getMessage(){ return msgString; }
     public int getStart() { return start; }
     
      public int action(int cmd , long oidPayTaxItemCode){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidPayTaxItemCode != 0){
					try{
						payTaxItemCode = PstPayTaxItemCode.fetchExc(oidPayTaxItemCode);
					}catch(Exception exc){
					}
				}

				frmPayTaxItemCode.requestEntityObject(payTaxItemCode); 

                                //System.out.println("frmEmployee.errorSize() : "+frmEmployee.errorSize());
                                
				if(frmPayTaxItemCode.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
                                //System.out.println("employee.getResigned() : "+employee.getResigned());
                                /*if (employee.getResigned() == 1) { 
                                    employee.setLockerId(0);
                                    employee.setBarcodeNumber(null);
                                } */                               
                                
                                // ---- untuk bali dynasty karena tidak memakai locker maka di comment ----
                                /*
                                // get Request Value Of  Locker
                                frmLocker.requestEntityObject(locker);
                                locker.setOID(employee.getLockerId());

                                System.out.println("PstEmployee.checkLocker(locker) : "+PstEmployee.checkLocker(locker));                                
				if(PstEmployee.checkLocker(locker)){
                                    msgString = "Locker has been used by another employee";
                                    return RSLT_FORM_INCOMPLETE ;
                                }

                                if((locker.getLocationId() != 0) && (locker.getLockerNumber() != null && locker.getLockerNumber().length()>0)){
                                        try{
                                        if(locker.getOID() == 0){
                                                long oid = pstLocker.insertExc(locker);
                                                employee.setLockerId(oid);
                                        }else{
                                                System.out.println("++++"+locker.getOID());
                                            long oid = PstLocker.updateLocker(locker);
                                        }
                                        
                                        }catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
                                }
                                */
                                

				if(payTaxItemCode.getOID()==0){
					try{
						long oid = pstPayTaxItemCode.insertExc(this.payTaxItemCode);

                        /**
                        * using in aiso to set employee to be contact
                        PstContactList pstContactList = new PstContactList();
                        if(employee.getIsAssignToAccounting()){
							long oidContact = pstContactList.insertEmployeeToContact(this.employee);
                        }else{
							long oidContact = pstContactList.deleteEmployeeFromContact(oidEmployee);
                        }
                        */

                        //on add new employee -- add also to leave stock
                       /* if(oid!=0){
                             LeaveStock stock = new LeaveStock();
                             stock.setEmployeeId(oid);
                             PstLeaveStock.insertExc(stock);
                        }*/


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
						long oid = pstPayTaxItemCode.updateExc(this.payTaxItemCode);
                        /**
                        * using in aiso to set employee to be contact
                        PstContactList pstContactList = new PstContactList();
                        if(employee.getIsAssignToAccounting()){
							long oidContact = pstContactList.insertEmployeeToContact(this.employee);
                        }else{
							long oidContact = pstContactList.deleteEmployeeFromContact(oidEmployee);
                        }
                        */

					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidPayTaxItemCode != 0) {
					try {
						payTaxItemCode = PstPayTaxItemCode.fetchExc(oidPayTaxItemCode);
                                                /*if(employee.getLockerId() != 0){
                                                        locker = PstLocker.fetchExc(employee.getLockerId());
                                                }*/
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidPayTaxItemCode != 0) {
					try {
						payTaxItemCode = PstPayTaxItemCode.fetchExc(oidPayTaxItemCode);
                                                /*if(employee.getLockerId() != 0){
                                                        locker = PstLocker.fetchExc(employee.getLockerId());
                                                }*/
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
                        System.out.println("oidPayTaxItemCode "+oidPayTaxItemCode);
				if (oidPayTaxItemCode != 0){
					try{
						long oid = PstPayTaxItemCode.deleteExc(oidPayTaxItemCode);
                                                /*oid = PstEmpLanguage.deleteByEmployee(oidEmployee);
                                                oid = PstFamilyMember.deleteByEmployee(oidEmployee);
                                                oid = PstExperience.deleteByEmployee(oidEmployee);
                                                //oid = PstEducation.deleteByEmployee(oidEmployee);
                                                oid = PstCareerPath.deleteByEmployee(oidEmployee);  
                                                oid = PstEmpSalary.deleteByEmployee(oidEmployee);
                                                oid = PstEmpSchedule.deleteByEmployee(oidEmployee);
                                                oid = PstLeave.deleteByEmployee(oidEmployee);
                                                oid = PstDayOfPayment.deleteByEmployee(oidEmployee);
                                                oid = PstPresence.deleteByEmployee(oidEmployee);*/
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
