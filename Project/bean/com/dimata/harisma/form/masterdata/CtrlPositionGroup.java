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

public class CtrlPositionGroup extends Control implements I_Language{
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
    private PositionGroup positionGroup;
    private PstPositionGroup pstPositionGroup;
    private FrmPositionGroup frmPositionGroup;
    int language = LANGUAGE_DEFAULT;

    public CtrlPositionGroup(HttpServletRequest request) {
        msgString = "";
        positionGroup = new PositionGroup();
        try {
            pstPositionGroup = new PstPositionGroup(0);
        } catch (Exception e) {
            ;
        }
        frmPositionGroup = new FrmPositionGroup(request, positionGroup);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPositionGroup.addError(frmPositionGroup.FRM_FIELD_POSITION_GROUP_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PositionGroup getdPositionGroup() {
        return positionGroup;
    }

    public FrmPositionGroup getForm() {
        return frmPositionGroup;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPositionGroup){
        return action(cmd, oidPositionGroup, "");
    }
    
    public int action(int cmd, long oidPositionGroup, String oidDeleteAll) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

          case Command.SAVE :
				if(oidPositionGroup != 0){
					try{
						positionGroup = PstPositionGroup.fetchExc(oidPositionGroup);
					}catch(Exception exc){
					}
				}

				frmPositionGroup.requestEntityObject(positionGroup);

				if(frmPositionGroup.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(positionGroup.getOID()==0){
					try{
						long oid = pstPositionGroup.insertExc(this.positionGroup);
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
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
						long oid = pstPositionGroup.updateExc(this.positionGroup);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidPositionGroup != 0) {
					try {
						positionGroup = PstPositionGroup.fetchExc(oidPositionGroup);
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidPositionGroup != 0) {
					try {
						positionGroup = PstPositionGroup.fetchExc(oidPositionGroup);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidPositionGroup != 0){
					try{
						long oid = PstPositionGroup.deleteExc(oidPositionGroup);
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
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
                                    String[] splits = oidDeleteAll.split(",");
                                    for (String asset : splits) {
                                        if (asset != "") {
                                            long oidDoc = Long.parseLong(asset);
                                            if (oidDoc != 0) {
                                                try {
                                                    long oid = PstPositionGroup.deleteExc(oidDoc);
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
