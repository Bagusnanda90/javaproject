/*
 * FrmOvt_Type.java
 *
 * Created on April 11, 2007, 6:01 PM
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
public class FrmOvt_Type extends FRMHandler implements I_FRMInterface, I_FRMType{
    
    private Ovt_Type ovt_Type;
    
    public static final String FRM_OVT_TYPE =  "FRM_OVT_TYPE" ;

	public static final int FRM_FIELD_OVT_TYPE_ID 		=  0 ;
	public static final int FRM_FIELD_OVT_TYPE_CODE 	=  1 ;
	public static final int FRM_FIELD_TYPE_OF_DAY           =  2 ;
	public static final int FRM_FIELD_DESCRIPTION		=  3 ;
	public static final int FRM_FIELD_HOUR_BEGIN		=  4 ;
	public static final int FRM_FIELD_HOUR_END		=  5 ;
        public static final int FRM_FIELD_OWRITE_BY_SCHDL	=  6 ;
        public static final int FRM_FIELD_EMP_LEVEL_MIN		=  7 ;
        public static final int FRM_FIELD_EMP_LEVEL_MAX		=  8 ;
        public static final int FRM_FIELD_MASTER_LEVEL_MIN      =  9 ;
        public static final int FRM_FIELD_MASTER_LEVEL_MAX      =  10 ;
      
        
        public static String[] fieldNames = {
            "FRM_FIELD_OVT_TYPE_ID",
            "FRM_FIELD_OVT_TYPE_CODE",
            "FRM_FIELD_TYPE_OF_DAY",
            "FRM_FIELD_DESCRIPTION",
            "FRM_FIELD_HOUR_BEGIN",
            "FRM_FIELD_HOUR_END",
            "FRM_FIELD_OWRITE_BY_SCHDL",
            "FRM_FIELD_EMP_LEVEL_MIN",
            "FRM_FIELD_EMP_LEVEL_MAX",
            "FRM_FIELD_MASTER_LEVEL_MIN",
            "FRM_FIELD_MASTER_LEVEL_MAX",
        } ;
        
        
         public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_STRING,
            TYPE_INT,
            TYPE_STRING,
            TYPE_DATE,
            TYPE_DATE,
            TYPE_INT,
            TYPE_INT,
            TYPE_INT,
            TYPE_LONG,
            TYPE_LONG
        } ;
    
    /** Creates a new instance of FrmOvt_Type */
    public FrmOvt_Type() {
    }
    
    public FrmOvt_Type(Ovt_Type ovt_Type){
		this.ovt_Type = ovt_Type;
     }
    
     public FrmOvt_Type(HttpServletRequest request, Ovt_Type ovt_Type){
		super(new FrmOvt_Type(ovt_Type), request);
		this.ovt_Type = ovt_Type;
        
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
        return FRM_OVT_TYPE;
    }
    
     public Ovt_Type getEntityObject(){ return ovt_Type; }
     
     public void requestEntityObject(Ovt_Type ovt_Type) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        //ovt_Idx.setHour_from(getDouble(FRM_FIELD_HOUR_FROM));
                        ovt_Type.setOvt_Type_Code(getString(FRM_FIELD_OVT_TYPE_CODE));
                        ovt_Type.setType_of_day(getInt(FRM_FIELD_TYPE_OF_DAY));
                        ovt_Type.setDescription(getString(FRM_FIELD_DESCRIPTION));
                        ovt_Type.setStd_work_hour_begin(getDate(FRM_FIELD_HOUR_BEGIN));
                        ovt_Type.setStd_work_hour_end(getDate(FRM_FIELD_HOUR_END));
                        ovt_Type.setOwrite_by_schdl(getInt(FRM_FIELD_OWRITE_BY_SCHDL));
                        ovt_Type.setEmpLevelMin(getInt(FRM_FIELD_EMP_LEVEL_MIN));
                        ovt_Type.setEmpLevelMax(getInt(FRM_FIELD_EMP_LEVEL_MAX));
                        ovt_Type.setMasterLevelMin(getLong(FRM_FIELD_MASTER_LEVEL_MIN));
                        ovt_Type.setMasterLevelMax(getLong(FRM_FIELD_MASTER_LEVEL_MAX));
                      }catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}  
}
