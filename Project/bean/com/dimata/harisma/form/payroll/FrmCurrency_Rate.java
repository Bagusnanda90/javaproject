/*
 * FrmCurrency_Rate.java
 *
 * Created on April 5, 2007, 10:25 AM
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
public class FrmCurrency_Rate extends FRMHandler implements I_FRMInterface, I_FRMType{
    
        private Currency_Rate currency_Rate;
       
        public static final String FRM_CURRENCY_RATE =  "FRM_CURRENCY_RATE" ;

	public static final int FRM_FIELD_CURR_RATE_ID			=  0 ;
	public static final int FRM_FIELD_CURR_CODE			=  1 ;
	public static final int FRM_FIELD_RATE_BANK			=  2 ;
	public static final int FRM_FIELD_RATE_COMPANY			=  3 ;
	public static final int FRM_FIELD_RATE_TAX			=  4 ;
	public static final int FRM_FIELD_START_DATE		=  5 ;
        public static final int FRM_FIELD_END_DATE		=  6 ;
        public static final int FRM_FIELD_STATUS		=  7 ;
        
        
        public static String[] fieldNames = {
	"FRM_FIELD_CURR_RATE_ID",
        "FRM_FIELD_CURR_CODE",
	"FRM_FIELD_RATE_BANK",
        "FRM_FIELD_RATE_COMPANY",
	"FRM_FIELD_RATE_TAX",
        "FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE",
        "FRM_FIELD_STATUS"
        } ;
        
        
         public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_STRING,
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_FLOAT,
            TYPE_DATE,
            TYPE_DATE,
            TYPE_INT


	} ;
    
   public FrmCurrency_Rate() {
    }
    
    public FrmCurrency_Rate(Currency_Rate currency_Rate){
		this.currency_Rate = currency_Rate;
	}
    
     public FrmCurrency_Rate(HttpServletRequest request, Currency_Rate currency_Rate){
		super(new FrmCurrency_Rate(currency_Rate), request);
		this.currency_Rate = currency_Rate;
        
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
        return FRM_CURRENCY_RATE;
    }
    
    public Currency_Rate getEntityObject(){ return currency_Rate; }
    
    public void requestEntityObject(Currency_Rate currency_Rate) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        currency_Rate.setCurr_code(getString(FRM_FIELD_CURR_CODE));
                        currency_Rate.setRate_bank(getDouble(FRM_FIELD_RATE_BANK));
                        currency_Rate.setRate_company(getDouble(FRM_FIELD_RATE_COMPANY));
                        currency_Rate.setStatus(getInt(FRM_FIELD_STATUS));
                        currency_Rate.setTax_rate(getDouble(FRM_FIELD_RATE_TAX));
                        currency_Rate.setTgl_akhir(getDate(FRM_FIELD_END_DATE));
                        currency_Rate.setTgl_mulai(getDate(FRM_FIELD_START_DATE));
                        
              	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
