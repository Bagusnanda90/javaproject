/* 
 * Form Name  	:  FrmSrcCommentCardHeader.java 
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

public class FrmSrcCommentCardHeader extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcCommentCardHeader srcCommentCardHeader;

	public static final String FRM_NAME_SRCCOMMENTCARDHEADER		=  "FRM_NAME_SRCCOMMENTCARDHEADER" ;

	public static final int FRM_FIELD_NAME			=  0 ;
	public static final int FRM_FIELD_CARDDATEFROM			=  1 ;
	public static final int FRM_FIELD_CARDDATETO			=  2 ;
	public static final int FRM_FIELD_DEPARTMENT			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_NAME",  "FRM_FIELD_CARDDATEFROM",
		"FRM_FIELD_CARDDATETO",  "FRM_FIELD_DEPARTMENT"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_DATE,
		TYPE_DATE,  TYPE_LONG
	} ;

	public FrmSrcCommentCardHeader(){
	}
	public FrmSrcCommentCardHeader(SrcCommentCardHeader srcCommentCardHeader){
		this.srcCommentCardHeader = srcCommentCardHeader;
	}

	public FrmSrcCommentCardHeader(HttpServletRequest request, SrcCommentCardHeader srcCommentCardHeader){
		super(new FrmSrcCommentCardHeader(srcCommentCardHeader), request);
		this.srcCommentCardHeader = srcCommentCardHeader;
	}

	public String getFormName() { return FRM_NAME_SRCCOMMENTCARDHEADER; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcCommentCardHeader getEntityObject(){ return srcCommentCardHeader; }

	public void requestEntityObject(SrcCommentCardHeader srcCommentCardHeader) {
		try{
			this.requestParam();
			srcCommentCardHeader.setName(getString(FRM_FIELD_NAME));
			srcCommentCardHeader.setCarddatefrom(getDate(FRM_FIELD_CARDDATEFROM));
			srcCommentCardHeader.setCarddateto(getDate(FRM_FIELD_CARDDATETO));
			srcCommentCardHeader.setDepartment(getLong(FRM_FIELD_DEPARTMENT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
