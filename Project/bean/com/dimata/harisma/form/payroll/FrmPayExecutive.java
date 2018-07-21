/*
 * FrmPayExecutive.java
 *
 * Created on April 2, 2007, 8:48 AM
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
public class FrmPayExecutive extends FRMHandler implements I_FRMInterface, I_FRMType {
    private PayExecutive payExecutive;

	public static final String FRM_PAY_EXECUTIVES =  "FRM_PAY_EXECUTIVES" ;
        
        public static final int FRM_FIELD_EXECUTIVE_ID			=  0 ;
	public static final int FRM_FIELD_TAX_FORM			=  1 ;
	public static final int FRM_FIELD_EXECUTIVE_NAME			=  2 ;
        
        
         public static String[] fieldNames = {
	"FRM_FIELD_EXECUTIVE_ID",
        "FRM_FIELD_TAX_FORM",
	"FRM_FIELD_EXECUTIVE_NAME",
       };
       
       
       public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
	TYPE_STRING + ENTRY_REQUIRED,
       };
       
      
    
    /** Creates a new instance of FrmPayExecutive */
    public FrmPayExecutive() {
    }
    
    public FrmPayExecutive(PayExecutive payExecutive){
		this.payExecutive = payExecutive;
	}
    
    public FrmPayExecutive(HttpServletRequest request, PayExecutive payExecutive){
		super(new FrmPayExecutive(payExecutive), request);
		this.payExecutive = payExecutive;
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
         return FRM_PAY_EXECUTIVES;
    }
    
    public PayExecutive getEntityObject(){ return payExecutive; }
    
     public void requestEntityObject(PayExecutive payExecutive) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        payExecutive.setTaxForm(getString(FRM_FIELD_TAX_FORM));
                        payExecutive.setExecutiveName(getString(FRM_FIELD_EXECUTIVE_NAME));
               	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
