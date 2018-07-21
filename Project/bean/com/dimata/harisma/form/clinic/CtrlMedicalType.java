/* 
 * Ctrl Name  		:  CtrlMedicalType.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.clinic;

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
import com.dimata.harisma.entity.clinic.*;

public class CtrlMedicalType extends Control implements I_Language 
{
	public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "Medical Type sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Medical Type already exist", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private MedicalType medicalType;
	private PstMedicalType pstMedicalType;
	private FrmMedicalType frmMedicalType;
	int language = LANGUAGE_FOREIGN;

	public CtrlMedicalType(HttpServletRequest request){
		msgString = "";
		medicalType = new MedicalType();
		try{
			pstMedicalType = new PstMedicalType(0);
		}catch(Exception e){;}
		frmMedicalType = new FrmMedicalType(request, medicalType);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmMedicalType.addError(frmMedicalType.FRM_FIELD_MEDICAL_TYPE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public MedicalType getMedicalType() { return medicalType; } 

	public FrmMedicalType getForm() { return frmMedicalType; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidMedicalType){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidMedicalType != 0){
					try{
						medicalType = PstMedicalType.fetchExc(oidMedicalType);
                                                //medicalType.setOID(oidMedicalType);
					}catch(Exception exc){
					}
				}

                                System.out.println("\nmedicalType oid : "+medicalType.getOID());    

				frmMedicalType.requestEntityObject(medicalType);

				if(frmMedicalType.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

                                String whereClause = "("+PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE]+" = '"+medicalType.getTypeCode()+"'"+
                                                                 " AND "+PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_NAME]+" = '"+medicalType.getTypeName()+"')"+
                                                     " AND "+PstMedicalType.fieldNames[PstMedicalType.FLD_MEDICAL_TYPE_ID]+" <> "+oidMedicalType;
                                
                                
                                System.out.println("\n\n whereClause : "+whereClause);
                                
                                Vector lists = PstMedicalType.list(0,0,whereClause,"");
                                
                                System.out.println("lists : "+lists);
                                
                                if(lists != null && lists.size()>0){
                                    
                                    System.out.println("already exist bo....\n");
                                    
                                    msgString = resultText[language][RSLT_EST_CODE_EXIST];
                                    return RSLT_EST_CODE_EXIST;
                                }

				if(medicalType.getOID()==0){
					try{
                                                System.out.println("\ninsert : medicalType.getOID() : "+medicalType.getOID());
                                            
						long oid = pstMedicalType.insertExc(this.medicalType);
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
						long oid = pstMedicalType.updateExc(this.medicalType);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidMedicalType != 0) {
					try {
						medicalType = PstMedicalType.fetchExc(oidMedicalType);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidMedicalType != 0) {
					try {
						medicalType = PstMedicalType.fetchExc(oidMedicalType);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidMedicalType != 0){
					try{
						long oid = PstMedicalType.deleteExc(oidMedicalType);
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
