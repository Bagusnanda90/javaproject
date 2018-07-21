/* 
 * Form Name  	:  FrmCategoryCriteria.java 
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

public class FrmCategoryCriteria extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CategoryCriteria categoryCriteria;

	public static final String FRM_NAME_CATEGORYCRITERIA		=  "FRM_NAME_CATEGORYCRITERIA" ;

	public static final int FRM_FIELD_CATEGORY_CRITERIA_ID	=  0 ;
	public static final int FRM_FIELD_CRITERIA		=  1 ;
	public static final int FRM_FIELD_DESC_1		=  2 ;
	public static final int FRM_FIELD_DESC_2		=  3 ;
	public static final int FRM_FIELD_DESC_3		=  4 ;
	public static final int FRM_FIELD_DESC_4		=  5 ;
	public static final int FRM_FIELD_DESC_5		=  6 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CATEGORY_CRITERIA_ID",  "FRM_FIELD_CRITERIA", "FRM_FIELD_DESC_1", "FRM_FIELD_DESC_2", 
                "FRM_FIELD_DESC_3", "FRM_FIELD_DESC_4", "FRM_FIELD_DESC_5"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING,  TYPE_STRING,  TYPE_STRING,  TYPE_STRING,  TYPE_STRING
	} ;

	public FrmCategoryCriteria(){
	}
	public FrmCategoryCriteria(CategoryCriteria categoryCriteria){
		this.categoryCriteria = categoryCriteria;
	}

	public FrmCategoryCriteria(HttpServletRequest request, CategoryCriteria categoryCriteria){
		super(new FrmCategoryCriteria(categoryCriteria), request);
		this.categoryCriteria = categoryCriteria;
	}

	public String getFormName() { return FRM_NAME_CATEGORYCRITERIA; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CategoryCriteria getEntityObject(){ return categoryCriteria; }

	public void requestEntityObject(CategoryCriteria categoryCriteria) {
		try{
			this.requestParam();
			categoryCriteria.setCriteria(getString(FRM_FIELD_CRITERIA));
			categoryCriteria.setDesc1(getString(FRM_FIELD_DESC_1));
			categoryCriteria.setDesc2(getString(FRM_FIELD_DESC_2));
			categoryCriteria.setDesc3(getString(FRM_FIELD_DESC_3));
			categoryCriteria.setDesc4(getString(FRM_FIELD_DESC_4));
			categoryCriteria.setDesc5(getString(FRM_FIELD_DESC_5));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
