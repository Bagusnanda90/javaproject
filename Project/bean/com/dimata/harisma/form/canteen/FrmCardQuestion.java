/* 
 * Form Name  	:  FrmCardQuestion.java 
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

public class FrmCardQuestion extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CardQuestion cardQuestion;

	public static final String FRM_NAME_CARDQUESTION		=  "FRM_NAME_CARDQUESTION" ;

	public static final int FRM_FIELD_CARD_QUESTION_ID			=  0 ;
	public static final int FRM_FIELD_CARD_QUESTION_GROUP_ID			=  1 ;
	public static final int FRM_FIELD_QUESTION			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CARD_QUESTION_ID",  "FRM_FIELD_CARD_QUESTION_GROUP_ID",
		"FRM_FIELD_QUESTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmCardQuestion(){
	}
	public FrmCardQuestion(CardQuestion cardQuestion){
		this.cardQuestion = cardQuestion;
	}

	public FrmCardQuestion(HttpServletRequest request, CardQuestion cardQuestion){
		super(new FrmCardQuestion(cardQuestion), request);
		this.cardQuestion = cardQuestion;
	}

	public String getFormName() { return FRM_NAME_CARDQUESTION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CardQuestion getEntityObject(){ return cardQuestion; }

	public void requestEntityObject(CardQuestion cardQuestion) {
		try{
			this.requestParam();
			cardQuestion.setCardQuestionGroupId(getLong(FRM_FIELD_CARD_QUESTION_GROUP_ID));
			cardQuestion.setQuestion(getString(FRM_FIELD_QUESTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
