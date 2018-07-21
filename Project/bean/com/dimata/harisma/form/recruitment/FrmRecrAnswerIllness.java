/* 
 * Form Name  	:  FrmRecrAnswerIllness.java 
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

public class FrmRecrAnswerIllness extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrAnswerIllness recrAnswerIllness;

	public static final String FRM_NAME_RECRANSWERILLNESS		=  "FRM_NAME_RECRANSWERILLNESS" ;

	public static final int FRM_FIELD_RECR_ANSWER_ILLNESS_ID			=  0 ;
	public static final int FRM_FIELD_RECR_ILLNESS_ID			=  1 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  2 ;
	public static final int FRM_FIELD_ANSWER			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_ANSWER_ILLNESS_ID",  "FRM_FIELD_RECR_ILLNESS_ID",
		"FRM_FIELD_RECR_APPLICATION_ID",  "FRM_FIELD_ANSWER"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_INT + ENTRY_REQUIRED
	} ;

	public FrmRecrAnswerIllness(){
	}
	public FrmRecrAnswerIllness(RecrAnswerIllness recrAnswerIllness){
		this.recrAnswerIllness = recrAnswerIllness;
	}

	public FrmRecrAnswerIllness(HttpServletRequest request, RecrAnswerIllness recrAnswerIllness){
		super(new FrmRecrAnswerIllness(recrAnswerIllness), request);
		this.recrAnswerIllness = recrAnswerIllness;
	}

	public String getFormName() { return FRM_NAME_RECRANSWERILLNESS; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrAnswerIllness getEntityObject(){ return recrAnswerIllness; }

	public void requestEntityObject(RecrAnswerIllness recrAnswerIllness) {
		try{
			this.requestParam();
			recrAnswerIllness.setRecrIllnessId(getLong(FRM_FIELD_RECR_ILLNESS_ID));
			recrAnswerIllness.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
			recrAnswerIllness.setAnswer(getInt(FRM_FIELD_ANSWER));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
