/* 
 * Form Name  	:  FrmRecrInterviewer.java 
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

public class FrmRecrInterviewer extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrInterviewer recrInterviewer;

	public static final String FRM_NAME_RECRINTERVIEWER		=  "FRM_NAME_RECRINTERVIEWER" ;

	public static final int FRM_FIELD_RECR_INTERVIEWER_ID		=  0 ;
	public static final int FRM_FIELD_INTERVIEWER			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_INTERVIEWER_ID",  "FRM_FIELD_INTERVIEWER"
	} ;

	public static int[] fieldTypes = {
                TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmRecrInterviewer(){
	}
	public FrmRecrInterviewer(RecrInterviewer recrInterviewer){
		this.recrInterviewer = recrInterviewer;
	}

	public FrmRecrInterviewer(HttpServletRequest request, RecrInterviewer recrInterviewer){
		super(new FrmRecrInterviewer(recrInterviewer), request);
		this.recrInterviewer = recrInterviewer;
	}

	public String getFormName() { return FRM_NAME_RECRINTERVIEWER; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrInterviewer getEntityObject(){ return recrInterviewer; }

	public void requestEntityObject(RecrInterviewer recrInterviewer) {
		try{
			this.requestParam();
			recrInterviewer.setInterviewer(getString(FRM_FIELD_INTERVIEWER));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
