/* 
 * Form Name  	:  FrmDayOfPayment.java 
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

public class FrmDayOfPayment extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private DayOfPayment dayOfPayment;

	public static final String FRM_NAME_DAYOFPAYMENT	=  "FRM_NAME_DAYOFPAYMENT" ;

	public static final int FRM_FIELD_DAY_OF_PAYMENT_ID	=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID		=  1 ;
	public static final int FRM_FIELD_DURATION		=  2 ;
	public static final int FRM_FIELD_DP_FROM		=  3 ;
	public static final int FRM_FIELD_DP_TO			=  4 ;
	public static final int FRM_FIELD_APR_DEPTHEAD_DATE	=  5 ;
	public static final int FRM_FIELD_CONTACT_ADDRESS	=  6 ;
	public static final int FRM_FIELD_REMARKS		=  7 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DAY_OF_PAYMENT_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_DURATION",  "FRM_FIELD_DP_FROM",
		"FRM_FIELD_DP_TO",  "FRM_FIELD_APR_DEPTHEAD_DATE",
		"FRM_FIELD_CONTACT_ADDRESS",  "FRM_FIELD_REMARKS"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_INT + ENTRY_REQUIRED,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_DATE,
		TYPE_STRING,  TYPE_STRING
	} ;

	public FrmDayOfPayment(){
	}
	public FrmDayOfPayment(DayOfPayment dayOfPayment){
		this.dayOfPayment = dayOfPayment;
	}

	public FrmDayOfPayment(HttpServletRequest request, DayOfPayment dayOfPayment){
		super(new FrmDayOfPayment(dayOfPayment), request);
		this.dayOfPayment = dayOfPayment;
	}

	public String getFormName() { return FRM_NAME_DAYOFPAYMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public DayOfPayment getEntityObject(){ return dayOfPayment; }

	public void requestEntityObject(DayOfPayment dayOfPayment) {
		try{
			this.requestParam();
			dayOfPayment.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			dayOfPayment.setDuration(getInt(FRM_FIELD_DURATION));
			dayOfPayment.setDpFrom(getDate(FRM_FIELD_DP_FROM));
			dayOfPayment.setDpTo(getDate(FRM_FIELD_DP_TO));
			dayOfPayment.setAprDeptheadDate(getDate(FRM_FIELD_APR_DEPTHEAD_DATE));
			dayOfPayment.setContactAddress(getString(FRM_FIELD_CONTACT_ADDRESS));
			dayOfPayment.setRemarks(getString(FRM_FIELD_REMARKS));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
