/*
 * FrmPaySlipComp.java
 *
 * Created on April 26, 2007, 3:35 PM
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
public class FrmPaySlipComp extends FRMHandler implements I_FRMInterface, I_FRMType {
     private PaySlipComp paySlipComp;
     
     public static final String FRM_PAY_SLIP_COMP =  "FRM_PAY_SLIP_COMP" ;
     
     public static final int FRM_FIELD_PAY_SLIP_COMP_ID			=  0 ;
     public static final int FRM_FIELD_PAY_SLIP_ID			=  1 ;
     public static final int FRM_FIELD_COMP_CODE			=  2 ;
     public static final int FRM_FIELD_COMP_VALUE			=  3 ;
     
     public static String[] fieldNames = {
	"FRM_FIELD_PAY_SLIP_COMP_ID",
        "FRM_FIELD_PAY_SLIP_ID",
	"FRM_FIELD_COMP_CODE",
        "FRM_FIELD_COMP_VALUE"
     };
       
     public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
	TYPE_STRING + ENTRY_REQUIRED,
        TYPE_FLOAT,
     };
     
     

    
    /** Creates a new instance of FrmPaySlipComp */
    public FrmPaySlipComp() {
    }
    
    public FrmPaySlipComp(PaySlipComp paySlipComp){
		this.paySlipComp = paySlipComp;
    }
    
    public FrmPaySlipComp(HttpServletRequest request, PaySlipComp paySlipComp){
		super(new FrmPaySlipComp(paySlipComp), request);
		this.paySlipComp = paySlipComp;
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
        return FRM_PAY_SLIP_COMP;
    }
    
    public PaySlipComp getEntityObject(){ return paySlipComp; }
    
    public void requestEntityObject(PaySlipComp paySlipComp) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        paySlipComp.setPaySlipId(getLong(FRM_FIELD_PAY_SLIP_ID));
                        paySlipComp.setCompCode(getString(FRM_FIELD_COMP_CODE)!=null? (getString(FRM_FIELD_COMP_CODE)).trim():"");                        
                        paySlipComp.setCompValue(getDouble(FRM_FIELD_COMP_VALUE));
               	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
