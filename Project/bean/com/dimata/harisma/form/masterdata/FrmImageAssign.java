/* 
 * Form Name  	:  FrmImageAssign.java 
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

public class FrmImageAssign extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ImageAssign imageAssign;

	public static final String FRM_IMAGE_ASSIGN		=  "FRM_IMAGE_ASSIGN" ;

	public static final int FRM_FIELD_IMG_ASSIGN_ID		=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID		=  1 ;
	public static final int FRM_FIELD_PATH		=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_IMG_ASSIGN_ID",  
                "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_PATH"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_LONG+ENTRY_REQUIRED,
		TYPE_STRING
	} ;

	public FrmImageAssign(){
	}
	public FrmImageAssign(ImageAssign imageAssign){
		this.imageAssign = imageAssign;
	}

	public FrmImageAssign(HttpServletRequest request, ImageAssign imageAssign){
		super(new FrmImageAssign(imageAssign), request);
		this.imageAssign = imageAssign;
	}

	public String getFormName() { return FRM_IMAGE_ASSIGN; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ImageAssign getEntityObject(){ return imageAssign; }

	public void requestEntityObject(ImageAssign imageAssign) {
		try{
			this.requestParam();
			imageAssign.setEmployeeOid(getLong(FRM_FIELD_EMPLOYEE_ID));
			imageAssign.setPath(getString(FRM_FIELD_PATH));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
