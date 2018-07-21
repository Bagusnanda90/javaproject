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
import com.dimata.harisma.entity.search.SrcEmployee;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.*;
import org.apache.jasper.tagplugins.jstl.core.Catch;

public class CtrlEmpDocExpenses extends Control implements I_Language{
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
    private EmpDocExpenses empDocField;
    private PstEmpDocExpenses pstEmpDocExpenses;
    private FrmEmpDocExpenses frmEmpDocExpenses;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpDocExpenses(HttpServletRequest request) {
        msgString = "";
        empDocField = new EmpDocExpenses();
        try {
            pstEmpDocExpenses = new PstEmpDocExpenses(0);
        } catch (Exception e) {
            ;
        }
        frmEmpDocExpenses = new FrmEmpDocExpenses(request, empDocField);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpDocExpenses.addError(frmEmpDocExpenses.FRM_FIELD_EMP_DOC_EXPENSE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpDocExpenses getdEmpDocExpenses() {
        return empDocField;
    }

    public FrmEmpDocExpenses getForm() {
        return frmEmpDocExpenses;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }
   public int action(int cmd, long oidEmpDocExpenses) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidEmpDocExpenses != 0){
					try{
						empDocField = pstEmpDocExpenses.fetchExc(oidEmpDocExpenses);
					}catch(Exception exc){
					}
				}

				frmEmpDocExpenses.requestEntityObject(empDocField);

				if(frmEmpDocExpenses.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                DocMasterExpense docMasterExpense =new DocMasterExpense();
                                try {
                                    docMasterExpense =  PstDocMasterExpense.fetchExc(this.empDocField.getDocMasterExpenseId());
                                } catch (Exception e){}
                                
                                if((this.empDocField.getBudgetValue() > docMasterExpense.getBudget_max()) && (this.empDocField.getBudgetValue() < docMasterExpense.getBudget_min())) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
                                this.empDocField.setExpenseUnit(docMasterExpense.getUnit_type());
				if(empDocField.getOID()==0){
					try{
						long oid = pstEmpDocExpenses.insertExc(this.empDocField);
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
						long oid = pstEmpDocExpenses.updateExc(this.empDocField);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidEmpDocExpenses != 0) {
					try {
						empDocField = pstEmpDocExpenses.fetchExc(oidEmpDocExpenses);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpDocExpenses != 0) {
					try {
						empDocField = pstEmpDocExpenses.fetchExc(oidEmpDocExpenses);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidEmpDocExpenses != 0){
					try{
						long oid = pstEmpDocExpenses.deleteExc(oidEmpDocExpenses);
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
