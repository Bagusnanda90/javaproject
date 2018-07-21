/* 
 * Form Name  	:  FrmAssessmentFormSection.java 
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

package com.dimata.harisma.form.employee.assessment;

/* java package */ 
import javax.servlet.http.HttpServletRequest;

/* qdep package */ 
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;

/* project package */

import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormSection;

public class FrmAssessmentFormSection extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private AssessmentFormSection assessmentFormSection;

	public static final String FRM_ASS_FORM_SECTION   =  "FRM_ASS_FORM_SECTION" ;
        
        public static final  int FRM_FIELD_ASS_FORM_SECTION_ID   = 0;
	public static final  int FRM_FIELD_ASS_FORM_MAIN_ID      = 1;
	public static final  int FRM_FIELD_SECTION               = 2;
	public static final  int FRM_FIELD_DESCRIPTION           = 3;
	public static final  int FRM_FIELD_SECTION_L2            = 4;
	public static final  int FRM_FIELD_DESCRIPTION_L2        = 5;
	public static final  int FRM_FIELD_ORDER_NUMBER          = 6;
	public static final  int FRM_FIELD_PAGE                  = 7;
	public static final  int FRM_FIELD_TYPE                  = 8;
	public static final  int FRM_FIELD_POINT_EVAL_ID         = 9;
        public static final  int FRM_FIELD_PREDICATE_EVAL_ID     = 10;
        public static final  int FRM_FIELD_WEIGHT_POINT          =11;
	public static String[] fieldNames = {
                "FRM_FIELD_ASS_FORM_SECTION_ID",
		"FRM_FIELD_ASS_FORM_MAIN_ID",
		"FRM_FIELD_SECTION",
		"FRM_FIELD_DESCRIPTION",
		"FRM_FIELD_SECTION_L2",
		"FRM_FIELD_DESCRIPTION_L2",
		"FRM_FIELD_ORDER_NUMBER",
		"FRM_FIELD_PAGE",
		"FRM_FIELD_TYPE",
                "FRM_FIELD_POINT_EVAL_ID",
                "FRM_FIELD_PREDICATE_EVAL_ID",
                "FRM_FIELD_WEIGHT_POINT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,
		TYPE_LONG+ENTRY_REQUIRED,
		TYPE_STRING+ENTRY_REQUIRED,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_INT,
		TYPE_INT,
		TYPE_INT,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_FLOAT
	} ;

	public FrmAssessmentFormSection(){
	}
	public FrmAssessmentFormSection(AssessmentFormSection assessmentFormSection){
		this.assessmentFormSection = assessmentFormSection;
	}

	public FrmAssessmentFormSection(HttpServletRequest request, AssessmentFormSection assessmentFormSection){
		super(new FrmAssessmentFormSection(assessmentFormSection), request);
		this.assessmentFormSection = assessmentFormSection;
	}

	public String getFormName() { return FRM_ASS_FORM_SECTION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public AssessmentFormSection getEntityObject(){ return assessmentFormSection; }

	public void requestEntityObject(AssessmentFormSection assessmentFormSection) {
		try{
			this.requestParam();
			assessmentFormSection.setAssFormMainId(getLong(FRM_FIELD_ASS_FORM_MAIN_ID));
			assessmentFormSection.setSection(getString(FRM_FIELD_SECTION));
			assessmentFormSection.setDescription(getString(FRM_FIELD_DESCRIPTION));
			assessmentFormSection.setSection_L2(getString(FRM_FIELD_SECTION_L2));
			assessmentFormSection.setDescription_L2(getString(FRM_FIELD_DESCRIPTION_L2));
			assessmentFormSection.setOrderNumber(getInt(FRM_FIELD_ORDER_NUMBER));
			assessmentFormSection.setPage(getInt(FRM_FIELD_PAGE));
			assessmentFormSection.setType(getInt(FRM_FIELD_TYPE));
                        assessmentFormSection.setPointEvaluationId(getLong(FRM_FIELD_POINT_EVAL_ID));
                        assessmentFormSection.setPredicateEvaluationId(getLong(FRM_FIELD_PREDICATE_EVAL_ID));
                        assessmentFormSection.setWeightPoint(getFloat(FRM_FIELD_WEIGHT_POINT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
