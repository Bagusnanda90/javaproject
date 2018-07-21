/* 
 * Form Name  	:  FrmRecrWorkHistory.java 
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

package com.dimata.harisma.form.recruitment;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.recruitment.*;

public class FrmRecrWorkHistory extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrWorkHistory recrWorkHistory;

	public static final String FRM_NAME_RECRWORKHISTORY		=  "FRM_NAME_RECRWORKHISTORY" ;

	public static final int FRM_FIELD_RECR_WORK_HISTORY_ID			=  0 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  1 ;
	public static final int FRM_FIELD_POSITION			=  2 ;
	public static final int FRM_FIELD_START_DATE			=  3 ;
	public static final int FRM_FIELD_END_DATE			=  4 ;
	public static final int FRM_FIELD_DUTIES			=  5 ;
	public static final int FRM_FIELD_COMM_SALARY			=  6 ;
	public static final int FRM_FIELD_LAST_SALARY			=  7 ;
	public static final int FRM_FIELD_COMPANY_NAME			=  8 ;
	public static final int FRM_FIELD_COMPANY_ADDRESS			=  9 ;
	public static final int FRM_FIELD_COMPANY_PHONE			=  10 ;
	public static final int FRM_FIELD_COMPANY_NATURE			=  11 ;
	public static final int FRM_FIELD_COMPANY_SPV			=  12 ;
	public static final int FRM_FIELD_LEAVE_REASON			=  13 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_WORK_HISTORY_ID",  "FRM_FIELD_RECR_APPLICATION_ID",
		"FRM_FIELD_POSITION",  "FRM_FIELD_START_DATE",
		"FRM_FIELD_END_DATE",  "FRM_FIELD_DUTIES",
		"FRM_FIELD_COMM_SALARY",  "FRM_FIELD_LAST_SALARY",
		"FRM_FIELD_COMPANY_NAME",  "FRM_FIELD_COMPANY_ADDRESS",
		"FRM_FIELD_COMPANY_PHONE",  "FRM_FIELD_COMPANY_NATURE",
		"FRM_FIELD_COMPANY_SPV",  "FRM_FIELD_LEAVE_REASON"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_STRING,
		TYPE_INT,  TYPE_INT,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmRecrWorkHistory(){
	}
	public FrmRecrWorkHistory(RecrWorkHistory recrWorkHistory){
		this.recrWorkHistory = recrWorkHistory;
	}

	public FrmRecrWorkHistory(HttpServletRequest request, RecrWorkHistory recrWorkHistory){
		super(new FrmRecrWorkHistory(recrWorkHistory), request);
		this.recrWorkHistory = recrWorkHistory;
	}

	public String getFormName() { return FRM_NAME_RECRWORKHISTORY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrWorkHistory getEntityObject(){ return recrWorkHistory; }

	public void requestEntityObject(RecrWorkHistory recrWorkHistory) {
		try{
			this.requestParam();
			recrWorkHistory.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
			recrWorkHistory.setPosition(getString(FRM_FIELD_POSITION));
			recrWorkHistory.setStartDate(getDate(FRM_FIELD_START_DATE));
			recrWorkHistory.setEndDate(getDate(FRM_FIELD_END_DATE));
			recrWorkHistory.setDuties(getString(FRM_FIELD_DUTIES));
			recrWorkHistory.setCommSalary(getInt(FRM_FIELD_COMM_SALARY));
			recrWorkHistory.setLastSalary(getInt(FRM_FIELD_LAST_SALARY));
			recrWorkHistory.setCompanyName(getString(FRM_FIELD_COMPANY_NAME));
			recrWorkHistory.setCompanyAddress(getString(FRM_FIELD_COMPANY_ADDRESS));
			recrWorkHistory.setCompanyPhone(getString(FRM_FIELD_COMPANY_PHONE));
			recrWorkHistory.setCompanyNature(getString(FRM_FIELD_COMPANY_NATURE));
			recrWorkHistory.setCompanySpv(getString(FRM_FIELD_COMPANY_SPV));
			recrWorkHistory.setLeaveReason(getString(FRM_FIELD_LEAVE_REASON));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
