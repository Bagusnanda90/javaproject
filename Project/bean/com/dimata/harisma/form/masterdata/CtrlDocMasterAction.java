/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author  	:  Priska
 * @version  	:  [version]
 */

/*******************************************************************
 * Class Description 	: CtrlCompany
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Priska
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
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.*;

public class CtrlDocMasterAction extends Control implements I_Language{
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
    private DocMasterAction docMasterAction;
    private PstDocMasterAction pstDocMasterAction;
    private FrmDocMasterAction frmDocMasterAction;
    private FrmDocMasterActionParam frmDocMasterActionParam;
    private PstDocMasterActionParam pstDocMasterActionParam;
    int language = LANGUAGE_DEFAULT;

    public CtrlDocMasterAction(HttpServletRequest request) {
        msgString = "";
        docMasterAction = new DocMasterAction();
        try {
            pstDocMasterAction = new PstDocMasterAction(0);
        } catch (Exception e) {
            ;
        }
        frmDocMasterAction = new FrmDocMasterAction(request, docMasterAction);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDocMasterAction.addError(frmDocMasterAction.FRM_FIELD_DOC_ACTION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public DocMasterAction getdDocMasterAction() {
        return docMasterAction;
    }

    public FrmDocMasterAction getForm() {
        return frmDocMasterAction;
    }
     public FrmDocMasterActionParam getFormParam() {
        return frmDocMasterActionParam;
    }
    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidDocMasterAction, String actionNameKey) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidDocMasterAction != 0){
					try{
						docMasterAction = PstDocMasterAction.fetchExc(oidDocMasterAction);
					}catch(Exception exc){
					}
				}

				frmDocMasterAction.requestEntityObject(docMasterAction);
                                frmDocMasterAction.requestEntityObjectMultiple(actionNameKey);
                                Vector vDocMasterActionParam = frmDocMasterAction.getVDocMasterActionParam();
                                        
                                        
				if(frmDocMasterAction.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(docMasterAction.getOID()==0){
					try{
						long oid = pstDocMasterAction.insertExc(this.docMasterAction);
                                                if (oid > 0 ){
                                                    //delete dulu sebelumnya
                                                    pstDocMasterActionParam.deletewhereActionId(this.docMasterAction.getOID());
                                                    for (int i = 0; i < vDocMasterActionParam.size();i++){
                                                        DocMasterActionParam docMasterActionParam1 = new DocMasterActionParam();
                                                        try{
                                                            docMasterActionParam1 = (DocMasterActionParam) vDocMasterActionParam.get(i); 
                                                            docMasterActionParam1.setDocActionId(this.docMasterAction.getOID());
                                                        } catch (Exception e){ }
                                                     
                                                            long oid1 = pstDocMasterActionParam.insertExc(docMasterActionParam1);
                                                        
                                                    }
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
						long oid = pstDocMasterAction.updateExc(this.docMasterAction);
                                                 if (oid > 0 ){
                                                    //delete dulu sebelumnya
                                                    pstDocMasterActionParam.deletewhereActionId(this.docMasterAction.getOID());
                                                    for (int i = 0; i < vDocMasterActionParam.size();i++){
                                                        DocMasterActionParam docMasterActionParam1 = new DocMasterActionParam();
                                                        try{
                                                            docMasterActionParam1 = (DocMasterActionParam) vDocMasterActionParam.get(i);
                                                            docMasterActionParam1.setDocActionId(this.docMasterAction.getOID());
                                                        } catch (Exception e){ }
                                                     
                                                            long oid1 = pstDocMasterActionParam.insertExc(docMasterActionParam1);
                                                        
                                                    }
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
				if (oidDocMasterAction != 0) {
					try {
						docMasterAction = PstDocMasterAction.fetchExc(oidDocMasterAction);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidDocMasterAction != 0) {
					try {
						docMasterAction = PstDocMasterAction.fetchExc(oidDocMasterAction);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidDocMasterAction != 0){
					try{
						long oid = PstDocMasterAction.deleteExc(oidDocMasterAction);
                                                if (oid > 0 ){
                                                    //delete dulu sebelumnya
                                                    pstDocMasterActionParam.deletewhereActionId(oidDocMasterAction);
                                                    
                                                }
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
