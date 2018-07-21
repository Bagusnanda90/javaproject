/* 
 * Form Name  	:  FrmLeaveStock.java 
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

public class FrmLeaveStock extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private LeaveStock leaveStock;

	public static final String FRM_NAME_LEAVESTOCK		=  "FRM_NAME_LEAVESTOCK" ;

	public static final int FRM_FIELD_LEAVE_STOCK_ID			=  0 ;
	public static final int FRM_FIELD_LEAVE_PERIOD_ID			=  1 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  2 ;
	public static final int FRM_FIELD_AL_AMOUNT			=  3 ;
	public static final int FRM_FIELD_LL_AMOUNT			=  4 ;
	public static final int FRM_FIELD_DP_AMOUNT			=  5 ;
	public static final int FRM_FIELD_ADD_AL			=  6 ;
	public static final int FRM_FIELD_ADD_LL			=  7 ;
	public static final int FRM_FIELD_ADD_DP			=  8 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LEAVE_STOCK_ID",  "FRM_FIELD_LEAVE_PERIOD_ID",
		"FRM_FIELD_EMPLOYEE_ID",  "FRM_FIELD_AL_AMOUNT",
		"FRM_FIELD_LL_AMOUNT",  "FRM_FIELD_DP_AMOUNT",
		"FRM_FIELD_ADD_AL",  "FRM_FIELD_ADD_LL",
		"FRM_FIELD_ADD_DP"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG,
		TYPE_LONG,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT
	} ;

	public FrmLeaveStock(){
	}
	public FrmLeaveStock(LeaveStock leaveStock){
		this.leaveStock = leaveStock;
	}

	public FrmLeaveStock(HttpServletRequest request, LeaveStock leaveStock){
		super(new FrmLeaveStock(leaveStock), request);
		this.leaveStock = leaveStock;
	}

	public String getFormName() { return FRM_NAME_LEAVESTOCK; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public LeaveStock getEntityObject(){ return leaveStock; }

	public void requestEntityObject(LeaveStock leaveStock) {
		try{
			this.requestParam();
			leaveStock.setLeavePeriodId(getLong(FRM_FIELD_LEAVE_PERIOD_ID));
			leaveStock.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			leaveStock.setAlAmount(getInt(FRM_FIELD_AL_AMOUNT));
			leaveStock.setLlAmount(getInt(FRM_FIELD_LL_AMOUNT));
			leaveStock.setDpAmount(getInt(FRM_FIELD_DP_AMOUNT));
			leaveStock.setAddAl(getInt(FRM_FIELD_ADD_AL));
			leaveStock.setAddLl(getInt(FRM_FIELD_ADD_LL));
			leaveStock.setAddDp(getInt(FRM_FIELD_ADD_DP));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
