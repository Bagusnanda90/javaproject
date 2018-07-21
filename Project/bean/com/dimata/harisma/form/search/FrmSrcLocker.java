/* 
 * Form Name  	:  FrmSrcLocker.java 
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

public class FrmSrcLocker extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcLocker srcLocker;

	public static final String FRM_NAME_SRCLOCKER		=  "FRM_NAME_SRCLOCKER" ;

	public static final int FRM_FIELD_LOCATION			=  0 ;
	public static final int FRM_FIELD_LOCKERNUMBER			=  1 ;
	public static final int FRM_FIELD_LOCKERKEY			=  2 ;
	public static final int FRM_FIELD_SPAREKEY			=  3 ;
	public static final int FRM_FIELD_CONDITION			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_LOCATION",  "FRM_FIELD_LOCKERNUMBER",
		"FRM_FIELD_LOCKERKEY",  "FRM_FIELD_SPAREKEY",
		"FRM_FIELD_CONDITION"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING
	} ;

	public FrmSrcLocker(){
	}
	public FrmSrcLocker(SrcLocker srcLocker){
		this.srcLocker = srcLocker;
	}

	public FrmSrcLocker(HttpServletRequest request, SrcLocker srcLocker){
		super(new FrmSrcLocker(srcLocker), request);
		this.srcLocker = srcLocker;
	}

	public String getFormName() { return FRM_NAME_SRCLOCKER; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcLocker getEntityObject(){ return srcLocker; }

	public void requestEntityObject(SrcLocker srcLocker) {
		try{
			this.requestParam();
			srcLocker.setLocation(getLong(FRM_FIELD_LOCATION));
			srcLocker.setLockernumber(getString(FRM_FIELD_LOCKERNUMBER));
			srcLocker.setLockerkey(getString(FRM_FIELD_LOCKERKEY));
			srcLocker.setSparekey(getString(FRM_FIELD_SPAREKEY));
			srcLocker.setCondition(getLong(FRM_FIELD_CONDITION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
