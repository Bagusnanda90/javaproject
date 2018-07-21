/* 
 * Form Name  	:  FrmChecklistMark.java 
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

public class FrmChecklistMark extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ChecklistMark checklistMark;

	public static final String FRM_NAME_CHECKLISTMARK		=  "FRM_NAME_CHECKLISTMARK" ;

	public static final int FRM_FIELD_CHECKLIST_MARK_ID			=  0 ;
	public static final int FRM_FIELD_CHECKLIST_MARK			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CHECKLIST_MARK_ID",  "FRM_FIELD_CHECKLIST_MARK"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmChecklistMark(){
	}
	public FrmChecklistMark(ChecklistMark checklistMark){
		this.checklistMark = checklistMark;
	}

	public FrmChecklistMark(HttpServletRequest request, ChecklistMark checklistMark){
		super(new FrmChecklistMark(checklistMark), request);
		this.checklistMark = checklistMark;
	}

	public String getFormName() { return FRM_NAME_CHECKLISTMARK; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ChecklistMark getEntityObject(){ return checklistMark; }

	public void requestEntityObject(ChecklistMark checklistMark) {
		try{
			this.requestParam();
			checklistMark.setChecklistMark(getString(FRM_FIELD_CHECKLIST_MARK));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
