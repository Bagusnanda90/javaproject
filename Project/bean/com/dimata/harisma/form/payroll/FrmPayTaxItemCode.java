/*
 * FrmPayTaxItemCode.java
 *
 * Created on April 4, 2007, 3:12 PM
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
public class FrmPayTaxItemCode extends FRMHandler implements I_FRMInterface, I_FRMType {
    private PayTaxItemCode payTaxItemCode;
    public static final String FRM_PAY_TAX_ITEM_CODE =  "FRM_PAY_TAX_ITEM_CODE" ;
    
    public static final int FRM_FIELD_TAX_ITEM_CD_ID		=  0 ;
    public static final int FRM_FIELD_TAX_ITEM_CODE		=  1 ;
    public static final int FRM_FIELD_TAX_CODE			=  2 ;
    public static final int FRM_FIELD_TAX_ITEM_NAME			=  3 ;
    
    public static String[] fieldNames = {
        "FRM_FIELD_TAX_ITEM_CD_ID",
	"FRM_FIELD_TAX_ITEM_CODE",
        "FRM_TAX_CODE",
	"FRM_TAX_ITEM_NAME",
    };
    
    public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED,
	TYPE_STRING + ENTRY_REQUIRED,
    };
    
    /** Creates a new instance of FrmPayTaxItemCode */
    public FrmPayTaxItemCode() {
    }
    
     public FrmPayTaxItemCode(PayTaxItemCode payTaxItemCode){
		this.payTaxItemCode = payTaxItemCode;
   }
     
     public FrmPayTaxItemCode(HttpServletRequest request, PayTaxItemCode payTaxItemCode){
		super(new FrmPayTaxItemCode(payTaxItemCode), request);
		this.payTaxItemCode = payTaxItemCode;
        
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
         return FRM_PAY_TAX_ITEM_CODE;
    }
    
    public PayTaxItemCode getEntityObject(){ return payTaxItemCode; }
    
    public void requestEntityObject(PayTaxItemCode payTaxItemCode) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        payTaxItemCode.setTaxItemCode(getString(FRM_FIELD_TAX_ITEM_CODE));
                        payTaxItemCode.setTaxCode(getString(FRM_FIELD_TAX_CODE));
                        payTaxItemCode.setTaxItemName(getString(FRM_FIELD_TAX_ITEM_NAME));
                     
              	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
    
    
    
}
