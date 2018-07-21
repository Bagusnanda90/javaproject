
package com.dimata.harisma.form.employee;

// import java
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// import dimata
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;

// import harisma
import com.dimata.harisma.entity.employee.*;

/**
 *
 * @author bayu
 */

public class CtrlTrainingAttendacePlan extends Control implements I_Language {

        public static final int RSLT_OK               =   0;
	public static final int RSLT_UNKNOWN_ERROR    =   1;
	public static final int RSLT_CODE_EXIST       =   2;
	public static final int RSLT_FORM_INCOMPLETE  =   3;

	public static String[][] resultText = 
        {
            { "Berhasil", "Tidak dapat diproses", "No id sudah ada", "Data tidak lengkap" },
            { "Success", "Can not process", "Id code exist", "Data incomplete" }
	};
	
	private String msgString = "";
        private int start = 0;
        private int language = LANGUAGE_DEFAULT;
        
	private TrainingAttendancePlan trainingAttendance;
	private PstTrainingAttendancePlan pstTrainingAttendance;
	private FrmTrainingAttendancePlan frmTrainingAttendance;
	

	public CtrlTrainingAttendacePlan(HttpServletRequest request) {
            msgString = "";
            trainingAttendance = new TrainingAttendancePlan();
            
            try {
                pstTrainingAttendance = new PstTrainingAttendancePlan(0);
            }
            catch(Exception e) {}
            
            frmTrainingAttendance = new FrmTrainingAttendancePlan(request, trainingAttendance);
	}

        
	private String getSystemMessage(int msgCode){
            switch (msgCode) {
                case I_DBExceptionInfo.MULTIPLE_ID :
                    this.frmTrainingAttendance.addError(FrmTrainingAttendancePlan.FRM_FIELD_TRAIN_ATTEND_ID, resultText[language][RSLT_CODE_EXIST]);
                    return resultText[language][RSLT_CODE_EXIST];
                    
                default:
                    return resultText[language][RSLT_UNKNOWN_ERROR]; 
            }
	}

	private int getControlMsgId(int msgCode){
            switch (msgCode){
                case I_DBExceptionInfo.MULTIPLE_ID :
                    return RSLT_CODE_EXIST;
                    
                default:
                    return RSLT_UNKNOWN_ERROR;
            }
	}

        
	public int getLanguage() { 
            return language; 
        }

	public void setLanguage(int language){ 
            this.language = language; 
        }

	public TrainingAttendancePlan getVenue() {
            return trainingAttendance; 
        } 

	public FrmTrainingAttendancePlan getForm() {
            return frmTrainingAttendance; 
        }

	public String getMessage() {
            return msgString; 
        }

	public int getStart() { 
            return start; 
        }
        

	public int action(int command, long oidTrainType){
            msgString = "";
            int excCode = I_DBExceptionInfo.NO_EXCEPTION;
            int rsCode = RSLT_OK;
            
            switch(command) {
                case Command.ADD :
                        break;

                case Command.SAVE :
                        if(oidTrainType != 0) {
                            try {
                                trainingAttendance = PstTrainingAttendancePlan.fetchExc(oidTrainType);
                            }
                            catch(Exception e) {}
                        }
      
                        frmTrainingAttendance.requestEntityObject(trainingAttendance);

                        if(frmTrainingAttendance.errorSize() > 0) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE ;
                        }

                        if(trainingAttendance.getOID()==0) {
                            try {
                                long oid = PstTrainingAttendancePlan.insertExc(trainingAttendance);
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
                        else {
                            try {
                                long oid = PstTrainingAttendancePlan.updateExc(trainingAttendance);
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
                        if(oidTrainType != 0) {
                            try {
                                trainingAttendance = PstTrainingAttendancePlan.fetchExc(oidTrainType);
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
                        if(oidTrainType != 0) {
                            try {
                                trainingAttendance = PstTrainingAttendancePlan.fetchExc(oidTrainType);
                                msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
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
                        if (oidTrainType != 0) {
                            try {
                                long oid = PstTrainingAttendancePlan.deleteExc(oidTrainType);
                                
                                if(oid != 0) {
                                    excCode = RSLT_OK;
                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);                                    
                                }
                                else {
                                    excCode = RSLT_FORM_INCOMPLETE;
                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);                                    
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
        
        public int updateAttendance(int command, long oidEmployee, int hoursPlanned, long oidTrainingPlan){
             msgString = "";
             int excCode = I_DBExceptionInfo.NO_EXCEPTION;
             int rsCode = RSLT_OK;
             
             String whereClause = "";
             long attendanceId = 0;
             TrainingAttendancePlan trainAttendance = new TrainingAttendancePlan();
               
             switch(command){			
                    case Command.SAVE :

                        // check availability of current attendance
                        whereClause = PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan + " AND " +
                                      PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_EMP_ID] + "=" + oidEmployee;

                        try {
                            Vector listAttendance = PstTrainingAttendancePlan.list(0, 0, whereClause, "");

                            // if attendance is already saved, get the id
                            if(listAttendance != null && listAttendance.size() > 0)
                                attendanceId = ((TrainingAttendancePlan)listAttendance.firstElement()).getOID();


                            if(attendanceId == 0) {     /* create new */
                                trainAttendance = new TrainingAttendancePlan();                               
                            }
                            else {                      /* get existing */
                                trainAttendance = PstTrainingAttendancePlan.fetchExc(attendanceId);                               
                            }
                            
                            // update value
                            trainAttendance.setTrainPlanid(oidTrainingPlan);
                            trainAttendance.setEmployeeId(oidEmployee);
                            trainAttendance.setDuration(hoursPlanned);
                        }
                        catch(Exception e) {
                            System.err.println("Error on setting attendance");
                        }

                        
                        // update or insert this attendance                        
                        try{
                            if(trainAttendance.getOID() == 0) {     /* insert new */
                                long oid = PstTrainingAttendancePlan.insertExc(trainAttendance);
                            }
                            else {   /* update existing */
                                long oid = PstTrainingAttendancePlan.updateExc(trainAttendance);
                            }
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
                   
                        break;

                    case Command.DELETE :

                        // check availability of current attendance
                        whereClause = PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan + " AND " +
                                      PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_EMP_ID] + "=" + oidEmployee;

                        try {
                            Vector listAttendance = PstTrainingAttendancePlan.list(0, 0, whereClause, "");

                            // if attendance is already saved, get the id
                            if(listAttendance != null && listAttendance.size() > 0)
                                attendanceId = ((TrainingAttendancePlan)listAttendance.firstElement()).getOID();

                        }
                        catch(Exception e) {
                            System.err.println("Error on checking attendance");
                        }


                        // delete or do nothing to this attendance                        
                        try{
                            if(trainAttendance.getOID() == 0) {     /* delete existing */
                                long oid = PstTrainingAttendancePlan.deleteExc(attendanceId);

                                if(oid != 0){
                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                    excCode = RSLT_OK;
                                }
                                else{
                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                    excCode = RSLT_FORM_INCOMPLETE;
                                }      
                            }
                            else {   /* do nothing */
                                break;
                            }
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
             
            return rsCode;
	}       

        
}
