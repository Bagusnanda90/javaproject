/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.mappingoutlet;

import com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutlet;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmExtraScheduleOutlet extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ExtraScheduleOutlet extraScheduleOutlet;

	public static final String FRM_NAME_EXTRA_SCHEDULE_OUTLET	 =  "FRM_NAME_EXTRA_SCHEDULE_OUTLET" ;

    public static final int FRM_EXTRA_SCHEDULE_MAPPING_ID = 0;
    public static final int FRM_REQUEST_DATE_EXTRA_SCHEDULE = 1;
    public static final int FRM_COMPANY_ID = 2;
    public static final int FRM_DIVISION_ID = 3;
    public static final int FRM_DEPARTMENT_ID = 4;
    public static final int FRM_SECTION_ID = 5;
    public static final int FRM_COST_CENTER_ID = 6;
    public static final int FRM_EXTRA_SCHEDULE_ADJECTIVE = 7;
    public static final int FRM_EXTRA_SCHEDULE_NUMBER = 8;
    public static final int FRM_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE = 9;
    public static final int FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT = 10;
    public static final int FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT = 11;
    public static final int FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT = 12;
    public static final int FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE = 13;
    public static final int FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE = 14;
    public static final int FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE = 15;
    public static final int FRM_COUNT_IDX = 16;
    public static final int FRM_FLAG_SAVE_EMPLOYEE = 17;

	public static String[] fieldNames = {
            "FRM_EXTRA_SCHEDULE_MAPPING_ID",
            "FRM_REQUEST_DATE_EXTRA_SCHEDULE",
            "FRM_COMPANY_ID",
            "FRM_DIVISION_ID",
            "FRM_DEPARTMENT_ID",
            "FRM_SECTION_ID",
            "FRM_COST_CENTER_ID",
            "FRM_EXTRA_SCHEDULE_ADJECTIVE",
            "FRM_EXTRA_SCHEDULE_NUMBER",
            "FRM_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE",
            "FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT",
            "FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT",
            "FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT",
            "FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE",
            "FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE",
            "FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE",
            "FRM_COUNT_IDX",
            "FRM_FLAG_SAVE_EMPLOYEE"
	} ;

	public static int[] fieldTypes = {
            TYPE_LONG,//"FRM_EXTRA_SCHEDULE_MAPPING_ID",
            TYPE_DATE,//"FRM_REQUEST_DATE_EXTRA_SCHEDULE",
            TYPE_LONG,//"FRM_COMPANY_ID",
            TYPE_LONG,//"FRM_DIVISION_ID",
            TYPE_LONG,//"FRM_DEPARTMENT_ID",
            TYPE_LONG,//"FRM_SECTION_ID",
            TYPE_LONG,//"FRM_COST_CENTER_ID",
            TYPE_STRING,//"FRM_EXTRA_SCHEDULE_ADJECTIVE",
            TYPE_STRING,//"FRM_EXTRA_SCHEDULE_NUMBER",
            TYPE_INT,//"FRM_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE",
            TYPE_LONG,//"FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT",
            TYPE_LONG,//"FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT",
            TYPE_LONG,//"FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT",
            TYPE_DATE,//"FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE",
            TYPE_DATE,//"FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE",
            TYPE_DATE,//"FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT_DATE",
            TYPE_INT,
            TYPE_STRING
	};

	public FrmExtraScheduleOutlet(){
	}
	public FrmExtraScheduleOutlet(ExtraScheduleOutlet objExtraScheduleOutlet){
		this.extraScheduleOutlet = objExtraScheduleOutlet;
	}

	public FrmExtraScheduleOutlet(HttpServletRequest request, ExtraScheduleOutlet objExtraScheduleOutlet){
		super(new FrmExtraScheduleOutlet(objExtraScheduleOutlet), request);
		this.extraScheduleOutlet = objExtraScheduleOutlet;
	}

	public String getFormName() { return FRM_NAME_EXTRA_SCHEDULE_OUTLET; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ExtraScheduleOutlet getEntityObject(){ return extraScheduleOutlet; }

	public void requestEntityObject(ExtraScheduleOutlet objExtraScheduleOutlet) {
		try{
            this.requestParam();
            //objExtraScheduleOutlet.setOID(getLong(FRM_EXTRA_SCHEDULE_MAPPING_ID));
            objExtraScheduleOutlet.setRequestDate(getDate(FRM_REQUEST_DATE_EXTRA_SCHEDULE));
            objExtraScheduleOutlet.setNumberForm(getString(FRM_EXTRA_SCHEDULE_NUMBER));
            objExtraScheduleOutlet.setCompanyId(getLong(FRM_COMPANY_ID));
            objExtraScheduleOutlet.setDivisionId(getLong(FRM_DIVISION_ID));
            objExtraScheduleOutlet.setDepartmentId(getLong(FRM_DEPARTMENT_ID));
            objExtraScheduleOutlet.setSectionId(getLong(FRM_SECTION_ID));
            objExtraScheduleOutlet.setCountIdx(getInt(FRM_COUNT_IDX));
            objExtraScheduleOutlet.setCostCenterId(getLong(FRM_COST_CENTER_ID));
            objExtraScheduleOutlet.setDocStsForm(getInt(FRM_STATUS_DOCUMENT_FORM_EXTRA_SCHEDULE));
            objExtraScheduleOutlet.setExtraScheduleObjctive(getString(FRM_EXTRA_SCHEDULE_ADJECTIVE));
            objExtraScheduleOutlet.setEmpApprovall(getLong(FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT));
            objExtraScheduleOutlet.setDtEmpApprovall(getDate(FRM_REQUEST_EMPLOYEE_ID_APPOVALL_DOCUMENT_DATE));
            objExtraScheduleOutlet.setEmpApprovall1(getLong(FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT));
            objExtraScheduleOutlet.setDtEmpApprovall1(getDate(FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE));
            objExtraScheduleOutlet.setEmpApprovall2(getLong(FRM_REQUEST_EMPLOYEE_ID_APPOVALL2_DOCUMENT));
            objExtraScheduleOutlet.setDtEmpApprovall2(getDate(FRM_REQUEST_EMPLOYEE_ID_APPOVALL1_DOCUMENT_DATE));
            objExtraScheduleOutlet.setExtraScheduleObjctive(getString(FRM_EXTRA_SCHEDULE_ADJECTIVE));
              objExtraScheduleOutlet.setFlagSaveEmployee(getString(FRM_FLAG_SAVE_EMPLOYEE));           
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
