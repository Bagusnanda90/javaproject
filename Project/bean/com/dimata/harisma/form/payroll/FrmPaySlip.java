/*
 * FrmPaySlip.java
 *
 * Created on April 25, 2007, 9:09 AM
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
public class FrmPaySlip extends FRMHandler implements I_FRMInterface, I_FRMType {
     private PaySlip paySlip;
     
     public static final String FRM_PAY_SLIP =  "FRM_PAY_SLIP" ;
     
     public static final int FRM_FIELD_PAY_SLIP_ID			=  0 ;
     public static final int FRM_FIELD_PERIOD_ID			=  1 ;
     public static final int FRM_FIELD_EMPLOYEE_ID                      =  2 ;
     public static final int FRM_FIELD_STATUS			=  3 ;
     public static final int FRM_FIELD_PAID_STATUS			=  4 ;
     public static final int FRM_FIELD_PAY_SLIP_DATE			=  5 ;
     public static final int FRM_FIELD_DAY_PRESENT			=  6 ;
     public static final int FRM_FIELD_DAY_PAID_LV                      =  7 ;
     public static final int FRM_FIELD_DAY_ABSENT			=  8 ;
     public static final int FRM_FIELD_DAY_UNPAID_LV			=  9 ;
     public static final int FRM_FIELD_DIVISION			=  10 ;
     public static final int FRM_FIELD_DEPARTMENT			=  11 ;
     public static final int FRM_FIELD_POSITION			=  12 ;
     public static final int FRM_FIELD_SECTION			=  13 ;
     public static final int FRM_FIELD_NOTE                     =  14 ;
     public static final int FRM_FIELD_COMMENC_DATE             =  15 ;
     public static final int FRM_FIELD_PAYMENT_TYPE			=  16 ;
     public static final int FRM_FIELD_BANK_ID                  =  17 ;
     public static final int FRM_FIELD_PAY_SLIP_TYPE			=  18 ;
     public static final int FRM_FIELD_COMP_CODE			=  19 ;
     public static final int FRM_FIELD_DAY_LATE			=  20 ;
     public static final int FRM_FIELD_PROCENTASE_PRESENCE			=  21 ;
     
     
     public static String[] fieldNames = {
            "FRM_FIELD_PAY_SLIP_ID",
            "FRM_FIELD_PERIOD_ID",
            "FRM_FIELD_EMPLOYEE_ID",
            "FRM_FIELD_STATUS",
            "FRM_FIELD_PAID_STATUS",
            "FRM_FIELD_PAY_SLIP_DATE",
            "FRM_FIELD_DAY_PRESENT",
            "FRM_FIELD_DAY_PAID_LV",
            "FRM_FIELD_DAY_ABSENT",
            "FRM_FIELD_PAY_UNPAID_LV",
            "FRM_FIELD_DIVISION",
            "FRM_FIELD_DEPARTMENT",
            "FRM_FIELD_POSITION",
            "FRM_FIELD_SECTION",
            "FRM_FIELD_NOTE",
            "FRM_FIELD_COMMENC_DATE",
            "FRM_FIELD_PAYMENT_TYPE",
            "FRM_FIELD_BANK_ID",
            "FRM_FIELD_PAY_SLIP_TYPE",
            "FRM_FIELD_COMP_CODE",
            "FRM_FIELD_DAY_LATE",
            "FRM_FIELD_PROCENTASE_PRESENCE",
         };
         
         public static int[] fieldTypes = {
           /* TYPE_LONG,
            TYPE_INT + ENTRY_REQUIRED,
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_FLOAT + ENTRY_REQUIRED,
            TYPE_INT + ENTRY_REQUIRED,
            TYPE_FLOAT + ENTRY_REQUIRED,*/
            
                TYPE_LONG ,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_INT,
                TYPE_INT,
                TYPE_DATE,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_DATE,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_INT,
                TYPE_STRING,
                TYPE_FLOAT,
                TYPE_FLOAT,
         };
    
    
    /** Creates a new instance of FrmPaySlip */
    public FrmPaySlip() {
    }
    
     public FrmPaySlip(PaySlip paySlip){
		this.paySlip = paySlip;
    }
     
      public FrmPaySlip(HttpServletRequest request, PaySlip paySlip){
		super(new FrmPaySlip(paySlip), request);
		this.paySlip = paySlip;
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
         return FRM_PAY_SLIP;
    }
     
     public PaySlip getEntityObject(){ return paySlip; }
     
     public void requestEntityObject(PaySlip paySlip) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        paySlip.setPeriodId(getLong(FRM_FIELD_PERIOD_ID));
                        paySlip.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
                        paySlip.setStatus(getInt(FRM_FIELD_STATUS));
                        paySlip.setPaidStatus(getInt(FRM_FIELD_PAID_STATUS));
                        paySlip.setPaySlipDate(getDate(FRM_FIELD_PAY_SLIP_DATE));
                        paySlip.setDayPresent(getDouble(FRM_FIELD_DAY_PRESENT));
                        paySlip.setDayPaidLv(getDouble(FRM_FIELD_DAY_PAID_LV));
                        paySlip.setDayAbsent(getDouble(FRM_FIELD_PERIOD_ID));
                        paySlip.setDayUnpaidLv(getDouble(FRM_FIELD_DAY_UNPAID_LV));
                        paySlip.setDivision(getString(FRM_FIELD_DIVISION));
                        paySlip.setDepartment(getString(FRM_FIELD_DEPARTMENT));
                        paySlip.setPosition(getString(FRM_FIELD_POSITION));
                        paySlip.setSection(getString(FRM_FIELD_SECTION));
                        paySlip.setNote(getString(FRM_FIELD_NOTE));
                        paySlip.setCommencDate(getDate(FRM_FIELD_COMMENC_DATE));
                        paySlip.setPaymentType(getLong(FRM_FIELD_PAYMENT_TYPE));
                        paySlip.setBankId(getLong(FRM_FIELD_BANK_ID));
                        paySlip.setPaymentType(getLong(FRM_FIELD_PAYMENT_TYPE));
                        paySlip.setPaySlipType(getInt(FRM_FIELD_PAY_SLIP_TYPE));
                        paySlip.setCompCode(getString(FRM_FIELD_COMP_CODE));
                        paySlip.setDayLate(getDouble(FRM_FIELD_DAY_LATE));
                        paySlip.setProcentasePresence(getDouble(FRM_FIELD_PROCENTASE_PRESENCE));
                       // paySlip.setProcentasePresence(getDouble(FRM_FIELD_PROCENTASE_PRESENCE));
                  
                      
               	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
