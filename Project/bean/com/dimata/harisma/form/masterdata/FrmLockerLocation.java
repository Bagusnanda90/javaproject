/* 
 * Form Name  	:  FrmLockerLocation.java 
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

public class FrmLockerLocation extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private LockerLocation lockerLocation;

	public static final String FRM_NAME_LOCKERLOCATION		=  "FRM_NAME_LOCKERLOCATION" ;

	public static final int FRM_FIELD_LOCATION_ID			=  0 ;
	public static final int FRM_FIELD_LOCATION			=  1 ;
	public static final int FRM_FIELD_SEX			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LOCATION_ID",  "FRM_FIELD_LOCATION",
		"FRM_FIELD_SEX"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING
	} ;

	public FrmLockerLocation(){
	}
	public FrmLockerLocation(LockerLocation lockerLocation){
		this.lockerLocation = lockerLocation;
	}

	public FrmLockerLocation(HttpServletRequest request, LockerLocation lockerLocation){
		super(new FrmLockerLocation(lockerLocation), request);
		this.lockerLocation = lockerLocation;
	}

	public String getFormName() { return FRM_NAME_LOCKERLOCATION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public LockerLocation getEntityObject(){ return lockerLocation; }

	public void requestEntityObject(LockerLocation lockerLocation) {
		try{
			this.requestParam();
			lockerLocation.setLocation(getString(FRM_FIELD_LOCATION));
			lockerLocation.setSex(getString(FRM_FIELD_SEX));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
