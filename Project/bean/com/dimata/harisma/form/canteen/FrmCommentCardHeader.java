/* 
 * Form Name  	:  FrmCommentCardHeader.java 
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

package com.dimata.harisma.form.canteen;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.canteen.*;

public class FrmCommentCardHeader extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CommentCardHeader commentCardHeader;

	public static final String FRM_NAME_COMMENTCARDHEADER		=  "FRM_NAME_COMMENTCARDHEADER" ;

	public static final int FRM_FIELD_COMMENT_CARD_HEADER_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_CARD_DATETIME			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_COMMENT_CARD_HEADER_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_CARD_DATETIME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED
	} ;

	public FrmCommentCardHeader(){
	}
	public FrmCommentCardHeader(CommentCardHeader commentCardHeader){
		this.commentCardHeader = commentCardHeader;
	}

	public FrmCommentCardHeader(HttpServletRequest request, CommentCardHeader commentCardHeader){
		super(new FrmCommentCardHeader(commentCardHeader), request);
		this.commentCardHeader = commentCardHeader;
	}

	public String getFormName() { return FRM_NAME_COMMENTCARDHEADER; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CommentCardHeader getEntityObject(){ return commentCardHeader; }

	public void requestEntityObject(CommentCardHeader commentCardHeader) {
		try{
			this.requestParam();
			commentCardHeader.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			commentCardHeader.setCardDatetime(getDate(FRM_FIELD_CARD_DATETIME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
