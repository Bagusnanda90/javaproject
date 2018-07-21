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

public class CtrlDocMasterFlow extends Control implements I_Language{
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
    private DocMasterFlow docMasterFlow;
    private PstDocMasterFlow pstDocMasterFlow;
    private FrmDocMasterFlow frmDocMasterFlow;
    int language = LANGUAGE_DEFAULT;

    public CtrlDocMasterFlow(HttpServletRequest request) {
        msgString = "";
        docMasterFlow = new DocMasterFlow();
        try {
            pstDocMasterFlow = new PstDocMasterFlow(0);
        } catch (Exception e) {
            ;
        }
        frmDocMasterFlow = new FrmDocMasterFlow(request, docMasterFlow);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDocMasterFlow.addError(frmDocMasterFlow.FRM_FIELD_DOC_MASTER_FLOW_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public DocMasterFlow getdDocMasterFlow() {
        return docMasterFlow;
    }

    public FrmDocMasterFlow getForm() {
        return frmDocMasterFlow;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidDocMasterFlow, String oidDelete) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidDocMasterFlow != 0){
					try{
						docMasterFlow = pstDocMasterFlow.fetchExc(oidDocMasterFlow);
					}catch(Exception exc){
					}
				}

				frmDocMasterFlow.requestEntityObject(docMasterFlow);

				if(frmDocMasterFlow.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(docMasterFlow.getOID()==0){
					try{
						long oid = pstDocMasterFlow.insertExc(this.docMasterFlow);
                                                msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
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
						long oid = pstDocMasterFlow.updateExc(this.docMasterFlow);
                                                msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidDocMasterFlow != 0) {
					try {
						docMasterFlow = PstDocMasterFlow.fetchExc(oidDocMasterFlow);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidDocMasterFlow != 0) {
					try {
						docMasterFlow = PstDocMasterFlow.fetchExc(oidDocMasterFlow);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidDocMasterFlow != 0){
					try{
						long oid = PstDocMasterFlow.deleteExc(oidDocMasterFlow);
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
                        case Command.DELETEALL:
                                //splits => untuk mengurutkan beberapa data menjadi baris data
                                String[] splits = oidDelete.split(",");
                                for (String asset : splits) {
                                    if (asset != "") {
                                        long oidDoc = Long.parseLong(asset);
                                        if (oidDoc != 0) {
                                            try {
                                                long oid = PstDocMasterFlow.deleteExc(oidDoc);
                                                if (oid != 0) {
                                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                                    excCode = RSLT_OK;
                                                } else {
                                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                                    excCode = RSLT_FORM_INCOMPLETE;
                                                }
                                            } catch (DBException dbexc) {
                                                excCode = dbexc.getErrorCode();
                                                msgString = getSystemMessage(excCode);
                                            } catch (Exception exc) {
                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                            }
                                        }
                                    }
                                }
                                break;    

			default :

		}
		return rsCode;
	}
}
