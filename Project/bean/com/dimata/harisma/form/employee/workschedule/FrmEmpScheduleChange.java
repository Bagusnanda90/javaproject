/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee.workschedule;

import com.dimata.harisma.entity.employee.workschedule.EmpScheduleChange;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/* 
 * Form Name  	:  FrmEmpScheduleChange.java 
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


public class FrmEmpScheduleChange extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private EmpScheduleChange empScheduleChange;

  public static final String FRM_NAME_EMP_SCHEDULE_CHANGE = "FRM_NAME_EMP_SCHEDULE_CHANGE";
  public static final int FRM_FIELD_EMP_SCHEDULE_CHANGE_ID = 0;
  public static final int FRM_FIELD_DATE_OF_REQUEST_DATETIME = 1;
  public static final int FRM_FIELD_STATUS_DOC = 2;
  public static final int FRM_FIELD_TYPE_OF_FORM = 3;
  public static final int FRM_FIELD_TYPE_OF_SCHEDULE = 4;
  public static final int FRM_FIELD_APPLICANT_EMPLOYEE_ID = 5;
  public static final int FRM_FIELD_EXCHANGE_EMPLOYEE_ID = 6;
  public static final int FRM_FIELD_ORIGINAL_DATE = 7;
  public static final int FRM_FIELD_ORIGINAL_SCHEDULE_ID = 8;
  public static final int FRM_FIELD_NEW_CHANGE_DATE = 9;
  public static final int FRM_FIELD_NEW_CHANGE_SCHEDULE_ID = 10;
  public static final int FRM_FIELD_REASON = 11;
  public static final int FRM_FIELD_REMARK = 12;
  public static final int FRM_FIELD_APPROVAL_LEVEL1_ID = 13;
  public static final int FRM_FIELD_APPROVAL_LEVEL2_ID = 14;
  public static final int FRM_FIELD_APPROVAL_DATE_LEVEL1 = 15;
  public static final int FRM_FIELD_APPROVAL_DATE_LEVEL2 = 16;
  public static final int FRM_FIELD_APPROVAL_DATE_APPLICANT = 17;
  public static final int FRM_FIELD_APPROVAL_DATE_EXCHANGE = 18;
  public static final int FRM_FIELD_CHECKED_BY_ID = 19;
  public static final int FRM_FIELD_CHECKED_DATE = 20;
  public static final int FRM_FIELD_APPROVAL_LEVEL1_NOTE = 21;
  public static final int FRM_FIELD_APPROVAL_LEVEL2_NOTE = 22;
  public static final int FRM_FIELD_PERSONAL_TYPE = 23;
  public static final int FRM_FIELD_REMARK2 = 24;

  public static String[] fieldNames = {
    "FRM_FIELD_EMP_SCHEDULE_CHANGE_ID",
    "FRM_FIELD_DATE_OF_REQUEST_DATETIME",
    "FRM_FIELD_STATUS_DOC",
    "FRM_FIELD_TYPE_OF_FORM",
    "FRM_FIELD_TYPE_OF_SCHEDULE",
    "FRM_FIELD_APPLICANT_EMPLOYEE_ID",
    "FRM_FIELD_EXCHANGE_EMPLOYEE_ID",
    "FRM_FIELD_ORIGINAL_DATE",
    "FRM_FIELD_ORIGINAL_SCHEDULE_ID",
    "FRM_FIELD_NEW_CHANGE_DATE",
    "FRM_FIELD_NEW_CHANGE_SCHEDULE_ID",
    "FRM_FIELD_REASON",
    "FRM_FIELD_REMARK",
    "FRM_FIELD_APPROVAL_LEVEL1_ID",
    "FRM_FIELD_APPROVAL_LEVEL2_ID",
    "FRM_FIELD_APPROVAL_DATE_LEVEL1",
    "FRM_FIELD_APPROVAL_DATE_LEVEL2",
    "FRM_FIELD_APPROVAL_DATE_APPLICANT",
    "FRM_FIELD_APPROVAL_DATE_EXCHANGE",
    "FRM_FIELD_CHECKED_BY_ID",
    "FRM_FIELD_CHECKED_DATE",
    "FRM_FIELD_APPROVAL_LEVEL1_NOTE",
    "FRM_FIELD_APPROVAL_LEVEL2_NOTE",
    "FRM_FIELD_PERSONAL_TYPE",
    "FRM_FIELD_REMARK2",
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,
    TYPE_DATE,
    TYPE_INT,
    TYPE_INT,
    TYPE_INT,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_DATE,
    TYPE_LONG,
    TYPE_DATE,
    TYPE_LONG,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_DATE,
    TYPE_DATE,
    TYPE_DATE,
    TYPE_DATE,
    TYPE_LONG,
    TYPE_DATE,
    TYPE_STRING,
    TYPE_STRING,
    TYPE_INT,
    TYPE_STRING
	} ;

	public FrmEmpScheduleChange(){
	}
	public FrmEmpScheduleChange(EmpScheduleChange empScheduleChange){
		this.empScheduleChange = empScheduleChange;
	}

	public FrmEmpScheduleChange(HttpServletRequest request, EmpScheduleChange empScheduleChange){
		super(new FrmEmpScheduleChange(empScheduleChange), request);
		this.empScheduleChange = empScheduleChange;
	}

	public String getFormName() { return FRM_NAME_EMP_SCHEDULE_CHANGE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public EmpScheduleChange getEntityObject(){ return empScheduleChange; }

	public void requestEntityObject(EmpScheduleChange entEmpScheduleChange) {
		try{
			this.requestParam();
			
    entEmpScheduleChange.setDateOfRequestDatetime(getDate(FRM_FIELD_DATE_OF_REQUEST_DATETIME));
    entEmpScheduleChange.setStatusDoc(getInt(FRM_FIELD_STATUS_DOC));
    entEmpScheduleChange.setTypeOfForm(getInt(FRM_FIELD_TYPE_OF_FORM));
    entEmpScheduleChange.setTypeOfSchedule(getInt(FRM_FIELD_TYPE_OF_SCHEDULE));
    entEmpScheduleChange.setApplicantEmployeeId(getLong(FRM_FIELD_APPLICANT_EMPLOYEE_ID));
    entEmpScheduleChange.setExchangeEmployeeId(getLong(FRM_FIELD_EXCHANGE_EMPLOYEE_ID));
    entEmpScheduleChange.setOriginalDate(getDate(FRM_FIELD_ORIGINAL_DATE));
    entEmpScheduleChange.setOriginalScheduleId(getLong(FRM_FIELD_ORIGINAL_SCHEDULE_ID));
    entEmpScheduleChange.setNewChangeDate(getDate(FRM_FIELD_NEW_CHANGE_DATE));
    entEmpScheduleChange.setNewChangeScheduleId(getLong(FRM_FIELD_NEW_CHANGE_SCHEDULE_ID));
    entEmpScheduleChange.setReason(getString(FRM_FIELD_REASON));
    entEmpScheduleChange.setRemark(getString(FRM_FIELD_REMARK));
    entEmpScheduleChange.setApprovalLevel1Id(getLong(FRM_FIELD_APPROVAL_LEVEL1_ID));
    entEmpScheduleChange.setApprovalLevel2Id(getLong(FRM_FIELD_APPROVAL_LEVEL2_ID));
    entEmpScheduleChange.setApprovalDateLevel1(getDate(FRM_FIELD_APPROVAL_DATE_LEVEL1));
    entEmpScheduleChange.setApprovalDateLevel2(getDate(FRM_FIELD_APPROVAL_DATE_LEVEL2));
    entEmpScheduleChange.setApprovalDateApplicant(getDate(FRM_FIELD_APPROVAL_DATE_APPLICANT));
    entEmpScheduleChange.setApprovalDateExchange(getDate(FRM_FIELD_APPROVAL_DATE_EXCHANGE));
    entEmpScheduleChange.setCheckedById(getLong(FRM_FIELD_CHECKED_BY_ID));
    entEmpScheduleChange.setCheckedDate(getDate(FRM_FIELD_CHECKED_DATE));
    entEmpScheduleChange.setApprovalLevel1Note(getString(FRM_FIELD_APPROVAL_LEVEL1_NOTE));
    entEmpScheduleChange.setApprovalLevel2Note(getString(FRM_FIELD_APPROVAL_LEVEL2_NOTE));
    entEmpScheduleChange.setPersonalType(getInt(FRM_FIELD_PERSONAL_TYPE));
    entEmpScheduleChange.setRemark2(getString(FRM_FIELD_REMARK2));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
