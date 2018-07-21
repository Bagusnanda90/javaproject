/* 
 * Form Name  	:  FrmCafeEvaluation.java 
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

public class FrmCafeEvaluation extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private CafeEvaluation cafeEvaluation;

	public static final String FRM_NAME_CAFEEVALUATION		=  "FRM_NAME_CAFEEVALUATION" ;

	public static final int FRM_FIELD_CAFE_EVALUATION_ID			=  0 ;
	public static final int FRM_FIELD_CHECKLIST_MARK_ID			=  1 ;
	public static final int FRM_FIELD_CAFE_CHECKLIST_ID			=  2 ;
	public static final int FRM_FIELD_CHECKLIST_ITEM_ID			=  3 ;
	public static final int FRM_FIELD_REMARK			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CAFE_EVALUATION_ID",  "FRM_FIELD_CHECKLIST_MARK_ID",
		"FRM_FIELD_CAFE_CHECKLIST_ID",  "FRM_FIELD_CHECKLIST_ITEM_ID",
		"FRM_FIELD_REMARK"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING
	} ;

	public FrmCafeEvaluation(){
	}
	public FrmCafeEvaluation(CafeEvaluation cafeEvaluation){
		this.cafeEvaluation = cafeEvaluation;
	}

	public FrmCafeEvaluation(HttpServletRequest request, CafeEvaluation cafeEvaluation){
		super(new FrmCafeEvaluation(cafeEvaluation), request);
		this.cafeEvaluation = cafeEvaluation;
	}

	public String getFormName() { return FRM_NAME_CAFEEVALUATION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public CafeEvaluation getEntityObject(){ return cafeEvaluation; }

	public void requestEntityObject(CafeEvaluation cafeEvaluation) {
		try{
			this.requestParam();
			cafeEvaluation.setChecklistMarkId(getLong(FRM_FIELD_CHECKLIST_MARK_ID));
			cafeEvaluation.setCafeChecklistId(getLong(FRM_FIELD_CAFE_CHECKLIST_ID));
			cafeEvaluation.setChecklistItemId(getLong(FRM_FIELD_CHECKLIST_ITEM_ID));
			cafeEvaluation.setRemark(getString(FRM_FIELD_REMARK));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
