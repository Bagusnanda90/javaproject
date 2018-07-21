/* 
 * Form Name  	:  FrmRecrLanguage.java 
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

public class FrmRecrLanguage extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private RecrLanguage recrLanguage;

	public static final String FRM_NAME_RECRLANGUAGE		=  "FRM_NAME_RECRLANGUAGE" ;

	public static final int FRM_FIELD_RECR_LANGUAGE_ID			=  0 ;
	public static final int FRM_FIELD_RECR_APPLICATION_ID			=  1 ;
	public static final int FRM_FIELD_LANGUAGE_ID			=  2 ;
	public static final int FRM_FIELD_SPOKEN			=  3 ;
	public static final int FRM_FIELD_WRITTEN			=  4 ;
	public static final int FRM_FIELD_READING			=  5 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECR_LANGUAGE_ID",  "FRM_FIELD_RECR_APPLICATION_ID",
		"FRM_FIELD_LANGUAGE_ID",  "FRM_FIELD_SPOKEN",
		"FRM_FIELD_WRITTEN",  "FRM_FIELD_READING"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG + ENTRY_REQUIRED,  TYPE_INT,
		TYPE_INT,  TYPE_INT
	} ;

	public FrmRecrLanguage(){
	}
	public FrmRecrLanguage(RecrLanguage recrLanguage){
		this.recrLanguage = recrLanguage;
	}

	public FrmRecrLanguage(HttpServletRequest request, RecrLanguage recrLanguage){
		super(new FrmRecrLanguage(recrLanguage), request);
		this.recrLanguage = recrLanguage;
	}

	public String getFormName() { return FRM_NAME_RECRLANGUAGE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public RecrLanguage getEntityObject(){ return recrLanguage; }

	public void requestEntityObject(RecrLanguage recrLanguage) {
		try{
			this.requestParam();
			recrLanguage.setRecrApplicationId(getLong(FRM_FIELD_RECR_APPLICATION_ID));
			recrLanguage.setLanguageId(getLong(FRM_FIELD_LANGUAGE_ID));
			recrLanguage.setSpoken(getInt(FRM_FIELD_SPOKEN));
			recrLanguage.setWritten(getInt(FRM_FIELD_WRITTEN));
			recrLanguage.setReading(getInt(FRM_FIELD_READING));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
