/*
 * FrmPayTaxVariabel.java
 *
 * Created on August 10, 2007, 1:47 PM
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
public class FrmPayTaxVariabel  extends FRMHandler implements I_FRMInterface, I_FRMType{
    
    private PayTaxVariabel payTaxVariabel;
    public static final String FRM_PAY_TAX_VARIABEL =  "FRM_PAY_TAX_VARIABEL" ;

	public static final int FRM_FIELD_TAX_VARIABEL_ID		=  0 ;
	public static final int FRM_FIELD_LEVEL_CODE			=  1 ;
	public static final int FRM_FIELD_VARIABEL_NAME			=  2 ;
	public static final int FRM_FIELD_VARIABEL_VALUE		=  3 ;
        public static final int FRM_FIELD_JENIS_VARIABEL                = 4 ;
        public static final int FRM_FIELD_PERSEN_VARIABEL               = 5;
	
       public static String[] fieldNames = {
	"FRM_FIELD_TAX_VARIABEL_ID",
        "FRM_FIELD_LEVEL_CODE",
	"FRM_FIELD_VARIABEL_NAME",
        "FRM_FIELD_VARIABEL_VALUE",
        "FRM_FIELD_JENIS_VARIABEL",
        "FRM_FIELD_PERSEN_VARIABEL"
       } ;
       
       public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
	TYPE_INT,
        TYPE_INT,
        TYPE_FLOAT
       } ;
    /** Creates a new instance of FrmPayTaxVariabel */
    public FrmPayTaxVariabel() {
    }
    
    public FrmPayTaxVariabel(PayTaxVariabel payTaxVariabel){
		this.payTaxVariabel = payTaxVariabel;
	}
    
     public FrmPayTaxVariabel(HttpServletRequest request, PayTaxVariabel payTaxVariabel){
		super(new FrmPayTaxVariabel(payTaxVariabel), request);
		this.payTaxVariabel = payTaxVariabel;
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
        return FRM_PAY_TAX_VARIABEL;
    }
    
    public PayTaxVariabel getEntityObject(){ return payTaxVariabel; }
     
     public void requestEntityObject(PayTaxVariabel payTaxVariabel) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        payTaxVariabel.setLevelCode(getString(FRM_FIELD_LEVEL_CODE));
                        payTaxVariabel.setNameVariabel(getString(FRM_FIELD_VARIABEL_NAME));
                        payTaxVariabel.setValueVariabel(getInt(FRM_FIELD_VARIABEL_VALUE));
                        payTaxVariabel.setJenis_Variabel(getInt(FRM_FIELD_JENIS_VARIABEL));
                        payTaxVariabel.setPersen_variabel(getDouble(FRM_FIELD_PERSEN_VARIABEL));
                        
              	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	} 
    
}
