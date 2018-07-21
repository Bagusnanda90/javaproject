/* 
 * Form Name  	:  FrmDevImprovement.java 
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

package com.dimata.harisma.form.employee;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;

public class FrmDevImprovement extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private DevImprovement devImprovement;

	public static final String FRM_NAME_DEVIMPROVEMENT		=  "FRM_NAME_DEVIMPROVEMENT" ;

	public static final int FRM_FIELD_DEV_IMPROVEMENT_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_APPRAISAL			=  1 ;
	public static final int FRM_FIELD_GROUP_CATEGORY_ID			=  2 ;
	public static final int FRM_FIELD_IMPROVEMENT			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DEV_IMPROVEMENT_ID",  "FRM_FIELD_EMPLOYEE_APPRAISAL",
		"FRM_FIELD_GROUP_CATEGORY_ID",  "FRM_FIELD_IMPROVEMENT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmDevImprovement(){
	}
	public FrmDevImprovement(DevImprovement devImprovement){
		this.devImprovement = devImprovement;
	}

	public FrmDevImprovement(HttpServletRequest request, DevImprovement devImprovement){
		super(new FrmDevImprovement(devImprovement), request);
		this.devImprovement = devImprovement;
	}

	public String getFormName() { return FRM_NAME_DEVIMPROVEMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public DevImprovement getEntityObject(){ return devImprovement; }

	public void requestEntityObject(DevImprovement devImprovement) {
		try{
			this.requestParam();
			devImprovement.setEmployeeAppraisal(getLong(FRM_FIELD_EMPLOYEE_APPRAISAL));
			devImprovement.setGroupCategoryId(getLong(FRM_FIELD_GROUP_CATEGORY_ID));
			devImprovement.setImprovement(getString(FRM_FIELD_IMPROVEMENT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
