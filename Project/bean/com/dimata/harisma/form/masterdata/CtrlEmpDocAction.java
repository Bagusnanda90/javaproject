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
import com.dimata.harisma.entity.employee.CareerPath;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmployee;
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
import com.dimata.harisma.entity.masterdata.location.Location;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.*;

public class CtrlEmpDocAction extends Control implements I_Language{
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
    private EmpDocAction empDocAction;
    private PstEmpDocAction pstEmpDocAction;
    private FrmEmpDocAction frmEmpDocAction;
    //private FrmEmpDocActionParam frmEmpDocActionParam;
    //private PstEmpDocActionParam pstEmpDocActionParam;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpDocAction(HttpServletRequest request) {
        msgString = "";
        empDocAction = new EmpDocAction();
        try {
            pstEmpDocAction = new PstEmpDocAction(0);
        } catch (Exception e) {
            ;
        }
        frmEmpDocAction = new FrmEmpDocAction(request, empDocAction);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpDocAction.addError(frmEmpDocAction.FRM_FIELD_EMP_DOC_ACTION_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpDocAction getdEmpDocAction() {
        return empDocAction;
    }

    public FrmEmpDocAction getForm() {
        return frmEmpDocAction;
    }
//     public FrmEmpDocActionParam getFormParam() {
//        return frmEmpDocActionParam;
//    }
    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpDocAction,long oidDocAction, long oidEmpDoc,int saveType) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidEmpDocAction != 0){
					try{
						empDocAction = PstEmpDocAction.fetchExc(oidEmpDocAction);
					}catch(Exception exc){
					}
				}

			      // frmEmpDocAction.requestEntityObject(empDocAction);
                              // frmEmpDocAction.requestEntityObjectMultiple(actionNameKey);
                              // Vector vEmpDocActionParam = frmEmpDocAction.getVEmpDocActionParam();
                                        
                                        
//				if(frmEmpDocAction.errorSize()>0) {
//					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
//					return RSLT_FORM_INCOMPLETE ;
//				}
                                
                                DocMasterAction docMasterAction = new DocMasterAction();
                                try {
                                    docMasterAction = PstDocMasterAction.fetchExc(oidDocAction);
                                } catch (Exception e){
                                }
                                if (docMasterAction != null){
                                    empDocAction.setActionName(docMasterAction.getActionName());
                                    empDocAction.setActionTitle(docMasterAction.getActionTitle());
                                    empDocAction.setEmpDocId(oidEmpDoc);
                                }
                                
                                
                                                                
                                
                                
                                
                                
                                
                                
                                
                                
                                
				if(empDocAction.getOID()==0){
					try{
						long oid = pstEmpDocAction.insertExc(this.empDocAction);
                                                String whereC = PstDocMasterActionParam.fieldNames[PstDocMasterActionParam.FLD_DOC_ACTION_ID]+ " = " +this.empDocAction.getOID();
                                                Vector list = PstDocMasterActionParam.list(0, 0, whereC, "");
                                                
//                                                if (oid > 0 ){
//                                                    //delete dulu sebelumnya
//                                                    //pstEmpDocActionPar.deletewhereActionId(this.empDocAction.getOID());
//                                                    for (int i = 0; i < list.size();i++){
//                                                        EmpDocActionParam empDocActionParam1 = new EmpDocActionParam();
//                                                        try{
//                                                            empDocActionParam1 = (EmpDocActionParam) vEmpDocActionParam.get(i); 
//                                                            empDocActionParam1.setDocActionId(this.empDocAction.getOID());
//                                                        } catch (Exception e){ }
//                                                     
//                                                            long oid1 = pstEmpDocActionParam.insertExc(empDocActionParam1);
//                                                        
//                                                    }
//                                                }
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
						long oid = pstEmpDocAction.updateExc(this.empDocAction);
//                                                 if (oid > 0 ){
//                                                    //delete dulu sebelumnya
//                                                    pstEmpDocActionParam.deletewhereActionId(this.empDocAction.getOID());
//                                                    for (int i = 0; i < vEmpDocActionParam.size();i++){
//                                                        EmpDocActionParam empDocActionParam1 = new EmpDocActionParam();
//                                                        try{
//                                                            empDocActionParam1 = (EmpDocActionParam) vEmpDocActionParam.get(i);
//                                                            empDocActionParam1.setDocActionId(this.empDocAction.getOID());
//                                                        } catch (Exception e){ }
//                                                     
//                                                            long oid1 = pstEmpDocActionParam.insertExc(empDocActionParam1);
//                                                        
//                                                    }
//                                                }
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidEmpDocAction != 0) {
					try {
						empDocAction = PstEmpDocAction.fetchExc(oidEmpDocAction);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpDocAction != 0) {
					try {
						empDocAction = PstEmpDocAction.fetchExc(oidEmpDocAction);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidEmpDocAction != 0){
					try{
						long oid = PstEmpDocAction.deleteExc(oidEmpDocAction);
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
