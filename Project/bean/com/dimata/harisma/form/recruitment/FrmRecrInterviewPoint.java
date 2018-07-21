/* 
 * Form Name  	:  FrmRecrInterviewPoint.java 
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

public class FrmRecrInterviewPoint extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrInterviewPoint recrInterviewPoint;

	public static final String FRM_NAME_RECRINTERVIEWPOINT		=  "FRM_NAME_RECRINTERVIEWPOINT" ;

	public static final int FRM_FIELD_RECR_INTERVIEW_POINT_ID			=  0 ;
	public static final int FRM_FIELD_INTERVIEW_POINT			=  1 ;
	public static final int FRM_FIELD_INTERVIEW_MARK			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_INTERVIEW_POINT_ID",  "FRM_FIELD_INTERVIEW_POINT",
		"FRM_FIELD_INTERVIEW_MARK"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_INT + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmRecrInterviewPoint(){
	}
	public FrmRecrInterviewPoint(RecrInterviewPoint recrInterviewPoint){
		this.recrInterviewPoint = recrInterviewPoint;
	}

	public FrmRecrInterviewPoint(HttpServletRequest request, RecrInterviewPoint recrInterviewPoint){
		super(new FrmRecrInterviewPoint(recrInterviewPoint), request);
		this.recrInterviewPoint = recrInterviewPoint;
	}

	public String getFormName() { return FRM_NAME_RECRINTERVIEWPOINT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrInterviewPoint getEntityObject(){ return recrInterviewPoint; }

	public void requestEntityObject(RecrInterviewPoint recrInterviewPoint) {
		try{
			this.requestParam();
			recrInterviewPoint.setInterviewPoint(getInt(FRM_FIELD_INTERVIEW_POINT));
			recrInterviewPoint.setInterviewMark(getString(FRM_FIELD_INTERVIEW_MARK));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
