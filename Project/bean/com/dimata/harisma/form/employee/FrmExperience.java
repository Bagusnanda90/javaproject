/* 
 * Form Name  	:  FrmExperience.java 
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
import java.sql.Date;
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;
import com.dimata.util.Formater;

public class FrmExperience extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Experience experience;

	public static final String FRM_NAME_EXPERIENCE		=  "FRM_NAME_EXPERIENCE" ;

	public static final int FRM_FIELD_WORK_HISTORY_PAST_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_COMPANY_NAME			=  2 ;
	public static final int FRM_FIELD_START_DATE			=  3 ;
	public static final int FRM_FIELD_END_DATE			=  4 ;
	public static final int FRM_FIELD_POSITION			=  5 ;
	public static final int FRM_FIELD_MOVE_REASON			=  6 ;
	public static final int FRM_FIELD_PROVIDER_ID			=  7 ;
        public static final int FRM_FIELD_SALARY_RECEIVED               = 8 ;

	public static String[] fieldNames = {
		"FRM_FIELD_WORK_HISTORY_PAST_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_COMPANY_NAME",  "FRM_FIELD_START_DATE",
		"FRM_FIELD_END_DATE",  "FRM_FIELD_POSITION",
		"FRM_FIELD_MOVE_REASON", "FRM_FIELD_PROVIDER_ID",
                "FRM_FIELD_SALARY_RECEIVED"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_STRING + ENTRY_REQUIRED,
                TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED, 
                TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,
                TYPE_LONG,
                TYPE_STRING
	} ;

	public FrmExperience(){
	}
	public FrmExperience(Experience experience){
		this.experience = experience;
	}

	public FrmExperience(HttpServletRequest request, Experience experience){
		super(new FrmExperience(experience), request);
		this.experience = experience;
	}

	public String getFormName() { return FRM_NAME_EXPERIENCE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Experience getEntityObject(){ return experience; }

	public void requestEntityObject(Experience experience) {
		try{
			this.requestParam();
			experience.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			experience.setCompanyName(getString(FRM_FIELD_COMPANY_NAME));
			experience.setStartDate(Formater.formatDate(getString(FRM_FIELD_START_DATE)+"-01","yyyy-MM-dd"));
			experience.setEndDate(Formater.formatDate(getString(FRM_FIELD_END_DATE)+"-01","yyyy-MM-dd"));
			experience.setPosition(getString(FRM_FIELD_POSITION));
			experience.setMoveReason(getString(FRM_FIELD_MOVE_REASON));
			experience.setProviderID(getLong(FRM_FIELD_PROVIDER_ID));
                        experience.setSalaryReceived(getString(FRM_FIELD_SALARY_RECEIVED));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
