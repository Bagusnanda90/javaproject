/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.form.search;

/**
 *
 * @author Tu Roy
 */

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

/* qdep package */ 
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.search.*;


public class FrmSrcLeaveAppAlClosing extends FRMHandler implements I_FRMInterface, I_FRMType{
    
    private SrcLeaveAppAlClosing srcLeaveAppAlClosing;
    
    public static final String FRM_NAME_SRCLEAVE_APP = "FRM_SRCLEAVE_APP_AL_CLOSING";
    
        public static final int FRM_FIELD_EMP_NUMBER        =  0 ;
	public static final int FRM_FIELD_FULLNAME          =  1 ;        
	public static final int FRM_FIELD_DEPARTMENT        =  2 ;
	public static final int FRM_FIELD_SECTION           =  3 ;
	public static final int FRM_FIELD_POSITION          =  4 ;
        public static final int FRM_FIELD_CATEGORY          =  5 ;
        public static final int FRM_FIELD_DIVISION          =  6 ;
        public static final int FRM_FIELD_COMMANCING_START  =  7 ;
        public static final int FRM_FIELD_COMMANCING_END    =  8 ;
        public static final int FRM_FIELD_ORDER_BY          =  9 ;
        public static final int FRM_FIELD_RESIGNED          =  10 ;
        public static final int FRM_FIELD_PERIOD            =  11 ;
        public static final int FRM_FIELD_RADIO_BTN         =  12 ;
        
        //update by satrya 2013-10-1
        public static final int FRM_FIELD_STATUS         =  13 ;
        
	public static String[] fieldNames = 
        {
		"FRM_FIELD_EMP_NUMBER",  
                "FRM_FIELD_FULLNAME",
		"FRM_FIELD_DEPARTMENT",  
                "FRM_FIELD_SECTION",
		"FRM_FIELD_POSITION",
                "FRM_FIELD_CATEGORY",
                "FRM_FIELD_DIVISION",
                "FRM_FIELD_COMMANCING_START",
                "FRM_FIELD_COMMANCING_END",
                "FRM_FIELD_ORDER_BY",
                "FRM_FIELD_RESIGNED",
                "FRM_FIELD_PERIOD",
                "FRM_FIELD_RADIO_BTN",
                //update by satrya 2013-10-01
                "FRM_FIELD_STATUS"
	} ;
        
        public static int[] fieldTypes = {
		TYPE_STRING,  
                TYPE_STRING,
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT,
                TYPE_INT,
                TYPE_LONG,
                TYPE_INT,
                //update by satrya 2013-10-01
                TYPE_INT
	} ;
        
       public FrmSrcLeaveAppAlClosing(){}
       
       public FrmSrcLeaveAppAlClosing(SrcLeaveAppAlClosing srcLeaveAppAlClosing){
            this.srcLeaveAppAlClosing = srcLeaveAppAlClosing;
       }
       
       public FrmSrcLeaveAppAlClosing(HttpServletRequest request, SrcLeaveAppAlClosing srcLeaveAppAlClosing){
		super(new FrmSrcLeaveAppAlClosing(srcLeaveAppAlClosing), request);
		this.srcLeaveAppAlClosing = srcLeaveAppAlClosing;
       }
       
       public String getFormName() { return FRM_NAME_SRCLEAVE_APP; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcLeaveAppAlClosing getEntityObject(){ return srcLeaveAppAlClosing; }
        
        public void requestEntityObject(SrcLeaveAppAlClosing srcLeaveAppAlClosing) {
        try {
            this.requestParam();
            srcLeaveAppAlClosing.setEmpNum(getString(FRM_FIELD_EMP_NUMBER));
            srcLeaveAppAlClosing.setFullName(getString(FRM_FIELD_FULLNAME));
            srcLeaveAppAlClosing.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT));
            srcLeaveAppAlClosing.setCategoryId(getLong(FRM_FIELD_CATEGORY));
            srcLeaveAppAlClosing.setSectionId(getLong(FRM_FIELD_SECTION));
            srcLeaveAppAlClosing.setPositionId(getLong(FRM_FIELD_POSITION));
            srcLeaveAppAlClosing.setDivisionId(getLong(FRM_FIELD_DIVISION));
            srcLeaveAppAlClosing.setEmpCommancingDateStart(getDate(FRM_FIELD_COMMANCING_START));
            srcLeaveAppAlClosing.setEmpCommancingDateEnd(getDate(FRM_FIELD_COMMANCING_END));
            srcLeaveAppAlClosing.setOrderBy(getInt(FRM_FIELD_ORDER_BY));
            srcLeaveAppAlClosing.setResigned(getInt(FRM_FIELD_RESIGNED));
            srcLeaveAppAlClosing.setPeriodId(getLong(FRM_FIELD_PERIOD));
            srcLeaveAppAlClosing.setRadioBtn(getInt(FRM_FIELD_RADIO_BTN));
            //update by satrya 2013-10-01
            srcLeaveAppAlClosing.setStatus(getInt(FRM_FIELD_STATUS)); 
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
        
    

}
