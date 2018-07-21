/* 
 * Form Name  	:  FrmLeavePeriode.java 
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

public class FrmLeavePeriod extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private LeavePeriod leavePeriod;

	public static final String FRM_NAME_LEAVEPERIODE		=  "FRM_NAME_LEAVEPERIODE" ;

	public static final int FRM_FIELD_LEAVE_PERIOD_ID			=  0 ;
	public static final int FRM_FIELD_START_DATE			=  1 ;
	public static final int FRM_FIELD_END_DATE			=  2 ;
	public static final int FRM_FIELD_STATUS			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LEAVE_PERIOD_ID",  "FRM_FIELD_START_DATE",
		"FRM_FIELD_END_DATE",  "FRM_FIELD_STATUS"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_DATE,
		TYPE_DATE,  TYPE_BOOL
	} ;

	public FrmLeavePeriod(){
	}
	public FrmLeavePeriod(LeavePeriod leavePeriod){
		this.leavePeriod = leavePeriod;
	}

	public FrmLeavePeriod(HttpServletRequest request, LeavePeriod leavePeriod){
		super(new FrmLeavePeriod(leavePeriod), request);
		this.leavePeriod = leavePeriod;
	}

	public String getFormName() { return FRM_NAME_LEAVEPERIODE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public LeavePeriod getEntityObject(){ return leavePeriod; }

	public void requestEntityObject(LeavePeriod leavePeriod) {
		try{
			this.requestParam();
			leavePeriod.setStartDate(getDate(FRM_FIELD_START_DATE));
			leavePeriod.setEndDate(getDate(FRM_FIELD_END_DATE));
			leavePeriod.setStatus(getBoolean(FRM_FIELD_STATUS));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
