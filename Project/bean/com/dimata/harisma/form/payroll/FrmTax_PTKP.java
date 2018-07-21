/*
 * FrmTax_PTKP.java
 *
 * Created on August 20, 2007, 2:02 PM
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
public class FrmTax_PTKP extends FRMHandler implements I_FRMInterface, I_FRMType{
    
    private Tax_PTKP tax_PTKP;
    
    public static final String FRM_TAX_PTKP =  "FRM_TAX_PTKP" ;
    public static final int FRM_FIELD_PAY_TAX_PTKP_ID			=  0 ;
    public static final int FRM_FIELD_MARTIAL_ID			=  1 ;
    public static final int FRM_FIELD_SETAHUN_PTKP			=  2 ;
    public static final int FRM_FIELD_SEBULAN_PTKP		=  3 ;
    public static final int FRM_FIELD_REGULASI_ID               = 4;
    
    public static String[] fieldNames = {
	"FRM_FIELD_PAY_TAX_PTKP_ID",
        "FRM_FIELD_MARTIAL_ID",
	"FRM_FIELD_SETAHUN_PTKP",
        "FRM_FIELD_SEBULAN_PTKP",
        "FRM_FIELD_REGULASI_ID"
	} ;
        
     public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG
    } ;
    /** Creates a new instance of FrmTax_PTKP */
    public FrmTax_PTKP() {
    }
    
    public FrmTax_PTKP(Tax_PTKP tax_PTKP){
		this.tax_PTKP = tax_PTKP;
	}
    
     public FrmTax_PTKP(HttpServletRequest request, Tax_PTKP tax_PTKP){
		super(new FrmTax_PTKP(tax_PTKP), request);
		this.tax_PTKP = tax_PTKP;
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
        return FRM_TAX_PTKP;
    }
    
    public Tax_PTKP getEntityObject(){ return tax_PTKP; }

    public void requestEntityObject(Tax_PTKP tax_PTKP) {
            try{
                    this.requestParam();
                    tax_PTKP.setMartialId(getLong(FRM_FIELD_MARTIAL_ID));
                    tax_PTKP.setPtkp_setahun(getInt(FRM_FIELD_SETAHUN_PTKP));
                    tax_PTKP.setPtkp_sebulan(getInt(FRM_FIELD_SEBULAN_PTKP)); 
                    tax_PTKP.setRegulasi_id(getLong(FRM_FIELD_REGULASI_ID));
            }catch(Exception e){
                    System.out.println("Error on requestEntityObject : "+e.toString());
            }
    }
    
}
