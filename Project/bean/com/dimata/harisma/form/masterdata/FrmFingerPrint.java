/* 
 * Form Name  	:  FrmFingerPrint.java 
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

public class FrmFingerPrint extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private FingerPrint fingerPrint;

	public static final String FRM_NAME_FINGER_PRINT		=  "FRM_NAME_FINGER_PRINT" ;

        public static final int FRM_FIELD_FINGER_PRINT_ID		=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_NUM			=  1 ;
	public static final int FRM_FIELD_FINGER_PRINT			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_FINGER_PRINT_ID",  
                "FRM_FIELD_EMPLOYEE_NUM",
		"FRM_FIELD_FINGER_PRINT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_STRING + ENTRY_REQUIRED,
		TYPE_INT + ENTRY_REQUIRED
	} ;

	public FrmFingerPrint(){
	}
	public FrmFingerPrint(FingerPrint fingerPrint){
		this.fingerPrint = fingerPrint;
	}

	public FrmFingerPrint(HttpServletRequest request, FingerPrint fingerPrint){
		super(new FrmFingerPrint(fingerPrint), request);
		this.fingerPrint = fingerPrint;
	}

	public String getFormName() { return FRM_NAME_FINGER_PRINT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public FingerPrint getEntityObject(){ return fingerPrint; }

	public void requestEntityObject(FingerPrint fingerPrint) {
		try{
			this.requestParam();
			fingerPrint.setEmployeeNum(getString(FRM_FIELD_EMPLOYEE_NUM));
			fingerPrint.setFingerPrint(getInt(FRM_FIELD_FINGER_PRINT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
