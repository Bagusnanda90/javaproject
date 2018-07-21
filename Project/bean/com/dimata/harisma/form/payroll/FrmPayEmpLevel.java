/*
 * FrmPayEmpLevel.java
 *
 * Created on April 5, 2007, 12:57 PM
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
 * @author  yunny
 */
public class FrmPayEmpLevel extends FRMHandler implements I_FRMInterface, I_FRMType {
    private PayEmpLevel payEmpLevel;
    public static final String FRM_PAY_EMP_LEVEL =  "FRM_PAY_EMP_LEVEL" ;
    
    public static final int FRM_FIELD_PAY_EMP_LEVEL_ID			=  0 ;
    public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
    public static final int FRM_FIELD_LEVEL_CODE=  2 ;
    public static final int FRM_FIELD_START_DATE=  3 ;
    public static final int FRM_FIELD_BANK_ID			=  4 ;  
    public static final int FRM_FIELD_BANK_ACC_NR		=  5 ;
    public static final int FRM_FIELD_POS_FOR_TAX		=  6 ;
    public static final int FRM_FIELD_PAY_PER_BEGIN     =  7 ;
    public static final int FRM_FIELD_PAY_PER_END     =  8 ;
    public static final int FRM_FIELD_COMMENCING_ST=  9 ;
    public static final int FRM_FIELD_PREV_INCOME=  10 ;
    public static final int FRM_FIELD_PREV_TAX_PAID=  11 ;
    public static final int FRM_FIELD_STATUS_DATA=  12 ;
    public static final int FRM_FIELD_MEAL_ALLOWANCE=  13 ;
    public static final int FRM_FIELD_OVT_IDX_TYPE=  14 ;
    
    
      public static String[] fieldNames = {
	"FRM_FIELD_PAY_EMP_LEVEL_ID",
        "FRM_FIELD_EMPLOYEE_ID",
	"FRM_FIELD_LEVEL_CODE",
        "FRM_FIELD_START_DATE",
	"FRM_FIELD_BANK_ID",
        "FRM_FIELD_BANK_ACC_NR",
        "FRM_FIELD_POS_FOR_TAX",
        "FRM_FIELD_PAY_PER_BEGIN",
        "FRM_FIELD_PAY_PER_END",
        "FRM_FIELD_COMMENCING_ST",
        "FRM_FIELD_PREV_INCOME",
        "FRM_FIELD_PREV_TAX_PAID",
        "FRM_FIELD_STATUS_DATA",
        "FRM_FIELD_MEAL_ALLOWANCE",
        "FRM_FIELD_OVT_IDX_TYPE",
        } ;
        
        public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
	TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_FLOAT,
	TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
	} ;
                  
    /** Creates a new instance of FrmPayEmpLevel */
    public FrmPayEmpLevel() {
    }
    
    public FrmPayEmpLevel(PayEmpLevel payEmpLevel){
		this.payEmpLevel = payEmpLevel;
   }
   public FrmPayEmpLevel(HttpServletRequest request, PayEmpLevel payEmpLevel){
		super(new FrmPayEmpLevel(payEmpLevel), request);
		this.payEmpLevel = payEmpLevel;
        
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
        return FRM_PAY_EMP_LEVEL;
    }
    
     public PayEmpLevel getEntityObject(){ return payEmpLevel; }
     
     public void requestEntityObject(PayEmpLevel payEmpLevel) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        payEmpLevel.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
                        payEmpLevel.setLevelCode(getString(FRM_FIELD_LEVEL_CODE));
                        payEmpLevel.setStartDate(getDate(FRM_FIELD_START_DATE));
                        payEmpLevel.setBankId(getLong(FRM_FIELD_BANK_ID));
                        payEmpLevel.setBankAccNr(getString(FRM_FIELD_BANK_ACC_NR));
                        payEmpLevel.setPosForTax(getString(FRM_FIELD_POS_FOR_TAX));
                        payEmpLevel.setPayPerBegin(getInt(FRM_FIELD_PAY_PER_BEGIN));
                        payEmpLevel.setPayPerEnd(getInt(FRM_FIELD_PAY_PER_END));
                        payEmpLevel.setCommencingSt(getInt(FRM_FIELD_COMMENCING_ST));
                        payEmpLevel.setPrevIncome(getDouble(FRM_FIELD_PREV_INCOME));
                        payEmpLevel.setPrevTaxPaid(getInt(FRM_FIELD_PREV_TAX_PAID));
                        payEmpLevel.setStatusData(getInt(FRM_FIELD_STATUS_DATA));
                        payEmpLevel.setMealAllowance(getInt(FRM_FIELD_MEAL_ALLOWANCE));
                        payEmpLevel.setOvtIdxType(getInt(FRM_FIELD_OVT_IDX_TYPE));
                      
              	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
