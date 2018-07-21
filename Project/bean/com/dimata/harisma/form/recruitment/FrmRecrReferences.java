/* 
 * Form Name  	:  FrmRecrReferences.java 
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

public class FrmRecrReferences extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrReferences recrReferences;

	public static final String FRM_NAME_RECRREFERENCES		=  "FRM_NAME_RECRREFERENCES" ;

	public static final int FRM_FIELD_RECR_REFERENCES_ID			=  0 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  1 ;
	public static final int FRM_FIELD_NAME			=  2 ;
	public static final int FRM_FIELD_COMPANY			=  3 ;
	public static final int FRM_FIELD_POSITION			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_REFERENCES_ID",  "FRM_FIELD_RECR_APPLICATION_ID",
		"FRM_FIELD_NAME",  "FRM_FIELD_COMPANY",
		"FRM_FIELD_POSITION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING,
		TYPE_STRING
	} ;

	public FrmRecrReferences(){
	}
	public FrmRecrReferences(RecrReferences recrReferences){
		this.recrReferences = recrReferences;
	}

	public FrmRecrReferences(HttpServletRequest request, RecrReferences recrReferences){
		super(new FrmRecrReferences(recrReferences), request);
		this.recrReferences = recrReferences;
	}

	public String getFormName() { return FRM_NAME_RECRREFERENCES; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrReferences getEntityObject(){ return recrReferences; }

	public void requestEntityObject(RecrReferences recrReferences) {
		try{
			this.requestParam();
			recrReferences.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
			recrReferences.setName(getString(FRM_FIELD_NAME));
			recrReferences.setCompany(getString(FRM_FIELD_COMPANY));
			recrReferences.setPosition(getString(FRM_FIELD_POSITION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
