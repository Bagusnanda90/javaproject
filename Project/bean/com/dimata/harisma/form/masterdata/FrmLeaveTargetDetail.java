/* 
 * Form Name  	:  FrmLeaveTargetDetail.java 
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

package com.dimata.harisma.form.masterdata;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmLeaveTargetDetail extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private LeaveTargetDetail leaveTargetDetail;

	public static final String FRM_NAME_LEAVE_TARGET_DETAIL		=  "FRM_NAME_LEAVE_TARGET_DETAIL" ;

        public static final  int FRM_FIELD_LEAVE_TARGET_DETAIL_ID      = 0;
	public static final  int FRM_FIELD_LEAVE_TARGET_ID         = 1;
	public static final  int FRM_FIELD_TARGET_DP        = 2;
	public static final  int FRM_FIELD_TARGET_AL        = 3;
	public static final  int FRM_FIELD_TARGET_LL        = 4;
	public static final  int FRM_FIELD_DEPARTMENT_ID        = 5;

	public static final  String[] fieldNames = {
		"FRM_FIELD_LEAVE_TARGET_DETAIL_ID",
		"FRM_FIELD_LEAVE_TARGET_ID",
		"FRM_FIELD_TARGET_DP",
		"FRM_FIELD_TARGET_AL",
		"FRM_FIELD_TARGET_LL",
		"FRM_FIELD_DEPARTMENT_ID"
	 }; 
	public static int[] fieldTypes = {
		TYPE_LONG,  
		TYPE_LONG,  
                TYPE_FLOAT + ENTRY_REQUIRED,
                TYPE_FLOAT + ENTRY_REQUIRED,
                TYPE_FLOAT + ENTRY_REQUIRED,
		TYPE_LONG
	} ;

	public FrmLeaveTargetDetail(){
	}
	public FrmLeaveTargetDetail(LeaveTargetDetail leaveTargetDetail){
		this.leaveTargetDetail = leaveTargetDetail;
	}

	public FrmLeaveTargetDetail(HttpServletRequest request, LeaveTargetDetail leaveTargetDetail){
		super(new FrmLeaveTargetDetail(leaveTargetDetail), request);
		this.leaveTargetDetail = leaveTargetDetail;
	}

	public String getFormName() { return FRM_NAME_LEAVE_TARGET_DETAIL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public LeaveTargetDetail getEntityObject(){ return leaveTargetDetail; }

	public void requestEntityObject(LeaveTargetDetail leaveTargetDetail) {
		try{
			this.requestParam();
			leaveTargetDetail.setLeaveTargetId(getLong(FRM_FIELD_LEAVE_TARGET_ID));
			leaveTargetDetail.setTargetDP(getFloat(FRM_FIELD_TARGET_DP));
			leaveTargetDetail.setTargetAL(getFloat(FRM_FIELD_TARGET_AL));
			leaveTargetDetail.setTargetLL(getFloat(FRM_FIELD_TARGET_LL));
			leaveTargetDetail.setDeparmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
