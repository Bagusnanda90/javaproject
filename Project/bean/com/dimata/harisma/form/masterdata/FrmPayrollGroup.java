/* 
 * Form Name  	:  FrmPayrollGroup.java 
 * Created on 	:  27-07-2015 AM/PM 
 * 
 * @author  	: Priska
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

public class FrmPayrollGroup extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private PayrollGroup payrollGroup;

	public static final String FRM_PAYROLL_GROUP_NAME	 =  "FRM_NAME_POSITION" ;

	public static final int FRM_FIELD_PAYROLL_GROUP_ID       =  0 ;
	public static final int FRM_FIELD_PAYROLL_GROUP_NAME     =  1 ;
	public static final int FRM_FIELD_DESCRIPTION            =  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_PAYROLL_GROUP_ID",  
                "FRM_FIELD_PAYROLL_GROUP_NAME",
		"FRM_FIELD_DESCRIPTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING
	};

	public FrmPayrollGroup(){
	}
	public FrmPayrollGroup(PayrollGroup payrollGroup){
		this.payrollGroup = payrollGroup;
	}

	public FrmPayrollGroup(HttpServletRequest request, PayrollGroup payrollGroup){
		super(new FrmPayrollGroup(payrollGroup), request);
		this.payrollGroup = payrollGroup;
	}

	public String getFormName() { return FRM_PAYROLL_GROUP_NAME; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public PayrollGroup getEntityObject(){ return payrollGroup; }

	public void requestEntityObject(PayrollGroup payrollGroup) {
		try{
			this.requestParam();
                        
			payrollGroup.setOID(getLong(FRM_FIELD_PAYROLL_GROUP_ID));
			payrollGroup.setPayrollGroupName(getString(FRM_FIELD_PAYROLL_GROUP_NAME));
			payrollGroup.setDescription(getString(FRM_FIELD_DESCRIPTION));
                }catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
