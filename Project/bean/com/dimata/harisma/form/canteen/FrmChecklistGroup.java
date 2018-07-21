/* 
 * Form Name  	:  FrmChecklistGroup.java 
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

public class FrmChecklistGroup extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ChecklistGroup checklistGroup;

	public static final String FRM_NAME_CHECKLISTGROUP		=  "FRM_NAME_CHECKLISTGROUP" ;

	public static final int FRM_FIELD_CHECKLIST_GROUP_ID			=  0 ;
	public static final int FRM_FIELD_CHECKLIST_GROUP			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CHECKLIST_GROUP_ID",  "FRM_FIELD_CHECKLIST_GROUP"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmChecklistGroup(){
	}
	public FrmChecklistGroup(ChecklistGroup checklistGroup){
		this.checklistGroup = checklistGroup;
	}

	public FrmChecklistGroup(HttpServletRequest request, ChecklistGroup checklistGroup){
		super(new FrmChecklistGroup(checklistGroup), request);
		this.checklistGroup = checklistGroup;
	}

	public String getFormName() { return FRM_NAME_CHECKLISTGROUP; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ChecklistGroup getEntityObject(){ return checklistGroup; }

	public void requestEntityObject(ChecklistGroup checklistGroup) {
		try{
			this.requestParam();
			checklistGroup.setChecklistGroup(getString(FRM_FIELD_CHECKLIST_GROUP));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
