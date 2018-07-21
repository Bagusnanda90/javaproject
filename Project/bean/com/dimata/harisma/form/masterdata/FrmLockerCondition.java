/* 
 * Form Name  	:  FrmLockerCondition.java 
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

public class FrmLockerCondition extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private LockerCondition lockerCondition;

	public static final String FRM_NAME_LOCKERCONDITION		=  "FRM_NAME_LOCKERCONDITION" ;

	public static final int FRM_FIELD_CONDITION_ID			=  0 ;
	public static final int FRM_FIELD_CONDITION			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CONDITION_ID",  "FRM_FIELD_CONDITION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmLockerCondition(){
	}
	public FrmLockerCondition(LockerCondition lockerCondition){
		this.lockerCondition = lockerCondition;
	}

	public FrmLockerCondition(HttpServletRequest request, LockerCondition lockerCondition){
		super(new FrmLockerCondition(lockerCondition), request);
		this.lockerCondition = lockerCondition;
	}

	public String getFormName() { return FRM_NAME_LOCKERCONDITION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public LockerCondition getEntityObject(){ return lockerCondition; }

	public void requestEntityObject(LockerCondition lockerCondition) {
		try{
			this.requestParam();
			lockerCondition.setCondition(getString(FRM_FIELD_CONDITION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
