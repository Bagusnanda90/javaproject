/* 
 * Form Name  	:  FrmAppraisal.java 
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

package com.dimata.harisma.form.employee.appraisal;

/* java package */ 
import com.dimata.harisma.entity.employee.appraisal.Appraisal;
import javax.servlet.http.HttpServletRequest;

/* qdep package */ 
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;

/* project package */


public class FrmAppraisal extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Appraisal appraisal;

	public static final String FRM_APPRAISAL   =  "FRM_APPRAISAL" ;

	public static final  int FRM_FIELD_APPRAISAL_ID = 0;
	public static final  int FRM_FIELD_ASS_FORM_ITEM_ID = 1;
	public static final  int FRM_FIELD_APP_MAIN_ID = 2;
	public static final  int FRM_FIELD_EMP_COMMENT = 3;
	public static final  int FRM_FIELD_ASS_COMMENT = 4;
	public static final  int FRM_FIELD_RATING = 5;
	public static final  int FRM_FIELD_ANSWER_1 = 6;
	public static final  int FRM_FIELD_ANSWER_2 = 7;
	public static final  int FRM_FIELD_ANSWER_3 = 8;
	public static final  int FRM_FIELD_ANSWER_4 = 9;
	public static final  int FRM_FIELD_ANSWER_5 = 10;
	public static final  int FRM_FIELD_ANSWER_6 = 11;
        public static final  int FRM_FIELD_REALIZATION=12;
        public static final  int FRM_FIELD_EVIDENCE =13;
        public static final  int FRM_FIELD_POINT =14;
        

	public static String[] fieldNames = {
		"FRM_FIELD_HR_APPRAISAL_ID",
		"FRM_FIELD_ASS_FORM_ITEM_ID",
		"FRM_FIELD_HR_APP_MAIN_ID",
		"FRM_FIELD_EMP_COMMENT",
		"FRM_FIELD_ASS_COMMENT",
		"FRM_FIELD_RATING",
		"FRM_FIELD_ANSWER_1",
		"FRM_FIELD_ANSWER_2",
		"FRM_FIELD_ANSWER_3",
		"FRM_FIELD_ANSWER_4",
		"FRM_FIELD_ANSWER_5",
		"FRM_FIELD_ANSWER_6",
                "FRM_FIELD_REALIZATION",
                "FRM_FIELD_EVIDENCE",
                "FRM_FIELD_POINT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,
		TYPE_LONG,
		TYPE_LONG,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_FLOAT,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
		TYPE_STRING,
                TYPE_FLOAT,
                TYPE_STRING,
                TYPE_FLOAT
	} ;

	public FrmAppraisal(){
	}
	public FrmAppraisal(Appraisal appraisal){
		this.appraisal = appraisal;
	}

	public FrmAppraisal(HttpServletRequest request, Appraisal appraisal){
		super(new FrmAppraisal(appraisal), request);
		this.appraisal = appraisal;
	}

	public String getFormName() { return FRM_APPRAISAL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Appraisal getEntityObject(){ return appraisal; }

	public void requestEntityObject(Appraisal appraisal) {
		try{
			this.requestParam();
                        appraisal.setAppMainId(getLong(FRM_FIELD_APP_MAIN_ID));
                        appraisal.setAssComment(getString(FRM_FIELD_ASS_COMMENT));
                        appraisal.setAssFormItemId(getLong(FRM_FIELD_ASS_FORM_ITEM_ID));
                        appraisal.setEmpComment(getString(FRM_FIELD_EMP_COMMENT));
                        appraisal.setRating(getDouble(FRM_FIELD_RATING));
                        appraisal.setAnswer_1(getString(FRM_FIELD_ANSWER_1));
                        appraisal.setAnswer_2(getString(FRM_FIELD_ANSWER_2));
                        appraisal.setAnswer_3(getString(FRM_FIELD_ANSWER_3));
                        appraisal.setAnswer_4(getString(FRM_FIELD_ANSWER_4));
                        appraisal.setAnswer_5(getString(FRM_FIELD_ANSWER_5));
                        appraisal.setAnswer_6(getString(FRM_FIELD_ANSWER_6));
                        appraisal.setRealization(getFloat(FRM_FIELD_REALIZATION));
                        appraisal.setEvidence(getString(FRM_FIELD_EVIDENCE));
                        appraisal.setPoint(getFloat(FRM_FIELD_POINT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
