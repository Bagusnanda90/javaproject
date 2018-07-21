/* 
 * Ctrl Name  		:  CtrlTraining.java 
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

package com.dimata.harisma.form.masterdata;

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

public class CtrlTraining extends Control implements I_Language 
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
	private Training training;
	private PstTraining pstTraining;
	private FrmTraining frmTraining;
	int language = LANGUAGE_DEFAULT;

	public CtrlTraining(HttpServletRequest request){
		msgString = "";
		training = new Training();
		try{
			pstTraining = new PstTraining(0);
		}catch(Exception e){;}
		frmTraining = new FrmTraining(request, training);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmTraining.addError(frmTraining.FRM_FIELD_TRAINING_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public Training getTraining() { return training; } 

	public FrmTraining getForm() { return frmTraining; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidTraining, String[] depid){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
        long oid = 0;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidTraining != 0){
					try{
						training = PstTraining.fetchExc(oidTraining);
					}catch(Exception exc){
					}
				}

				frmTraining.requestEntityObject(training);

				if(frmTraining.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}



				if(training.getOID()==0){
					try{
						oid = pstTraining.insertExc(this.training);
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
						oid = pstTraining.updateExc(this.training);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}

                if(oid!=0){
                    PstTrainingDept.deleteAllByTraining(oid);
                    if(depid!=null && (depid.length > 0)){
                        for(int i=0; i<depid.length; i++){
                            String str = depid[i];
                            long depOID = Long.parseLong(str);

                            TrainingDept td = new TrainingDept();
                            td.setTrainingId(oid);
                            td.setDepartmentId(depOID);
                            try{
                            	PstTrainingDept.insertExc(td);
                            }
                            catch(Exception e){
                                System.out.println("--------- exception :: dep training inst : "+e.toString());
                            }

                        }
                    }
                }

				break;

			case Command.EDIT :
				if (oidTraining != 0) {
					try {
						training = PstTraining.fetchExc(oidTraining);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidTraining != 0) {
					try {
						training = PstTraining.fetchExc(oidTraining);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidTraining != 0){
					try{
						oid = PstTraining.deleteExc(oidTraining);
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
