
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

public class FrmMedicalLevel extends FRMHandler implements I_FRMInterface, I_FRMType {

        private MedicalLevel medicalLevel;

	public static final String FRM_NAME_MEDICAL_RECORD		=  "FRM_NAME_MEDICAL_LEVEL" ;

	public static final int FRM_FIELD_MEDICAL_LEVEL_ID		=  0 ;
	public static final int FRM_FIELD_MEDICAL_LEVEL_NAME		=  1 ;
	public static final int FRM_FIELD_MEDICAL_LEVEL_CLASS		=  2 ;
	public static final int FRM_FIELD_SORT_NUMBER		=  3 ;
	

	public static String[] fieldNames = {
		"FRM_FIELD_MEDICAL_LEVEL_ID",  
                "FRM_FIELD_MEDICAL_LEVEL_NAME",
                "FRM_FIELD_MEDICAL_LEVEL_CLASS",
                "FRM_FIELD_SORT_NUMBER"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_STRING + ENTRY_REQUIRED,
                TYPE_STRING + ENTRY_REQUIRED,
                TYPE_INT + ENTRY_REQUIRED
	} ;

        
	public FrmMedicalLevel(){
	}
        
	public FrmMedicalLevel(MedicalLevel medicalLevel){
		this.medicalLevel = medicalLevel;
	}

	public FrmMedicalLevel(HttpServletRequest request, MedicalLevel medicalLevel){
		super(new FrmMedicalLevel(medicalLevel), request);
		this.medicalLevel = medicalLevel;
	}

	public String getFormName() { return FRM_NAME_MEDICAL_RECORD; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedicalLevel getEntityObject(){ return medicalLevel; }

	public void requestEntityObject(MedicalLevel medicalLevel) {
		try{
			this.requestParam();
			medicalLevel.setLevelName(getString(FRM_FIELD_MEDICAL_LEVEL_NAME));			
			medicalLevel.setLevelClass(getString(FRM_FIELD_MEDICAL_LEVEL_CLASS));			
                        medicalLevel.setSortNumber(getInt(FRM_FIELD_SORT_NUMBER));
		}
                catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
