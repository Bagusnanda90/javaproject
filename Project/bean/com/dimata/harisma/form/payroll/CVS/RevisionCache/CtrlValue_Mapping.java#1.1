/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll.value_mapping;

/**
 *
 * @author GUSWIK
 */


/* java package */ 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.entity.payroll.value_mapping.PstValue_Mapping;
import com.dimata.harisma.entity.payroll.value_mapping.Value_Mapping;

public class CtrlValue_Mapping extends Control implements I_Language {
    
        public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;
        public static int RSLT_FORM_DATA_NOT_FOUND=4;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap","Data tidak ada"},
		{"Succes", "Can not process", "Estimation code exist", "Data incomplete","Data Not Found"}
	};
        
        private int start;
	private String msgString;
	private Value_Mapping value_Mapping;
	private PstValue_Mapping pstValue_Mapping;
	private FrmValue_Mapping frmValue_Mapping;
        
        int language = LANGUAGE_DEFAULT;
    
    /** Creates a new instance of CtrlPayComponent */
   public CtrlValue_Mapping(HttpServletRequest request){
		msgString = "";
		value_Mapping = new Value_Mapping();
        //locker = new Locker();
		try{
			pstValue_Mapping = new PstValue_Mapping(0);
            //pstLocker = new PstLocker(0);
		}catch(Exception e){;}

        frmValue_Mapping = new FrmValue_Mapping(request,value_Mapping);
		//frmLocker = new FrmLocker(request, locker);
	}
    
   
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmValue_Mapping.addError(frmValue_Mapping.FRM_FIELD_VALUE_MAPPING_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
      
      public Value_Mapping getValue_Mapping() { return value_Mapping; } 
      
      public FrmValue_Mapping getForm() { return frmValue_Mapping; }
      
      public String getMessage(){ return msgString; }
      
      public int getStart() { return start; }
      
      
      public int action(int cmd , long oidValue_Mapping){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidValue_Mapping != 0){
					try{
						value_Mapping = PstValue_Mapping.fetchExc(oidValue_Mapping);
					}catch(Exception exc){
					}
				}

				frmValue_Mapping.requestEntityObject(value_Mapping); 

                                //System.out.println("frmEmployee.errorSize() : "+frmEmployee.errorSize());
                                
				if(frmValue_Mapping.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                             
				if(value_Mapping.getOID()==0){
					try{
						long oid = PstValue_Mapping.insertExc(this.value_Mapping);

                  

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
						long oid = PstValue_Mapping.updateExc(this.value_Mapping);
                  
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidValue_Mapping != 0) {
					try {
						value_Mapping = PstValue_Mapping.fetchExc(oidValue_Mapping);
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
				if (oidValue_Mapping != 0) {
					try {
						value_Mapping = PstValue_Mapping.fetchExc(oidValue_Mapping);
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
                        System.out.println("oidPayComponent "+oidValue_Mapping);
				if (oidValue_Mapping != 0){
					try{
						long oid = PstValue_Mapping.deleteExc(oidValue_Mapping);
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
