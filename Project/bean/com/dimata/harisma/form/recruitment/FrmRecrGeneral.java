/* 
 * Form Name  	:  FrmRecrGeneral.java 
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

public class FrmRecrGeneral extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrGeneral recrGeneral;

	public static final String FRM_NAME_RECRGENERAL		=  "FRM_NAME_RECRGENERAL" ;

	public static final int FRM_FIELD_RECR_GENERAL_ID			=  0 ;
	public static final int FRM_FIELD_QUESTION			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_GENERAL_ID",  "FRM_FIELD_QUESTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmRecrGeneral(){
	}
	public FrmRecrGeneral(RecrGeneral recrGeneral){
		this.recrGeneral = recrGeneral;
	}

	public FrmRecrGeneral(HttpServletRequest request, RecrGeneral recrGeneral){
		super(new FrmRecrGeneral(recrGeneral), request);
		this.recrGeneral = recrGeneral;
	}

	public String getFormName() { return FRM_NAME_RECRGENERAL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrGeneral getEntityObject(){ return recrGeneral; }

	public void requestEntityObject(RecrGeneral recrGeneral) {
		try{
			this.requestParam();
			recrGeneral.setQuestion(getString(FRM_FIELD_QUESTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
