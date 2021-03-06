/* 
 * Form Name  	:  FrmContactClass.java 
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

package com.dimata.common.form.contact;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*; 
/* project package */
import com.dimata.common.entity.contact.*;

public class FrmContactClass extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ContactClass contactClass;

	public static final String FRM_NAME_CONTACTCLASS		=  "FRM_NAME_CONTACTCLASS" ;

	public static final int FRM_FIELD_CONTACT_CLASS_ID			=  0 ;
	public static final int FRM_FIELD_CLASS_NAME			=  1 ;
	public static final int FRM_FIELD_CLASS_DESCRIPTION			=  2 ;
    public static final int FRM_FIELD_CLASS_TYPE			=  3 ;
     public static final int FRM_FIELD_SERIES_NUMBER	 		=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CONTACT_CLASS_ID",  "FRM_FIELD_CLASS_NAME",
		"FRM_FIELD_CLASS_DESCRIPTION","FRM_FIELD_CLASS_TYPE",
                "FRM_FIELD_SERIES_NUMBER"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING,TYPE_INT,TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmContactClass(){
	}
	public FrmContactClass(ContactClass contactClass){
		this.contactClass = contactClass;
	}

	public FrmContactClass(HttpServletRequest request, ContactClass contactClass){
		super(new FrmContactClass(contactClass), request);
		this.contactClass = contactClass;
	}

	public String getFormName() { return FRM_NAME_CONTACTCLASS; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ContactClass getEntityObject(){ return contactClass; }

	public void requestEntityObject(ContactClass contactClass) {
		try{
			this.requestParam();
			contactClass.setClassName(getString(FRM_FIELD_CLASS_NAME));
			contactClass.setClassDescription(getString(FRM_FIELD_CLASS_DESCRIPTION));
                        contactClass.setClassType(getInt(FRM_FIELD_CLASS_TYPE));
                        contactClass.setSeriesNumber(getString(FRM_FIELD_SERIES_NUMBER));
                        
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
