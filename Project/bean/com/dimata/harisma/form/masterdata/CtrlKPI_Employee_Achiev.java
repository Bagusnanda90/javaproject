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

public class CtrlKPI_Employee_Achiev extends Control implements I_Language{
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
    private KPI_Employee_Achiev kPI_Employee_Achiev;
    private PstKPI_Employee_Achiev pstKPI_Employee_Achiev;
    private FrmKPI_Employee_Achiev frmKPI_Employee_Achiev;
    int language = LANGUAGE_DEFAULT;

    public CtrlKPI_Employee_Achiev(HttpServletRequest request) {
        msgString = "";
        kPI_Employee_Achiev = new KPI_Employee_Achiev();
        try {
            pstKPI_Employee_Achiev = new PstKPI_Employee_Achiev(0);
        } catch (Exception e) {
            ;
        }
        frmKPI_Employee_Achiev = new FrmKPI_Employee_Achiev(request, kPI_Employee_Achiev);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmKPI_Employee_Achiev.addError(frmKPI_Employee_Achiev.FRM_FIELD_KPI_EMPLOYEE_ACHIEV_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public KPI_Employee_Achiev getdKPI_Employee_Achiev() {
        return kPI_Employee_Achiev;
    }

    public FrmKPI_Employee_Achiev getForm() {
        return frmKPI_Employee_Achiev;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidKPI_Employee_Achiev) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidKPI_Employee_Achiev != 0){
					try{
						kPI_Employee_Achiev = PstKPI_Employee_Achiev.fetchExc(oidKPI_Employee_Achiev);
					}catch(Exception exc){
					}
				}

				frmKPI_Employee_Achiev.requestEntityObject(kPI_Employee_Achiev);

				if(frmKPI_Employee_Achiev.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(kPI_Employee_Achiev.getOID()==0){
					try{
						long oid = pstKPI_Employee_Achiev.insertExc(this.kPI_Employee_Achiev);
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
						long oid = pstKPI_Employee_Achiev.updateExc(this.kPI_Employee_Achiev);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidKPI_Employee_Achiev != 0) {
					try {
						kPI_Employee_Achiev = PstKPI_Employee_Achiev.fetchExc(oidKPI_Employee_Achiev);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidKPI_Employee_Achiev != 0) {
					try {
						kPI_Employee_Achiev = PstKPI_Employee_Achiev.fetchExc(oidKPI_Employee_Achiev);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidKPI_Employee_Achiev != 0){
					try{
						long oid = PstKPI_Employee_Achiev.deleteExc(oidKPI_Employee_Achiev);
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
