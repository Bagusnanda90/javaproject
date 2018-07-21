/* 
 * Form Name  	:  FrmStaffRequisition.java 
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

public class FrmStaffRequisition extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private StaffRequisition staffRequisition;

	public static final String FRM_NAME_STAFFREQUISITION		=  "FRM_NAME_STAFFREQUISITION" ;

	public static final int FRM_FIELD_STAFF_REQUISITION_ID		=  0 ;
	public static final int FRM_FIELD_DEPARTMENT_ID			=  1 ;
	public static final int FRM_FIELD_SECTION_ID			=  2 ;
	public static final int FRM_FIELD_POSITION_ID			=  3 ;
	public static final int FRM_FIELD_EMP_CATEGORY_ID		=  4 ;
	public static final int FRM_FIELD_REQUISITION_TYPE		=  5 ;
	public static final int FRM_FIELD_NEEDED_MALE			=  6 ;
	public static final int FRM_FIELD_NEEDED_FEMALE			=  7 ;
	public static final int FRM_FIELD_EXP_COMM_DATE			=  8 ;
	public static final int FRM_FIELD_TEMP_FOR			=  9 ;
	public static final int FRM_FIELD_APPROVED_BY			=  10 ;
	public static final int FRM_FIELD_APPROVED_DATE			=  11 ;
	public static final int FRM_FIELD_ACKNOWLEDGED_BY		=  12 ;
	public static final int FRM_FIELD_ACKNOWLEDGED_DATE		=  13 ;
	public static final int FRM_FIELD_REQUESTED_BY			=  14 ;
	public static final int FRM_FIELD_REQUESTED_DATE		=  15 ;
        public static final int FRM_FIELD_APPROVED_DATE_STR		=  16 ;
        public static final int FRM_FIELD_ACKNOWLEDGED_DATE_STR		=  17 ;
        public static final int FRM_FIELD_REQUESTED_DATE_STR		=  18 ;
        public static final int FRM_FIELD_EXP_COMM_DATE_STR		=  19 ;

	public static String[] fieldNames = {
		"FRM_FIELD_STAFF_REQUISITION_ID",  "FRM_FIELD_DEPARTMENT_ID",
		"FRM_FIELD_SECTION_ID",  "FRM_FIELD_POSITION_ID",
		"FRM_FIELD_EMP_CATEGORY_ID",  "FRM_FIELD_REQUISITION_TYPE",
		"FRM_FIELD_NEEDED_MALE",  "FRM_FIELD_NEEDED_FEMALE",
		"FRM_FIELD_EXP_COMM_DATE",  "FRM_FIELD_TEMP_FOR",
		"FRM_FIELD_APPROVED_BY",  "FRM_FIELD_APPROVED_DATE",
		"FRM_FIELD_ACKNOWLEDGED_BY",  "FRM_FIELD_ACKNOWLEDGED_DATE",
		"FRM_FIELD_REQUESTED_BY",  "FRM_FIELD_REQUESTED_DATE", 
                "FRM_FIELD_APPROVED_DATE_STR", "FRM_FIELD_ACKNOWLEDGED_DATE_STR",
                "FRM_FIELD_REQUESTED_DATE_STR", "FRM_FIELD_EXP_COMM_DATE_STR"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG ,  TYPE_INT,//TYPE_INT + ENTRY_REQUIRED,
		TYPE_INT,  TYPE_INT,
		TYPE_DATE,  TYPE_INT,
		TYPE_LONG,  TYPE_DATE,
		TYPE_LONG,  TYPE_DATE,
		TYPE_LONG,  TYPE_DATE,
                TYPE_STRING, TYPE_STRING,
                TYPE_STRING, TYPE_STRING
	} ;

	public FrmStaffRequisition(){
	}
	public FrmStaffRequisition(StaffRequisition staffRequisition){
		this.staffRequisition = staffRequisition;
	}

	public FrmStaffRequisition(HttpServletRequest request, StaffRequisition staffRequisition){
		super(new FrmStaffRequisition(staffRequisition), request);
		this.staffRequisition = staffRequisition;
	}

	public String getFormName() { return FRM_NAME_STAFFREQUISITION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public StaffRequisition getEntityObject(){ return staffRequisition; }

	public void requestEntityObject(StaffRequisition staffRequisition) {
		try{
			this.requestParam();
                        String appDateStr = getString(FRM_FIELD_APPROVED_DATE_STR);
                        String ackDateStr = getString(FRM_FIELD_ACKNOWLEDGED_DATE_STR);
                        String reqDateStr = getString(FRM_FIELD_REQUESTED_DATE_STR);
                        String expComDateStr = getString(FRM_FIELD_EXP_COMM_DATE_STR);
                        
			staffRequisition.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
			staffRequisition.setSectionId(getLong(FRM_FIELD_SECTION_ID));
			staffRequisition.setPositionId(getLong(FRM_FIELD_POSITION_ID));
			staffRequisition.setEmpCategoryId(getLong(FRM_FIELD_EMP_CATEGORY_ID));
			staffRequisition.setRequisitionType(getInt(FRM_FIELD_REQUISITION_TYPE));
			staffRequisition.setNeededMale(getInt(FRM_FIELD_NEEDED_MALE));
			staffRequisition.setNeededFemale(getInt(FRM_FIELD_NEEDED_FEMALE));
			staffRequisition.setExpCommDate(expComDateStr.equals("") ? getDate(FRM_FIELD_EXP_COMM_DATE) : java.sql.Date.valueOf(expComDateStr));
			staffRequisition.setTempFor(getInt(FRM_FIELD_TEMP_FOR));
			staffRequisition.setApprovedBy(getLong(FRM_FIELD_APPROVED_BY));
			staffRequisition.setApprovedDate(appDateStr.equals("") ? getDate(FRM_FIELD_APPROVED_DATE) : java.sql.Date.valueOf(appDateStr));
			staffRequisition.setAcknowledgedBy(getLong(FRM_FIELD_ACKNOWLEDGED_BY));
			staffRequisition.setAcknowledgedDate(ackDateStr.equals("") ? getDate(FRM_FIELD_ACKNOWLEDGED_DATE) : java.sql.Date.valueOf(ackDateStr));
			staffRequisition.setRequestedBy(getLong(FRM_FIELD_REQUESTED_BY));
			staffRequisition.setRequestedDate(reqDateStr.equals("") ? getDate(FRM_FIELD_REQUESTED_DATE)  : java.sql.Date.valueOf(reqDateStr));
                        
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
