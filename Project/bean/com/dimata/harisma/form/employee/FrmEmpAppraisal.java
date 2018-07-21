/* 
 * Form Name  	:  FrmEmpAppraisal.java 
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

public class FrmEmpAppraisal extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private EmpAppraisal empAppraisal;

	public static final String FRM_NAME_EMPAPPRAISAL		=  "FRM_NAME_EMPAPPRAISAL" ;

	public static final int FRM_FIELD_EMPLOYEE_APPRAISAL_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_APPRAISOR_ID			=  2 ;
	public static final int FRM_FIELD_DATE_OF_APPRAISAL			=  3 ;
	public static final int FRM_FIELD_LAST_APPRAISAL			=  4 ;
	public static final int FRM_FIELD_TOTAL_SCORE			=  5 ;
	public static final int FRM_FIELD_TOTAL_CRITERIA			=  6 ;
	public static final int FRM_FIELD_SCORE_AVERAGE			=  7 ;
	public static final int FRM_FIELD_DATE_PERFORMANCE			=  8 ;
	public static final int FRM_FIELD_TIME_PERFORMANCE			=  9 ;
	public static final int FRM_FIELD_GROUP_RANK_ID			=  10 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMPLOYEE_APPRAISAL_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_APPRAISOR_ID",  "FRM_FIELD_DATE_OF_APPRAISAL",
		"FRM_FIELD_LAST_APPRAISAL",  "FRM_FIELD_TOTAL_SCORE",
		"FRM_FIELD_TOTAL_CRITERIA",  "FRM_FIELD_SCORE_AVERAGE",
		"FRM_FIELD_DATE_PERFORMANCE",  "FRM_FIELD_TIME_PERFORMANCE", "FRM_FIELD_GROUP_RANK_ID"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_DATE,  TYPE_INT,
		TYPE_INT,  TYPE_FLOAT,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_DATE, TYPE_LONG
	} ;

	public FrmEmpAppraisal(){
	}
	public FrmEmpAppraisal(EmpAppraisal empAppraisal){
		this.empAppraisal = empAppraisal;
	}

	public FrmEmpAppraisal(HttpServletRequest request, EmpAppraisal empAppraisal){
		super(new FrmEmpAppraisal(empAppraisal), request);
		this.empAppraisal = empAppraisal;
	}

	public String getFormName() { return FRM_NAME_EMPAPPRAISAL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public EmpAppraisal getEntityObject(){ return empAppraisal; }

	public void requestEntityObject(EmpAppraisal empAppraisal) {
		try{
			this.requestParam();
			empAppraisal.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			empAppraisal.setAppraisorId(getLong(FRM_FIELD_APPRAISOR_ID));
			empAppraisal.setDateOfAppraisal(getDate(FRM_FIELD_DATE_OF_APPRAISAL));
			empAppraisal.setLastAppraisal(getDate(FRM_FIELD_LAST_APPRAISAL));
			empAppraisal.setTotalScore(getInt(FRM_FIELD_TOTAL_SCORE));
			empAppraisal.setTotalCriteria(getInt(FRM_FIELD_TOTAL_CRITERIA));
			empAppraisal.setScoreAverage(getDouble(FRM_FIELD_SCORE_AVERAGE));
			empAppraisal.setDatePerformance(getDate(FRM_FIELD_DATE_PERFORMANCE));
                        empAppraisal.setGroupRankId(getLong(FRM_FIELD_GROUP_RANK_ID));
			//empAppraisal.setTimePerformance(getDate(FRM_FIELD_TIME_PERFORMANCE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
