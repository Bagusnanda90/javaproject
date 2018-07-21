/* 
 * Form Name  	:  FrmCardQuestionGroup.java 
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

public class FrmCardQuestionGroup extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CardQuestionGroup cardQuestionGroup;

	public static final String FRM_NAME_CARDQUESTIONGROUP		=  "FRM_NAME_CARDQUESTIONGROUP" ;

	public static final int FRM_FIELD_CARD_QUESTION_GROUP_ID			=  0 ;
	public static final int FRM_FIELD_GROUP_NAME			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CARD_QUESTION_GROUP_ID",  "FRM_FIELD_GROUP_NAME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmCardQuestionGroup(){
	}
	public FrmCardQuestionGroup(CardQuestionGroup cardQuestionGroup){
		this.cardQuestionGroup = cardQuestionGroup;
	}

	public FrmCardQuestionGroup(HttpServletRequest request, CardQuestionGroup cardQuestionGroup){
		super(new FrmCardQuestionGroup(cardQuestionGroup), request);
		this.cardQuestionGroup = cardQuestionGroup;
	}

	public String getFormName() { return FRM_NAME_CARDQUESTIONGROUP; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CardQuestionGroup getEntityObject(){ return cardQuestionGroup; }

	public void requestEntityObject(CardQuestionGroup cardQuestionGroup) {
		try{
			this.requestParam();
			cardQuestionGroup.setGroupName(getString(FRM_FIELD_GROUP_NAME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
