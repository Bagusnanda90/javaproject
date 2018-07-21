/* 
 * Form Name  	:  FrmLeave.java 
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

package com.dimata.harisma.form.attendance;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.attendance.*;

public class FrmLeave extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Leave leave;

	public static final String FRM_NAME_LEAVE		=  "FRM_NAME_LEAVE" ;

	public static final int FRM_FIELD_LEAVE_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_SUBMIT_DATE			=  2 ;
	public static final int FRM_FIELD_LEAVE_FROM			=  3 ;
	public static final int FRM_FIELD_LEAVE_TO			=  4 ;
	public static final int FRM_FIELD_DURATION			=  5 ;
	public static final int FRM_FIELD_REASON			=  6 ;
	public static final int FRM_FIELD_LONG_LEAVE			=  7 ;
	public static final int FRM_FIELD_ANNUAL_LEAVE			=  8 ;
	public static final int FRM_FIELD_LEAVE_WO_PAY			=  9 ;
	public static final int FRM_FIELD_MATERNITY_LEAVE			=  10 ;
	public static final int FRM_FIELD_DAY_OFF			=  11 ;
	public static final int FRM_FIELD_PUBLIC_HOLIDAY			=  12 ;
	public static final int FRM_FIELD_EXTRA_DAY_OFF			=  13 ;
	public static final int FRM_FIELD_SICK_LEAVE			=  14 ;
	public static final int FRM_FIELD_PERIOD_AL_FROM			=  15 ;
	public static final int FRM_FIELD_PERIOD_AL_TO			=  16 ;
	public static final int FRM_FIELD_AL_ENTITLEMENT			=  17 ;
	public static final int FRM_FIELD_AL_TAKEN			=  18 ;
	public static final int FRM_FIELD_AL_BALANCE			=  19 ;
	public static final int FRM_FIELD_PERIOD_LL_FROM			=  20 ;
	public static final int FRM_FIELD_PERIOD_LL_TO			=  21 ;
	public static final int FRM_FIELD_LL_ENTITLEMENT			=  22 ;
	public static final int FRM_FIELD_LL_TAKEN			=  23 ;
	public static final int FRM_FIELD_LL_BALANCE			=  24 ;
	public static final int FRM_FIELD_APR_SPV_DATE			=  25 ;
	public static final int FRM_FIELD_APR_DEPTHEAD_DATE			=  26 ;
	public static final int FRM_FIELD_APR_PMGR_DATE			=  27 ;
    public static final int FRM_FIELD_LEAVE_TYPE			= 28;

	public static String[] fieldNames = {
		"FRM_FIELD_LEAVE_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_SUBMIT_DATE",  "FRM_FIELD_LEAVE_FROM",
		"FRM_FIELD_LEAVE_TO",  "FRM_FIELD_DURATION",
		"FRM_FIELD_REASON",  "FRM_FIELD_LONG_LEAVE",
		"FRM_FIELD_ANNUAL_LEAVE",  "FRM_FIELD_LEAVE_WO_PAY",
		"FRM_FIELD_MATERNITY_LEAVE",  "FRM_FIELD_DAY_OFF",
		"FRM_FIELD_PUBLIC_HOLIDAY",  "FRM_FIELD_EXTRA_DAY_OFF",
		"FRM_FIELD_SICK_LEAVE",  "FRM_FIELD_PERIOD_AL_FROM",
		"FRM_FIELD_PERIOD_AL_TO",  "FRM_FIELD_AL_ENTITLEMENT",
		"FRM_FIELD_AL_TAKEN",  "FRM_FIELD_AL_BALANCE",
		"FRM_FIELD_PERIOD_LL_FROM",  "FRM_FIELD_PERIOD_LL_TO",
		"FRM_FIELD_LL_ENTITLEMENT",  "FRM_FIELD_LL_TAKEN",
		"FRM_FIELD_LL_BALANCE",  "FRM_FIELD_APR_SPV_DATE",
		"FRM_FIELD_APR_DEPTHEAD_DATE",  "FRM_FIELD_APR_PMGR_DATE",
        "FRM_FIELD_LEAVE_TYPE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_INT + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_DATE,
		TYPE_DATE,  TYPE_DATE,
        TYPE_INT
	} ;

	public FrmLeave(){
	}
	public FrmLeave(Leave leave){
		this.leave = leave;
	}

	public FrmLeave(HttpServletRequest request, Leave leave){
		super(new FrmLeave(leave), request);
		this.leave = leave;
	}

	public String getFormName() { return FRM_NAME_LEAVE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Leave getEntityObject(){ return leave; }

	public void requestEntityObject(Leave leave) {
		try{
			this.requestParam();
			leave.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			leave.setSubmitDate(getDate(FRM_FIELD_SUBMIT_DATE));
			leave.setLeaveFrom(getDate(FRM_FIELD_LEAVE_FROM));
			leave.setLeaveTo(getDate(FRM_FIELD_LEAVE_TO));
			leave.setDuration(getInt(FRM_FIELD_DURATION));
			leave.setReason(getString(FRM_FIELD_REASON));
			leave.setLongLeave(getInt(FRM_FIELD_LONG_LEAVE));
			leave.setAnnualLeave(getInt(FRM_FIELD_ANNUAL_LEAVE));
			leave.setLeaveWoPay(getInt(FRM_FIELD_LEAVE_WO_PAY));
			leave.setMaternityLeave(getInt(FRM_FIELD_MATERNITY_LEAVE));
			leave.setDayOff(getInt(FRM_FIELD_DAY_OFF));
			leave.setPublicHoliday(getInt(FRM_FIELD_PUBLIC_HOLIDAY));
			leave.setExtraDayOff(getInt(FRM_FIELD_EXTRA_DAY_OFF));
			leave.setSickLeave(getInt(FRM_FIELD_SICK_LEAVE));
			leave.setPeriodAlFrom(getInt(FRM_FIELD_PERIOD_AL_FROM));
			leave.setPeriodAlTo(getInt(FRM_FIELD_PERIOD_AL_TO));
			leave.setAlEntitlement(getInt(FRM_FIELD_AL_ENTITLEMENT));
			leave.setAlTaken(getInt(FRM_FIELD_AL_TAKEN));
			leave.setAlBalance(getInt(FRM_FIELD_AL_BALANCE));
			leave.setPeriodLlFrom(getInt(FRM_FIELD_PERIOD_LL_FROM));
			leave.setPeriodLlTo(getInt(FRM_FIELD_PERIOD_LL_TO));
			leave.setLlEntitlement(getInt(FRM_FIELD_LL_ENTITLEMENT));
			leave.setLlTaken(getInt(FRM_FIELD_LL_TAKEN));
			leave.setLlBalance(getInt(FRM_FIELD_LL_BALANCE));
			leave.setAprSpvDate(getDate(FRM_FIELD_APR_SPV_DATE));
			leave.setAprDeptheadDate(getDate(FRM_FIELD_APR_DEPTHEAD_DATE));
			leave.setAprPmgrDate(getDate(FRM_FIELD_APR_PMGR_DATE));
            leave.setLeaveType(getInt(FRM_FIELD_LEAVE_TYPE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
