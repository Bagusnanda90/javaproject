/* 
 * Form Name  	:  FrmLockerTreatment.java 
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

public class FrmLockerTreatment extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private LockerTreatment lockerTreatment;

	public static final String FRM_NAME_LOCKERTREATMENT		=  "FRM_NAME_LOCKERTREATMENT" ;

	public static final int FRM_FIELD_TREATMENT_ID			=  0 ;
	public static final int FRM_FIELD_LOCATION_ID			=  1 ;
	public static final int FRM_FIELD_TREATMENT_DATE			=  2 ;
	public static final int FRM_FIELD_TREATMENT			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_TREATMENT_ID",  "FRM_FIELD_LOCATION_ID",
		"FRM_FIELD_TREATMENT_DATE",  "FRM_FIELD_TREATMENT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmLockerTreatment(){
	}
	public FrmLockerTreatment(LockerTreatment lockerTreatment){
		this.lockerTreatment = lockerTreatment;
	}

	public FrmLockerTreatment(HttpServletRequest request, LockerTreatment lockerTreatment){
		super(new FrmLockerTreatment(lockerTreatment), request);
		this.lockerTreatment = lockerTreatment;
	}

	public String getFormName() { return FRM_NAME_LOCKERTREATMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public LockerTreatment getEntityObject(){ return lockerTreatment; }

	public void requestEntityObject(LockerTreatment lockerTreatment) {
		try{
			this.requestParam();
			lockerTreatment.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
			lockerTreatment.setTreatmentDate(getDate(FRM_FIELD_TREATMENT_DATE));
			lockerTreatment.setTreatment(getString(FRM_FIELD_TREATMENT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
