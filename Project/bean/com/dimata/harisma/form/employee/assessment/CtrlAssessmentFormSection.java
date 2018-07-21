/* 
 * Ctrl Name  		:  CtrlAssessmentFormSection.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.employee.assessment;

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
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.form.locker.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.PstAssessmentFormSection;

public class CtrlAssessmentFormSection extends Control implements I_Language 
{
	public static int RSLT_OK = 0;
	public static int RSLT_UNKNOWN_ERROR = 1;
	public static int RSLT_EST_CODE_EXIST = 2;
	public static int RSLT_FORM_INCOMPLETE = 3;

	public static String[][] resultText = {
		{"Berhasil", "Tidak dapat diproses", "Kode sudah ada", "Data tidak lengkap"},
		{"Succes", "Can not process", "Code exist", "Data incomplete"}
	};

	private int start;
	private String msgString;
	private AssessmentFormSection assessmentFormSection;
	private PstAssessmentFormSection pstAssessmentFormSection;
	private FrmAssessmentFormSection frmAssessmentFormSection;


	int language = LANGUAGE_DEFAULT;

	public CtrlAssessmentFormSection(HttpServletRequest request){
            msgString = "";
            assessmentFormSection = new AssessmentFormSection();
            try{
                    pstAssessmentFormSection = new PstAssessmentFormSection(0);
            }catch(Exception e){;}

            frmAssessmentFormSection = new FrmAssessmentFormSection(request,assessmentFormSection);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
                    case I_DBExceptionInfo.MULTIPLE_ID :
                            this.frmAssessmentFormSection.addError(frmAssessmentFormSection.FRM_FIELD_ASS_FORM_SECTION_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public AssessmentFormSection getAssessmentFormSection() { return assessmentFormSection; } 

	public FrmAssessmentFormSection getForm() { return frmAssessmentFormSection; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidAssessmentFormSection){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidAssessmentFormSection != 0){
					try{
						assessmentFormSection = PstAssessmentFormSection.fetchExc(oidAssessmentFormSection);
					}catch(Exception exc){
					}
				}

				frmAssessmentFormSection.requestEntityObject(assessmentFormSection); 

                                //System.out.println("frmAssessmentFormSection.errorSize() : "+frmAssessmentFormSection.errorSize());
                                
				if(frmAssessmentFormSection.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
				if(assessmentFormSection.getOID()==0){
					try{
						long oid = pstAssessmentFormSection.insertExc(this.assessmentFormSection);
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
						long oid = pstAssessmentFormSection.updateExc(this.assessmentFormSection);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidAssessmentFormSection != 0) {
					try {
						assessmentFormSection = PstAssessmentFormSection.fetchExc(oidAssessmentFormSection);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidAssessmentFormSection != 0) {
					try {
                                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                                            assessmentFormSection = PstAssessmentFormSection.fetchExc(oidAssessmentFormSection);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidAssessmentFormSection != 0){
					try{
						long oid = PstAssessmentFormSection.deleteExc(oidAssessmentFormSection);
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
