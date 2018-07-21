
package com.dimata.harisma.form.employee;

// import java
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// import qdep
import com.dimata.qdep.form.*;

// import harisma
import com.dimata.harisma.entity.employee.*;

/**
 *
 * @author bayu
 */

public class FrmTrainingSchedule extends FRMHandler implements I_FRMInterface, I_FRMType {

    public static final String FRM_NAME_TRAIN_SCHEDULE          =  "FRM_TRAIN_SCHEDULE";

        public static final int FRM_FIELD_TRAIN_SCHEDULE_ID     =  0;
	public static final int FRM_FIELD_TRAIN_PLAN_ID         =  1;
	public static final int FRM_FIELD_TRAIN_DATE            =  2;
        public static final int FRM_FIELD_START_TIME            =  3;
        public static final int FRM_FIELD_END_TIME              =  4;
        public static final int FRM_FIELD_TRAIN_VENUE_ID        =  5;
        public static final int FRM_FIELD_TRAIN_END_DATE        =  6;
        public static final int FRM_FIELD_TOTAL_HOUR        =  7;
        
	
	public static String[] fieldNames = 
        {
            "FRM_TRAIN_SCHEDULE_ID",
            "FRM_TRAIN_PLAN_ID",
            "FRM_TRAIN_DATE",
            "FRM_START_TIME",
            "FRM_END_TIME",
            "FRM_TRAIN_VENUE_ID",
            "FRM_TRAIN_END_DATE",
            "FRM_TOTAL_HOUR"    
	};

	public static int[] fieldTypes = 
        { 
            TYPE_LONG,
            TYPE_LONG,
            TYPE_DATE,
            TYPE_DATE,
            TYPE_DATE,
            TYPE_LONG + ENTRY_REQUIRED,
            TYPE_DATE,
            TYPE_INT
	};
        
        
        private TrainingSchedule trainingSchedule;


	public FrmTrainingSchedule(){
	}
        
	public FrmTrainingSchedule(TrainingSchedule trainingSchedule){
            this.trainingSchedule = trainingSchedule;
	}

	public FrmTrainingSchedule(HttpServletRequest request, TrainingSchedule trainingSchedule){
            super(new FrmTrainingSchedule(trainingSchedule), request);
            this.trainingSchedule = trainingSchedule;
	}

        
	public String getFormName() { 
            return FRM_NAME_TRAIN_SCHEDULE; 
        } 

	public int[] getFieldTypes() { 
            return fieldTypes; 
        }

	public String[] getFieldNames() {
            return fieldNames; 
        } 

	public int getFieldSize() { 
            return fieldNames.length; 
        } 

        
	public TrainingSchedule getEntityObject(){ 
            return trainingSchedule; 
        }

	public void requestEntityObject(TrainingSchedule trainingSchedule) {
            try {
                this.requestParam();
                trainingSchedule.setTrainPlanId(this.getLong(FRM_FIELD_TRAIN_PLAN_ID));
                trainingSchedule.setTrainDate(this.getDate(FRM_FIELD_TRAIN_DATE));  
                trainingSchedule.setTrainEndDate(this.getDate(FRM_FIELD_TRAIN_END_DATE));  
                trainingSchedule.setStartTime(this.getDate(FRM_FIELD_START_TIME)); 
                trainingSchedule.setEndTime(this.getDate(FRM_FIELD_END_TIME)); 
                trainingSchedule.setTrainVenueId(this.getLong(FRM_FIELD_TRAIN_VENUE_ID));       
                trainingSchedule.setTotalHour(this.getInt(FRM_FIELD_TOTAL_HOUR));       
            }
            catch(Exception e) {
                e.printStackTrace();
            }
	}
    
}
