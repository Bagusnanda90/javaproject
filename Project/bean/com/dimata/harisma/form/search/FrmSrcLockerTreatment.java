/* 
 * Form Name  	:  FrmSrcLockerTreatment.java 
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

public class FrmSrcLockerTreatment extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcLockerTreatment srcLockerTreatment;

	public static final String FRM_NAME_SRCLOCKERTREATMENT		=  "FRM_NAME_SRCLOCKERTREATMENT" ;

	public static final int FRM_FIELD_LOCATION			=  0 ;
	public static final int FRM_FIELD_TREATMENTDATEFROM			=  1 ;
	public static final int FRM_FIELD_TREATMENTDATETO			=  2 ;
	public static final int FRM_FIELD_TREATMENT			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LOCATION",  "FRM_FIELD_TREATMENTDATEFROM",
		"FRM_FIELD_TREATMENTDATETO",  "FRM_FIELD_TREATMENT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_DATE,
		TYPE_DATE,  TYPE_STRING
	} ;

	public FrmSrcLockerTreatment(){
	}
	public FrmSrcLockerTreatment(SrcLockerTreatment srcLockerTreatment){
		this.srcLockerTreatment = srcLockerTreatment;
	}

	public FrmSrcLockerTreatment(HttpServletRequest request, SrcLockerTreatment srcLockerTreatment){
		super(new FrmSrcLockerTreatment(srcLockerTreatment), request);
		this.srcLockerTreatment = srcLockerTreatment;
	}

	public String getFormName() { return FRM_NAME_SRCLOCKERTREATMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcLockerTreatment getEntityObject(){ return srcLockerTreatment; }

	public void requestEntityObject(SrcLockerTreatment srcLockerTreatment) {
		try{
			this.requestParam();
			srcLockerTreatment.setLocation(getLong(FRM_FIELD_LOCATION));
			srcLockerTreatment.setTreatmentdatefrom(getDate(FRM_FIELD_TREATMENTDATEFROM));
			srcLockerTreatment.setTreatmentdateto(getDate(FRM_FIELD_TREATMENTDATETO));
			srcLockerTreatment.setTreatment(getString(FRM_FIELD_TREATMENT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
