/* 
 * Form Name  	:  FrmEmpSalary.java 
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

public class FrmEmpSalary extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private EmpSalary empSalary;

	public static final String FRM_NAME_EMPSALARY		=  "FRM_NAME_EMPSALARY" ;

	public static final int FRM_FIELD_EMP_SALARY_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_LOS1			=  2 ;
	public static final int FRM_FIELD_LOS2			=  3 ;
	public static final int FRM_FIELD_CURR_BASIC			=  4 ;
	public static final int FRM_FIELD_CURR_TRANSPORT			=  5 ;
	public static final int FRM_FIELD_CURR_TOTAL			=  6 ;
	public static final int FRM_FIELD_NEW_BASIC			=  7 ;
	public static final int FRM_FIELD_NEW_TRANSPORT			=  8 ;
	public static final int FRM_FIELD_NEW_TOTAL			=  9 ;
	public static final int FRM_FIELD_INC_SALARY			=  10 ;
	public static final int FRM_FIELD_INC_TRANSPORT			=  11 ;
	public static final int FRM_FIELD_ADDITIONAL			=  12 ;
	public static final int FRM_FIELD_INC_TOTAL			=  13 ;
	public static final int FRM_FIELD_PERCENTAGE_BASIC			=  14 ;
	public static final int FRM_FIELD_PERCENT_TRANSPORT			=  15 ;
	public static final int FRM_FIELD_PERCENTAGE_TOTAL			=  16 ;
    //public static final int FRM_FIELD_CURR_DATE				=  17 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMP_SALARY_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_LOS1",  "FRM_FIELD_LOS2",
		"FRM_FIELD_CURR_BASIC",  "FRM_FIELD_CURR_TRANSPORT",
		"FRM_FIELD_CURR_TOTAL",  "FRM_FIELD_NEW_BASIC",
		"FRM_FIELD_NEW_TRANSPORT",  "FRM_FIELD_NEW_TOTAL",
		"FRM_FIELD_INC_SALARY",  "FRM_FIELD_INC_TRANSPORT",
		"FRM_FIELD_ADDITIONAL",  "FRM_FIELD_INC_TOTAL",
		"FRM_FIELD_PERCENTAGE_BASIC",  "FRM_FIELD_PERCENT_TRANSPORT",
		"FRM_FIELD_PERCENTAGE_TOTAL"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_INT,  TYPE_INT,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT
	} ;

	public FrmEmpSalary(){
	}
	public FrmEmpSalary(EmpSalary empSalary){
		this.empSalary = empSalary;
	}

	public FrmEmpSalary(HttpServletRequest request, EmpSalary empSalary){
		super(new FrmEmpSalary(empSalary), request);
		this.empSalary = empSalary;
	}

	public String getFormName() { return FRM_NAME_EMPSALARY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public EmpSalary getEntityObject(){ return empSalary; }

	public void requestEntityObject(EmpSalary empSalary) {
		try{
			this.requestParam();
			empSalary.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			empSalary.setLos1(getInt(FRM_FIELD_LOS1));
			empSalary.setLos2(getInt(FRM_FIELD_LOS2));
			empSalary.setCurrBasic(getDouble(FRM_FIELD_CURR_BASIC));
			empSalary.setCurrTransport(getDouble(FRM_FIELD_CURR_TRANSPORT));
			empSalary.setCurrTotal(getDouble(FRM_FIELD_CURR_TOTAL));
			empSalary.setNewBasic(getDouble(FRM_FIELD_NEW_BASIC));
			empSalary.setNewTransport(getDouble(FRM_FIELD_NEW_TRANSPORT));
			empSalary.setNewTotal(getDouble(FRM_FIELD_NEW_TOTAL));
			empSalary.setIncSalary(getDouble(FRM_FIELD_INC_SALARY));
			empSalary.setIncTransport(getDouble(FRM_FIELD_INC_TRANSPORT));
			empSalary.setAdditional(getDouble(FRM_FIELD_ADDITIONAL));
			empSalary.setIncTotal(getDouble(FRM_FIELD_INC_TOTAL));
			empSalary.setPercentageBasic(getDouble(FRM_FIELD_PERCENTAGE_BASIC));
			empSalary.setPercentTransport(getDouble(FRM_FIELD_PERCENT_TRANSPORT));
			empSalary.setPercentageTotal(getDouble(FRM_FIELD_PERCENTAGE_TOTAL));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
