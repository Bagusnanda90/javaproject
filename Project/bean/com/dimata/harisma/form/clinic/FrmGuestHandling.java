/* 
 * Form Name  	:  FrmGuestHandling.java 
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

package com.dimata.harisma.form.clinic;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.clinic.*;

public class FrmGuestHandling extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private GuestHandling guestHandling;

	public static final String FRM_NAME_GUESTHANDLING		=  "FRM_NAME_GUESTHANDLING" ;

	public static final int FRM_FIELD_GUEST_CLINIC_ID			=  0 ;
	public static final int FRM_FIELD_DATE			=  1 ;
	public static final int FRM_FIELD_GUEST_NAME			=  2 ;
	public static final int FRM_FIELD_ROOM			=  3 ;
	public static final int FRM_FIELD_DIAGNOSIS			=  4 ;
	public static final int FRM_FIELD_TREATMENT			=  5 ;
	public static final int FRM_FIELD_DESCRIPTION			=  6 ;

	public static String[] fieldNames = {
		"FRM_FIELD_GUEST_CLINIC_ID",  "FRM_FIELD_DATE",
		"FRM_FIELD_GUEST_NAME",  "FRM_FIELD_ROOM",
		"FRM_FIELD_DIAGNOSIS",  "FRM_FIELD_TREATMENT",
		"FRM_FIELD_DESCRIPTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_DATE + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING
	} ;

	public FrmGuestHandling(){
	}
	public FrmGuestHandling(GuestHandling guestHandling){
		this.guestHandling = guestHandling;
	}

	public FrmGuestHandling(HttpServletRequest request, GuestHandling guestHandling){
		super(new FrmGuestHandling(guestHandling), request);
		this.guestHandling = guestHandling;
	}

	public String getFormName() { return FRM_NAME_GUESTHANDLING; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public GuestHandling getEntityObject(){ return guestHandling; }

	public void requestEntityObject(GuestHandling guestHandling) {
		try{
			this.requestParam();
			guestHandling.setDate(getDate(FRM_FIELD_DATE));
			guestHandling.setGuestName(getString(FRM_FIELD_GUEST_NAME));
			guestHandling.setRoom(getString(FRM_FIELD_ROOM));
			guestHandling.setDiagnosis(getString(FRM_FIELD_DIAGNOSIS));
			guestHandling.setTreatment(getString(FRM_FIELD_TREATMENT));
			guestHandling.setDescription(getString(FRM_FIELD_DESCRIPTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
