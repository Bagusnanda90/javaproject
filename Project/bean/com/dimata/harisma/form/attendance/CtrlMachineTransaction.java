/*
 * Ctrl Name  		:  CtrlPresence.java
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

package com.dimata.harisma.form.attendance;

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
import com.dimata.gui.jsp.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.PstPeriod;

// import barcode/machineTransaction logger -- add by edhy
import com.dimata.harisma.utility.service.tma.*;

public class CtrlMachineTransaction extends Control implements I_Language {
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
    private MachineTransaction machineTransaction;
    private PstMachineTransaction pstMachineTransaction;
    private FrmMachineTransaction frmMachineTransaction;
    int language = LANGUAGE_DEFAULT;
    
    public CtrlMachineTransaction(HttpServletRequest request){            
        msgString = "";
        machineTransaction = new MachineTransaction();
        try{
            pstMachineTransaction = new PstMachineTransaction(0);
        }catch(Exception e){;}
        frmMachineTransaction = new FrmMachineTransaction(request, machineTransaction);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmMachineTransaction.addError(FrmMachineTransaction.FRM_FIELD_MACHINE_TRANS_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public MachineTransaction getPresence() { return machineTransaction; }
    
    public FrmMachineTransaction getForm() { return frmMachineTransaction; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidMachineTransaction){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidMachineTransaction != 0){
					try{
						machineTransaction = PstMachineTransaction.fetchExc(oidMachineTransaction);
					}catch(Exception exc){
					}
				}

				frmMachineTransaction.requestEntityObject(machineTransaction);

				if(frmMachineTransaction.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(machineTransaction.getOID()==0){
					try{
						long oid = pstMachineTransaction.insertExc(this.machineTransaction);
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
						long oid = pstMachineTransaction.updateExc(this.machineTransaction);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidMachineTransaction != 0) {
					try {
						machineTransaction = PstMachineTransaction.fetchExc(oidMachineTransaction);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidMachineTransaction != 0) {
					try {
						machineTransaction = PstMachineTransaction.fetchExc(oidMachineTransaction);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidMachineTransaction != 0){
					try{
						long oid = PstMachineTransaction.deleteExc(oidMachineTransaction);
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
