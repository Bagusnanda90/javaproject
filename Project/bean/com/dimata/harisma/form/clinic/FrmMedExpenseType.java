/* 
 * Form Name  	:  FrmMedExpenseType.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
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

public class FrmMedExpenseType extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private MedExpenseType medExpenseType;

	public static final String FRM_NAME_MEDEXPENSETYPE =  "FRM_NAME_MEDEXPENSETYPE" ;

	public static final int FRM_FIELD_MEDICINE_EXPENSE_TYPE_ID =  0 ;
	public static final int FRM_FIELD_TYPE	=  1 ;
        public static final int FRM_FIELD_SHOW_STATUS = 2; // 2015-01-12 | Hendra McHen

	public static String[] fieldNames = {
		"FRM_FIELD_MEDICINE_EXPENSE_TYPE_ID",  "FRM_FIELD_TYPE", "FRM_FIELD_SHOW_STATUS"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED, TYPE_INT
	} ;

	public FrmMedExpenseType(){
	}
	public FrmMedExpenseType(MedExpenseType medExpenseType){
		this.medExpenseType = medExpenseType;
	}

	public FrmMedExpenseType(HttpServletRequest request, MedExpenseType medExpenseType){
		super(new FrmMedExpenseType(medExpenseType), request);
		this.medExpenseType = medExpenseType;
	}

	public String getFormName() { return FRM_NAME_MEDEXPENSETYPE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MedExpenseType getEntityObject(){ return medExpenseType; }

	public void requestEntityObject(MedExpenseType medExpenseType) {
		try{
			this.requestParam();
			medExpenseType.setType(getString(FRM_FIELD_TYPE));
                        medExpenseType.setShowStatus(getInt(FRM_FIELD_SHOW_STATUS));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
