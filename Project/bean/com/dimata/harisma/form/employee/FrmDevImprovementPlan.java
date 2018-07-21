/* 
 * Form Name  	:  FrmDevImprovementPlan.java 
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

public class FrmDevImprovementPlan extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private DevImprovementPlan devImprovementPlan;

	public static final String FRM_NAME_DEVIMPROVEMENTPLAN		=  "FRM_NAME_DEVIMPROVEMENTPLAN" ;

	public static final int FRM_FIELD_DEV_IMPROVEMENT_PLAN_ID			=  0 ;
	public static final int FRM_FIELD_CATEGORY_APPRAISAL_ID			=  1 ;
	public static final int FRM_FIELD_EMPLOYEE_APPRAISAL			=  2 ;
	public static final int FRM_FIELD_IMPROV_PLAN			=  3 ;
    public static final int FRM_FIELD_RECOMMEND			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DEV_IMPROVEMENT_PLAN_ID",  "FRM_FIELD_CATEGORY_APPRAISAL_ID",
		"FRM_FIELD_EMPLOYEE_APPRAISAL",  "FRM_FIELD_IMPROV_PLAN","FRM_FIELD_RECOMMEND"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
        TYPE_BOOL
	} ;

	public FrmDevImprovementPlan(){
	}
	public FrmDevImprovementPlan(DevImprovementPlan devImprovementPlan){
		this.devImprovementPlan = devImprovementPlan;
	}

	public FrmDevImprovementPlan(HttpServletRequest request, DevImprovementPlan devImprovementPlan){
		super(new FrmDevImprovementPlan(devImprovementPlan), request);
		this.devImprovementPlan = devImprovementPlan;
	}

	public String getFormName() { return FRM_NAME_DEVIMPROVEMENTPLAN; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public DevImprovementPlan getEntityObject(){ return devImprovementPlan; }

	public void requestEntityObject(DevImprovementPlan devImprovementPlan) {
		try{
			this.requestParam();
			devImprovementPlan.setCategoryAppraisalId(getLong(FRM_FIELD_CATEGORY_APPRAISAL_ID));
			devImprovementPlan.setEmployeeAppraisal(getLong(FRM_FIELD_EMPLOYEE_APPRAISAL));
			devImprovementPlan.setImprovPlan(getString(FRM_FIELD_IMPROV_PLAN));
            devImprovementPlan.setRecommend(getBoolean(FRM_FIELD_RECOMMEND));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
