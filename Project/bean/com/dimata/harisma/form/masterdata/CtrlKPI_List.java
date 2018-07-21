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

public class CtrlKPI_List extends Control implements I_Language{
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
    private KPI_List kPI_List;
    private PstKPI_List pstKPI_List;
    private FrmKPI_List frmKPI_List;
    int language = LANGUAGE_DEFAULT;

    public CtrlKPI_List(HttpServletRequest request) {
        msgString = "";
        kPI_List = new KPI_List();
        try {
            pstKPI_List = new PstKPI_List(0);
        } catch (Exception e) {
            ;
        }
        frmKPI_List = new FrmKPI_List(request, kPI_List);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmKPI_List.addError(frmKPI_List.FRM_FIELD_KPI_LIST_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public KPI_List getdKPI_List() {
        return kPI_List;
    }

    public FrmKPI_List getForm() {
        return frmKPI_List;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidKPI_List) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidKPI_List != 0){
					try{
						kPI_List = PstKPI_List.fetchExc(oidKPI_List);
					}catch(Exception exc){
					}
				}

				frmKPI_List.requestEntityObject(kPI_List);

				if(frmKPI_List.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(kPI_List.getOID()==0){
					try{
						long oid = pstKPI_List.insertExc(this.kPI_List);
                                                
                                                for (int i= 0 ; i<this.kPI_List.getArrkpigroupSize();i++ ){
                                                   long grpId = this.kPI_List.getArrkpigroup(i);
                                                   KPI_List_Group kPI_List_Group = new KPI_List_Group();
                                                   kPI_List_Group.setKpiListId(oid);
                                                   kPI_List_Group.setKpiGroupId(grpId);
                                                   long p = PstKPI_List_Group.insertExc(kPI_List_Group);
                                                }
                                                // kPI_List.getArrkpigroup(cmd);
                                                
                                                
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
						long oid = pstKPI_List.updateExc(this.kPI_List);
                                                
                                                    long d = PstKPI_List_Group.deletewhereGroup(oid);
                                               
                                                for (int i= 0 ; i<this.kPI_List.getArrkpigroupSize();i++ ){
                                                   long grpId = this.kPI_List.getArrkpigroup(i);
                                                   KPI_List_Group kPI_List_Group = new KPI_List_Group();
                                                   kPI_List_Group.setKpiListId(oid);
                                                   kPI_List_Group.setKpiGroupId(grpId);
                                                   long p = PstKPI_List_Group.insertExc(kPI_List_Group);
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
				if (oidKPI_List != 0) {
					try {
						kPI_List = PstKPI_List.fetchExc(oidKPI_List);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidKPI_List != 0) {
					try {
						kPI_List = PstKPI_List.fetchExc(oidKPI_List);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidKPI_List != 0){
					try{
						long oid = PstKPI_List.deleteExc(oidKPI_List);
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
