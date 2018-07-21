/* 
 * Form Name  	:  FrmRecrInterviewerFactor.java 
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

public class FrmRecrInterviewerFactor extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrInterviewerFactor recrInterviewerFactor;

	public static final String FRM_NAME_RECRINTERVIEWERFACTOR		=  "FRM_NAME_RECRINTERVIEWERFACTOR" ;

	public static final int FRM_FIELD_RECR_INTERVIEWER_FACTOR_ID			=  0 ;
	public static final int FRM_FIELD_RECR_INTERVIEW_FACTOR_ID			=  1 ;
	public static final int FRM_FIELD_RECR_INTERVIEWER_ID			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_INTERVIEWER_FACTOR_ID",  "FRM_FIELD_RECR_INTERVIEW_FACTOR_ID",
		"FRM_FIELD_RECR_INTERVIEWER_ID"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED
	} ;

	public FrmRecrInterviewerFactor(){
	}
	public FrmRecrInterviewerFactor(RecrInterviewerFactor recrInterviewerFactor){
		this.recrInterviewerFactor = recrInterviewerFactor;
	}

	public FrmRecrInterviewerFactor(HttpServletRequest request, RecrInterviewerFactor recrInterviewerFactor){
		super(new FrmRecrInterviewerFactor(recrInterviewerFactor), request);
		this.recrInterviewerFactor = recrInterviewerFactor;
	}

	public String getFormName() { return FRM_NAME_RECRINTERVIEWERFACTOR; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrInterviewerFactor getEntityObject(){ return recrInterviewerFactor; }

	public void requestEntityObject(RecrInterviewerFactor recrInterviewerFactor) {
		try{
			this.requestParam();
			recrInterviewerFactor.setRecrInterviewFactorId(getLong(FRM_FIELD_RECR_INTERVIEW_FACTOR_ID));
			recrInterviewerFactor.setRecrInterviewerId(getLong(FRM_FIELD_RECR_INTERVIEWER_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
