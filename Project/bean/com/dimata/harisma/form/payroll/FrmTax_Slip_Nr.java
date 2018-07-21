/*
 * FrmTax_Slip_Nr.java
 *
 * Created on April 5, 2007, 3:22 PM
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
public class FrmTax_Slip_Nr extends FRMHandler implements I_FRMInterface, I_FRMType{
    
        private Tax_Slip_Nr tax_Slip_Nr;
    
        public static final String FRM_TAX_SLIP_NR =  "FRM_TAX_SLIP_NR" ;

	public static final int FRM_FIELD_TAX_SLIP_NR_ID		=  0 ;
        public static final int FRM_FIELD_PERIOD_ID			=  1 ;
        public static final int FRM_FIELD_TAX_CODE			=  2 ;
        public static final int FRM_FIELD_PREFIX			=  3 ;
        public static final int FRM_FIELD_SUFIX			=  4 ;
        public static final int FRM_FIELD_DIGIT		        =  5 ;
       
        
        
        public static String[] fieldNames = {
	"FRM_FIELD_TAX_SLIP_NR_ID",
        "FRM_FIELD_PERIOD_ID",
	"FRM_FIELD_TAX_CODE",
        "FRM_FIELD_PREFIX",
	"FRM_FIELD_SUFIX",
        "FRM_FIELD_DIGIT"
        } ;
        
        
         public static int[] fieldTypes = {
            TYPE_LONG,
            TYPE_LONG,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_STRING,
            TYPE_INT
        } ;
    /** Creates a new instance of FrmTax_Slip_Nr */
    public FrmTax_Slip_Nr() {
    }
    
    public FrmTax_Slip_Nr(Tax_Slip_Nr tax_Slip_Nr){
		this.tax_Slip_Nr = tax_Slip_Nr;
    }
    
     public FrmTax_Slip_Nr(HttpServletRequest request, Tax_Slip_Nr tax_Slip_Nr){
		super(new FrmTax_Slip_Nr(tax_Slip_Nr), request);
		this.tax_Slip_Nr = tax_Slip_Nr;
        
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
        return FRM_TAX_SLIP_NR;
    }
    
     public Tax_Slip_Nr getEntityObject(){ return tax_Slip_Nr; }
     
     public void requestEntityObject(Tax_Slip_Nr tax_Slip_Nr) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        tax_Slip_Nr.setDigit(getInt(FRM_FIELD_DIGIT));
                        tax_Slip_Nr.setPeriod_id(getLong(FRM_FIELD_PERIOD_ID));
                        tax_Slip_Nr.setPrefix(getString(FRM_FIELD_PREFIX));
                        tax_Slip_Nr.setSufix(getString(FRM_FIELD_SUFIX));
                        tax_Slip_Nr.setTax_code(getString(FRM_FIELD_TAX_CODE));
                        
                        
              	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}

