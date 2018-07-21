/* 
 * Form Name  	:  FrmEmployeeVisit.java 
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

package com.dimata.harisma.form.clinic;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.clinic.*;

public class FrmEmployeeVisit extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private EmployeeVisit employeeVisit;

	public static final String FRM_NAME_EMPLOYEEVISIT		=  "FRM_NAME_EMPLOYEEVISIT" ;

	public static final int FRM_FIELD_EMP_VISIT_ID			=  0 ;
	public static final int FRM_FIELD_VISIT_DATE			=  1 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  2 ;
	public static final int FRM_FIELD_DIAGNOSE			=  3 ;
	public static final int FRM_FIELD_VISITED_BY			=  4 ;
	public static final int FRM_FIELD_DESCRIPTION			=  5 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMP_VISIT_ID",  "FRM_FIELD_VISIT_DATE",
		"FRM_FIELD_EMPLOYEE_ID",  "FRM_FIELD_DIAGNOSE",
		"FRM_FIELD_VISITED_BY",  "FRM_FIELD_DESCRIPTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_STRING
	} ;

	public FrmEmployeeVisit(){
	}
	public FrmEmployeeVisit(EmployeeVisit employeeVisit){
		this.employeeVisit = employeeVisit;
	}

	public FrmEmployeeVisit(HttpServletRequest request, EmployeeVisit employeeVisit){
		super(new FrmEmployeeVisit(employeeVisit), request);
		this.employeeVisit = employeeVisit;
	}

	public String getFormName() { return FRM_NAME_EMPLOYEEVISIT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public EmployeeVisit getEntityObject(){ return employeeVisit; }

	public void requestEntityObject(EmployeeVisit employeeVisit) {
		try{
			this.requestParam();
			employeeVisit.setVisitDate(getDate(FRM_FIELD_VISIT_DATE));
			employeeVisit.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			employeeVisit.setDiagnose(getString(FRM_FIELD_DIAGNOSE));
			employeeVisit.setVisitedBy(getLong(FRM_FIELD_VISITED_BY));
			employeeVisit.setDescription(getString(FRM_FIELD_DESCRIPTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
