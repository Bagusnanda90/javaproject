/* 
 * Form Name  	:  FrmMealTime.java 
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

public class FrmMealTime extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private MealTime mealTime;

	public static final String FRM_NAME_MEALTIME		=  "FRM_NAME_MEALTIME" ;

	public static final int FRM_FIELD_MEAL_TIME_ID			=  0 ;
	public static final int FRM_FIELD_MEAL_TIME			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_MEAL_TIME_ID",  "FRM_FIELD_MEAL_TIME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmMealTime(){
	}
	public FrmMealTime(MealTime mealTime){
		this.mealTime = mealTime;
	}

	public FrmMealTime(HttpServletRequest request, MealTime mealTime){
		super(new FrmMealTime(mealTime), request);
		this.mealTime = mealTime;
	}

	public String getFormName() { return FRM_NAME_MEALTIME; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public MealTime getEntityObject(){ return mealTime; }

	public void requestEntityObject(MealTime mealTime) {
		try{
			this.requestParam();
			mealTime.setMealTime(getString(FRM_FIELD_MEAL_TIME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
