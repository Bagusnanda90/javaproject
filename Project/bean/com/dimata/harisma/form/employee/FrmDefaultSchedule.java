/* 
 * Form Name  	:  FrmFamilyMember.java 
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

public class FrmDefaultSchedule extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private DefaultSchedule defaultSchedule;

	public static final String FRM_NAME_DFLT_SCHEDULE		=  "FRM_NAME_DFLT_SCHEDULE" ;

	public static final int FRM_FIELD_DFLT_SCHEDULE_ID  	=  0 ;
        public static final int FRM_FIELD_EMPLOYEE_ID 		=  1;
        public static final int FRM_FIELD_SCHEDULE_1 		=  2;
        public static final int FRM_FIELD_SCHEDULE_2		=  3;
        public static final int FRM_FIELD_DAY_INDEX		=  4;


	public static String[] fieldNames = {
		"FRM_FIELD_DFLT_SCHEDULE_ID",  "FRM_FIELD_EMPLOYEE_ID",
                "FRM_FIELD_SCHEDULE_1","FRM_FIELD_SCHEDULE_2", "FRM_FIELD_DAY_INDEX"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG,  TYPE_LONG, TYPE_LONG,TYPE_INT
	} ;

	public FrmDefaultSchedule(){
	}
	public FrmDefaultSchedule(DefaultSchedule defaultSchedule){
		this.defaultSchedule = defaultSchedule;
	}

	public FrmDefaultSchedule(HttpServletRequest request, DefaultSchedule defaultSchedule){
		super(new FrmDefaultSchedule(defaultSchedule), request);
		this.defaultSchedule = defaultSchedule;
	}

	public String getFormName() { return FRM_NAME_DFLT_SCHEDULE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public DefaultSchedule getEntityObject(){ return defaultSchedule; }

	public void requestEntityObject(DefaultSchedule defaultSchedule) {
		try{
			this.requestParam();
			defaultSchedule.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
                        defaultSchedule.setSchedule1(getLong(FRM_FIELD_SCHEDULE_1));
                        defaultSchedule.setSchedule2(getLong(FRM_FIELD_SCHEDULE_2));
                        defaultSchedule.setDayIndex(getInt(FRM_FIELD_DAY_INDEX));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
