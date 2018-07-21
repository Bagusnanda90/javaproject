/*
 * FrmOvt_Employee.java
 *
 * Created on April 6, 2007, 3:43 PM
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
public class FrmOvt_Employee extends FRMHandler implements I_FRMInterface, I_FRMType{
    
     private Ovt_Employee ovt_Employee;
       
        public static final String FRM_OVT_EMPLOYEE =  "FRM_OVT_EMPLOYEE" ;

	public static final int FRM_FIELD_OVT_EMPLY_ID 			=  0 ;
	public static final int FRM_FIELD_PERIOD_ID 			=  1 ;
	public static final int FRM_FIELD_EMPLOYEE_NUM 			=  2 ;
	public static final int FRM_FIELD_WORK_DATE			=  3 ;
	public static final int FRM_FIELD_STD_WORK_SCHDL		=  4 ;
	public static final int FRM_FIELD_OVT_START		=  5 ;
        public static final int FRM_FIELD_OVT_END		=  6 ;
        public static final int FRM_FIELD_OVT_DURATION 		=  7 ;
        public static final int FRM_FIELD_OVT_DOC_NR  		=  8 ;
        public static final int FRM_FIELD_STATUS  		=  9 ;
        public static final int FRM_FIELD_PAY_SLIP_ID		=  10 ;
        
        public static final int FRM_FIELD_OVT_CODE              = 11;
        public static final int FRM_FIELD_TOT_IDX               = 12;
        
        public static String[] fieldNames = {
            "FRM_FIELD_OVT_EMPLY_ID",
            "FRM_FIELD_PERIOD_ID",
            "FRM_FIELD_EMPLOYEE_NUM",
            "FRM_FIELD_WORK_DATE",
            "FRM_FIELD_STD_WORK_SCHDL",
            "FRM_FIELD_OVT_START",
            "FRM_FIELD_OVT_END",
            "FRM_FIELD_OVT_DURATION",
            "FRM_FIELD_OVT_DOC_NR",
            "FRM_FIELD_STATUS",
            "FRM_FIELD_PAY_SLIP_ID",
            
            "FRM_FIELD_OVT_CODE",
            "FRM_FIELD_TOT_IDX"
        } ;
        
        
         public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_LONG,
            TYPE_STRING,
            TYPE_DATE,
            TYPE_STRING,
            TYPE_DATE,
            TYPE_DATE,
            TYPE_FLOAT,
            TYPE_STRING,
            TYPE_INT,
            TYPE_LONG,
            
            TYPE_STRING,
            TYPE_FLOAT
        } ;
    /** Creates a new instance of FrmOvt_Employee */
    public FrmOvt_Employee() {
    }
    
     public FrmOvt_Employee(Ovt_Employee ovt_Employee){
		this.ovt_Employee = ovt_Employee;
     }
     
     public FrmOvt_Employee(HttpServletRequest request, Ovt_Employee ovt_Employee){
		super(new FrmOvt_Employee(ovt_Employee), request);
		this.ovt_Employee = ovt_Employee;
        
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
        return FRM_OVT_EMPLOYEE;
    }
    
    public Ovt_Employee getEntityObject(){ return ovt_Employee; }
    
    public void requestEntityObject(Ovt_Employee ovt_Employee) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        ovt_Employee.setDuration(getDouble(FRM_FIELD_OVT_DURATION));
                        ovt_Employee.setEmployee_num(getString(FRM_FIELD_EMPLOYEE_NUM));
                        ovt_Employee.setOvt_End(getDate(FRM_FIELD_OVT_END));
                        ovt_Employee.setOvt_Start(getDate(FRM_FIELD_OVT_START));
                        ovt_Employee.setOvt_doc_nr(getString(FRM_FIELD_OVT_DOC_NR));
                        ovt_Employee.setPay_slip_id(getLong(FRM_FIELD_PAY_SLIP_ID));
                        ovt_Employee.setPeriodId(getLong(FRM_FIELD_PERIOD_ID));
                        ovt_Employee.setStatus(getInt(FRM_FIELD_STATUS));
                        ovt_Employee.setWorkDate(getDate(FRM_FIELD_WORK_DATE));
                        ovt_Employee.setWork_schedule(getString(FRM_FIELD_STD_WORK_SCHDL));
                        
                        ovt_Employee.setOvt_code(getString(FRM_FIELD_OVT_CODE));
                        ovt_Employee.setTot_Idx(getDouble(FRM_FIELD_TOT_IDX));
              	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
