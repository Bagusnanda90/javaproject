/*
 * CtrlOvt_Employee.java
 *
 * Created on April 6, 2007, 4:44 PM
 */

package com.dimata.harisma.form.payroll;

import com.dimata.gui.jsp.ControlDate;
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
 * @author  emerliana
 */
public class CtrlOvt_Employee extends Control implements I_Language {
    
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
	private Ovt_Employee ovt_Employee;
	private PstOvt_Employee pstOvt_Employee;
	private FrmOvt_Employee frmOvt_Employee;
        
        int language = LANGUAGE_DEFAULT;
        
    public CtrlOvt_Employee(HttpServletRequest request){
		msgString = "";
		ovt_Employee = new Ovt_Employee();
		try{
			pstOvt_Employee = new PstOvt_Employee(0);
		}catch(Exception e){;}
		frmOvt_Employee = new FrmOvt_Employee(request, ovt_Employee);
	}
    
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmOvt_Employee.addError(frmOvt_Employee.FRM_FIELD_OVT_EMPLY_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
     
     public Ovt_Employee getOvt_Employee() { return ovt_Employee; } 

     public FrmOvt_Employee getForm() { return frmOvt_Employee; }

     public String getMessage(){ return msgString; }

     public int getStart() { return start; }
     
     public int action(int cmd , long oidOvt_Employee, HttpServletRequest request){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
                                System.out.println("ON CONTROL COMMAND SAVE");
				if(oidOvt_Employee != 0){
					try{
						ovt_Employee = PstOvt_Employee.fetchExc(oidOvt_Employee);
					}catch(Exception exc){
					}
				}
                                
                                
				frmOvt_Employee.requestEntityObject(ovt_Employee);
                                
                                //untuk date hour start
                                Date date_start_Time = ControlDate.getDate(FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START], request);
                                Date hour_start_Time = ControlDate.getTime(FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_START], request);
                                
                                
                                int y = date_start_Time.getYear();
                                int M = date_start_Time.getMonth();
                                int d = date_start_Time.getDate();
                                int h = hour_start_Time.getHours();
                                int m = hour_start_Time.getMinutes();
                                Date startDateTime = new Date(y, M, d, h, m);
                                
                                
                                
                                //untuk date hour end
                                Date date_End_Time = ControlDate.getDate(FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END], request);
                                Date hour_end_Time = ControlDate.getTime(FrmOvt_Employee.fieldNames[FrmOvt_Employee.FRM_FIELD_OVT_END], request);
                                
                                
                                int y1 = date_End_Time.getYear();
                                int M1 = date_End_Time.getMonth();
                                int d1 = date_End_Time.getDate();
                                int h1 = hour_end_Time.getHours();
                                int m1 = hour_end_Time.getMinutes();
                                Date startEndTime = new Date(y1, M1, d1, h1, m1);
                                
                                if(frmOvt_Employee.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
                                ovt_Employee.setOvt_Start(startDateTime);
                                ovt_Employee.setOvt_End(startEndTime);
                                
                                System.out.println("ini Number employenya yanga da di control:::::::"+ovt_Employee.getEmployee_num());
                                
                                if(ovt_Employee.getEmployee_num().length()>0)
                                {
                                        if(ovt_Employee.getOID()==0){
                                                try{
                                                        long oid = pstOvt_Employee.insertExc(this.ovt_Employee);
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
                                                        long oid = pstOvt_Employee.updateExc(this.ovt_Employee);
                                                }catch (DBException dbexc){
                                                        excCode = dbexc.getErrorCode();
                                                        msgString = getSystemMessage(excCode);
                                                }catch (Exception exc){
                                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
                                                }

                                        }
                                }
				break;

			case Command.EDIT :
				if (oidOvt_Employee != 0) {
					try {
						ovt_Employee = PstOvt_Employee.fetchExc(oidOvt_Employee);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidOvt_Employee != 0) {
                                    try {
                                        ovt_Employee = PstOvt_Employee.fetchExc(oidOvt_Employee);
                                    } catch (DBException dbexc){
                                        excCode = dbexc.getErrorCode();
                                        msgString = getSystemMessage(excCode);
                                    } catch (Exception exc){
                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    }
                                }
                        break;

			case Command.DELETE :
				if (oidOvt_Employee != 0){
					try{
						long oid = PstOvt_Employee.deleteExc(oidOvt_Employee);
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
