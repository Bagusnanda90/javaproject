/* 
 * Form Name  	:  FrmOutletStart.java 
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

package com.dimata.harisma.form.startdata;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.startdata.*;

public class FrmOutletStart extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private OutletStart outletStart;

	public static final String FRM_NAME_OUTLETSTART		=  "FRM_NAME_OUTLETSTART" ;

	public static final int FRM_FIELD_ID			=  0 ;
	public static final int FRM_FIELD_DEP			=  1 ;
	public static final int FRM_FIELD_DEP_NAME			=  2 ;
	public static final int FRM_FIELD_LOCATION			=  3 ;
	public static final int FRM_FIELD_LOC_NAME			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_ID",  "FRM_FIELD_DEP",
		"FRM_FIELD_DEP_NAME",  "FRM_FIELD_LOCATION",
		"FRM_FIELD_LOC_NAME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING
	} ;

	public FrmOutletStart(){
	}
	public FrmOutletStart(OutletStart outletStart){
		this.outletStart = outletStart;
	}

	public FrmOutletStart(HttpServletRequest request, OutletStart outletStart){
		super(new FrmOutletStart(outletStart), request);
		this.outletStart = outletStart;
	}

	public String getFormName() { return FRM_NAME_OUTLETSTART; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public OutletStart getEntityObject(){ return outletStart; }

	public void requestEntityObject(OutletStart outletStart) {
		try{
			this.requestParam();
			outletStart.setDep(getString(FRM_FIELD_DEP));
			outletStart.setDepName(getString(FRM_FIELD_DEP_NAME));
			outletStart.setLocation(getString(FRM_FIELD_LOCATION));
			outletStart.setLocName(getString(FRM_FIELD_LOC_NAME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
