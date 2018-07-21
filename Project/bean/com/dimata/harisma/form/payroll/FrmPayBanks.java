/*
 * FrmPayBanks.java
 *
 * Created on March 31, 2007, 11:32 AM
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
 * @author  autami
 */
public class FrmPayBanks extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private PayBanks payBanks;
    
    public static final String FRM_PAY_BANKS =  "FRM_PAY_BANKS" ;
    
    public static final int FRM_FIELD_BANK_ID		=  0 ;
    public static final int FRM_FIELD_BANK_NAME		=  1 ;
    public static final int FRM_FIELD_BANK_BRANCH	=  2 ;
    public static final int FRM_FIELD_BANK_ADDRESS	=  3 ;
    public static final int FRM_FIELD_BANK_SWIFTCODE	=  4 ;
    public static final int FRM_FIELD_BANK_TELP		=  5 ;
    public static final int FRM_FIELD_BANK_FAX		=  6 ;
   
    public static String[] fieldNames = {
        "FRM_FIELD_BANK_ID",
        "FRM_FIELD_BANK_NAME",
        "FRM_FIELD_BANK_BRANCH",
        "FRM_FIELD_BANK_ADDRESS",
        "FRM_FIELD_BANK_SWIFTCODE",
        "FRM_FIELD_BANK_TELP",
        "FRM_FIELD_BANK_FAX",
    };
	
        
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        
    };
	
        
    /** Creates a new instance of FrmPayBanks */
    public FrmPayBanks() {
    }    
    
    public FrmPayBanks(PayBanks payBanks){
        this.payBanks = payBanks;
    }
    
    public FrmPayBanks(HttpServletRequest request, PayBanks payBanks){
        super(new FrmPayBanks(payBanks), request);
        this.payBanks = payBanks;
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
        return FRM_PAY_BANKS;
    }
    
    public PayBanks getEntityObject(){
        return payBanks;
    }
    
    public void requestEntityObject(PayBanks payBanks){
       try{
           this.requestParam();
           
           payBanks.setBankName(getString(FRM_FIELD_BANK_NAME));
           payBanks.setBankBranch(getString(FRM_FIELD_BANK_BRANCH));
           payBanks.setBankAddress(getString(FRM_FIELD_BANK_ADDRESS));
           payBanks.setSwiftCode(getString(FRM_FIELD_BANK_SWIFTCODE));
           payBanks.setBankTelp(getString(FRM_FIELD_BANK_TELP));
           payBanks.setBankFax(getString(FRM_FIELD_BANK_FAX));
           
       }catch(Exception e){
           System.out.println("Error on requestEntityObject : "+e.toString());
       }
			 
    }
    
}
