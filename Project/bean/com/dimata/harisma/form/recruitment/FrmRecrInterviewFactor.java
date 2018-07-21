/* 
 * Form Name  	:  FrmRecrInterviewFactor.java 
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

public class FrmRecrInterviewFactor extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrInterviewFactor recrInterviewFactor;

	public static final String FRM_NAME_RECRINTERVIEWFACTOR		=  "FRM_NAME_RECRINTERVIEWFACTOR" ;

	public static final int FRM_FIELD_RECR_INTERVIEW_FACTOR_ID			=  0 ;
	public static final int FRM_FIELD_INTERVIEW_FACTOR			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_INTERVIEW_FACTOR_ID",  "FRM_FIELD_INTERVIEW_FACTOR"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmRecrInterviewFactor(){
	}
	public FrmRecrInterviewFactor(RecrInterviewFactor recrInterviewFactor){
		this.recrInterviewFactor = recrInterviewFactor;
	}

	public FrmRecrInterviewFactor(HttpServletRequest request, RecrInterviewFactor recrInterviewFactor){
		super(new FrmRecrInterviewFactor(recrInterviewFactor), request);
		this.recrInterviewFactor = recrInterviewFactor;
	}

	public String getFormName() { return FRM_NAME_RECRINTERVIEWFACTOR; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrInterviewFactor getEntityObject(){ return recrInterviewFactor; }

	public void requestEntityObject(RecrInterviewFactor recrInterviewFactor) {
		try{
			this.requestParam();
			recrInterviewFactor.setInterviewFactor(getString(FRM_FIELD_INTERVIEW_FACTOR));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
