/* 
 * Form Name  	:  FrmExpRecapitulate.java 
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

public class FrmExpRecapitulate extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ExpRecapitulate expRecapitulate;

	public static final String FRM_NAME_EXPRECAPITULATE		=  "FRM_NAME_EXPRECAPITULATE" ;

	public static final int FRM_FIELD_REC_MEDICAL_EXPENCE_ID			=  0 ;
	public static final int FRM_FIELD_PERIODE			=  1 ;
	public static final int FRM_FIELD_MEDICAL_TYPE_ID			=  2 ;
	public static final int FRM_FIELD_AMOUNT			=  3 ;
	public static final int FRM_FIELD_DISCOUNT_IN_PERCENT			=  4 ;
	public static final int FRM_FIELD_DISCOUNT_IN_RP			=  5 ;
	public static final int FRM_FIELD_TOTAL			=  6 ;
	public static final int FRM_FIELD_PERSON			=  7 ;

	public static String[] fieldNames = {
		"FRM_FIELD_REC_MEDICAL_EXPENCE_ID",  "FRM_FIELD_PERIODE",
		"FRM_FIELD_MEDICAL_TYPE_ID",
	    "FRM_FIELD_AMOUNT",
		"FRM_FIELD_DISCOUNT_IN_PERCENT",  "FRM_FIELD_DISCOUNT_IN_RP",
		"FRM_FIELD_TOTAL",  "FRM_FIELD_PERSON"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,    TYPE_FLOAT + ENTRY_REQUIRED,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT + ENTRY_REQUIRED,  TYPE_INT + ENTRY_REQUIRED
	} ;

	public FrmExpRecapitulate(){
	}
	public FrmExpRecapitulate(ExpRecapitulate expRecapitulate){
		this.expRecapitulate = expRecapitulate;
	}

	public FrmExpRecapitulate(HttpServletRequest request, ExpRecapitulate expRecapitulate){
		super(new FrmExpRecapitulate(expRecapitulate), request);
		this.expRecapitulate = expRecapitulate;
	}

	public String getFormName() { return FRM_NAME_EXPRECAPITULATE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ExpRecapitulate getEntityObject(){ return expRecapitulate; }

	public void requestEntityObject(ExpRecapitulate expRecapitulate) {
		try{
			this.requestParam();
			expRecapitulate.setPeriode(getDate(FRM_FIELD_PERIODE));
			expRecapitulate.setMedicalTypeId(getLong(FRM_FIELD_MEDICAL_TYPE_ID));
			expRecapitulate.setAmount(getDouble(FRM_FIELD_AMOUNT));
			expRecapitulate.setDiscountInPercent(getDouble(FRM_FIELD_DISCOUNT_IN_PERCENT));
            System.out.println(">>>"+getDouble(FRM_FIELD_DISCOUNT_IN_RP));
			expRecapitulate.setDiscountInRp(getDouble(FRM_FIELD_DISCOUNT_IN_RP));
			expRecapitulate.setTotal(getDouble(FRM_FIELD_TOTAL));
			expRecapitulate.setPerson(getInt(FRM_FIELD_PERSON));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
