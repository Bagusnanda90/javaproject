/* 
 * Form Name  	:  FrmCommentCard.java 
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

public class FrmCommentCard extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CommentCard commentCard;

	public static final String FRM_NAME_COMMENTCARD		=  "FRM_NAME_COMMENTCARD" ;

	public static final int FRM_FIELD_COMMENT_CARD_ID			=  0 ;
	public static final int FRM_FIELD_CHECKLIST_MARK_ID			=  1 ;
	public static final int FRM_FIELD_COMMENT_CARD_HEADER_ID			=  2 ;
	public static final int FRM_FIELD_CARD_QUESTION_ID			=  3 ;
	public static final int FRM_FIELD_REMARK			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_COMMENT_CARD_ID",  "FRM_FIELD_CHECKLIST_MARK_ID",
		"FRM_FIELD_COMMENT_CARD_HEADER_ID",  "FRM_FIELD_CARD_QUESTION_ID",
		"FRM_FIELD_REMARK"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG,
		TYPE_LONG,  TYPE_LONG,
		TYPE_STRING
	} ;

	public FrmCommentCard(){
	}
	public FrmCommentCard(CommentCard commentCard){
		this.commentCard = commentCard;
	}

	public FrmCommentCard(HttpServletRequest request, CommentCard commentCard){
		super(new FrmCommentCard(commentCard), request);
		this.commentCard = commentCard;
	}

	public String getFormName() { return FRM_NAME_COMMENTCARD; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CommentCard getEntityObject(){ return commentCard; }

	public void requestEntityObject(CommentCard commentCard) {
		try{
			this.requestParam();
			commentCard.setChecklistMarkId(getLong(FRM_FIELD_CHECKLIST_MARK_ID));
			commentCard.setCommentCardHeaderId(getLong(FRM_FIELD_COMMENT_CARD_HEADER_ID));
			commentCard.setCardQuestionId(getLong(FRM_FIELD_CARD_QUESTION_ID));
			commentCard.setRemark(getString(FRM_FIELD_REMARK));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
