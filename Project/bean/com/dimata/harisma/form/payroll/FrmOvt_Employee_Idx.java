/*
 * FrmOvt_Employee_Idx.java
 *
 * Created on April 7, 2007, 11:45 AM
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
public class FrmOvt_Employee_Idx extends FRMHandler implements I_FRMInterface, I_FRMType{
    
      private Ovt_Employee_Idx ovt_Employee_Idx;
       
        public static final String FRM_OVT_EMPLOYEE_IDX =  "FRM_OVT_EMPLOYEE_IDX" ;

	public static final int FRM_FIELD_OVT_EMPL_IDX 			=  0 ;
	public static final int FRM_FIELD_OVT_IDX_ID 			=  1 ;
	public static final int FRM_FIELD_OVT_EMPLY_ID 			=  2 ;
	public static final int FRM_FIELD_VALUE_IDX			=  3 ;
	
        public static String[] fieldNames = {
            "FRM_FIELD_OVT_EMPL_IDX",
            "FRM_FIELD_OVT_IDX_ID",
            "FRM_FIELD_OVT_EMPLY_ID",
            "FRM_FIELD_VALUE_IDX"
        } ;
        
        
         public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_LONG,
            TYPE_LONG,
            TYPE_FLOAT,
        } ;
    /** Creates a new instance of FrmOvt_Employee_Idx */
    public FrmOvt_Employee_Idx() {
    }
    
    public FrmOvt_Employee_Idx(Ovt_Employee_Idx ovt_Employee_Idx){
		this.ovt_Employee_Idx = ovt_Employee_Idx;
     }
     
     public FrmOvt_Employee_Idx(HttpServletRequest request, Ovt_Employee_Idx ovt_Employee_Idx){
		super(new FrmOvt_Employee_Idx(ovt_Employee_Idx), request);
		this.ovt_Employee_Idx = ovt_Employee_Idx;
        
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
        return FRM_OVT_EMPLOYEE_IDX;
    }
    
    public Ovt_Employee_Idx getEntityObject(){ return ovt_Employee_Idx; }
    
    public void requestEntityObject(Ovt_Employee_Idx ovt_Employee_Idx) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        ovt_Employee_Idx.setEmployee_id(getLong(FRM_FIELD_OVT_EMPLY_ID));
                        ovt_Employee_Idx.setOvt_idx_id(getLong(FRM_FIELD_OVT_IDX_ID));
                        ovt_Employee_Idx.setValue_idx(getDouble(FRM_FIELD_VALUE_IDX));
                
                }catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
