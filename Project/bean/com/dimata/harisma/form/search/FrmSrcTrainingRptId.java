
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
 * @author bayu
 */

public class FrmSrcTrainingRptId extends FRMHandler implements I_FRMInterface, I_FRMType {

        private SrcTrainingRptId srcTrainingRpt;

	public static final String FRM_NAME_TRAIN_RPT       =  "FRM_TRAINING_REPORT" ;

	public static final int FRM_FIELD_DEPARTMENT        =  0 ;
	public static final int FRM_FIELD_EMPLOYEE          =  1 ;
	public static final int FRM_FIELD_PAYROLL           =  2 ;
        public static final int FRM_FIELD_NAME              =  3 ;
        public static final int FRM_FIELD_START_DATE        =  4 ;
        public static final int FRM_FIELD_END_DATE          =  5 ;    

	public static String[] fieldNames = {
		"FRM_FIELD_DEPARTMENT",  
                "FRM_FIELD_EMPLOYEE",
		"FRM_FIELD_PAYROLL", 
                "FRM_FIELD_NAME",
                "FRM_FIELD_START_DATE",
                "FRM_FIELD_END_DATE",            
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING, 
                TYPE_STRING,
                TYPE_DATE,
                TYPE_DATE,                
	} ;


        public static final int ORDER_EMPLOYEE_NAME		= 0;
        public static final int ORDER_PAYROLL_NUM			= 1;
        public static final int ORDER_DEPARTMENT			= 2;

        /*public static final String[] orderKey  = {"Name", "Payroll Number", "Department"};
        public static final int[] orderValue  = {0,1,2};

        public static Vector getOrderKey(){
            Vector order = new Vector();

            for(int i=0;i<orderKey.length;i++)          
                order.add(orderKey[i]);

            return order;
        }

        public static Vector getOrderValue(){
            Vector order = new Vector();
            
            for(int i=0;i<orderValue.length;i++)            
                order.add(""+orderValue[i]);
            
            return order;
        }*/


	public FrmSrcTrainingRptId(){
	}
        
	public FrmSrcTrainingRptId(SrcTrainingRptId srcTrainingRpt){
		this.srcTrainingRpt = srcTrainingRpt;
	}

	public FrmSrcTrainingRptId(HttpServletRequest request, SrcTrainingRptId srcTrainingRpt){
		super(new FrmSrcTrainingRptId(srcTrainingRpt), request);
		this.srcTrainingRpt = srcTrainingRpt;
	}

	public String getFormName() { return FRM_NAME_TRAIN_RPT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcTrainingRptId getEntityObject(){ return srcTrainingRpt; }

	public void requestEntityObject(SrcTrainingRptId srcTrainingRpt) {
		try{
			this.requestParam();
			srcTrainingRpt.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT));
			srcTrainingRpt.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE));
			srcTrainingRpt.setPayroll(getString(FRM_FIELD_PAYROLL));
                        srcTrainingRpt.setName(getString(FRM_FIELD_NAME));
                        srcTrainingRpt.setStartDate(getDate(FRM_FIELD_START_DATE));
                        srcTrainingRpt.setEndDate(getDate(FRM_FIELD_END_DATE));                                         
		}
                catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
