/* 
 * Form Name  	:  FrmSrcStaffRequisition.java 
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

public class FrmSrcStaffRequisition extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcStaffRequisition srcStaffRequisition;

	public static final String FRM_NAME_SRCSTAFFREQUISITION		=  "FRM_NAME_SRCSTAFFREQUISITION" ;

	public static final int FRM_FIELD_REQTYPE			=  0 ;
	public static final int FRM_FIELD_DEPARTMENT			=  1 ;
	public static final int FRM_FIELD_SECTION			=  2 ;
	public static final int FRM_FIELD_POSITION			=  3 ;
	public static final int FRM_FIELD_STATUS			=  4 ;
	public static final int FRM_FIELD_REQDATE_FROM			=  5 ;
        public static final int FRM_FIELD_REQDATE_TO			=  6 ;

	public static String[] fieldNames = {
		"FRM_FIELD_REQTYPE",  "FRM_FIELD_DEPARTMENT",
		"FRM_FIELD_SECTION",  "FRM_FIELD_POSITION",
		"FRM_FIELD_STATUS",  "FRM_FIELD_REQDATE_FROM",  
                "FRM_FIELD_REQDATE_TO"
	} ;

	public static int[] fieldTypes = {
		TYPE_INT,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_DATE,  
                TYPE_DATE
	} ;

	public FrmSrcStaffRequisition(){
	}
	public FrmSrcStaffRequisition(SrcStaffRequisition srcStaffRequisition){
		this.srcStaffRequisition = srcStaffRequisition;
	}

	public FrmSrcStaffRequisition(HttpServletRequest request, SrcStaffRequisition srcStaffRequisition){
		super(new FrmSrcStaffRequisition(srcStaffRequisition), request);
		this.srcStaffRequisition = srcStaffRequisition;
	}

	public String getFormName() { return FRM_NAME_SRCSTAFFREQUISITION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcStaffRequisition getEntityObject(){ return srcStaffRequisition; }

	public void requestEntityObject(SrcStaffRequisition srcStaffRequisition) {
		try{
			this.requestParam();
			srcStaffRequisition.setReqtype(getInt(FRM_FIELD_REQTYPE));
			srcStaffRequisition.setDepartment(getLong(FRM_FIELD_DEPARTMENT));
			srcStaffRequisition.setSection(getLong(FRM_FIELD_SECTION));
			srcStaffRequisition.setPosition(getLong(FRM_FIELD_POSITION));
			srcStaffRequisition.setStatus(getLong(FRM_FIELD_STATUS));
			srcStaffRequisition.setReqdateFrom(getDate(FRM_FIELD_REQDATE_FROM));
                        srcStaffRequisition.setReqdateTo(getDate(FRM_FIELD_REQDATE_TO));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
