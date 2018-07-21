/*
 * FrmSrcCanteenVisitation.java
 *
 * Created on May 18, 1999, 11:55 AM
 */

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

/**
 *
 * @author  gedhy
 */
public class FrmSrcCanteenVisitation extends FRMHandler implements I_FRMInterface, I_FRMType 
{
    
	private SrcCanteenVisitation srcCanteenVisitation;

	public static final String FRM_NAME_SRC_CANTEEN_VISITATION		=  "FRM_NAME_SRC_CANTEEN_VISITATION" ;

	public static final int FRM_FIELD_EMPNUMBER			=  0 ;
	public static final int FRM_FIELD_FULLNAME			=  1 ;
	public static final int FRM_FIELD_DEPARTMENT			=  2 ;
	public static final int FRM_FIELD_SECTION			=  3 ;
	public static final int FRM_FIELD_POSITION			=  4 ;
	public static final int FRM_FIELD_DATEFROM			=  5 ;
	public static final int FRM_FIELD_DATETO			=  6 ;
        public static final int FRM_FIELD_PERIOD_CHECKED		=  7 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMPNUMBER",  "FRM_FIELD_FULLNAME",
		"FRM_FIELD_DEPARTMENT",  "FRM_FIELD_SECTION",
		"FRM_FIELD_POSITION",  "FRM_FIELD_DATEFROM",
		"FRM_FIELD_DATETO", "FRM_FIELD_PERIOD_CHECKED"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_DATE,
		TYPE_DATE, TYPE_INT
	} ;

	public FrmSrcCanteenVisitation(){
	}
	public FrmSrcCanteenVisitation(SrcCanteenVisitation srcCanteenVisitation){
		this.srcCanteenVisitation = srcCanteenVisitation;
	}

	public FrmSrcCanteenVisitation(HttpServletRequest request, SrcCanteenVisitation srcCanteenVisitation){
		super(new FrmSrcCanteenVisitation(srcCanteenVisitation), request);
		this.srcCanteenVisitation = srcCanteenVisitation;
	}

	public String getFormName() { return FRM_NAME_SRC_CANTEEN_VISITATION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcCanteenVisitation getEntityObject(){ return srcCanteenVisitation; }

	public void requestEntityObject(SrcCanteenVisitation srcCanteenVisitation) {
		try{
			this.requestParam();                           
			srcCanteenVisitation.setEmpnumber(getString(FRM_FIELD_EMPNUMBER));
			srcCanteenVisitation.setFullname(getString(FRM_FIELD_FULLNAME));
			srcCanteenVisitation.setDepartment(getString(FRM_FIELD_DEPARTMENT));
			srcCanteenVisitation.setSection(getString(FRM_FIELD_SECTION));
			srcCanteenVisitation.setPosition(getString(FRM_FIELD_POSITION));
			srcCanteenVisitation.setDatefrom(getDate(FRM_FIELD_DATEFROM));
			srcCanteenVisitation.setDateto(getDate(FRM_FIELD_DATETO));  
                        srcCanteenVisitation.setPeriodCheck(getBoolean(FRM_FIELD_PERIOD_CHECKED));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}    
}
