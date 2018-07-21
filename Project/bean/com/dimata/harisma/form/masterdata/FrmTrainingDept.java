/* 
 * Form Name  	:  FrmTrainingDept.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmTrainingDept extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private TrainingDept trainingDept;

	public static final String FRM_NAME_TRAININGDEPT		=  "FRM_NAME_TRAININGDEPT" ;

	public static final int FRM_FIELD_TRAINING_DEPT_ID			=  0 ;
	public static final int FRM_FIELD_DEPARTMENT_ID			=  1 ;
	public static final int FRM_FIELD_TRAINING_ID			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_TRAINING_DEPT_ID",  "FRM_FIELD_DEPARTMENT_ID",
		"FRM_FIELD_TRAINING_ID"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG
	} ;

	public FrmTrainingDept(){
	}
	public FrmTrainingDept(TrainingDept trainingDept){
		this.trainingDept = trainingDept;
	}

	public FrmTrainingDept(HttpServletRequest request, TrainingDept trainingDept){
		super(new FrmTrainingDept(trainingDept), request);
		this.trainingDept = trainingDept;
	}

	public String getFormName() { return FRM_NAME_TRAININGDEPT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public TrainingDept getEntityObject(){ return trainingDept; }

	public void requestEntityObject(TrainingDept trainingDept) {
		try{
			this.requestParam();
			trainingDept.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
			trainingDept.setTrainingId(getLong(FRM_FIELD_TRAINING_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
