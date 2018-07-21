/* 
 * Form Name  	:  FrmMedicalRecord.java 
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

public class FrmMedicalRecord extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private MedicalRecord medicalRecord;

	public static final String FRM_NAME_MEDICALRECORD		=  "FRM_NAME_MEDICALRECORD" ;

	public static final int FRM_FIELD_MEDICAL_RECORD_ID			=  0 ;
	public static final int FRM_FIELD_FAMILY_MEMBER_ID			=  1 ;
	public static final int FRM_FIELD_DISEASE_TYPE_ID			=  2 ;
	public static final int FRM_FIELD_MEDICAL_TYPE_ID			=  3 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  4 ;
	public static final int FRM_FIELD_RECORD_DATE			=  5 ;
	public static final int FRM_FIELD_AMOUNT			=  6 ;
	public static final int FRM_FIELD_DISCOUNT_IN_PERCENT		=  7 ;
	public static final int FRM_FIELD_DISCOUNT_IN_RP			=  8 ;
	public static final int FRM_FIELD_TOTAL			=  9 ;
        public static final int FRM_FIELD_MEDICAL_CASE_ID       = 10;
        public static final int FRM_FIELD_CASE_QUANTITY       = 11;


	public static String[] fieldNames = {
		"FRM_FIELD_MEDICAL_RECORD_ID",  "FRM_FIELD_FAMILY_MEMBER_ID",
		"FRM_FIELD_DISEASE_TYPE_ID",  "FRM_FIELD_MEDICAL_TYPE_ID",
		"FRM_FIELD_EMPLOYEE_ID",  "FRM_FIELD_RECORD_DATE",
		"FRM_FIELD_AMOUNT", "FRM_FIELD_DISCOUNT_PERCENT",
        "FRM_FIELD_DISCOUNT_RP", "FRM_FIELD_TOTAL", "FRM_FIELD_MEDICAL_CASE_ID", "FRM_FIELD_CASE_QUANTITY"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG,
		TYPE_LONG,  TYPE_LONG,
		TYPE_LONG,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_FLOAT + ENTRY_REQUIRED,
        TYPE_FLOAT, TYPE_FLOAT,
        TYPE_FLOAT, TYPE_LONG, TYPE_FLOAT
	} ;

	public FrmMedicalRecord(){
	}
	public FrmMedicalRecord(MedicalRecord medicalRecord){
		this.medicalRecord = medicalRecord;
	}

	public FrmMedicalRecord(HttpServletRequest request, MedicalRecord medicalRecord){
		super(new FrmMedicalRecord(medicalRecord), request);
		this.medicalRecord = medicalRecord;
	}

	public String getFormName() { return FRM_NAME_MEDICALRECORD; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedicalRecord getEntityObject(){ return medicalRecord; }

	public void requestEntityObject(MedicalRecord medicalRecord) {
		try{
			this.requestParam();
			medicalRecord.setFamilyMemberId(getLong(FRM_FIELD_FAMILY_MEMBER_ID));
			medicalRecord.setDiseaseTypeId(getLong(FRM_FIELD_DISEASE_TYPE_ID));
			medicalRecord.setMedicalTypeId(getLong(FRM_FIELD_MEDICAL_TYPE_ID));
			medicalRecord.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			medicalRecord.setRecordDate(getDate(FRM_FIELD_RECORD_DATE));
			medicalRecord.setAmount(getDouble(FRM_FIELD_AMOUNT));
			medicalRecord.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			medicalRecord.setRecordDate(getDate(FRM_FIELD_RECORD_DATE));
			medicalRecord.setAmount(getDouble(FRM_FIELD_AMOUNT));
			medicalRecord.setDiscountInPercent(getDouble(FRM_FIELD_DISCOUNT_IN_PERCENT));
			medicalRecord.setDiscountInRp(getDouble(FRM_FIELD_DISCOUNT_IN_RP));
			medicalRecord.setTotal(getDouble(FRM_FIELD_TOTAL));
			medicalRecord.setMedicalCaseId(getLong(FRM_FIELD_MEDICAL_CASE_ID));
			medicalRecord.setCaseQuantity(getDouble(FRM_FIELD_CASE_QUANTITY));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
