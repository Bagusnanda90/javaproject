/* 
 * Form Name  	:  FrmTraining.java 
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

public class FrmTraining extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Training training;

	public static final String FRM_NAME_TRAINING		=  "FRM_NAME_TRAINING" ;

	public static final int FRM_FIELD_TRAINING_ID		=  0 ;
	public static final int FRM_FIELD_NAME			=  1 ;
	public static final int FRM_FIELD_DESCRIPTION		=  2 ;
        public static final int FRM_FIELD_TYPE                  =  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_TRAINING_ID", 
                "FRM_FIELD_NAME",
		"FRM_FIELD_DESCRIPTION", 
                "FRM_FIELD_TYPE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING, 
                TYPE_LONG + ENTRY_REQUIRED
	} ;

	public FrmTraining(){
	}
	public FrmTraining(Training training){
		this.training = training;
	}

	public FrmTraining(HttpServletRequest request, Training training){
		super(new FrmTraining(training), request);
		this.training = training;
	}

	public String getFormName() { return FRM_NAME_TRAINING; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Training getEntityObject(){ return training; }

	public void requestEntityObject(Training training) {
		try{
			this.requestParam();
			training.setName(getString(FRM_FIELD_NAME));
			training.setDescription(getString(FRM_FIELD_DESCRIPTION));
                        training.setType(getLong(FRM_FIELD_TYPE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
