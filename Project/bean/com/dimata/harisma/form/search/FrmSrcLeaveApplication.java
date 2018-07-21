/*
 * FrmSrcLeaveApplicationApplication.java
 *
 * Created on October 22, 2004, 4:15 PM
 */

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

/**
 *
 * @author  gedhy
 */
public class FrmSrcLeaveApplication  extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcLeaveApplication srcLeaveApplication;
  
	public static final String FRM_NAME_SRCLEAVE_APPLICATION =  "FRM_NAME_SRCLEAVE_APPLICATION" ;

	public static final int FRM_FIELD_EMP_NUMBER        =  0 ;
	public static final int FRM_FIELD_FULLNAME          =  1 ;
	public static final int FRM_FIELD_DEPARTMENT        =  2 ;
	public static final int FRM_FIELD_SECTION           =  3 ;
	public static final int FRM_FIELD_POSITION          =  4 ;
        public static final int FRM_FIELD_SUBMISSION        =  5 ;
        public static final int FRM_FIELD_SUBMISSION_DATE   =  6 ;
        public static final int FRM_FIELD_TAKEN             =  7 ;
        public static final int FRM_FIELD_TAKEN_DATE        =  8 ;
        public static final int FRM_FIELD_DOC_STATUS        =  9 ;
        public static final int FRM_FIELD_APPROVAL_STATUS        =  10 ;

	public static String[] fieldNames = 
        {
		"FRM_FIELD_EMP_NUMBER",  
                "FRM_FIELD_FULLNAME",
		"FRM_FIELD_DEPARTMENT",  
                "FRM_FIELD_SECTION",
		"FRM_FIELD_POSITION",
                "FRM_FIELD_SUBMISSION",
                "FRM_FIELD_SUBMISSION_DATE",
                "FRM_FIELD_TAKEN",
                "FRM_FIELD_TAKEN_DATE",
                "FRM_FIELD_DOC_STATUS",
                "FRM_FIELD_APPROVAL_STATUS"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  
                TYPE_STRING,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,
                TYPE_INT,
                TYPE_DATE,
                TYPE_INT,
                TYPE_DATE,
                TYPE_INT,
                TYPE_INT
	} ;

	public FrmSrcLeaveApplication()
        {
	}
        
	public FrmSrcLeaveApplication(SrcLeaveApplication srcLeaveApplication)
        {
		this.srcLeaveApplication = srcLeaveApplication;
	}

	public FrmSrcLeaveApplication(HttpServletRequest request, SrcLeaveApplication srcLeaveApplication){
		super(new FrmSrcLeaveApplication(srcLeaveApplication), request);
		this.srcLeaveApplication = srcLeaveApplication;
	}

	public String getFormName() { return FRM_NAME_SRCLEAVE_APPLICATION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcLeaveApplication getEntityObject(){ return srcLeaveApplication; }

	public void requestEntityObject(SrcLeaveApplication srcLeaveApplication) 
        {
		try
                {
			this.requestParam();
			srcLeaveApplication.setEmpNum(getString(FRM_FIELD_EMP_NUMBER));
			srcLeaveApplication.setFullName(getString(FRM_FIELD_FULLNAME));
			srcLeaveApplication.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT));
			srcLeaveApplication.setSectionId(getLong(FRM_FIELD_SECTION));
			srcLeaveApplication.setPositionId(getLong(FRM_FIELD_POSITION));
                        srcLeaveApplication.setSubmission(getBoolean(FRM_FIELD_SUBMISSION));
                        srcLeaveApplication.setSubmissionDate(getDate(FRM_FIELD_SUBMISSION_DATE));
                        srcLeaveApplication.setTaken(getBoolean(FRM_FIELD_TAKEN));
                        srcLeaveApplication.setTakenDate(getDate(FRM_FIELD_TAKEN_DATE));
                        srcLeaveApplication.setStatus(getInt(FRM_FIELD_DOC_STATUS));
                        srcLeaveApplication.setApprovalStatus(getInt(FRM_FIELD_APPROVAL_STATUS));
		}
                catch(Exception e)
                {
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}    
}