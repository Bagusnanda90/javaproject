/* 
 * Form Name  	:  FrmOriChecklistActivity.java 
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

public class FrmOriChecklistActivity extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private OriChecklistActivity oriChecklistActivity;

	public static final String FRM_NAME_ORICHECKLISTACTIVITY		=  "FRM_NAME_ORICHECKLISTACTIVITY" ;

	public static final int FRM_FIELD_ORI_CHECKLIST_ACTIVITY_ID			=  0 ;
	public static final int FRM_FIELD_ORI_CHECKLIST_ID			=  1 ;
	public static final int FRM_FIELD_ORI_ACTIVITY_ID			=  2 ;
	public static final int FRM_FIELD_DONE			=  3 ;
	public static final int FRM_FIELD_ACTIVITY_DATE			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_ORI_CHECKLIST_ACTIVITY_ID",  "FRM_FIELD_ORI_CHECKLIST_ID",
		"FRM_FIELD_ORI_ACTIVITY_ID",  "FRM_FIELD_DONE",
		"FRM_FIELD_ACTIVITY_DATE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_INT + ENTRY_REQUIRED,
		TYPE_DATE
	} ;

	public FrmOriChecklistActivity(){
	}
	public FrmOriChecklistActivity(OriChecklistActivity oriChecklistActivity){
		this.oriChecklistActivity = oriChecklistActivity;
	}

	public FrmOriChecklistActivity(HttpServletRequest request, OriChecklistActivity oriChecklistActivity){
		super(new FrmOriChecklistActivity(oriChecklistActivity), request);
		this.oriChecklistActivity = oriChecklistActivity;
	}

	public String getFormName() { return FRM_NAME_ORICHECKLISTACTIVITY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public OriChecklistActivity getEntityObject(){ return oriChecklistActivity; }

	public void requestEntityObject(OriChecklistActivity oriChecklistActivity) {
		try{
			this.requestParam();
			oriChecklistActivity.setOriChecklistId(getLong(FRM_FIELD_ORI_CHECKLIST_ID));
			oriChecklistActivity.setOriActivityId(getLong(FRM_FIELD_ORI_ACTIVITY_ID));
			oriChecklistActivity.setDone(getInt(FRM_FIELD_DONE));
			oriChecklistActivity.setActivityDate(getDate(FRM_FIELD_ACTIVITY_DATE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
