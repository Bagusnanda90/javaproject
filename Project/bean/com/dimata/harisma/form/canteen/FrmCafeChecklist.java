/* 
 * Form Name  	:  FrmCafeChecklist.java 
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

public class FrmCafeChecklist extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CafeChecklist cafeChecklist;

	public static final String FRM_NAME_CAFECHECKLIST		=  "FRM_NAME_CAFECHECKLIST" ;

	public static final int FRM_FIELD_CAFE_CHECKLIST_ID			=  0 ;
	public static final int FRM_FIELD_MEAL_TIME_ID			=  1 ;
	public static final int FRM_FIELD_CHECK_DATE			=  2 ;
	public static final int FRM_FIELD_CHECKED_BY			=  3 ;
	public static final int FRM_FIELD_APPROVED_BY			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CAFE_CHECKLIST_ID",  "FRM_FIELD_MEAL_TIME_ID",
		"FRM_FIELD_CHECK_DATE",  "FRM_FIELD_CHECKED_BY",
		"FRM_FIELD_APPROVED_BY"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_LONG,
		TYPE_LONG
	} ;

	public FrmCafeChecklist(){
	}
	public FrmCafeChecklist(CafeChecklist cafeChecklist){
		this.cafeChecklist = cafeChecklist;
	}

	public FrmCafeChecklist(HttpServletRequest request, CafeChecklist cafeChecklist){
		super(new FrmCafeChecklist(cafeChecklist), request);
		this.cafeChecklist = cafeChecklist;
	}

	public String getFormName() { return FRM_NAME_CAFECHECKLIST; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CafeChecklist getEntityObject(){ return cafeChecklist; }

	public void requestEntityObject(CafeChecklist cafeChecklist) {
		try{
			this.requestParam();
			cafeChecklist.setMealTimeId(getLong(FRM_FIELD_MEAL_TIME_ID));
			cafeChecklist.setCheckDate(getDate(FRM_FIELD_CHECK_DATE));
			cafeChecklist.setCheckedBy(getLong(FRM_FIELD_CHECKED_BY));
			cafeChecklist.setApprovedBy(getLong(FRM_FIELD_APPROVED_BY));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
