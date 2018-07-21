/* 
 * Form Name  	:  FrmRecognition.java 
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

package com.dimata.harisma.form.employee;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;

public class FrmRecognition extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Recognition recognition;

	public static final String FRM_NAME_RECOGNITION		=  "FRM_NAME_RECOGNITION" ;

	public static final int FRM_FIELD_RECOGNITION_ID			=  0 ;
	public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
	public static final int FRM_FIELD_RECOG_DATE			=  2 ;
	public static final int FRM_FIELD_POINT			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RECOGNITION_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_RECOG_DATE",  "FRM_FIELD_POINT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_INT + ENTRY_REQUIRED
	} ;

	public FrmRecognition(){
	}
	public FrmRecognition(Recognition recognition){
		this.recognition = recognition;
	}

	public FrmRecognition(HttpServletRequest request, Recognition recognition){
		super(new FrmRecognition(recognition), request);
		this.recognition = recognition;
	}

	public String getFormName() { return FRM_NAME_RECOGNITION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Recognition getEntityObject(){ return recognition; }

	public void requestEntityObject(Recognition recognition) {
		try{
			this.requestParam();
			recognition.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			recognition.setRecogDate(getDate(FRM_FIELD_RECOG_DATE));
			recognition.setPoint(getInt(FRM_FIELD_POINT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
