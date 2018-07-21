/* 
 * Form Name  	:  FrmChecklistItem.java 
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

public class FrmChecklistItem extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ChecklistItem checklistItem;

	public static final String FRM_NAME_CHECKLISTITEM		=  "FRM_NAME_CHECKLISTITEM" ;

	public static final int FRM_FIELD_CHECKLIST_ITEM_ID			=  0 ;
	public static final int FRM_FIELD_CHECKLIST_GROUP_ID			=  1 ;
	public static final int FRM_FIELD_CHECKLIST_ITEM			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CHECKLIST_ITEM_ID",  "FRM_FIELD_CHECKLIST_GROUP_ID",
		"FRM_FIELD_CHECKLIST_ITEM"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmChecklistItem(){
	}
	public FrmChecklistItem(ChecklistItem checklistItem){
		this.checklistItem = checklistItem;
	}

	public FrmChecklistItem(HttpServletRequest request, ChecklistItem checklistItem){
		super(new FrmChecklistItem(checklistItem), request);
		this.checklistItem = checklistItem;
	}

	public String getFormName() { return FRM_NAME_CHECKLISTITEM; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ChecklistItem getEntityObject(){ return checklistItem; }

	public void requestEntityObject(ChecklistItem checklistItem) {
		try{
			this.requestParam();
			checklistItem.setChecklistGroupId(getLong(FRM_FIELD_CHECKLIST_GROUP_ID));
			checklistItem.setChecklistItem(getString(FRM_FIELD_CHECKLIST_ITEM));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
