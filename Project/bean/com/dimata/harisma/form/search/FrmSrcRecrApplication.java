/* 
 * Form Name  	:  FrmSrcRecrApplication.java 
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

public class FrmSrcRecrApplication extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcRecrApplication srcRecrApplication;

	public static final String FRM_NAME_SRCRECRAPPLICATION	=  "FRM_NAME_SRCRECRAPPLICATION" ;

	public static final int FRM_FIELD_NAME			=  0 ;
	public static final int FRM_FIELD_POSITION		=  1 ;
	public static final int FRM_FIELD_APPLDATE_FROM		=  2 ;
	public static final int FRM_FIELD_APPLDATE_TO		=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_NAME",  "FRM_FIELD_POSITION",
		"FRM_FIELD_APPLDATE_FROM", "FRM_FIELD_APPLDATE_TO"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_DATE, TYPE_DATE
	} ;

	public FrmSrcRecrApplication(){
	}
	public FrmSrcRecrApplication(SrcRecrApplication srcRecrApplication){
		this.srcRecrApplication = srcRecrApplication;
	}

	public FrmSrcRecrApplication(HttpServletRequest request, SrcRecrApplication srcRecrApplication){
		super(new FrmSrcRecrApplication(srcRecrApplication), request);
		this.srcRecrApplication = srcRecrApplication;
	}

	public String getFormName() { return FRM_NAME_SRCRECRAPPLICATION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcRecrApplication getEntityObject(){ return srcRecrApplication; }

	public void requestEntityObject(SrcRecrApplication srcRecrApplication) {
		try{
			this.requestParam();
			srcRecrApplication.setName(getString(FRM_FIELD_NAME));
			srcRecrApplication.setPosition(getString(FRM_FIELD_POSITION));
			srcRecrApplication.setAppldateFrom(getDate(FRM_FIELD_APPLDATE_FROM));
                        srcRecrApplication.setAppldateTo(getDate(FRM_FIELD_APPLDATE_TO));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
