/* 
 * Form Name  	:  FrmDiseaseType.java 
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

package com.dimata.harisma.form.clinic;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.clinic.*;

public class FrmDiseaseType extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private DiseaseType diseaseType;

	public static final String FRM_NAME_DISEASETYPE		=  "FRM_NAME_DISEASETYPE" ;

	public static final int FRM_FIELD_DISEASE_TYPE_ID			=  0 ;
	public static final int FRM_FIELD_DISEASE_TYPE			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DISEASE_TYPE_ID",  "FRM_FIELD_DISEASE_TYPE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING
	} ;

	public FrmDiseaseType(){
	}
	public FrmDiseaseType(DiseaseType diseaseType){
		this.diseaseType = diseaseType;
	}

	public FrmDiseaseType(HttpServletRequest request, DiseaseType diseaseType){
		super(new FrmDiseaseType(diseaseType), request);
		this.diseaseType = diseaseType;
	}

	public String getFormName() { return FRM_NAME_DISEASETYPE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public DiseaseType getEntityObject(){ return diseaseType; }

	public void requestEntityObject(DiseaseType diseaseType) {
		try{
			this.requestParam();
			diseaseType.setDiseaseType(getString(FRM_FIELD_DISEASE_TYPE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
