/*
 * Form Name  	:  FrmEmployee.java
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

public class FrmEmployeeMutation extends FRMHandler implements I_FRMInterface, I_FRMType
{
	private Employee employee;

	public static final String FRM_NAME_EMPLOYEE_MUTATION		=  "FRM_NAME_EMPLOYEE_MUTATION" ;

	public static final int FRM_FIELD_EMPLOYEE_ID_MUTATION			=  0 ;
	public static final int FRM_FIELD_DEPARTMENT_ID_MUTATION		=  1 ;
	public static final int FRM_FIELD_POSITION_ID_MUTATION			=  2 ;
	public static final int FRM_FIELD_LEVEL_ID_MUTATION			=  3 ;
        public static final int FRM_FIELD_DIVISION_ID_MUTATION                  =  4 ;
        public static final int FRM_FIELD_SECTION_ID_MUTATION                   =  5 ;
        public static final int FRM_FIELD_COMPANY_ID_MUTATION                   =  6 ;
        public static final int FRM_FIELD_EMP_CATEGORY_ID_MUTATION              =  7 ;
        //priska 15-11-2014
        public static final int FRM_FIELD_LOCATION_ID_MUTATION                  =  8 ;
        public static final int FRM_FIELD_END_CONTRACT_MUTATION                 =  9 ;
        

	public static String[] fieldNames = {
            "FRM_FIELD_EMPLOYEE_ID_MUTATION",
            "FRM_FIELD_DEPARTMENT_ID_MUTATION",
            "FRM_FIELD_POSITION_ID_MUTATION",
            "FRM_FIELD_LEVEL_ID_MUTATION",
            "FRM_FIELD_DIVISION_ID_MUTATION",
            "FRM_FIELD_SECTION_ID_MUTATION",
            "FRM_FIELD_COMPANY_ID_MUTATION",
            "FRM_FIELD_EMP_CATEGORY_ID_MUTATION",
             //priska 15-11-2014
            "FRM_FIELD_LOCATION_ID_MUTATION",
            "FRM_FIELD_END_CONTRACT_MUTATION"
            
	} ;

	public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_LONG + ENTRY_REQUIRED,
            TYPE_LONG + ENTRY_REQUIRED,
            TYPE_LONG + ENTRY_REQUIRED,
            TYPE_LONG + ENTRY_REQUIRED,
            TYPE_LONG,
            TYPE_LONG + ENTRY_REQUIRED,
            TYPE_LONG + ENTRY_REQUIRED,
             //priska 15-11-2014
            TYPE_LONG,TYPE_DATE
	} ;

	public FrmEmployeeMutation(){
	}
	public FrmEmployeeMutation(Employee employee){
		this.employee = employee;
	}

	public FrmEmployeeMutation(HttpServletRequest request, Employee employee){
		super(new FrmEmployeeMutation(employee), request);
		this.employee = employee;
	}

	public String getFormName() { return FRM_NAME_EMPLOYEE_MUTATION; }

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; }

	public int getFieldSize() { return fieldNames.length; }

	public Employee getEntityObject(){ return employee; }

	public void requestEntityObject(Employee employee) {
		try{
			this.requestParam();
                        //update by devin 2014-02-17
                        employee.setEmpCategoryId(getLong(FRM_FIELD_EMPLOYEE_ID_MUTATION));
                        //end update
			employee.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID_MUTATION));
			employee.setPositionId(getLong(FRM_FIELD_POSITION_ID_MUTATION));
			employee.setLevelId(getLong(FRM_FIELD_LEVEL_ID_MUTATION));
                        employee.setDivisionId(getLong(FRM_FIELD_DIVISION_ID_MUTATION));
                        employee.setSectionId(getLong(FRM_FIELD_SECTION_ID_MUTATION));
                        employee.setCompanyId(getLong(FRM_FIELD_COMPANY_ID_MUTATION));
                        employee.setEmpCategoryId(getLong(FRM_FIELD_EMP_CATEGORY_ID_MUTATION));
                        employee.setLocationId(getLong(FRM_FIELD_LOCATION_ID_MUTATION));
                        employee.setEnd_contract(getDate(FRM_FIELD_END_CONTRACT_MUTATION));


		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
