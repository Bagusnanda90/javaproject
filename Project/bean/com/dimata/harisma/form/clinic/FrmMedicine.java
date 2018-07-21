/* 
 * Form Name  	:  FrmMedicine.java 
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

public class FrmMedicine extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Medicine medicine;

	public static final String FRM_NAME_MEDICINE		=  "FRM_NAME_MEDICINE" ;

	public static final int FRM_FIELD_MEDICINE_ID			=  0 ;
	public static final int FRM_FIELD_NAME			=  1 ;
	public static final int FRM_FIELD_UNIT_PRICE			=  2 ;
	public static final int FRM_FIELD_DESCRIPTION			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_MEDICINE_ID",  "FRM_FIELD_NAME",
		"FRM_FIELD_UNIT_PRICE",  "FRM_FIELD_DESCRIPTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_FLOAT + ENTRY_REQUIRED,  TYPE_STRING
	} ;

	public FrmMedicine(){
	}
	public FrmMedicine(Medicine medicine){
		this.medicine = medicine;
	}

	public FrmMedicine(HttpServletRequest request, Medicine medicine){
		super(new FrmMedicine(medicine), request);
		this.medicine = medicine;
	}

	public String getFormName() { return FRM_NAME_MEDICINE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Medicine getEntityObject(){ return medicine; }

	public void requestEntityObject(Medicine medicine) {
		try{
			this.requestParam();
			medicine.setName(getString(FRM_FIELD_NAME));
			medicine.setUnitPrice(getDouble(FRM_FIELD_UNIT_PRICE));
			medicine.setDescription(getString(FRM_FIELD_DESCRIPTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
