
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

public class FrmMedicalBudget extends FRMHandler implements I_FRMInterface, I_FRMType {

        private MedicalBudget medicalBudget;

	public static final String FRM_NAME_MEDICAL_BUDGET	=  "FRM_MEDICAL_BUDGET" ;

	public static final int FRM_FIELD_MEDICAL_LEVEL_ID	=  0 ;
	public static final int FRM_FIELD_MEDICAL_CASE_ID		=  1 ;
	public static final int FRM_FIELD_MEDICAL_BUDGET		=  2 ;
	public static final int FRM_FIELD_USE_PERIOD		=  3 ;
	public static final int FRM_FIELD_USE_PAX		=  4 ;
	

	public static String[] fieldNames = {
		"FRM_FIELD_MEDICAL_LEVEL_ID",  
                "FRM_FIELD_MEDICAL_CASE_ID",
                "FRM_FIELD_MEDICAL_BUDGET",
                "FRM_FIELD_USE_PERIOD",
                "FRM_FIELD_USE_PAX",
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG + ENTRY_REQUIRED,  
                TYPE_LONG + ENTRY_REQUIRED,  
                TYPE_FLOAT + ENTRY_REQUIRED,
                TYPE_INT,
                TYPE_INT
	} ;
        
	public FrmMedicalBudget(){
	}
        
	public FrmMedicalBudget(MedicalBudget medicalBudget){
		this.medicalBudget = medicalBudget;
	}

	public FrmMedicalBudget(HttpServletRequest request, MedicalBudget MedicalBudget){
		super(new FrmMedicalBudget(MedicalBudget), request);
		this.medicalBudget = MedicalBudget;
	}

	public String getFormName() { return FRM_NAME_MEDICAL_BUDGET; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedicalBudget getEntityObject(){ return medicalBudget; }

	public void requestEntityObject(MedicalBudget medicalBudget) {
		try{
			this.requestParam();
                        medicalBudget.setMedicalLevelId(getLong(FRM_FIELD_MEDICAL_LEVEL_ID));
                        medicalBudget.setMedicalCaseId(getInt(FRM_FIELD_MEDICAL_CASE_ID));
			medicalBudget.setBudget(getFloat(FRM_FIELD_MEDICAL_BUDGET));			
			medicalBudget.setUsePeriod(getInt(FRM_FIELD_USE_PERIOD));			
			medicalBudget.setUsePax(getInt(FRM_FIELD_USE_PAX));			
		}
                catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
