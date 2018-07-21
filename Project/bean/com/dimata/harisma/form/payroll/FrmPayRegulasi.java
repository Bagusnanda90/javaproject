/*
 * FrmPayRegulasi.java
 *
 * Created on August 29, 2007, 4:51 PM
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
public class FrmPayRegulasi extends FRMHandler implements I_FRMInterface, I_FRMType{
    
    
    private PayRegulasi payRegulasi;
    
    public static final String FRM_PAY_REGULASI =  "FRM_PAY_REGULASI" ;
    public static final int FRM_FIELD_REGULASI_ID			=  0 ;
    public static final int FRM_FIELD_PERIODE			=  1 ;
    public static final int FRM_FIELD_START_DATE			=  2 ;
    public static final int FRM_FIELD_END_DATE		=  3 ;
    public static final int FRM_FIELD_STATUS               = 4;
    
    public static String[] fieldNames = {
	"FRM_FIELD_REGULASI_ID",
        "FRM_FIELD_PERIODE",
	"FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE",
        "FRM_FIELD_STATUS"
	} ;
        
     public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT
    } ;
    /** Creates a new instance of FrmPayRegulasi */
    public FrmPayRegulasi() {
    }
    
    public FrmPayRegulasi(PayRegulasi payRegulasi){
		this.payRegulasi = payRegulasi;
	}
    
     public FrmPayRegulasi(HttpServletRequest request, PayRegulasi payRegulasi){
		super(new FrmPayRegulasi(payRegulasi), request);
		this.payRegulasi = payRegulasi;
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
         return FRM_PAY_REGULASI;
    }
    
    public PayRegulasi getEntityObject(){ return payRegulasi; }

    public void requestEntityObject(PayRegulasi payRegulasi) {
            try{
                    this.requestParam();
                    payRegulasi.setPeriod(getString(FRM_FIELD_PERIODE));
                    payRegulasi.setStartDate(getDate(FRM_FIELD_START_DATE));
                    payRegulasi.setEndDate(getDate(FRM_FIELD_END_DATE));
                    payRegulasi.setStatus(getInt(FRM_FIELD_STATUS));
                    
            }catch(Exception e){
                    System.out.println("Error on requestEntityObject : "+e.toString());
            }
    }
    
}
