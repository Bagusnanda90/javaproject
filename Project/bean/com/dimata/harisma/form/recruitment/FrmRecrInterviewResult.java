/* 
 * Form Name  	:  FrmRecrInterviewResult.java 
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

package com.dimata.harisma.form.recruitment;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.recruitment.*;

public class FrmRecrInterviewResult extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrInterviewResult recrInterviewResult;

	public static final String FRM_NAME_RECRINTERVIEWRESULT		=  "FRM_NAME_RECRINTERVIEWRESULT" ;

	public static final int FRM_FIELD_RECR_INTERVIEW_RESULT_ID			=  0 ;
	public static final int FRM_FIELD_RECR_INTERVIEW_POINT_ID			=  1 ;
	public static final int FRM_FIELD_RECR_INTERVIEWER_FACTOR_ID			=  2 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_INTERVIEW_RESULT_ID",  "FRM_FIELD_RECR_INTERVIEW_POINT_ID",
		"FRM_FIELD_RECR_INTERVIEWER_FACTOR_ID",  "FRM_FIELD_RECR_APPLICATION_ID"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_LONG + ENTRY_REQUIRED
	} ;

	public FrmRecrInterviewResult(){
	}
	public FrmRecrInterviewResult(RecrInterviewResult recrInterviewResult){
		this.recrInterviewResult = recrInterviewResult;
	}

	public FrmRecrInterviewResult(HttpServletRequest request, RecrInterviewResult recrInterviewResult){
		super(new FrmRecrInterviewResult(recrInterviewResult), request);
		this.recrInterviewResult = recrInterviewResult;
	}

	public String getFormName() { return FRM_NAME_RECRINTERVIEWRESULT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrInterviewResult getEntityObject(){ return recrInterviewResult; }

	public void requestEntityObject(RecrInterviewResult recrInterviewResult) {
		try{
			this.requestParam();
			recrInterviewResult.setRecrInterviewPointId(getLong(FRM_FIELD_RECR_INTERVIEW_POINT_ID));
			recrInterviewResult.setRecrInterviewerFactorId(getLong(FRM_FIELD_RECR_INTERVIEWER_FACTOR_ID));
			recrInterviewResult.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
