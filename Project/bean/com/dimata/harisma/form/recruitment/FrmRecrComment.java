/* 
 * Form Name  	:  FrmRecrComment.java 
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

public class FrmRecrComment extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrComment recrComment;

	public static final String FRM_NAME_RECRCOMMENT		=  "FRM_NAME_RECRCOMMENT" ;

	public static final int FRM_FIELD_RECR_COMMENT_ID			=  0 ;
	public static final int FRM_FIELD_RECR_INTERVIEWER_ID			=  1 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  2 ;
	public static final int FRM_FIELD_COMMENT			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_COMMENT_ID",  "FRM_FIELD_RECR_INTERVIEWER_ID",
		"FRM_FIELD_RECR_APPLICATION_ID",  "FRM_FIELD_COMMENT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_STRING
	} ;

	public FrmRecrComment(){
	}
	public FrmRecrComment(RecrComment recrComment){
		this.recrComment = recrComment;
	}

	public FrmRecrComment(HttpServletRequest request, RecrComment recrComment){
		super(new FrmRecrComment(recrComment), request);
		this.recrComment = recrComment;
	}

	public String getFormName() { return FRM_NAME_RECRCOMMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrComment getEntityObject(){ return recrComment; }

	public void requestEntityObject(RecrComment recrComment) {
		try{
			this.requestParam();
			recrComment.setRecrInterviewerId(getLong(FRM_FIELD_RECR_INTERVIEWER_ID));
			recrComment.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
			recrComment.setComment(getString(FRM_FIELD_COMMENT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
