/* 
 * Form Name  	:  FrmRecrAnswerGeneral.java 
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

public class FrmRecrAnswerGeneral extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrAnswerGeneral recrAnswerGeneral;

	public static final String FRM_NAME_RECRANSWERGENERAL		=  "FRM_NAME_RECRANSWERGENERAL" ;

	public static final int FRM_FIELD_RECR_ANSWER			=  0 ;
	public static final int FRM_FIELD_RECR_GENERAL_ID			=  1 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  2 ;
	public static final int FRM_FIELD_ANSWER			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_ANSWER",  "FRM_FIELD_RECR_GENERAL_ID",
		"FRM_FIELD_RECR_APPLICATION_ID",  "FRM_FIELD_ANSWER"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmRecrAnswerGeneral(){
	}
	public FrmRecrAnswerGeneral(RecrAnswerGeneral recrAnswerGeneral){
		this.recrAnswerGeneral = recrAnswerGeneral;
	}

	public FrmRecrAnswerGeneral(HttpServletRequest request, RecrAnswerGeneral recrAnswerGeneral){
		super(new FrmRecrAnswerGeneral(recrAnswerGeneral), request);
		this.recrAnswerGeneral = recrAnswerGeneral;
	}

	public String getFormName() { return FRM_NAME_RECRANSWERGENERAL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrAnswerGeneral getEntityObject(){ return recrAnswerGeneral; }

	public void requestEntityObject(RecrAnswerGeneral recrAnswerGeneral) {
		try{
			this.requestParam();
			recrAnswerGeneral.setRecrGeneralId(getLong(FRM_FIELD_RECR_GENERAL_ID));
			recrAnswerGeneral.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
			recrAnswerGeneral.setAnswer(getString(FRM_FIELD_ANSWER));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
