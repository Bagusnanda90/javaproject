/*
 * FrmCurrencyType.java
 *
 * Created on March 30, 2007, 1:49 PM
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
public class FrmCurrencyType extends FRMHandler implements I_FRMInterface, I_FRMType {
    private CurrencyType currencyType;
    public static final String FRM_CURRENCY_TYPE =  "FRM_CURRENCY_TYPE" ;

	public static final int FRM_FIELD_CURRENCY_TYPE_ID			=  0 ;
	public static final int FRM_FIELD_CODE			=  1 ;
	public static final int FRM_FIELD_NAME			=  2 ;
	public static final int FRM_FIELD_TAB_INDEX			=  3 ;
	public static final int FRM_FIELD_INCLUDE_INF_PROCESS			=  4 ;
	public static final int FRM_FIELD_DESCRIPTION		=  5 ;
        public static final int FRM_FIELD_FORMAT_CURRENCY		=  6 ;
        
        
        public static String[] fieldNames = {
	"FRM_FIELD_CURRENCY_TYPE_ID",
        "FRM_FIELD_CODE",
	"FRM_FIELD_NAME",
        "FRM_FIELD_TAB_INDEX",
	"FRM_FIELD_INCLUDE_INF_PROCESS",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_FORMAT_CURRENCY"
        } ;
        
        
         public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,
	TYPE_INT,
        TYPE_INT,
	TYPE_STRING,
        TYPE_STRING
        
	} ;
    
    /** Creates a new instance of FrmCurrencyType */
    public FrmCurrencyType() {
    }
    
    public FrmCurrencyType(CurrencyType currencyType){
		this.currencyType = currencyType;
	}
    
     public FrmCurrencyType(HttpServletRequest request, CurrencyType currencyType){
		super(new FrmCurrencyType(currencyType), request);
		this.currencyType = currencyType;
        
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
        return FRM_CURRENCY_TYPE;
    }
     public CurrencyType getEntityObject(){ return currencyType; }
     
      public void requestEntityObject(CurrencyType currencyType) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        currencyType.setCode(getString(FRM_FIELD_CODE));
                        currencyType.setName(getString(FRM_FIELD_NAME));
                        currencyType.setTabIndex(getInt(FRM_FIELD_TAB_INDEX));
                        currencyType.setIncludeInfProcess(getInt(FRM_FIELD_INCLUDE_INF_PROCESS));
                        currencyType.setDescription(getString(FRM_FIELD_DESCRIPTION));
                        currencyType.setFormatCurrency(getString(FRM_FIELD_FORMAT_CURRENCY));
              	}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
