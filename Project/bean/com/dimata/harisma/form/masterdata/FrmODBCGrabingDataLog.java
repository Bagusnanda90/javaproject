/* 
 * Form Name  	:  FrmODBCGrabingDataLog.java 
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

public class FrmODBCGrabingDataLog extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ODBCGrabingDataLog oDBCGrabingDataLog;

	public static final String FRM_NAME_ODBCGRABINGDATALOG		=  "FRM_NAME_ODBCGRABINGDATALOG" ;

	public static final int FRM_FIELD_LOG_ID			=  0 ;
	public static final int FRM_FIELD_DATE			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LOG_ID",  "FRM_FIELD_DATE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_DATE
	} ;

	public FrmODBCGrabingDataLog(){
	}
	public FrmODBCGrabingDataLog(ODBCGrabingDataLog oDBCGrabingDataLog){
		this.oDBCGrabingDataLog = oDBCGrabingDataLog;
	}

	public FrmODBCGrabingDataLog(HttpServletRequest request, ODBCGrabingDataLog oDBCGrabingDataLog){
		super(new FrmODBCGrabingDataLog(oDBCGrabingDataLog), request);
		this.oDBCGrabingDataLog = oDBCGrabingDataLog;
	}

	public String getFormName() { return FRM_NAME_ODBCGRABINGDATALOG; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ODBCGrabingDataLog getEntityObject(){ return oDBCGrabingDataLog; }

	public void requestEntityObject(ODBCGrabingDataLog oDBCGrabingDataLog) {
		try{
			this.requestParam();
			oDBCGrabingDataLog.setDate(getDate(FRM_FIELD_DATE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
