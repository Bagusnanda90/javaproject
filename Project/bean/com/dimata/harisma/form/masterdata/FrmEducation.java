/* 
 * Form Name  	:  FrmEducation.java 
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

public class FrmEducation extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Education education;

	public static final String FRM_NAME_EDUCATION		=  "FRM_NAME_EDUCATION" ;

	public static final int FRM_FIELD_EDUCATION_ID			=  0 ;
	public static final int FRM_FIELD_EDUCATION			=  1 ;
	public static final int FRM_FIELD_EDUCATION_DESC		=  2 ;
        public static final int FRM_FIELD_EDUCATION_LEVEL               = 3;

	public static String[] fieldNames = {
		"FRM_FIELD_EDUCATION_ID",  "FRM_FIELD_EDUCATION",
		"FRM_FIELD_EDUCATION_DESC",
                "FRM_FIELD_EDUCATION_LEVEL"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,
                TYPE_INT
	} ;

	public FrmEducation(){
	}
	public FrmEducation(Education education){
		this.education = education;
	}

	public FrmEducation(HttpServletRequest request, Education education){
		super(new FrmEducation(education), request);
		this.education = education;
	}

	public String getFormName() { return FRM_NAME_EDUCATION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Education getEntityObject(){ return education; }

	public void requestEntityObject(Education education) {
		try{
			this.requestParam();
			education.setEducation(getString(FRM_FIELD_EDUCATION));
			education.setEducationDesc(getString(FRM_FIELD_EDUCATION_DESC));
                        education.setEducationLevel(getInt(FRM_FIELD_EDUCATION_LEVEL));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
        
        public static Vector getUnitTypeValue(){
            Vector getUnitTypeValue = new Vector();
            getUnitTypeValue.add("-");
            return getUnitTypeValue();
                    
        }
}
