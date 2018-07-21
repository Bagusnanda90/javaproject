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
import com.dimata.harisma.entity.employee.PstEmpAward;
import com.dimata.harisma.entity.employee.PstEmpReprimand;
import com.dimata.harisma.entity.employee.PstTrainingHistory;
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

public class CtrlEmpDoc extends Control implements I_Language{
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
    private EmpDoc empDoc;
    private PstEmpDoc pstEmpDoc;
    private FrmEmpDoc frmEmpDoc;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpDoc(HttpServletRequest request) {
        msgString = "";
        empDoc = new EmpDoc();
        try {
            pstEmpDoc = new PstEmpDoc(0);
        } catch (Exception e) {
            ;
        }
        frmEmpDoc = new FrmEmpDoc(request, empDoc);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpDoc.addError(frmEmpDoc.FRM_FIELD_EMP_DOC_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpDoc getEmpDoc() {
        return empDoc;
    }

    public FrmEmpDoc getForm() {
        return frmEmpDoc;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpDoc, String oidDeleteAll) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidEmpDoc != 0){
					try{
						empDoc = PstEmpDoc.fetchExc(oidEmpDoc);
					}catch(Exception exc){
					}
				}

				frmEmpDoc.requestEntityObject(empDoc);

				if(frmEmpDoc.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(empDoc.getOID()==0){
					try{
						long oid = pstEmpDoc.insertExc(this.empDoc);
                                                msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                                                //Update Document Details
                                                if (oid != 0){
                                                    Vector listEmpDocList = new Vector();
                                                    Vector listEmpDocField = new Vector();
                                                    Vector listEmpDocRecipient = new Vector();
                                                    Vector listEmpDocListMutation = new Vector();
                                                    String whereList = PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID] + " = 0";
                                                    String whereField = PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID] + " = 0";
                                                    String whereRecipient = PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID] + " = 0";
                                                    String whereListMutation = PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID] + " =0";
                                                    
                                                    listEmpDocList = PstEmpDocList.list(0, 0, whereList, "");
                                                    listEmpDocField = PstEmpDocField.list(0, 0, whereField, "");
                                                    listEmpDocRecipient = PstEmpDocRecipient.list(0, 0, whereRecipient, "");
                                                    listEmpDocListMutation = PstEmpDocListMutation.list(0, 0, whereListMutation, "");
                                                    
                                                    if(listEmpDocList.size() > 0){
                                                        for (int i = 0; i < listEmpDocList.size(); i++){
                                                            EmpDocList docList = (EmpDocList) listEmpDocList.get(i);
                                                            String sqlUpdate = "UPDATE "+PstEmpDocList.TBL_HR_EMP_DOC_LIST+" SET ";
                                                            sqlUpdate += PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_ID]+"="+oid+"";
                                                            //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                                            sqlUpdate += " WHERE "+PstEmpDocList.fieldNames[PstEmpDocList.FLD_EMP_DOC_LIST_ID]+"="+docList.getOID();
                                                            DBHandler.execUpdate(sqlUpdate);
                                                            if (docList.getObject_name().equals("EMPAWARD")){
                                                                String sqlUpdateAward = "UPDATE "+PstEmpAward.TBL_AWARD+" SET ";
                                                                sqlUpdateAward += PstEmpAward.fieldNames[PstEmpAward.FLD_DOC_ID]+"="+oid+"";
                                                                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                                                sqlUpdateAward += " WHERE "+PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_ID]+"="+docList.getEmp_award_id();
                                                                DBHandler.execUpdate(sqlUpdateAward);
                                                            } 
                                                            if (docList.getObject_name().equals("EMPREPRIMAND")){
                                                                String sqlUpdateRep = "UPDATE "+PstEmpReprimand.TBL_REPRIMAND+" SET ";
                                                                sqlUpdateRep += PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMP_DOC_ID]+"="+oid+"";
                                                                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                                                sqlUpdateRep += " WHERE "+PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REPRIMAND_ID]+"="+docList.getEmp_reprimand();
                                                                DBHandler.execUpdate(sqlUpdateRep);
                                                            }
                                                            if (docList.getObject_name().equals("EMPTRAINING")){
                                                                String sqlUpdateRep = "UPDATE "+PstTrainingHistory.TBL_HR_TRAINING_HISTORY+" SET ";
                                                                sqlUpdateRep += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMP_DOC_ID]+"="+oid+"";
                                                                //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                                                sqlUpdateRep += " WHERE "+PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_HISTORY_ID]+"="+docList.getEmp_training();
                                                                DBHandler.execUpdate(sqlUpdateRep);
                                                            }
                                                        }
                                                    }
                                                    if(listEmpDocField.size() > 0){
                                                        for (int i = 0; i < listEmpDocField.size(); i++){
                                                            EmpDocField docField = (EmpDocField) listEmpDocField.get(i);
                                                            String sqlUpdate = "UPDATE "+PstEmpDocField.TBL_HR_EMP_DOC_FIELD+" SET ";
                                                            sqlUpdate += PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_ID]+"="+oid+"";
                                                            //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                                            sqlUpdate += " WHERE "+PstEmpDocField.fieldNames[PstEmpDocField.FLD_EMP_DOC_FIELD_ID]+"="+docField.getOID();
                                                            DBHandler.execUpdate(sqlUpdate);
                                                        }
                                                    }
                                                    if(listEmpDocRecipient.size() > 0){
                                                        for (int i=0; i < listEmpDocRecipient.size(); i++){
                                                            EmpDocRecipient empDocRecipient = (EmpDocRecipient) listEmpDocRecipient.get(i);
                                                            String sqlUpdate = "UPDATE "+PstEmpDocRecipient.TBL_HR_EMP_DOC_RECIPIENT+" SET ";
                                                            sqlUpdate += PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_ID]+"="+oid+"";
                                                            //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                                            sqlUpdate += " WHERE "+PstEmpDocRecipient.fieldNames[PstEmpDocRecipient.FLD_EMP_DOC_RECIPIENT_ID]+"="+empDocRecipient.getOID();
                                                            DBHandler.execUpdate(sqlUpdate);
                                                        }
                                                    }
                                                    if(listEmpDocListMutation.size() > 0){
                                                        for (int i=0; i <listEmpDocListMutation.size(); i++){
                                                            EmpDocListMutation empDocListMutation = (EmpDocListMutation) listEmpDocListMutation.get(i);
                                                            String sqlUpdate = "UPDATE "+PstEmpDocListMutation.TBL_EMP_DOC_LIST_MUTATION+" SET ";
                                                            sqlUpdate += PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_ID]+"="+oid+"";
                                                            //sqlUpdateEmp += PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]+"="+empCategoryId+" ";
                                                            sqlUpdate += " WHERE "+PstEmpDocListMutation.fieldNames[PstEmpDocListMutation.FLD_EMP_DOC_LIST_MUTATION_ID]+"="+empDocListMutation.getOID();
                                                            DBHandler.execUpdate(sqlUpdate);
                                                        }
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
						long oid = pstEmpDoc.updateExc(this.empDoc);
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
				if (oidEmpDoc != 0) {
					try {
						empDoc = PstEmpDoc.fetchExc(oidEmpDoc);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpDoc != 0) {
					try {
						empDoc = PstEmpDoc.fetchExc(oidEmpDoc);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidEmpDoc != 0){
					try{
						long oid = PstEmpDoc.deleteExc(oidEmpDoc);
                                                msgString = FRMMessage.getMsg(FRMMessage.MSG_DELETED);
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
                            case Command.POST :
                                EmpDoc empDocNew = new EmpDoc();
				if(oidEmpDoc != 0){
					try{
						empDocNew = PstEmpDoc.fetchExc(oidEmpDoc);
					}catch(Exception exc){
					}
				}

				frmEmpDoc.requestEntityObject(empDoc);

				empDocNew.setDetails(this.empDoc.getDetails());


                                try {
                                        long oid = pstEmpDoc.updateExc(empDocNew);
                                }catch (DBException dbexc){
                                        excCode = dbexc.getErrorCode();
                                        msgString = getSystemMessage(excCode);
                                }catch (Exception exc){
                                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
                                }

				
				break;
                                case Command.DELETEALL:
                                //splits => untuk mengurutkan beberapa data menjadi baris data
                                String[] splits = oidDeleteAll.split(",");
                                for (String asset : splits) {
                                    if (asset != "") {
                                        long oidDoc = Long.parseLong(asset);
                                        if (oidDoc != 0) {
                                            try {
                                                long oid = PstEmpDoc.deleteExc(oidDoc);
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
