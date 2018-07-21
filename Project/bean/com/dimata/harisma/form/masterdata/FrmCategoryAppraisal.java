/* 
 * Form Name  	:  FrmCategoryAppraisal.java 
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

public class FrmCategoryAppraisal extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CategoryAppraisal categoryAppraisal;

	public static final String FRM_NAME_CATEGORYAPPRAISAL		=  "FRM_NAME_CATEGORYAPPRAISAL" ;

	public static final int FRM_FIELD_CATEGORY_APPRAISAL_ID			=  0 ;
	public static final int FRM_FIELD_GROUP_CATEGORY_ID			=  1 ;
	public static final int FRM_FIELD_CATEGORY			=  2 ;
	public static final int FRM_FIELD_DESCRIPTION			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CATEGORY_APPRAISAL_ID",  "FRM_FIELD_GROUP_CATEGORY_ID",
		"FRM_FIELD_CATEGORY", "FRM_FIELD_DESCRIPTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG ,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING
	} ;

	public FrmCategoryAppraisal(){
	}
	public FrmCategoryAppraisal(CategoryAppraisal categoryAppraisal){
		this.categoryAppraisal = categoryAppraisal;
	}

	public FrmCategoryAppraisal(HttpServletRequest request, CategoryAppraisal categoryAppraisal){
		super(new FrmCategoryAppraisal(categoryAppraisal), request);
		this.categoryAppraisal = categoryAppraisal;
	}

	public String getFormName() { return FRM_NAME_CATEGORYAPPRAISAL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CategoryAppraisal getEntityObject(){ return categoryAppraisal; }

	public void requestEntityObject(CategoryAppraisal categoryAppraisal) {
		try{
			this.requestParam();
			categoryAppraisal.setGroupCategoryId(getLong(FRM_FIELD_GROUP_CATEGORY_ID));
			categoryAppraisal.setCategory(getString(FRM_FIELD_CATEGORY));
			categoryAppraisal.setDescription(getString(FRM_FIELD_DESCRIPTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
