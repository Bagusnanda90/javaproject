/* 
 * Form Name  	:  FrmScheduleCategory.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmScheduleCategory extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ScheduleCategory scheduleCategory;

	public static final String FRM_NAME_SCHEDULECATEGORY		=  "FRM_NAME_SCHEDULECATEGORY" ;

	public static final int FRM_FIELD_SCHEDULE_CATEGORY_ID			=  0 ;
	public static final int FRM_FIELD_CATEGORY			=  1 ;
	public static final int FRM_FIELD_DESCRIPTION			=  2 ;
        public static final int FRM_FIELD_CATEGORY_TYPE			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_SCHEDULE_CATEGORY_ID",  "FRM_FIELD_CATEGORY",
		"FRM_FIELD_DESCRIPTION","FRM_FIELD_CATEGORY_TYPE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING, TYPE_INT
	} ;

	public FrmScheduleCategory(){
	}
	public FrmScheduleCategory(ScheduleCategory scheduleCategory){
		this.scheduleCategory = scheduleCategory;
	}

	public FrmScheduleCategory(HttpServletRequest request, ScheduleCategory scheduleCategory){
		super(new FrmScheduleCategory(scheduleCategory), request);
		this.scheduleCategory = scheduleCategory;
	}

	public String getFormName() { return FRM_NAME_SCHEDULECATEGORY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ScheduleCategory getEntityObject(){ return scheduleCategory; }

	public void requestEntityObject(ScheduleCategory scheduleCategory) {
		try{
			this.requestParam();
			scheduleCategory.setCategory(getString(FRM_FIELD_CATEGORY));
			scheduleCategory.setDescription(getString(FRM_FIELD_DESCRIPTION));
                        scheduleCategory.setCategoryType(getInt(FRM_FIELD_CATEGORY_TYPE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
