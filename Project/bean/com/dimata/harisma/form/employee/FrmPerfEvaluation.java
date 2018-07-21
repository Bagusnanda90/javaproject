/* 
 * Form Name  	:  FrmPerfEvaluation.java 
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

public class FrmPerfEvaluation extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private PerformanceEvaluation performanceEvaluation;

	public static final String FRM_NAME_PERFEVALUATION		=  "FRM_NAME_PERFEVALUATION" ;

	public static final int FRM_FIELD_PERFORMANCE_APPRAISAL_ID			=  0 ;
	public static final int FRM_FIELD_EVALUATION_ID			=  1 ;
	public static final int FRM_FIELD_CATEGORY_CRITERIA_ID			=  2 ;
	public static final int FRM_FIELD_JUSTIFICATION			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_PERFORMANCE_APPRAISAL_ID",  "FRM_FIELD_EVALUATION_ID",
		"FRM_FIELD_CATEGORY_CRITERIA_ID",  "FRM_FIELD_JUSTIFICATION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmPerfEvaluation(){
	}
	public FrmPerfEvaluation(PerformanceEvaluation performanceEvaluation){
		this.performanceEvaluation = performanceEvaluation;
	}

	public FrmPerfEvaluation(HttpServletRequest request, PerformanceEvaluation performanceEvaluation){
		super(new FrmPerfEvaluation(performanceEvaluation), request);
		this.performanceEvaluation = performanceEvaluation;
	}

	public String getFormName() { return FRM_NAME_PERFEVALUATION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public PerformanceEvaluation getEntityObject(){ return performanceEvaluation; }

	public void requestEntityObject(PerformanceEvaluation performanceEvaluation) {
		try{
			this.requestParam();
			performanceEvaluation.setEvaluationId(getLong(FRM_FIELD_EVALUATION_ID));
			performanceEvaluation.setCategoryCriteriaId(getLong(FRM_FIELD_CATEGORY_CRITERIA_ID));
			performanceEvaluation.setJustification(getString(FRM_FIELD_JUSTIFICATION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
