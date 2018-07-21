/*
 * CtrlEmpPicture.java
 *
 * Created on November 30, 2007, 11:29 AM
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

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

public class CtrlEmpPicture extends Control implements I_Language{
    
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
    private EmpPicture empPicture;
    private PstEmpPicture pstEmpPicture;
    private FrmEmpPicture frmEmpPicture;
    int language = LANGUAGE_DEFAULT;
   
    
   public CtrlEmpPicture(HttpServletRequest request){
		msgString = "";
		empPicture = new EmpPicture();
		try{
			pstEmpPicture = new PstEmpPicture(0);
		}catch(Exception e){;}
		frmEmpPicture = new FrmEmpPicture(request, empPicture);
                
	}
   
    
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpPicture.addError(frmEmpPicture.FRM_FIELD_PIC_EMP_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

    public EmpPicture getEmpPicture() { return empPicture; } 

    public FrmEmpPicture getForm() { return frmEmpPicture; }

    public String getMessage(){ return msgString; }

    public int getStart() { return start; }
    
   public int action(int cmd , long oidEmpPicture, long oidEmployee){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidEmpPicture != 0){
					try{
						empPicture = PstEmpPicture.fetchExc(oidEmpPicture);
					}catch(Exception exc){
					}
				}

            	empPicture.setOID(oidEmpPicture);

				frmEmpPicture.requestEntityObject(empPicture);

                empPicture.setEmployeeId(oidEmployee);

				if(frmEmpPicture.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(empPicture.getOID()==0){
					try{
						long oid = pstEmpPicture.insertExc(this.empPicture);
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
						long oid = pstEmpPicture.updateExc(this.empPicture);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidEmpPicture != 0) {
					try {
						empPicture = PstEmpPicture.fetchExc(oidEmpPicture);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpPicture != 0) {
					try {
						empPicture = PstEmpPicture.fetchExc(oidEmpPicture);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpPicture != 0){
					try{
						long oid = PstEmpPicture.deleteExc(oidEmpPicture);
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
    /** Creates a new instance of CtrlEmpPicture */
    public CtrlEmpPicture() {
    }
    
}
