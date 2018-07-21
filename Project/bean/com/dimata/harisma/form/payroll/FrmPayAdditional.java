/*
 * FrmPayAdditional.java
 *
 * Created on November 23, 2007, 2:13 PM
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
public class FrmPayAdditional extends FRMHandler implements I_FRMInterface, I_FRMType{    
    private PayAdditional payAdditional;

    public static final String FRM_PAY_ADDITIONAL =  "FRM_PAY_ADDITIONAL" ;
    
    public static final int FRM_FIELD_SUMM_ADD_ID		=  0 ;
    public static final int FRM_FIELD_PERIOD_ID			=  1 ;
    public static final int FRM_FIELD_SUMMARY_NAME		=  2 ;
    public static final int FRM_FIELD_VALUE			=  3 ;
    
    
    public static String[] fieldNames = {
	"FRM_FIELD_SUMM_ADD_ID",
        "FRM_FIELD_PERIOD_ID",
	"FRM_FIELD_SUMMARY_NAME",
        "FRM_FIELD_VALUE"
      };
    
    public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_LONG,
	TYPE_STRING + ENTRY_REQUIRED,
        TYPE_FLOAT,
      };
    
   
    /** Creates a new instance of FrmPayAdditional */
    public FrmPayAdditional() {
    }
    
    public FrmPayAdditional(PayAdditional payAdditional){
		this.payAdditional = payAdditional;
	}
    
    public FrmPayAdditional(HttpServletRequest request, PayAdditional payAdditional){
		super(new FrmPayAdditional(payAdditional), request);
		this.payAdditional = payAdditional;
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
        return FRM_PAY_ADDITIONAL;
    }
    
     public PayAdditional getEntityObject(){ return payAdditional; }
     
     public void requestEntityObject(PayAdditional payAdditional) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        payAdditional.setPeriodId(getLong(FRM_FIELD_PERIOD_ID));
                        payAdditional.setSummaryName(getString(FRM_FIELD_SUMMARY_NAME));
                        payAdditional.setValue(getLong(FRM_FIELD_VALUE));
                 }catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
