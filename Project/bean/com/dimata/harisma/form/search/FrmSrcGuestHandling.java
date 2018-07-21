/* 
 * Form Name  	:  FrmSrcGuestHandling.java 
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

public class FrmSrcGuestHandling extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcGuestHandling srcGuestHandling;

	public static final String FRM_NAME_SRCGUESTHANDLING		=  "FRM_NAME_SRCGUESTHANDLING" ;

	public static final int FRM_FIELD_SRC_GUEST_NAME			=  0 ;
	public static final int FRM_FIELD_SRC_DIAGNOSIS			=  1 ;
	public static final int FRM_FIELD_DATE_FROM			=  2 ;
	public static final int FRM_FIELD_DATE_TO			=  3 ;
	public static final int FRM_FIELD_SORT_BY			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_SRC_GUEST_NAME",  "FRM_FIELD_SRC_DIAGNOSIS",
		"FRM_FIELD_DATE_FROM",  "FRM_FIELD_DATE_TO",
		"FRM_FIELD_SORT_BY"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_DATE,  TYPE_DATE,
		TYPE_STRING
	} ;

	public FrmSrcGuestHandling(){
	}
	public FrmSrcGuestHandling(SrcGuestHandling srcGuestHandling){
		this.srcGuestHandling = srcGuestHandling;
	}

	public FrmSrcGuestHandling(HttpServletRequest request, SrcGuestHandling srcGuestHandling){
		super(new FrmSrcGuestHandling(srcGuestHandling), request);
		this.srcGuestHandling = srcGuestHandling;
	}

	public String getFormName() { return FRM_NAME_SRCGUESTHANDLING; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcGuestHandling getEntityObject(){ return srcGuestHandling; }

	public void requestEntityObject(SrcGuestHandling srcGuestHandling) {
		try{
			this.requestParam();
			srcGuestHandling.setGuestName(getString(FRM_FIELD_SRC_GUEST_NAME));
			srcGuestHandling.setDiagnosis(getString(FRM_FIELD_SRC_DIAGNOSIS));
			srcGuestHandling.setDateFrom(getDate(FRM_FIELD_DATE_FROM));
			srcGuestHandling.setDateTo(getDate(FRM_FIELD_DATE_TO));
			srcGuestHandling.setSortBy(getString(FRM_FIELD_SORT_BY));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
