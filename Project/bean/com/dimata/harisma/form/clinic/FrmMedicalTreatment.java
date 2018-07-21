
package com.dimata.harisma.form.clinic;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

/* qdep package */ 
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.clinic.*;

/**
 *
 * @author bayu
 */

public class FrmMedicalTreatment extends FRMHandler implements I_FRMInterface, I_FRMType {

        private MedicalTreatment medicalTreatment;

	public static final String FRM_NAME_MEDICAL_TREATMENT		=  "FRM_NAME_MEDICAL_TREATMENT" ;

	public static final int FRM_FLD_MEDICAL_TREATMENT_ID		=  0 ;
	public static final int FRM_FLD_CASE_GROUP_ID                   =  1 ;
	public static final int FRM_FLD_CASE_NAME                       =  2 ;
	public static final int FRM_FLD_MAX_OCCUR                       =  3 ;
	public static final int FRM_FLD_OCCUR_PERIOD			=  4 ;
	public static final int FRM_FLD_MAX_BUDGET			=  5 ;
	public static final int FRM_FLD_BUDGET_PERIOD			=  6 ;
	public static final int FRM_FLD_BUDGET_TARGET                   =  7 ;
	

	public static String[] fieldNames = {
		"FRM_FLD_MEDICAL_TREATMENT_ID",  
                "FRM_FLD_CASE_GROUP_ID",
		"FRM_FLD_CASE_NAME",  
                "FRM_FLD_MAX_OCCUR",
		"FRM_FLD_OCCUR_PERIOD",  
                "FRM_FLD_MAX_BUDGET",
		"FRM_FLD_BUDGET_PERIOD", 
                "FRM_FLD_BUDGET_TARGET",        
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  
                TYPE_INT + ENTRY_REQUIRED,
		TYPE_INT,  
                TYPE_FLOAT + ENTRY_REQUIRED,
		TYPE_INT,
                TYPE_INT
	} ;

        
	public FrmMedicalTreatment(){
	}
        
	public FrmMedicalTreatment(MedicalTreatment medicalTreatment){
		this.medicalTreatment = medicalTreatment;
	}

	public FrmMedicalTreatment(HttpServletRequest request, MedicalTreatment medicalTreatment){
		super(new FrmMedicalTreatment(medicalTreatment), request);
		this.medicalTreatment = medicalTreatment;
	}

	public String getFormName() { return FRM_NAME_MEDICAL_TREATMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedicalTreatment getEntityObject(){ return medicalTreatment; }

	public void requestEntityObject(MedicalTreatment medicalTreatment) {
		try{
			this.requestParam();
			medicalTreatment.setCaseGroupId(getLong(FRM_FLD_CASE_GROUP_ID));
			medicalTreatment.setCaseName(getString(FRM_FLD_CASE_NAME));
			medicalTreatment.setMaxOccurance(getInt(FRM_FLD_MAX_OCCUR));
			medicalTreatment.setOccurancePeriod(getInt(FRM_FLD_OCCUR_PERIOD));
			medicalTreatment.setMaxBudget(getDouble(FRM_FLD_MAX_BUDGET));
			medicalTreatment.setBudgetPeriod(getInt(FRM_FLD_BUDGET_PERIOD));		
			medicalTreatment.setBudgetTarget(getInt(FRM_FLD_BUDGET_TARGET));			
		}
                catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}

    
}
