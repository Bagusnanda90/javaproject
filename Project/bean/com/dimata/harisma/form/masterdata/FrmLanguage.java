/* 
 * Form Name  	:  FrmLanguage.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmLanguage extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Language language;

	public static final String FRM_NAME_LANGUAGE		=  "FRM_NAME_LANGUAGE" ;

	public static final int FRM_FIELD_LANGUAGE_ID			=  0 ;
	public static final int FRM_FIELD_LANGUAGE			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LANGUAGE_ID",  "FRM_FIELD_LANGUAGE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmLanguage(){
	}
	public FrmLanguage(Language language){
		this.language = language;
	}

	public FrmLanguage(HttpServletRequest request, Language language){
		super(new FrmLanguage(language), request);
		this.language = language;
	}

	public String getFormName() { return FRM_NAME_LANGUAGE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Language getEntityObject(){ return language; }

	public void requestEntityObject(Language language) {
		try{
			this.requestParam();
			language.setLanguage(getString(FRM_FIELD_LANGUAGE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
