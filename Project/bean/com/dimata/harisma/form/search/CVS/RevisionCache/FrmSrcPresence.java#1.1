/* 
 * Form Name  	:  FrmSrcPresence.java 
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

public class FrmSrcPresence extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcPresence srcPresence;

	public static final String FRM_NAME_SRCPRESENCE		=  "FRM_NAME_SRCPRESENCE" ;

	public static final int FRM_FIELD_EMPNUMBER			=  0 ;
	public static final int FRM_FIELD_FULLNAME			=  1 ;
	public static final int FRM_FIELD_DEPARTMENT			=  2 ;
	public static final int FRM_FIELD_SECTION			=  3 ;
	public static final int FRM_FIELD_POSITION			=  4 ;
	public static final int FRM_FIELD_DATEFROM			=  5 ;
	public static final int FRM_FIELD_DATETO			=  6 ;
        public static final int FRM_FIELD_PERIOD_CHECKED		=  7 ;
        public static final int FRM_FIELD_PERIOD_STATUS 		=  8 ;
        public static final int FRM_FIELD_PERIOD_ANALYZED		=  9 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMPNUMBER",  "FRM_FIELD_FULLNAME",
		"FRM_FIELD_DEPARTMENT",  "FRM_FIELD_SECTION",
		"FRM_FIELD_POSITION",  "FRM_FIELD_DATEFROM",
		"FRM_FIELD_DATETO", "FRM_FIELD_PERIOD_CHECKED", 
                "FRM_FIELD_STATUS", "FRM_FIELD_ANALYZED"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_DATE,
		TYPE_DATE, TYPE_INT, TYPE_COLLECTION, TYPE_COLLECTION
	} ;

	public FrmSrcPresence(){
	}
	public FrmSrcPresence(SrcPresence srcPresence){
		this.srcPresence = srcPresence;
	}

	public FrmSrcPresence(HttpServletRequest request, SrcPresence srcPresence){
		super(new FrmSrcPresence(srcPresence), request);
		this.srcPresence = srcPresence;
	}

	public String getFormName() { return FRM_NAME_SRCPRESENCE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcPresence getEntityObject(){ return srcPresence; }

	public void requestEntityObject(SrcPresence srcPresence) {
		try{
			this.requestParam();                           
			srcPresence.setEmpnumber(getString(FRM_FIELD_EMPNUMBER));
			srcPresence.setFullname(getString(FRM_FIELD_FULLNAME).trim());
			srcPresence.setDepartment(getString(FRM_FIELD_DEPARTMENT));
			srcPresence.setSection(getString(FRM_FIELD_SECTION));
			srcPresence.setPosition(getString(FRM_FIELD_POSITION));
			srcPresence.setDatefrom(getDate(FRM_FIELD_DATEFROM));
			srcPresence.setDateto(getDate(FRM_FIELD_DATETO));  
                        srcPresence.setPeriodCheck(getBoolean(FRM_FIELD_PERIOD_CHECKED));
                        srcPresence.setStatusCheck(getVectorInt(fieldNames[FRM_FIELD_PERIOD_STATUS]));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
