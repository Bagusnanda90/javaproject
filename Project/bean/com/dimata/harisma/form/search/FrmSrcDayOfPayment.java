/* 
 * Form Name  	:  FrmSrcDayOfPayment.java 
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

package com.dimata.harisma.form.search;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.search.*;

public class FrmSrcDayOfPayment extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcDayOfPayment srcDayOfPayment;

	public static final String FRM_NAME_SRCDAYOFPAYMENT		=  "FRM_NAME_SRCDAYOFPAYMENT" ;

	public static final int FRM_FIELD_EMP_NUMBER			=  0 ;
	public static final int FRM_FIELD_FULL_NAME			=  1 ;
	public static final int FRM_FIELD_DEPARTMENT			=  2 ;
	public static final int FRM_FIELD_SECTION			=  3 ;
	public static final int FRM_FIELD_POSITION			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMP_NUMBER",  
                "FRM_FIELD_FULL_NAME",
		"FRM_FIELD_DEPARTMENT",  
                "FRM_FIELD_SECTION",
		"FRM_FIELD_POSITION"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING
	} ;

	public FrmSrcDayOfPayment(){
	}
	public FrmSrcDayOfPayment(SrcDayOfPayment srcDayOfPayment){
		this.srcDayOfPayment = srcDayOfPayment;
	}

	public FrmSrcDayOfPayment(HttpServletRequest request, SrcDayOfPayment srcDayOfPayment){
		super(new FrmSrcDayOfPayment(srcDayOfPayment), request);
		this.srcDayOfPayment = srcDayOfPayment;
	}

	public String getFormName() { return FRM_NAME_SRCDAYOFPAYMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcDayOfPayment getEntityObject(){ return srcDayOfPayment; }

	public void requestEntityObject(SrcDayOfPayment srcDayOfPayment) {
		try{
			this.requestParam();
			srcDayOfPayment.setEmpNumber(getString(FRM_FIELD_EMP_NUMBER));
			srcDayOfPayment.setFullName(getString(FRM_FIELD_FULL_NAME));
			srcDayOfPayment.setDepartment(getString(FRM_FIELD_DEPARTMENT));
			srcDayOfPayment.setSection(getString(FRM_FIELD_SECTION));
			srcDayOfPayment.setPosition(getString(FRM_FIELD_POSITION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
