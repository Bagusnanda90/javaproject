/* 
 * Form Name  	:  FrmLocker.java 
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

package com.dimata.harisma.form.locker;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.locker.*;

public class FrmLocker extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Locker locker;

	public static final String FRM_NAME_LOCKER		=  "FRM_NAME_LOCKER" ;

	public static final int FRM_FIELD_LOCKER_ID			=  0 ;
	public static final int FRM_FIELD_LOCATION_ID			=  1 ;
	public static final int FRM_FIELD_LOCKER_NUMBER			=  2 ;
	public static final int FRM_FIELD_KEY_NUMBER			=  3 ;
	public static final int FRM_FIELD_SPARE_KEY			=  4 ;
	public static final int FRM_FIELD_CONDITION_ID			=  5 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LOCKER_ID",  "FRM_FIELD_LOCATION_ID",
		"FRM_FIELD_LOCKER_NUMBER",  "FRM_FIELD_KEY_NUMBER",
		"FRM_FIELD_SPARE_KEY",  "FRM_FIELD_CONDITION_ID"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING,
		TYPE_STRING,  TYPE_LONG
	} ;

	public FrmLocker(){
	}
	public FrmLocker(Locker locker){
		this.locker = locker;
	}

	public FrmLocker(HttpServletRequest request, Locker locker){
		super(new FrmLocker(locker), request);
		this.locker = locker;
	}

	public String getFormName() { return FRM_NAME_LOCKER; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Locker getEntityObject(){ return locker; }

	public void requestEntityObject(Locker locker) {
		try{
			this.requestParam();
			locker.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
			locker.setLockerNumber(getString(FRM_FIELD_LOCKER_NUMBER));
			locker.setKeyNumber(getString(FRM_FIELD_KEY_NUMBER));
			locker.setSpareKey(getString(FRM_FIELD_SPARE_KEY));
			locker.setConditionId(getLong(FRM_FIELD_CONDITION_ID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
