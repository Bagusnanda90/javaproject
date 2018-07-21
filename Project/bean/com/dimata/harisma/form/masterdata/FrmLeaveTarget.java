/* 
 * Form Name  	:  FrmLeaveTarget.java 
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

public class FrmLeaveTarget extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private LeaveTarget leaveTarget;

	public static final String FRM_NAME_LEAVE_TARGET		=  "FRM_LEAVE_TARGET" ;
        
        public static final  int FRM_FIELD_LEAVE_TARGET_ID      = 0;
	public static final  int FRM_FIELD_TARGET_DATE         = 1;
	public static final  int FRM_FIELD_NAME        = 2;
	public static final  int FRM_FIELD_DP_TARGET        = 3;
	public static final  int FRM_FIELD_AL_TARGET        = 4;
	public static final  int FRM_FIELD_LL_TARGET        = 5;
        
	public static final  String[] fieldNames = {
		"FRM_FIELD_LEAVE_TARGET_ID",
		"FRM_FIELD_DATE",
		"FRM_FIELD_NAME",
		"FRM_FIELD_DP_TARGET",
		"FRM_FIELD_AL_TARGET",
		"FRM_FIELD_LL_TARGET"
	 }; 

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_DATE + ENTRY_REQUIRED,
		TYPE_STRING,
		TYPE_FLOAT,
		TYPE_FLOAT,
		TYPE_FLOAT
	} ;

	public FrmLeaveTarget(){
	}
	public FrmLeaveTarget(LeaveTarget leaveTarget){
		this.leaveTarget = leaveTarget;
	}

	public FrmLeaveTarget(HttpServletRequest request, LeaveTarget leaveTarget){
		super(new FrmLeaveTarget(leaveTarget), request);
		this.leaveTarget = leaveTarget;
	}

	public String getFormName() { return FRM_NAME_LEAVE_TARGET; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public LeaveTarget getEntityObject(){ return leaveTarget; }

	public void requestEntityObject(LeaveTarget leaveTarget) {
		try{
			this.requestParam();
			leaveTarget.setTargetDate(getDate(FRM_FIELD_TARGET_DATE));
			leaveTarget.setName(getString(FRM_FIELD_NAME));
			leaveTarget.setDpTarget(getDouble(FRM_FIELD_DP_TARGET));
			leaveTarget.setAlTarget(getDouble(FRM_FIELD_AL_TARGET));
			leaveTarget.setLlTarget(getDouble(FRM_FIELD_LL_TARGET));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
