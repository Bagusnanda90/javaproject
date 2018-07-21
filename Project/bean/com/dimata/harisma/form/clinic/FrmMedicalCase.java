
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

public class FrmMedicalCase extends FRMHandler implements I_FRMInterface, I_FRMType {

        private MedicalCase MedicalCase;

	public static final String FRM_NAME_MEDICAL_CASE		=  "FRM_MEDICAL_CASE" ;

	public static final int FRM_FIELD_MEDICAL_CASE_ID		=  0 ;
	public static final int FRM_FIELD_SORT_NUMBER		=  1 ;
	public static final int FRM_FIELD_CASE_GROUP		=  2 ;
	public static final int FRM_FIELD_CASE_NAME		=  3 ;
	public static final int FRM_FIELD_MAX_USE		=  4 ;
	public static final int FRM_FIELD_MAX_USE_PERIOD		=  5 ;
	public static final int FRM_FIELD_MIN_TAKEN_BY		=  6 ;
	public static final int FRM_FIELD_MIN_TAKEN_BY_PERIOD		=  7 ;
        public static final int FRM_FIELD_CASE_LINK = 8;
	public static final int FRM_FIELD_FORMULA =9;        
	

	public static String[] fieldNames = {
		"FRM_FIELD_MEDICAL_CASE_ID",  
                "FRM_FIELD_SORT_NUMBER",
                "FRM_FIELD_CASE_GROUP",
                "FRM_FIELD_CASE_NAME",
                "FRM_FIELD_MAX_USE",
                "FRM_FIELD_MAX_USE_PERIOD",
                "FRM_FIELD_MIN_TAKEN_BY",
                "FRM_FIELD_MIN_TAKEN_BY_PERIOD",
                "FRM_FIELD_CASE_LINK",
                "FRM_FIELD_FORMULA"
                        
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_INT + + ENTRY_REQUIRED,
                TYPE_STRING + ENTRY_REQUIRED,
                TYPE_STRING + ENTRY_REQUIRED,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_INT,
                TYPE_STRING,
                TYPE_STRING
	} ;

        
	public FrmMedicalCase(){
	}
        
	public FrmMedicalCase(MedicalCase MedicalCase){
		this.MedicalCase = MedicalCase;
	}

	public FrmMedicalCase(HttpServletRequest request, MedicalCase MedicalCase){
		super(new FrmMedicalCase(MedicalCase), request);
		this.MedicalCase = MedicalCase;
	}

	public String getFormName() { return FRM_NAME_MEDICAL_CASE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedicalCase getEntityObject(){ return MedicalCase; }

	public void requestEntityObject(MedicalCase medicalCase) {
		try{
			this.requestParam();
                        medicalCase.setSortNumber(getInt(FRM_FIELD_SORT_NUMBER));
			medicalCase.setCaseGroup(getString(FRM_FIELD_CASE_GROUP));			
			medicalCase.setCaseName(getString(FRM_FIELD_CASE_NAME));			
			medicalCase.setMaxUse(getInt(FRM_FIELD_MAX_USE));			
			medicalCase.setMaxUsePeriod(getInt(FRM_FIELD_MAX_USE_PERIOD));			
			medicalCase.setMinTakenBy(getInt(FRM_FIELD_MIN_TAKEN_BY));			
			medicalCase.setMinTakenByPeriod(getInt(FRM_FIELD_MIN_TAKEN_BY_PERIOD));			
			medicalCase.setCaseLink(getString(FRM_FIELD_CASE_LINK));			
			medicalCase.setFormula(getString(FRM_FIELD_FORMULA));			
		}
                catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
