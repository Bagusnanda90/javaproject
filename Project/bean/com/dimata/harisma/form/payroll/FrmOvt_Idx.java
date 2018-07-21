/*
 * FrmOvt_Idx.java
 *
 * Created on April 12, 2007, 10:49 AM
 */

package com.dimata.harisma.form.payroll;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.payroll.*;

/**
 *
 * @author  emerliana
 */
public class FrmOvt_Idx extends FRMHandler implements I_FRMInterface, I_FRMType{
    
        private Ovt_Idx ovt_Idx;

        public static final String FRM_OVT_IDX =  "FRM_OVT_IDX" ;

	public static final int FRM_FIELD_OVT_IDX_ID			=  0 ;
	public static final int FRM_FIELD_HOUR_FROM 		=  1 ;
	public static final int FRM_FIELD_HOUR_TO                   =  2 ;
	public static final int FRM_FIELD_OVT_IDX			=  3 ;
	public static final int FRM_FIELD_OVT_TYPE_CODE		=  4 ;
	
        public static String[] fieldNames = {
            "FRM_FIELD_OVT_IDX_ID",
            "FRM_FIELD_HOUR_FROM",
            "FRM_FIELD_HOUR_TO",
            "FRM_FIELD_OVT_IDX",
            "FRM_FIELD_OVT_TYPE_CODE"
        } ;
        
        
         public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_STRING
        } ;
        
    public FrmOvt_Idx() {
    }
    
    public FrmOvt_Idx(Ovt_Idx ovt_Idx){
		this.ovt_Idx = ovt_Idx;
     }
    
     public FrmOvt_Idx(HttpServletRequest request, Ovt_Idx ovt_Idx){
		super(new FrmOvt_Idx(ovt_Idx), request);
		this.ovt_Idx = ovt_Idx;
        
     }
    
    public String[] getFieldNames() {
         return fieldNames;
    }    
    
    public int getFieldSize() {
         return fieldNames.length;
    }    
    
    public int[] getFieldTypes() {
         return fieldTypes;
    }
    
    public String getFormName() {
          return FRM_OVT_IDX;
    }
    
     public Ovt_Idx getEntityObject(){ return ovt_Idx; }
     
     public void requestEntityObject(Ovt_Idx ovt_Idx) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        ovt_Idx.setHour_from(getDouble(FRM_FIELD_HOUR_FROM));
                        ovt_Idx.setHour_to(getDouble(FRM_FIELD_HOUR_TO));
                        ovt_Idx.setOvt_idx(getDouble(FRM_FIELD_OVT_IDX));
                        ovt_Idx.setOvt_type_code(getString(FRM_FIELD_OVT_TYPE_CODE));
                        
                        
                      }catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}  
    
}
