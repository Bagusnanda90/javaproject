/*
 * CtrlPayComponentIn.java
 *
 * Created on June 13, 2007, 9:51 AM
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

import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author  yunny
 */
public class CtrlPayComponentIn extends Control implements I_Language {
        
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
	private PayComponentIn payComponentIn;
	private PstPayComponentIn pstPayComponentIn;
	private FrmPayComponentIn frmPayComponentIn;
        
        int language = LANGUAGE_DEFAULT;

    
    /** Creates a new instance of CtrlPayComponentIn */
     public CtrlPayComponentIn(HttpServletRequest request){
		msgString = "";
		payComponentIn = new PayComponentIn();
        //locker = new Locker();
		try{
			pstPayComponentIn = new PstPayComponentIn(0);
            //pstLocker = new PstLocker(0);
		}catch(Exception e){;}

        frmPayComponentIn = new FrmPayComponentIn(request,payComponentIn);
		//frmLocker = new FrmLocker(request, locker);
	}
     
      private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmPayComponentIn.addError(frmPayComponentIn.FRM_FIELD_COMP_ID , resultText[language][RSLT_EST_CODE_EXIST] );
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
      
      public PayComponentIn getPayComponentIn() { return payComponentIn; } 
      
      public FrmPayComponentIn getForm() { return frmPayComponentIn; }
      
      public String getMessage(){ return msgString; }
      
      public int getStart() { return start; }
      
      public int action(int cmd , long oidPayComponentIn){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidPayComponentIn != 0){
					try{
						payComponentIn = PstPayComponentIn.fetchExc(oidPayComponentIn);
					}catch(Exception exc){
					}
				}

				frmPayComponentIn.requestEntityObject(payComponentIn); 

                                //System.out.println("frmEmployee.errorSize() : "+frmEmployee.errorSize());
                                
				if(frmPayComponentIn.errorSize()>0) {
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
                                

				if(payComponentIn.getOID()==0){
					try{
						long oid = pstPayComponentIn.insertExc(this.payComponentIn);

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
						long oid = pstPayComponentIn.updateExc(this.payComponentIn);
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
				if (oidPayComponentIn != 0) {
					try {
						payComponentIn = PstPayComponentIn.fetchExc(oidPayComponentIn);
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
				if (oidPayComponentIn != 0) {
					try {
						payComponentIn = PstPayComponentIn.fetchExc(oidPayComponentIn);
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
                        System.out.println("oidPayComponentIn "+oidPayComponentIn);
				if (oidPayComponentIn != 0){
					try{
						long oid = PstPayComponentIn.deleteExc(oidPayComponentIn);
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
