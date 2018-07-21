/* 
 * Form Name  	:  FrmEmployeeStart.java 
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

package com.dimata.harisma.form.startdata;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.startdata.*;

public class FrmEmployeeStart extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private EmployeeStart employeeStart;

	public static final String FRM_NAME_EMPLOYEESTART		=  "FRM_NAME_EMPLOYEESTART" ;

	public static final int FRM_FIELD_ID			=  0 ;
	public static final int FRM_FIELD_REG			=  1 ;
	public static final int FRM_FIELD_NIK			=  2 ;
	public static final int FRM_FIELD_NAME			=  3 ;
	public static final int FRM_FIELD_ADDRESS1			=  4 ;
	public static final int FRM_FIELD_ADDRESS2			=  5 ;
	public static final int FRM_FIELD_CITY			=  6 ;
	public static final int FRM_FIELD_PHONE			=  7 ;
	public static final int FRM_FIELD_SEX			=  8 ;
	public static final int FRM_FIELD_RELIGION			=  9 ;
	public static final int FRM_FIELD_DIVITION			=  10 ;
	public static final int FRM_FIELD_DEP			=  11 ;
	public static final int FRM_FIELD_LOCATION			=  12 ;
	public static final int FRM_FIELD_STATUS			=  13 ;
	public static final int FRM_FIELD_POSITION			=  14 ;
	public static final int FRM_FIELD_CHILD			=  15 ;
	public static final int FRM_FIELD_DOB			=  16 ;
	public static final int FRM_FIELD_START			=  17 ;

	public static String[] fieldNames = {
		"FRM_FIELD_ID",  "FRM_FIELD_REG",
		"FRM_FIELD_NIK",  "FRM_FIELD_NAME",
		"FRM_FIELD_ADDRESS1",  "FRM_FIELD_ADDRESS2",
		"FRM_FIELD_CITY",  "FRM_FIELD_PHONE",
		"FRM_FIELD_SEX",  "FRM_FIELD_RELIGION",
		"FRM_FIELD_DIVITION",  "FRM_FIELD_DEP",
		"FRM_FIELD_LOCATION",  "FRM_FIELD_STATUS",
		"FRM_FIELD_POSITION",  "FRM_FIELD_CHILD",
		"FRM_FIELD_DOB",  "FRM_FIELD_START"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_FLOAT,
		TYPE_STRING,  TYPE_STRING,
		TYPE_FLOAT,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_FLOAT,
		TYPE_STRING,  TYPE_DATE
	} ;

	public FrmEmployeeStart(){
	}
	public FrmEmployeeStart(EmployeeStart employeeStart){
		this.employeeStart = employeeStart;
	}

	public FrmEmployeeStart(HttpServletRequest request, EmployeeStart employeeStart){
		super(new FrmEmployeeStart(employeeStart), request);
		this.employeeStart = employeeStart;
	}

	public String getFormName() { return FRM_NAME_EMPLOYEESTART; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public EmployeeStart getEntityObject(){ return employeeStart; }

	public void requestEntityObject(EmployeeStart employeeStart) {
		try{
			this.requestParam();
			employeeStart.setReg(getString(FRM_FIELD_REG));
			employeeStart.setNik(getString(FRM_FIELD_NIK));
			employeeStart.setName(getString(FRM_FIELD_NAME));
			employeeStart.setAddress1(getString(FRM_FIELD_ADDRESS1));
			employeeStart.setAddress2(getString(FRM_FIELD_ADDRESS2));
			employeeStart.setCity(getString(FRM_FIELD_CITY));
			employeeStart.setPhone(getString(FRM_FIELD_PHONE));
			employeeStart.setSex(getString(FRM_FIELD_SEX));
			employeeStart.setReligion(getString(FRM_FIELD_RELIGION));
			employeeStart.setDivition(getDouble(FRM_FIELD_DIVITION));
			employeeStart.setDep(getString(FRM_FIELD_DEP));
			employeeStart.setLocation(getString(FRM_FIELD_LOCATION));
			employeeStart.setStatus(getString(FRM_FIELD_STATUS));
			employeeStart.setPosition(getString(FRM_FIELD_POSITION));
			employeeStart.setChild(getDouble(FRM_FIELD_CHILD));
			employeeStart.setDob(getString(FRM_FIELD_DOB));
			employeeStart.setStart(getDate(FRM_FIELD_START));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
