/* 
 * Form Name  	:  FrmPayPeriod.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: Satrya 
 * @version  	: 01 
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dimata 007
 */
public class FrmPayPeriod extends FRMHandler implements I_FRMInterface, I_FRMType {
    	private PayPeriod payPeriod;

	public static final String FRM_NAME_PERIOD		=  "FRM_NAME_PAY_PERIOD" ;

	public static final int FRM_FIELD_PERIOD_ID			=  0 ;
	public static final int FRM_FIELD_PERIOD			=  1 ;
	public static final int FRM_FIELD_START_DATE			=  2 ;
	public static final int FRM_FIELD_END_DATE			=  3 ;
        
        public static final int FRM_FIELD_WORK_DAYS                           =  4;
        public static final int FRM_FIELD_PAY_SLIP_DATE                       =  5;
        public static final int FRM_FIELD_MIN_REG_WAGE                       =  6;
        public static final int FRM_FIELD_PAY_PROCESS                         =  7;
        public static final int FRM_FIELD_PAY_PROC_BY                         =  8;
        public static final int FRM_FIELD_PAY_PROC_DATE                       =  9;
       
        
        public static final int FRM_FIELD_PAY_PROCESS_CLOSE                         =  10;
        public static final int FRM_FIELD_PAY_PROC_BY_CLOSE                       =  11;
        public static final int FRM_FIELD_PAY_PROC_DATE_CLOSE                       =  12;
         public static final int FRM_FIELD_TAX_IS_PAID                         =  13;

	public static String[] fieldNames = {
		"FRM_FIELD_PERIOD_ID",  "FRM_FIELD_PERIOD",
		"FRM_FIELD_START_DATE",  "FRM_FIELD_END_DATE",
                
                "FRM_FIELD_WORK_DAYS",   
                "FRM_FIELD_PAY_SLIP_DATE",
                "FRM_FIELD_MIN_REG_WAGE",
                "FRM_FIELD_PAY_PROCESS",
                "FRM_FIELD_PAY_PROC_BY",
                "FRM_FIELD_PAY_PROC_DATE",
                
                "FRM_FIELD_PAY_PROCESS_CLOSE",
                "FRM_FIELD_PAY_PROC_BY_CLOSE",
                "FRM_FIELD_PAY_PROC_DATE_CLOSE",
                "FRM_FIELD_TAX_IS_PAID"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_DATE + ENTRY_REQUIRED,  TYPE_DATE + ENTRY_REQUIRED,
                
                TYPE_INT,
                TYPE_DATE,
                TYPE_FLOAT,
                TYPE_INT,
                TYPE_STRING,
                TYPE_DATE,
                
                
                TYPE_INT,
                TYPE_STRING,
                TYPE_DATE,
                TYPE_INT
	} ;

	public FrmPayPeriod(){
            
	}
        
	public FrmPayPeriod(PayPeriod payPeriod){
		this.payPeriod = payPeriod;
	}

	public FrmPayPeriod(HttpServletRequest request, PayPeriod payPeriod){
		super(new FrmPayPeriod(payPeriod), request);
		this.payPeriod = payPeriod;
	}

	public String getFormName() { return FRM_NAME_PERIOD; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public PayPeriod getEntityObject(){ return payPeriod; }

	public void requestEntityObject(PayPeriod payPeriod) {
		try{
			this.requestParam();
			payPeriod.setPeriod(getString(FRM_FIELD_PERIOD));
			payPeriod.setStartDate(getDate(FRM_FIELD_START_DATE));
			payPeriod.setEndDate(getDate(FRM_FIELD_END_DATE));
                        
                        payPeriod.setWorkDays(getInt(FRM_FIELD_WORK_DAYS));
                        payPeriod.setPaySlipDate(getDate(FRM_FIELD_PAY_SLIP_DATE));
                         payPeriod.setMinRegWage(getDouble(FRM_FIELD_MIN_REG_WAGE));
                        payPeriod.setPayProsess(getInt(FRM_FIELD_PAY_PROCESS));
                        payPeriod.setPayProcBy(getString(FRM_FIELD_PAY_PROC_BY));
                        payPeriod.setPayProcDate(getDate(FRM_FIELD_PAY_PROC_DATE));
                        
                        
                        payPeriod.setPayProcessClose(getInt(FRM_FIELD_PAY_PROCESS_CLOSE));
                        payPeriod.setPayProcByClose(getString(FRM_FIELD_PAY_PROC_BY_CLOSE));
                        payPeriod.setPayProcDateClose(getDate(FRM_FIELD_PAY_PROC_DATE_CLOSE));
                        payPeriod.setTaxIsPaid(getInt(FRM_FIELD_TAX_IS_PAID));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
