/* 
 * Form Name  	:  FrmSpecialAcheivement.java 
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

package com.dimata.harisma.form.employee;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;

public class FrmSpecialAcheivement extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SpecialAchievement specialAchievement;

	public static final String FRM_NAME_SPECIALACHEIVEMENT		=  "FRM_NAME_SPECIALACHEIVEMENT" ;

	public static final int FRM_FIELD_SPECIAL_ACHIEVEMENT_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_TYPE_OF_AWARD			=  2 ;
	public static final int FRM_FIELD_PRESENTED_BY			=  3 ;
	public static final int FRM_FIELD_DATE			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_SPECIAL_ACHIEVEMENT_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_TYPE_OF_AWARD",  "FRM_FIELD_PRESENTED_BY",
		"FRM_FIELD_DATE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED
	} ;

	public FrmSpecialAcheivement(){
	}
	public FrmSpecialAcheivement(SpecialAchievement specialAchievement){
		this.specialAchievement = specialAchievement;
	}

	public FrmSpecialAcheivement(HttpServletRequest request, SpecialAchievement specialAchievement){
		super(new FrmSpecialAcheivement(specialAchievement), request);
		this.specialAchievement = specialAchievement;
	}

	public String getFormName() { return FRM_NAME_SPECIALACHEIVEMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SpecialAchievement getEntityObject(){ return specialAchievement; }

	public void requestEntityObject(SpecialAchievement specialAchievement) {
		try{
			this.requestParam();
			specialAchievement.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			specialAchievement.setTypeOfAward(getString(FRM_FIELD_TYPE_OF_AWARD));
			specialAchievement.setPresentedBy(getString(FRM_FIELD_PRESENTED_BY));
			specialAchievement.setDate(getDate(FRM_FIELD_DATE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
