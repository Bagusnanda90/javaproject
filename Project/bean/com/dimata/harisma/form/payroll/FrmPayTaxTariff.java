/*
 * FrmPayTaxTariff.java
 *
 * Created on April 3, 2007, 2:08 PM
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
public class FrmPayTaxTariff extends FRMHandler implements I_FRMInterface, I_FRMType {
     private PayTaxTariff payTaxTariff;
     
     public static final String FRM_PAY_TAX_TARIFF =  "FRM_PAY_TAX_TARIFF" ;
     
        public static final int FRM_FIELD_TAX_TARIFF_ID			=  0 ;
        public static final int FRM_FIELD_TAX_YEAR			=  1 ;
	public static final int FRM_FIELD_LEVEL			=  2 ;
        public static final int FRM_FIELD_SALARY_MIN			=  3 ;
        public static final int FRM_FIELD_SALARY_MAX			=  4 ;
        public static final int FRM_FIELD_TAX_TARIFF			=  5 ;
        
       
         public static String[] fieldNames = {
            "FRM_FIELD_TAX_TARIFF_ID",
            "FRM_FIELD_TAX_YEAR",
            "FRM_FIELD_LEVEL",
            "FRM_FIELD_SALARY_MIN",
            "FRM_FIELD_SALARY_MAX",
            "FRM_FIELD_TAX_TARIFF",
         };
         
          public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_INT + ENTRY_REQUIRED,
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_FLOAT + ENTRY_REQUIRED,
            TYPE_INT + ENTRY_REQUIRED,
            TYPE_FLOAT + ENTRY_REQUIRED,
         };
         
         
    /** Creates a new instance of FrmPayTaxTariff */
    public FrmPayTaxTariff() {
    }
    
     public FrmPayTaxTariff(PayTaxTariff payTaxTariff){
		this.payTaxTariff = payTaxTariff;
    }
    
     public FrmPayTaxTariff(HttpServletRequest request, PayTaxTariff payTaxTariff){
		super(new FrmPayTaxTariff(payTaxTariff), request);
		this.payTaxTariff = payTaxTariff;
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
         return FRM_PAY_TAX_TARIFF;
    }
     public PayTaxTariff getEntityObject(){ return payTaxTariff; }
     
      public void requestEntityObject(PayTaxTariff payTaxTariff) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        payTaxTariff.setTaxYear(getInt(FRM_FIELD_TAX_YEAR));
                        payTaxTariff.setLevel(getString(FRM_FIELD_LEVEL));
                        payTaxTariff.setSalaryMin(getDouble(FRM_FIELD_SALARY_MIN));
                        payTaxTariff.setSalaryMax(getInt(FRM_FIELD_SALARY_MAX));
                        payTaxTariff.setTaxTariff(getDouble(FRM_FIELD_TAX_TARIFF));
                      
               	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
