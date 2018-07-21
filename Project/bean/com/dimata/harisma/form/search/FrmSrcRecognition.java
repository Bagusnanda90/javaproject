/* 
 * Form Name  	:  FrmSrcRecognition.java 
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

public class FrmSrcRecognition extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcRecognition srcRecognition;

	public static final String FRM_NAME_SRCRECOGNITION		=  "FRM_NAME_SRCRECOGNITION" ;

	public static final int FRM_FIELD_FULL_NAME			=  0 ;
	public static final int FRM_FIELD_EMP_NUMBER			=  1 ;
	public static final int FRM_FIELD_RECOG_DATE_FROM		=  2 ;
	public static final int FRM_FIELD_RECOG_DATE_TO 		=  3 ;
	public static final int FRM_FIELD_DEPARTMENT			=  4 ;
	public static final int FRM_FIELD_POSITION			=  5 ;

	public static String[] fieldNames = {
		"FRM_FIELD_FULL_NAME",  "FRM_FIELD_EMP_NUMBER",
		"FRM_FIELD_RECOG_DATE_FROM",  "FRM_FIELD_RECOG_DATE_TO", 
                "FRM_FIELD_DEPARTMENT", "FRM_FIELD_POSITION"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_DATE,  TYPE_DATE,  TYPE_LONG,
		TYPE_LONG
	} ;

	public FrmSrcRecognition(){
	}
	public FrmSrcRecognition(SrcRecognition srcRecognition){
		this.srcRecognition = srcRecognition;
	}

	public FrmSrcRecognition(HttpServletRequest request, SrcRecognition srcRecognition){
		super(new FrmSrcRecognition(srcRecognition), request);
		this.srcRecognition = srcRecognition;
	}

	public String getFormName() { return FRM_NAME_SRCRECOGNITION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcRecognition getEntityObject(){ return srcRecognition; }

	public void requestEntityObject(SrcRecognition srcRecognition) {
		try{
			this.requestParam();
			srcRecognition.setFullName(getString(FRM_FIELD_FULL_NAME));
			srcRecognition.setEmpNumber(getString(FRM_FIELD_EMP_NUMBER));
			srcRecognition.setRecogDateFrom(getDate(FRM_FIELD_RECOG_DATE_FROM));
                        srcRecognition.setRecogDateTo(getDate(FRM_FIELD_RECOG_DATE_TO));
			srcRecognition.setDepartment(getLong(FRM_FIELD_DEPARTMENT));
			srcRecognition.setPosition(getLong(FRM_FIELD_POSITION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
