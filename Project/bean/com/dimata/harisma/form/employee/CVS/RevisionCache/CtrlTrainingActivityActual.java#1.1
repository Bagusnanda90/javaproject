/* 
 * Ctrl Name  		:  CtrlTrainingActivityActual.java 
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
import com.dimata.gui.jsp.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.employee.*;

public class CtrlTrainingActivityActual extends Control implements I_Language 
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
	private TrainingActivityActual trainingActivityActual;
	private PstTrainingActivityActual pstTrainingActivityActual;
	private FrmTrainingActivityActual frmTrainingActivityActual;
	int language = LANGUAGE_DEFAULT;

	public CtrlTrainingActivityActual(HttpServletRequest request){
		msgString = "";
		trainingActivityActual = new TrainingActivityActual();
		try{
			pstTrainingActivityActual = new PstTrainingActivityActual(0);
		}catch(Exception e){;}
		frmTrainingActivityActual = new FrmTrainingActivityActual(request, trainingActivityActual);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmTrainingActivityActual.addError(frmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_ACTUAL_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public TrainingActivityActual getTrainingActivityActual() { return trainingActivityActual; } 

	public FrmTrainingActivityActual getForm() { return frmTrainingActivityActual; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidTrainingActivityActual, long oidDepartment, HttpServletRequest request){
		msgString = "";
                System.out.println("cmd "+cmd);
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidTrainingActivityActual != 0){
					try{
						trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
					}catch(Exception exc){
					}
				}

				frmTrainingActivityActual.requestEntityObject(trainingActivityActual);
                Date timeIn = ControlDate.getTime(frmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_START_TIME], request);
                trainingActivityActual.setStartTime(timeIn);
                Date timeOut = ControlDate.getTime(frmTrainingActivityActual.fieldNames[frmTrainingActivityActual.FRM_FIELD_END_TIME], request);
                trainingActivityActual.setEndTime(timeOut);


				if(frmTrainingActivityActual.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(trainingActivityActual.getOID()==0){
					try{
						long oid = pstTrainingActivityActual.insertExc(this.trainingActivityActual);
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
						long oid = pstTrainingActivityActual.updateExc(this.trainingActivityActual);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidTrainingActivityActual != 0) {
					try {
						trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidTrainingActivityActual != 0) {
					try {
						trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidTrainingActivityActual != 0){
					try{
						long oid = PstTrainingActivityActual.deleteExc(oidTrainingActivityActual);
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
        
        public int action(int cmd, long oidTrainingActivityActual, TrainingActivityActual trainingActual, HttpServletRequest request){
		msgString = "";
                
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidTrainingActivityActual != 0){
					try{
						trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
					}catch(Exception exc){
					}
				}

				
				if(trainingActivityActual.getOID()==0){
					try{
						long oid = pstTrainingActivityActual.insertExc(trainingActual);
                                                trainingActual.setOID(oid);
					}
                                        catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}

				}else{
					try {
						long oid = pstTrainingActivityActual.updateExc(trainingActual);
                                                trainingActual.setOID(oid);
					}
                                        catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidTrainingActivityActual != 0) {
					try {
						trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidTrainingActivityActual != 0) {
					try {
						trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidTrainingActivityActual != 0){
					try{
						long oid = PstTrainingActivityActual.deleteExc(oidTrainingActivityActual);
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
        
        public synchronized int action(int cmd, String[] trainHours, long oidTraining, long oidSchedule, long oidTrainingPlan, long oidTrainingActivityActual, String[] oidAttendances, HttpServletRequest request){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
                            //update by devin 2014-04-18
                            if(oidTrainingPlan!=0){
                                String whereActual=PstTrainingActivityActual.fieldNames[PstTrainingActivityActual.FLD_TRAINING_ACTIVITY_PLAN_ID]+"="+oidTrainingPlan;
                                Vector cekActivityActual=PstTrainingActivityActual.list(0, 0, whereActual, "");
                                if(cekActivityActual!=null && cekActivityActual.size()>0){
                                    for(int y=0;y<cekActivityActual.size();y++){
                                        TrainingActivityActual actual =(TrainingActivityActual)cekActivityActual.get(y);
                                        oidTrainingActivityActual=actual.getOID();
                                    }
                                }
                            }
                            
				if(oidTrainingActivityActual != 0){
                                    try{
                                        trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
                                    }
                                    catch(Exception exc){}
				}

				frmTrainingActivityActual.requestEntityObject(trainingActivityActual);
                         
                                Date date = new Date(FRMQueryString.requestInt(request, "year"), FRMQueryString.requestInt(request, "month"), FRMQueryString.requestInt(request, "date"));
                                trainingActivityActual.setDate(date);
                              
                                Date timeIn = new Date(date.getYear(), date.getMonth(), date.getDate(), FRMQueryString.requestInt(request, "start_hour"), FRMQueryString.requestInt(request, "start_minute"));
                                trainingActivityActual.setStartTime(timeIn);
                
                                Date timeOut = new Date(date.getYear(), date.getMonth(), date.getDate(), FRMQueryString.requestInt(request, "end_hour"), FRMQueryString.requestInt(request, "end_minute"));
                                trainingActivityActual.setEndTime(timeOut);
                                
                                trainingActivityActual.setTrainingId(oidTraining);
                                trainingActivityActual.setScheduleId(oidSchedule);
                                
                                
                                if(oidAttendances != null)
                                    trainingActivityActual.setAtendees(oidAttendances.length);
                            
                                                             
				if(trainingActivityActual.getOID()==0){
					try{
                                            long oid = pstTrainingActivityActual.insertExc(this.trainingActivityActual);                              
                                            PstTrainingHistory.insertTrainingHistory(oidAttendances, trainHours, oidTraining, oidTrainingPlan, oidSchedule, oid);
					}
                                        catch(DBException dbexc){
                                            excCode = dbexc.getErrorCode();
                                            msgString = getSystemMessage(excCode);
                                            return getControlMsgId(excCode);
					}
                                        catch (Exception exc){
                                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                            return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}
				}
                                else{
					try {
                                            long oid = PstTrainingActivityActual.updateExc(this.trainingActivityActual);
                                            
                                            // update attendance on training history
                                            String where = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + "=" + trainingActivityActual.getOID();
                                            Vector listHist = PstTrainingHistory.list(0, 0, where, "");
                                            
                                            for(int i=0; i<listHist.size(); i++) {
                                                TrainingHistory hist = (TrainingHistory)listHist.get(i);
                                                oid = PstTrainingHistory.deleteExc(hist.getOID());
                                            }
                                            
                                            PstTrainingHistory.insertTrainingHistory(oidAttendances, trainHours, oidTraining, oidTrainingPlan, oidSchedule, trainingActivityActual.getOID());                                           
					}
                                        catch (DBException dbexc){
                                            excCode = dbexc.getErrorCode();
                                            msgString = getSystemMessage(excCode);
					}
                                        catch (Exception exc){
                                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}
				}
				break;

			case Command.EDIT :
				if (oidTrainingActivityActual != 0) {
                                        try {
                                            trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
                                        } 
                                        catch (DBException dbexc){
                                            excCode = dbexc.getErrorCode();
                                            msgString = getSystemMessage(excCode);
                                        } 
                                        catch (Exception exc){ 
                                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                        }
				}
				break;

			case Command.ASK :
				if (oidTrainingActivityActual != 0) {
					try {
                                            trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
					} 
                                        catch (DBException dbexc){
                                            excCode = dbexc.getErrorCode();
                                            msgString = getSystemMessage(excCode);
					} 
                                        catch (Exception exc){ 
                                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidTrainingActivityActual != 0){
					try{
						long oid = PstTrainingActivityActual.deleteExc(oidTrainingActivityActual);
						
                                                // also delete training history record
                                                String where = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + "=" + oid;
                                                Vector histories = PstTrainingHistory.list(0, 0, where, "");
                                                
                                                try {
                                                    for(int i=0; i<histories.size(); i++) {
                                                        TrainingHistory history = (TrainingHistory)histories.get(i);
                                                        oid = PstTrainingHistory.deleteExc(history.getOID());
                                                    }
                                                }
                                                catch(Exception e) {}
                                                
                                                if(oid!=0){
                                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                                    excCode = RSLT_OK;
						}
                                                else{
                                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                                    excCode = RSLT_FORM_INCOMPLETE;
						}
					}
                                        catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}
                                        catch(Exception exc){	
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			default :

		}
                
		return rsCode;
	}
}
