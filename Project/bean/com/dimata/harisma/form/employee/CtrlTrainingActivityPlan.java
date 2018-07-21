/* 
 * Ctrl Name  		:  CtrlTrainingActivityPlan.java 
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
import com.dimata.harisma.entity.masterdata.*;

public class CtrlTrainingActivityPlan extends Control implements I_Language 
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
	private TrainingActivityPlan trainingActivityPlan;
	private PstTrainingActivityPlan pstTrainingActivityPlan;
	private FrmTrainingActivityPlan frmTrainingActivityPlan;
	int language = LANGUAGE_DEFAULT;

	public CtrlTrainingActivityPlan(HttpServletRequest request){
		msgString = "";
		trainingActivityPlan = new TrainingActivityPlan();
		try{
			pstTrainingActivityPlan = new PstTrainingActivityPlan(0);
		}catch(Exception e){;}
		frmTrainingActivityPlan = new FrmTrainingActivityPlan(request, trainingActivityPlan);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmTrainingActivityPlan.addError(frmTrainingActivityPlan.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public TrainingActivityPlan getTrainingActivityPlan() { return trainingActivityPlan; } 

	public FrmTrainingActivityPlan getForm() { return frmTrainingActivityPlan; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidTrainingActivityPlan){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidTrainingActivityPlan != 0){
					try{
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					}catch(Exception exc){
					}
				}

				frmTrainingActivityPlan.requestEntityObject(trainingActivityPlan);

				if(frmTrainingActivityPlan.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                                
                                Training training = new Training();
                                
                                try {
                                    training = PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
                                }
                                catch(Exception e) {
                                    training = new Training();
                                }
                                
                                trainingActivityPlan.setProgram(training.getName());

				if(trainingActivityPlan.getOID()==0){
					try{
						long oid = pstTrainingActivityPlan.insertExc(this.trainingActivityPlan);
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
						long oid = pstTrainingActivityPlan.updateExc(this.trainingActivityPlan);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidTrainingActivityPlan != 0) {
					try {
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidTrainingActivityPlan != 0) {
					try {
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidTrainingActivityPlan != 0){
					try{
						long oid = PstTrainingActivityPlan.deleteExc(oidTrainingActivityPlan);
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
                                
                    case Command.BACK :
                                if(oidTrainingActivityPlan != 0){
					try{
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					}catch(Exception exc){
					}
				}

				frmTrainingActivityPlan.requestEntityObject(trainingActivityPlan);

			default :

		}
		return rsCode;
	}
        
        public int actionPlan(int cmd , long oidTrainingActivityPlan){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidTrainingActivityPlan != 0){
					try{
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					}catch(Exception exc){
					}
				}

				frmTrainingActivityPlan.requestEntityObject(trainingActivityPlan);                                
                                
				if(frmTrainingActivityPlan.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;   
				}

				if(trainingActivityPlan.getOID()==0){
					try{
						long oid = pstTrainingActivityPlan.insertExc(this.trainingActivityPlan);
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
						long oid = pstTrainingActivityPlan.updateExc(this.trainingActivityPlan);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidTrainingActivityPlan != 0) {
					try {
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidTrainingActivityPlan != 0) {
					try {
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidTrainingActivityPlan != 0){
					try{
						long oid = PstTrainingActivityPlan.deleteExc(oidTrainingActivityPlan);
                                                
                                                
                                                // delete related child s                                                
                                                try {
                                                    String where = "";
                                                    
                                                    where = PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] + "=" + oid;                                                    
                                                    Vector listAttendance = PstTrainingAttendancePlan.list(0, 0, where, "");
                                                    
                                                    for(int i=0; i<listAttendance.size(); i++) {
                                                        long loid = ((TrainingAttendancePlan)listAttendance.get(i)).getOID();
                                                        PstTrainingAttendancePlan.deleteExc(loid);
                                                    }
                                                    
                                                    where = PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] + "=" + oid;
                                                    Vector listSchedule = PstTrainingSchedule.list(0, 0, where, "");
                                                    
                                                    for(int i=0; i<listSchedule.size(); i++) {
                                                        long loid = ((TrainingSchedule)listSchedule.get(i)).getOID();
                                                        PstTrainingSchedule.deleteExc(loid);
                                                    }
                                                }
                                                catch(Exception e) {}
                                                
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
                                
                    case Command.BACK :
                                if(oidTrainingActivityPlan != 0){
					try{
						trainingActivityPlan = PstTrainingActivityPlan.fetchExc(oidTrainingActivityPlan);
					}catch(Exception exc){
					}
				}

				frmTrainingActivityPlan.requestEntityObject(trainingActivityPlan);

			default :

		}
		return rsCode;
	}
}
