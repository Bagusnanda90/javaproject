
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

public class FrmTrainingAttendancePlan extends FRMHandler implements I_FRMInterface, I_FRMType {
    
        public static final String FRM_NAME_TRAIN_ATTEND_PLAN      =  "FRM_TRAIN_ATTENDANCE_PLAN";

	public static final int FRM_FIELD_TRAIN_ATTEND_ID          =  0;
	public static final int FRM_FIELD_TRAIN_PLAN_ID            =  1;
	public static final int FRM_FIELD_EMPLOYEE_ID              =  2;
        public static final int FRM_FIELD_DURATION                 =  3;
	
	public static String[] fieldNames = 
        {
            "FRM_TRAIN_ATTENDANCE_PLAN_ID",  
            "FRM_TRAIN_PLAN_ID",
            "FRM_TRAIN_EMPLOYEE_ID",
            "FRM_TRAIN_DURATION"
	};

	public static int[] fieldTypes = 
        {
            TYPE_LONG,  
            TYPE_LONG,
            TYPE_LONG,
            TYPE_INT
	};
        
        
        private TrainingAttendancePlan trainingAttend;


	public FrmTrainingAttendancePlan(){
	}
        
	public FrmTrainingAttendancePlan(TrainingAttendancePlan trainingAttend){
            this.trainingAttend = trainingAttend;
	}

	public FrmTrainingAttendancePlan(HttpServletRequest request, TrainingAttendancePlan trainingAttend){
            super(new FrmTrainingAttendancePlan(trainingAttend), request);
            this.trainingAttend = trainingAttend;
	}

        
	public String getFormName() { 
            return FRM_NAME_TRAIN_ATTEND_PLAN; 
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

        
	public TrainingAttendancePlan getEntityObject(){ 
            return trainingAttend; 
        }

	public void requestEntityObject(TrainingAttendancePlan trainingAttend) {
            try {
                this.requestParam();
                trainingAttend.setTrainPlanid(this.getLong(FRM_FIELD_TRAIN_PLAN_ID));
                trainingAttend.setEmployeeId(this.getLong(FRM_FIELD_EMPLOYEE_ID));  
                trainingAttend.setDuration(this.getInt(FRM_FIELD_DURATION));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
	}
    
}
