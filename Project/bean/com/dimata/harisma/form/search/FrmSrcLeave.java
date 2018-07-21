/* 
 * Form Name  	:  FrmSrcLeave.java 
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

public class FrmSrcLeave extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcLeave srcLeave;

	public static final String FRM_NAME_SRCLEAVE		=  "FRM_NAME_SRCLEAVE" ;

	public static final int FRM_FIELD_EMP_NUMBER			=  0 ;
	public static final int FRM_FIELD_FULLNAME			=  1 ;
	public static final int FRM_FIELD_DEPARTMENT			=  2 ;
	public static final int FRM_FIELD_SECTION			=  3 ;
	public static final int FRM_FIELD_POSITION			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMP_NUMBER",  "FRM_FIELD_FULLNAME",
		"FRM_FIELD_DEPARTMENT",  "FRM_FIELD_SECTION",
		"FRM_FIELD_POSITION"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING
	} ;

	public FrmSrcLeave(){
	}
	public FrmSrcLeave(SrcLeave srcLeave){
		this.srcLeave = srcLeave;
	}

	public FrmSrcLeave(HttpServletRequest request, SrcLeave srcLeave){
		super(new FrmSrcLeave(srcLeave), request);
		this.srcLeave = srcLeave;
	}

	public String getFormName() { return FRM_NAME_SRCLEAVE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcLeave getEntityObject(){ return srcLeave; }

	public void requestEntityObject(SrcLeave srcLeave) {
		try{
			this.requestParam();
			srcLeave.setEmpNumber(getString(FRM_FIELD_EMP_NUMBER));
			srcLeave.setFullName(getString(FRM_FIELD_FULLNAME));
			srcLeave.setDepartment(getString(FRM_FIELD_DEPARTMENT));
			srcLeave.setSection(getString(FRM_FIELD_SECTION));
			srcLeave.setPosition(getString(FRM_FIELD_POSITION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
