/*
 * FrmSrcScheduleSymbol.java
 *
 * Created on January 5, 2005, 1:42 PM
 */

package com.dimata.harisma.form.search;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// dimata package
import com.dimata.gui.jsp.*;

/* qdep package */ 
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.search.*; 

/**
 *
 * @author  gedhy
 */
public class FrmSrcScheduleSymbol  extends FRMHandler implements I_FRMInterface, I_FRMType {
    
	private SrcScheduleSymbol srcScheduleSymbol;

	public static final String FRM_NAME_SRC_SCHEDULE_SYMBOL	=  "FRM_NAME_SRC_SCHEDULE_SYMBOL" ;

	public static final int FRM_FIELD_CATEGORY		=  0 ;
	public static final int FRM_FIELD_SYMBOL		=  1 ;
        public static final int FRM_FIELD_TIME_IN_START		=  2 ;
        public static final int FRM_FIELD_TIME_IN_END		=  3 ;
        public static final int FRM_FIELD_TIME_OUT_START	=  4 ;
        public static final int FRM_FIELD_TIME_OUT_END		=  5 ;
        public static final int FRM_FIELD_SELECT_TIME_IN	=  6 ;
        public static final int FRM_FIELD_SELECT_TIME_OUT	=  7 ;

	public static String[] fieldNames = {
		"FRM_FIELD_CATEGORY",  
		"FRM_FIELD_SYMBOL",
                "FRM_FIELD_TIME_IN_START",
                "FRM_FIELD_TIME_IN_END",
                "FRM_FIELD_TIME_OUT_START",
                "FRM_FIELD_TIME_OUT_END",
                "FRM_FIELD_SELECT_TIME_IN",
                "FRM_FIELD_SELECT_TIME_OUT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,
		TYPE_STRING,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT,
                TYPE_INT
	} ;

	public FrmSrcScheduleSymbol(){
	}
	public FrmSrcScheduleSymbol(SrcScheduleSymbol srcScheduleSymbol){
		this.srcScheduleSymbol = srcScheduleSymbol;
	}

	public FrmSrcScheduleSymbol(HttpServletRequest request, SrcScheduleSymbol srcScheduleSymbol){
		super(new FrmSrcScheduleSymbol(srcScheduleSymbol), request);
		this.srcScheduleSymbol = srcScheduleSymbol;
	}

	public String getFormName() { return FRM_NAME_SRC_SCHEDULE_SYMBOL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcScheduleSymbol getEntityObject(){ return srcScheduleSymbol; }

	public void requestEntityObject(SrcScheduleSymbol srcScheduleSymbol, HttpServletRequest requestFrm) {
		try{
			this.requestParam();
                        
                        Date TimeInStart = ControlDate.getTime(FrmSrcScheduleSymbol.fieldNames[FrmSrcScheduleSymbol.FRM_FIELD_TIME_IN_START], requestFrm);                       
                        Date TimeInEnd = ControlDate.getTime(FrmSrcScheduleSymbol.fieldNames[FrmSrcScheduleSymbol.FRM_FIELD_TIME_IN_END], requestFrm);                        
                        Date TimeOutStart = ControlDate.getTime(FrmSrcScheduleSymbol.fieldNames[FrmSrcScheduleSymbol.FRM_FIELD_TIME_OUT_START], requestFrm);                                                
                        Date TimeOutEnd = ControlDate.getTime(FrmSrcScheduleSymbol.fieldNames[FrmSrcScheduleSymbol.FRM_FIELD_TIME_OUT_END], requestFrm);                        
                        
			srcScheduleSymbol.setSchCategory(getLong(FRM_FIELD_CATEGORY));
			srcScheduleSymbol.setSchSymbol(getString(FRM_FIELD_SYMBOL));
                        srcScheduleSymbol.setTimeInStart(TimeInStart);
                        srcScheduleSymbol.setTimeInEnd(TimeInEnd);
                        srcScheduleSymbol.setTimeOutStart(TimeOutStart);
                        srcScheduleSymbol.setTimeOutEnd(TimeOutEnd);
                        srcScheduleSymbol.setSelectTimeIn(getBoolean(FRM_FIELD_SELECT_TIME_IN));
                        srcScheduleSymbol.setSelectTimeOut(getBoolean(FRM_FIELD_SELECT_TIME_OUT));
                        
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}    
}
