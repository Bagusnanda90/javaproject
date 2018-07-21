/*
 * FrmTaxType.java
 *
 * Created on March 30, 2007, 6:01 PM
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
public class FrmTaxType extends FRMHandler implements I_FRMInterface, I_FRMType {
    
        private TaxType taxType;
        public static final String FRM_TAX_TYPE =  "FRM_TAX_TYPE" ;

	public static final int FRM_FIELD_TAX_TYPE_ID			=  0 ;
	public static final int FRM_FIELD_TAX_CODE			=  1 ;
	public static final int FRM_FIELD_TAX_NAME			=  2 ;
	
        
        public static String[] fieldNames = {
	"FRM_FIELD_TAX_TYPE_ID",
        "FRM_FIELD_TAX_CODE",
	"FRM_FIELD_TAX_NAME"
        } ;
        
        
       public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING
	} ;
    /** Creates a new instance of FrmTaxType */
    public FrmTaxType() {
    }
    
     public FrmTaxType(TaxType taxType){
		this.taxType = taxType;
     }
    
     public FrmTaxType(HttpServletRequest request, TaxType taxType){
		super(new FrmTaxType(taxType), request);
		this.taxType = taxType;
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
        return FRM_TAX_TYPE;
    }
    
    public TaxType getEntityObject(){ return taxType; }
    
     public void requestEntityObject(TaxType taxType) {
        try{
                this.requestParam();
                //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                taxType.setTaxCode(getString(FRM_FIELD_TAX_CODE));
                taxType.setTaxType(getString(FRM_FIELD_TAX_NAME));
        }catch(Exception e){
                System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
