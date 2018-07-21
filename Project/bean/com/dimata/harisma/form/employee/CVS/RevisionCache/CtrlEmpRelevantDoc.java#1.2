/*
 * CtlEmpRelevantDoc.java
 *
 * Created on December 3, 2007, 5:56 PM
 */

package com.dimata.harisma.form.employee;

/**
 *
 * @author  yunny
 */

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


/**
 *
 * @author  yunny
 */
public class CtrlEmpRelevantDoc extends Control implements I_Language{
    
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
    private EmpRelevantDoc empRelevantDoc;
    private PstEmpRelevantDoc pstEmpRelevantDoc;
    private FrmEmpRelevantDoc frmEmpRelevantDoc;
    int language = LANGUAGE_DEFAULT;
    
    /** Creates a new instance of CtlEmpRelevantDoc */
     public CtrlEmpRelevantDoc(HttpServletRequest request){
		msgString = "";
		empRelevantDoc = new EmpRelevantDoc();
		try{
			pstEmpRelevantDoc = new PstEmpRelevantDoc(0);
		}catch(Exception e){;}
		frmEmpRelevantDoc = new FrmEmpRelevantDoc(request, empRelevantDoc);
                
	}
     
      private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpRelevantDoc.addError(frmEmpRelevantDoc.FRM_FIELD_DOC_RELEVANT_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

    public EmpRelevantDoc getEmpRelevantDoc() { return empRelevantDoc; } 

    public FrmEmpRelevantDoc getForm() { return frmEmpRelevantDoc; }

    public String getMessage(){ return msgString; }

    public int getStart() { return start; }
    
    
    public int action(int cmd , long oidEmpRelevantDoc, long oidEmployee){
		msgString = "";
                
                //ystem.out.println("cmd...."+cmd);
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
                                
				if(oidEmpRelevantDoc != 0){
					try{
						empRelevantDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
					}catch(Exception exc){
					}
				}

                            empRelevantDoc.setOID(oidEmpRelevantDoc);

                            frmEmpRelevantDoc.requestEntityObject(empRelevantDoc);

                      empRelevantDoc.setEmployeeId(oidEmployee);
                      if(this.empRelevantDoc.getDocTitle()=="" || this.empRelevantDoc==null){
                                                msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                                return RSLT_FORM_INCOMPLETE ;
                                            }
                      //System.out.println("frmEmpRelevantDoc.errorSize()"+frmEmpRelevantDoc.errorSize());

				/*if(frmEmpRelevantDoc.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}*/
 
				if(empRelevantDoc.getOID()==0){
					try{
                                            
						long oid = pstEmpRelevantDoc.insertExc(this.empRelevantDoc);
                                                 
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
                                            if(this.empRelevantDoc.getDocTitle()=="" || this.empRelevantDoc==null){
                                                msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                                return RSLT_FORM_INCOMPLETE ;
                                            }
						long oid = pstEmpRelevantDoc.updateExc(this.empRelevantDoc);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidEmpRelevantDoc != 0) {
					try {
						empRelevantDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpRelevantDoc != 0) {
					try {
						empRelevantDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpRelevantDoc != 0){
					try{
						long oid = PstEmpRelevantDoc.deleteExc(oidEmpRelevantDoc);
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
