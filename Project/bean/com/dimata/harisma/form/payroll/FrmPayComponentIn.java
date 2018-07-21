/*
 * FrmPayComponentIn.java
 *
 * Created on June 13, 2007, 9:39 AM
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
public class FrmPayComponentIn extends FRMHandler implements I_FRMInterface, I_FRMType {
     private PayComponentIn payComponentIn;
     
     public static final String FRM_PAY_COMPONENT_IN =  "FRM_PAY_COMPONENT_IN" ;
     
     public static final int FRM_FIELD_COMP_ID			=  0 ;
     public static final int FRM_FIELD_COMP_CODE			=  1 ;
     public static final int FRM_FIELD_COMP_NAME			=  2 ;
     
     
      public static String[] fieldNames = {
	"FRM_FIELD_COMP_ID",
        "FRM_FIELD_COMP_CODE",
	"FRM_FIELD_COMP_NAME"
      };
      
       public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING,
	TYPE_STRING,
       };
       
       
    
    /** Creates a new instance of FrmPayComponentIn */
    public FrmPayComponentIn() {
    }
    
    public FrmPayComponentIn(PayComponentIn payComponentIn){
		this.payComponentIn = payComponentIn;
    }
    
     public FrmPayComponentIn(HttpServletRequest request, PayComponentIn payComponentIn){
		super(new FrmPayComponentIn(payComponentIn), request);
		this.payComponentIn = payComponentIn;
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
         return FRM_PAY_COMPONENT_IN;
    }
    
    public PayComponentIn getEntityObject(){ return payComponentIn; }
    
    
     public void requestEntityObject(PayComponentIn payComponentIn) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        payComponentIn.setCompCode(getString(FRM_FIELD_COMP_CODE));
                        payComponentIn.setCompName(getString(FRM_FIELD_COMP_NAME));
                }catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
     
     
    
}
