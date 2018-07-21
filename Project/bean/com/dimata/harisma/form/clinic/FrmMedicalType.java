/* 
 * Form Name  	:  FrmMedicalType.java 
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

public class FrmMedicalType extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private MedicalType medicalType;

	public static final String FRM_NAME_MEDICALTYPE		=  "FRM_NAME_MEDICALTYPE" ;

	public static final int FRM_FIELD_MEDICAL_TYPE_ID			=  0 ;
	public static final int FRM_FIELD_TYPE_CODE			=  1 ;
	public static final int FRM_FIELD_TYPE_NAME			=  2 ;
        public static final int FRM_FIELD_MED_EXPENSE_TYPE_ID		=  3 ;
        public static final int FRM_FIELD_YEARLY_AMOUNT		=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_MEDICAL_TYPE_ID",  "FRM_FIELD_TYPE_CODE",
		"FRM_FIELD_TYPE_NAME", "FRM_FIELD_MED_EXPENSE_TYPE_ID",
                "FRM_FIELD_YEARLY_AMOUNT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,TYPE_LONG,
                TYPE_FLOAT  + ENTRY_REQUIRED
	} ;

	public FrmMedicalType(){
	}
	public FrmMedicalType(MedicalType medicalType){
		this.medicalType = medicalType;
	}

	public FrmMedicalType(HttpServletRequest request, MedicalType medicalType){
		super(new FrmMedicalType(medicalType), request);
		this.medicalType = medicalType;
	}

	public String getFormName() { return FRM_NAME_MEDICALTYPE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedicalType getEntityObject(){ return medicalType; }

	public void requestEntityObject(MedicalType medicalType) {
		try{
			this.requestParam();
            medicalType.setMedExpenseTypeId(getLong(FRM_FIELD_MED_EXPENSE_TYPE_ID));
			medicalType.setTypeCode(getString(FRM_FIELD_TYPE_CODE));
			medicalType.setTypeName(getString(FRM_FIELD_TYPE_NAME));
                        medicalType.setYearlyAmount(getDouble(FRM_FIELD_YEARLY_AMOUNT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
