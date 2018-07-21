/* 
 * Ctrl Name  		:  CtrlFamilyMember.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia
 * @version  		: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.employee;

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

public class CtrlDefaultSchedule extends Control implements I_Language 
{
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
	private FamilyMember familyMember;
	private PstFamilyMember pstFamilyMember;
	private FrmFamilyMember frmFamilyMember;
	int language = LANGUAGE_DEFAULT;

	public CtrlDefaultSchedule(HttpServletRequest request){
		msgString = "";
		familyMember = new FamilyMember();
		try{
			pstFamilyMember = new PstFamilyMember(0);
		}catch(Exception e){;}
		frmFamilyMember = new FrmFamilyMember(request, familyMember);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmFamilyMember.addError(frmFamilyMember.FRM_FIELD_FAMILY_MEMBER_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public FamilyMember getFamilyMember() { return familyMember; } 

	public FrmFamilyMember getForm() { return frmFamilyMember; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidEmployee, Vector famMember){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidEmployee != 0 && (famMember!=null && famMember.size()>0)){
                    PstFamilyMember.deleteByEmployee(oidEmployee);
                    for(int i=0; i<famMember.size(); i++){
                        FamilyMember fam = (FamilyMember)famMember.get(i);
                        fam.setEmployeeId(oidEmployee);

                        if(fam.getFullName().length()>0){
	                        try{
	                        	PstFamilyMember.insertExc(fam);
	                        }
	                        catch(Exception e){
	                            System.out.println("Exception e : "+e.toString());
	                        }
                        }
                    }
				}
	            else{
	                msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
	            }

              /*  familyMember.setOID(oidFamilyMember);
                familyMember.setEmployeeId(oidEmployee);

				frmFamilyMember.requestEntityObject(familyMember);

				if((frmFamilyMember.errorSize()>0)||(familyMember.getEmployeeId()==0)) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(familyMember.getOID()==0){
					try{
						long oid = pstFamilyMember.insertExc(this.familyMember);
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
						long oid = pstFamilyMember.updateExc(this.familyMember);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}    */
				break;

			case Command.EDIT :
			/*	if (oidFamilyMember != 0) {
					try {
						familyMember = PstFamilyMember.fetchExc(oidFamilyMember);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}  */
				break;

			case Command.ASK :
			/*	if (oidFamilyMember != 0) {
					try {
						familyMember = PstFamilyMember.fetchExc(oidFamilyMember);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}  */
				break;

			case Command.DELETE :
			/*	if (oidFamilyMember != 0){
					try{
						long oid = PstFamilyMember.deleteExc(oidFamilyMember);
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
				}  */
				break;

			default :

		}
		return rsCode;
	}
}
