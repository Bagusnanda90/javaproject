/* 
 * Form Name  	:  FrmOriChecklist.java 
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

package com.dimata.harisma.form.recruitment;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.recruitment.*;

public class FrmOriChecklist extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private OriChecklist oriChecklist;

	public static final String FRM_NAME_ORICHECKLIST		=  "FRM_NAME_ORICHECKLIST" ;

	public static final int FRM_FIELD_ORI_CHECKLIST_ID			=  0 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  1 ;
	public static final int FRM_FIELD_INTERVIEWER_ID			=  2 ;
	public static final int FRM_FIELD_SIGNATURE_DATE			=  3 ;
	public static final int FRM_FIELD_INTERVIEW_DATE			=  4 ;
	public static final int FRM_FIELD_SKILLS        			=  5 ;

	public static String[] fieldNames = {
		"FRM_FIELD_ORI_CHECKLIST_ID",  "FRM_FIELD_RECR_APPLICATION_ID",
		"FRM_FIELD_INTERVIEWER_ID",  "FRM_FIELD_SIGNATURE_DATE",
		"FRM_FIELD_INTERVIEW_DATE", "FRM_FIELD_SKILLS"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG,  TYPE_DATE,
                TYPE_DATE, TYPE_STRING
	} ;

	public FrmOriChecklist(){
	}
	public FrmOriChecklist(OriChecklist oriChecklist){
		this.oriChecklist = oriChecklist;
	}

	public FrmOriChecklist(HttpServletRequest request, OriChecklist oriChecklist){
		super(new FrmOriChecklist(oriChecklist), request);
		this.oriChecklist = oriChecklist;
	}

	public String getFormName() { return FRM_NAME_ORICHECKLIST; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public OriChecklist getEntityObject(){ return oriChecklist; }

	public void requestEntityObject(OriChecklist oriChecklist) {
		try{
			this.requestParam();
			oriChecklist.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
			oriChecklist.setInterviewerId(getLong(FRM_FIELD_INTERVIEWER_ID));
			oriChecklist.setSignatureDate(getDate(FRM_FIELD_SIGNATURE_DATE));
			oriChecklist.setInterviewDate(getDate(FRM_FIELD_INTERVIEW_DATE));
                        oriChecklist.setSkills(getString(FRM_FIELD_SKILLS));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
